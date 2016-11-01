package com.yogapay.boss.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public final class IPParser {

	/**
	 * 重定向模式 1
	 */
	private static final byte REDIRECT_MODE_1 = 0x01;
	
	/**
	 * 重定向模式 2
	 */
	private static final byte REDIRECT_MODE_2 = 0x02;
	
	/**
	 * 索引区, 每条索引的长度
	 */
	private static final int INDEX_LEN = 7;
	
	/**
	 * IP 库字符编码
	 */
	private static final String CHARSET = "GBK";

	/**
	 * 缓存 - 线程安全的
	 */
	private static final Map<String, IPLocation> cache = Collections.synchronizedMap(new HashMap<String, IPLocation>());

	/**
	 * IP 地址信息
	 * 
	 * country 并不是一定指的国家, 纯真 IP 库中, country 在国内可能只的是城市
	 * area 也不一定是区域, 有可能比较具体到 xx 机房等
	 * @author donjek
	 */
	public static final class IPLocation {
		public String country;
		public String area;

		private IPLocation(String country, String area) {
			this.country = country;
			this.area = area;
		}

		@Override
		public String toString() {
			return "LOC [country=" + country + ", area=" + area + "]";
		}
	}

	/**
	 * 工具类, 不允许实例化
	 */
	private IPParser() {
	}

	/**
	 * 解析 IP (线程安全的)
	 * 
	 * @param addr
	 * @return
	 * @throws IOException
	 * @author donjek
	 */
	public static IPLocation parse(String addr) throws IOException {

		if (cache.containsKey(addr)) return cache.get(addr);

		RandomAccessFile rf = new RandomAccessFile(IPParser.class.getResource("/qqwry.dat").getFile(), "r");
		FileChannel fc = rf.getChannel();

		// 映射整个IP库文件
		MappedByteBuffer mbb = fc.map(MapMode.READ_ONLY, 0, rf.length());
		// 设置字节序: Little-Endian
		mbb.order(ByteOrder.LITTLE_ENDIAN);

		try {
			int offset = getIpOffset(mbb, addr);
			//System.out.println(offset);
			if (offset < 0) {
				cache.put(addr, null);
				return null;
			}
			IPLocation loc = getLocation(mbb, offset);
			cache.put(addr, loc);
			return loc;
		} finally {
			if (rf != null) rf.close();
			if (fc != null) fc.close();
		}
	}

	/**
	 * 根据偏移量获取地址信息
	 * 
	 * @param mbb
	 * @param offset
	 * @return
	 * @throws IOException
	 */
	private static IPLocation getLocation(MappedByteBuffer mbb, int offset) throws IOException {
		mbb.position(offset + 4);

		String country = null;
		String area = null;

		byte flag = mbb.get();

		if (flag == REDIRECT_MODE_1) {
			int countryOffset = getInt3(mbb);
			mbb.position(countryOffset);
			flag = mbb.get();
			if (flag == REDIRECT_MODE_2) {
				country = getString(mbb, getInt3(mbb));
				mbb.position(countryOffset + 4);
			} else {
				country = getString(mbb, countryOffset);
			}

			area = getArea(mbb, mbb.position());
		} else if (flag == REDIRECT_MODE_2) {
			country = getString(mbb, getInt3(mbb));
			area = getArea(mbb, offset + 8);
		} else {
			country = getString(mbb, mbb.position() - 1);
			area = getArea(mbb, mbb.position());
		}

		String rep = "CZ88.NET";
		if (area != null) {
			area = StringUtils.trimToNull(area.replaceAll(rep, ""));
		}

		if (country != null) {
			country = StringUtils.trimToNull(country.replaceAll(rep, ""));
		}

		if (country == null && area == null) {
			return null;
		}
		return new IPLocation(country, area);
	}

	/**
	 * 计算地址在 ip 库的偏移量(使用二分法搜索索引区)
	 * 
	 * @param mbb
	 * @param ipAddr
	 * @return
	 * @throws IOException
	 */
	private static int getIpOffset(MappedByteBuffer mbb, String ipAddr) throws IOException {
		mbb.position(0);
		int start = mbb.getInt();
		int end = mbb.getInt();
		int maxIndex = end;
		byte[] ip = getIP(ipAddr);

		// 二分法检索
		int mid = 0;
		while (true) {
			mid = getMid(start, end);
			mbb.position(mid);
			int c = compareIP(ip, readIP(mbb));
			
			//System.out.println("m:" + mid + " s:" + start + " e:" + end + " r:" + c);

			if (c < 0) {
				end = mid;
			} else if (c > 0) {
				start = mid;
			} else if (c == 0) {
				return getInt3(mbb);
			}

			// 最后一次检查
			if (end - start == INDEX_LEN) {
				if (mid == end) {
					mid -= INDEX_LEN;
				} else if (mid == start && end == maxIndex) {
					mid += INDEX_LEN;
				}
				
				// 读取记录块偏移 ip, 进行比较, 看是否在范围内
				mbb.position(mid + 4);
				int offset = getInt3(mbb);
				mbb.position(offset);
				c = compareIP(ip, readIP(mbb));
				if (c <= 0) {
					// 在范围内, 认为这个 ip 段是正确的, 返回索引偏移量
					return offset;
				} else {
					// 否则, ip 库可能不存在这个段的 IP, 无法判断
					return -1;
				}
			} // # if
		}
	}

	private static int getMid(int start, int end) {
		int records = (end - start) / INDEX_LEN;
		records >>= 1;
		if (records == 0) {
			records = 1;
		}
		return start + records * INDEX_LEN;
	}

	private static int getInt3(MappedByteBuffer mbb) {
		ByteBuffer buf = ByteBuffer.allocate(4);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		byte[] tmp = new byte[3];
		mbb.get(tmp);
		buf.put(tmp);
		buf.put((byte) 0x00);
		buf.flip();
		int i = buf.getInt() & 0x00ffffff;
		return i;
	}

	private static byte[] readIP(MappedByteBuffer mbb) {
		byte[] ip = new byte[4];
		mbb.get(ip);
		byte t = ip[0];
		ip[0] = ip[3];
		ip[3] = t;
		t = ip[1];
		ip[1] = ip[2];
		ip[2] = t;
		return ip;
	}

	private static String getArea(MappedByteBuffer mbb, int offset) throws IOException {
		mbb.position(offset);
		byte b = mbb.get();
		if (b == REDIRECT_MODE_1 || b == REDIRECT_MODE_2) {
			int areaOffset = getInt3(mbb);
			if (areaOffset == 0) {
				return null;
			} else {
				return getString(mbb, areaOffset);
			}
		} else {
			return getString(mbb, offset);
		}
	}

	/**
	 * 读取 GBK 编码的字符串
	 * 
	 * @param mbb
	 * @param offset
	 * @return
	 * @throws IOException
	 */
	private static String getString(MappedByteBuffer mbb, int offset) throws IOException {
		byte[] buf = new byte[128];
		mbb.position(offset);
		byte b = 0;
		int i = 0;
		while ((b = mbb.get()) != 0) {
			buf[i++] = b;
		}
		return new String(buf, 0, i, CHARSET);
	}

	/**
	 * 取得 ip 的字节表示方式
	 * 支持域名
	 * 
	 * @param ip
	 * @return
	 * @throws IOException
	 */
	private static byte[] getIP(String ip) throws IOException {
		return InetAddress.getByName(ip).getAddress();
	}

	private static int compareIP(byte[] ip, byte[] beginIp) {
		for (int i = 0; i < 4; i++) {
			int r = compareByte(ip[i], beginIp[i]);
			if (r != 0) {
				return r;
			}
		}
		return 0;
	}

	/**
	 * 作为无符号比较
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	private static int compareByte(byte b1, byte b2) {
		if ((b1 & 0xFF) > (b2 & 0xFF)) {
			return 1;
		} else if ((b1 ^ b2) == 0) {
			return 0;
		} else {
			return -1;
		}
	}

	// simple test
	public static void main(String[] args) throws Exception {

		String[] ips = {
				// IP
				"220.181.111.85",
				"115.238.23.241",
				"203.208.46.161",
				"8.8.8.8",
				"8.8.4.4",

				// 域名测试 -- 依赖本地 jvm 查询, 解析(可能跟DNS相关, 会变动)
				"www.goog.com",
				"www.baidu.com",
				"www.qq.com",
				"www.sohu.com",
				"www.163.com",
				"weibo.com",
				"www.renren.com",
				"www.apple.com",

				// 本地, 局域网
				"127.0.0.1",
				"192.168.0.1",

				// 最后一条 IP
				"183.238.158.126"

		};

		for (String ip : ips) {
			String s = String.format("%-20s %-80s", ip, parse(ip));
			System.out.println(s);
		}
		
	}

}
