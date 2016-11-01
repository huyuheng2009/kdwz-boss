package com.yogapay.boss.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import com.yogapay.boss.service.LgcConfigService;
import com.yogapay.boss.service.MessageInfoService;
import com.yogapay.boss.utils.http.HttpUtils;


/**
 * Created by lei on 2016/4/26.
 */
public class SendMessage {
    public static boolean send(String phone, String content, String lgcNo,String channel,MessageInfoService messageInfoService,LgcConfigService lgcConfigService) throws IOException {
    	if (messageInfoService.msgCount(lgcNo)<2) {
			lgcConfigService.updateByTypeName("MOBILE_CONFIG", "TAKE_SEND_MSG", "0");
			lgcConfigService.updateByTypeName("MOBILE_CONFIG", "SEND_SEND_MSG", "0");
		}
    	System.out.println("==========sendMessage============");
        new Thread(new Sendm(phone, content, lgcNo,channel)).start();;
		return true;
    }
    public static void main(String[] args) throws IOException {
    	//send("13580129946", "您尾号*287的银行卡于2016-05-03 10:54:03消费人民币1000.00元【快递王子】", "0000");
	}
}


class Sendm implements Runnable {
	
	String phone; 
	String content; 
	String lgcNo;
	String channel ;
	
	public Sendm(String phone, String content, String lgcNo,String channel) {
		super();
		this.phone = phone;
		this.content = content;
		this.lgcNo = lgcNo;
		this.channel = channel ;
	}


	public void run() {
		 Date date = new Date();

	        String check = SHA.SHA1Encode1(phone + "yogapayHFT" + content + "PTSD");

	        StringBuffer bufStr = new StringBuffer();
	        bufStr.append("target=" + phone);
	        bufStr.append("&content=" + content);
	        bufStr.append("&operation=S");
	        bufStr.append("&note.businessCode=PTSD");
	        bufStr.append("&note.usage=验证码");
	        bufStr.append("&check=" + check);
	        //bufStr.append("&channel=szkyt");
	        bufStr.append("&channel="+channel);
	        bufStr.append("&lgcNo=" + lgcNo);

	        InputStream resultStr = HttpUtils.getSoapInputStream("http://message.yogapay.com/message/post", bufStr.toString());

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        int h = -1;

	        try {
				while ((h = resultStr.read()) != -1) {
				    baos.write(h);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String responseStr = baos.toString();

	        System.out.println("response===========" + responseStr);
	        Map<String, Object> map = JsonUtil.getMapFromJson(responseStr);
	        System.out.println("--------------------------------" + map.get("rescode") + "---------------------------------------");
	}
}