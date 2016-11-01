package com.yogapay.boss.utils;

import java.security.Key;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.jpos.iso.ISOUtil;
import org.jpos.security.SMAdapter;
import org.jpos.security.Util;
import org.jpos.security.jceadapter.JCEHandlerException;

/**
 * 
 * @author leo
 * 
 */
public class JCEHandler {

	static final String ALG_DES = "DES";
	static final String ALG_TRIPLE_DES = "DESede";
	static final String DES_MODE_ECB = "ECB";
	static final String DES_MODE_CBC = "CBC";
	static final String DES_NO_PADDING = "NoPadding";
	/**
	 * The JCE provider
	 */
	static Provider provider = null;

	static {
		try {
			provider = (Provider) Class.forName(
					"com.sun.crypto.provider.SunJCE").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Encrypts (wraps) a clear DES Key, it also sets odd parity before
	 * encryption
	 * 
	 * @param keyLength
	 *            bit length (key size) of the clear DES key (LENGTH_DES,
	 *            LENGTH_DES3_2KEY or LENGTH_DES3_3KEY)
	 * @param clearDESKey
	 *            DES/Triple-DES key whose format is "RAW" (for a DESede with 2
	 *            Keys, keyLength = 128 bits, while DESede key with 3 keys
	 *            keyLength = 192 bits)
	 * @param encryptingKey
	 *            can be a key of any type (RSA, DES, DESede...)
	 * @return encrypted DES key
	 * @throws org.jpos.security.jceadapter.JCEHandlerException
	 */
	public byte[] encryptDESKey(short keyLength, Key clearDESKey,
			Key encryptingKey) throws JCEHandlerException {
		byte[] encryptedDESKey = null;
		byte[] clearKeyBytes = extractDESKeyMaterial(keyLength, clearDESKey);
		// enforce correct (odd) parity before encrypting the key
		Util.adjustDESParity(clearKeyBytes);
		encryptedDESKey = doCryptStuff(clearKeyBytes, encryptingKey,
				Cipher.ENCRYPT_MODE);
		return encryptedDESKey;
	}

	/**
	 * Extracts the DES/DESede key material
	 * 
	 * @param keyLength
	 *            bit length (key size) of the DES key. (LENGTH_DES,
	 *            LENGTH_DES3_2KEY or LENGTH_DES3_3KEY)
	 * @param clearDESKey
	 *            DES/Triple-DES key whose format is "RAW"
	 * @return encoded key material
	 * @throws org.jpos.security.jceadapter.JCEHandlerException
	 */
	protected byte[] extractDESKeyMaterial(short keyLength, Key clearDESKey)
			throws JCEHandlerException {
		byte[] clearKeyBytes = null;
		String keyAlg = clearDESKey.getAlgorithm();
		String keyFormat = clearDESKey.getFormat();
		if (keyFormat.compareTo("RAW") != 0) {
			throw new JCEHandlerException(
					"Unsupported DES key encoding format: " + keyFormat);
		}
		if (!keyAlg.startsWith(ALG_DES)) {
			throw new JCEHandlerException("Unsupported key algorithm: "
					+ keyAlg);
		}
		clearKeyBytes = clearDESKey.getEncoded();
		clearKeyBytes = ISOUtil.trim(clearKeyBytes, getBytesLength(keyLength));
		return clearKeyBytes;
	}

	/**
	 * Forms the clear DES key given its "RAW" encoded bytes Does the inverse of
	 * extractDESKeyMaterial
	 * 
	 * @param keyLength
	 *            bit length (key size) of the DES key. (LENGTH_DES,
	 *            LENGTH_DES3_2KEY or LENGTH_DES3_3KEY)
	 * @param clearKeyBytes
	 *            the RAW DES/Triple-DES key
	 * @return clear key
	 * @throws org.jpos.security.jceadapter.JCEHandlerException
	 */
	public static Key formDESKey(short keyLength, byte[] clearKeyBytes)
			throws JCEHandlerException {
		Key key = null;
		switch (keyLength) {
		case SMAdapter.LENGTH_DES: {
			key = new SecretKeySpec(clearKeyBytes, ALG_DES);
		}
			break;
		case SMAdapter.LENGTH_DES3_2KEY: {
			// make it 3 components to work with JCE
			clearKeyBytes = ISOUtil.concat(clearKeyBytes, 0,
					getBytesLength(SMAdapter.LENGTH_DES3_2KEY), clearKeyBytes,
					0, getBytesLength(SMAdapter.LENGTH_DES));
		}
		case SMAdapter.LENGTH_DES3_3KEY: {
			key = new SecretKeySpec(clearKeyBytes, ALG_TRIPLE_DES);
		}
		}
		if (key == null)
			throw new JCEHandlerException("Unsupported DES key length: "
					+ keyLength + " bits");
		return key;
	}

	public static String encryptData(String data, String key) {
		return ISOUtil.hexString(encryptData(ISOUtil.hex2byte(data),
				ISOUtil.hex2byte(key)));
	}

	/**
	 * Encrypts data
	 * 
	 * @param data
	 * @param key
	 * @return encrypted data
	 * @exception org.jpos.security.jceadapter.JCEHandlerException
	 */
	public static byte[] encryptData(byte[] data, byte[] key) {
		int len = key.length * 8;
		Key keay;
		byte[] encryptedData = null;
		try {
			keay = formDESKey(Short.parseShort(len + ""), key);
			encryptedData = doCryptStuff(data, keay, Cipher.ENCRYPT_MODE);
		} catch (JCEHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedData;
	}

	static public String decryptData(String encryptedData, String key) {
		return ISOUtil.hexString(decryptData(ISOUtil.hex2byte(encryptedData),
				ISOUtil.hex2byte(key)));

	}

	/**
	 * Decrypts data
	 * 
	 * @param encryptedData
	 * @param key
	 * @return clear data
	 * @exception org.jpos.security.jceadapter.JCEHandlerException
	 */
	static public byte[] decryptData(byte[] encryptedData, byte[] key) {
		byte[] clearData = null;
		try {
			int len = key.length * 8;
			Key keay = formDESKey(Short.parseShort(len + ""), key);
			clearData = doCryptStuff(encryptedData, keay, Cipher.DECRYPT_MODE);
		} catch (JCEHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clearData;
	}

	/**
	 * Performs cryptographic DES operations (encryption/decryption) in ECB mode
	 * using JCE Cipher
	 * 
	 * @param data
	 * @param key
	 * @param cipherMode
	 *            Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
	 * @return result of the cryptographic operations
	 * @throws org.jpos.security.jceadapter.JCEHandlerException
	 */
	static byte[] doCryptStuff(byte[] data, Key key, int cipherMode)
			throws JCEHandlerException {
		return doCryptStuff(data, key, cipherMode, DES_MODE_ECB, null);
	}

	/**
	 * performs cryptographic operations (encryption/decryption) using JCE
	 * Cipher
	 * 
	 * @param data
	 * @param key
	 * @param CipherMode
	 *            Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
	 * @return result of the cryptographic operations
	 * @throws org.jpos.security.jceadapter.JCEHandlerException
	 */
	static byte[] doCryptStuff(byte[] data, Key key, int CipherMode,
			String desMode, byte[] iv) throws JCEHandlerException {
		byte[] result;
		String transformation;
		if (key.getAlgorithm().startsWith(ALG_DES)) {
			transformation = key.getAlgorithm() + "/" + desMode + "/"
					+ DES_NO_PADDING;
		} else {
			transformation = key.getAlgorithm();
		}
		AlgorithmParameterSpec aps = null;
		try {
			Cipher c1 = Cipher.getInstance(transformation, provider.getName());
			if (DES_MODE_CBC.equals(desMode))
				aps = new IvParameterSpec(iv);
			c1.init(CipherMode, key, aps);
			result = c1.doFinal(data);
		} catch (Exception e) {
			throw new JCEHandlerException(e);
		}
		return result;
	}

	/**
	 * Calculates the length of key in bytes
	 * 
	 * @param keyLength
	 *            bit length (key size) of the DES key. (LENGTH_DES,
	 *            LENGTH_DES3_2KEY or LENGTH_DES3_3KEY)
	 * @return keyLength/8
	 * @throws org.jpos.security.jceadapter.JCEHandlerException
	 *             if unknown key length
	 */
	static int getBytesLength(short keyLength) throws JCEHandlerException {
		int bytesLength = 0;
		switch (keyLength) {
		case 64:
			bytesLength = 8;
			break;
		case 128:
			bytesLength = 16;
			break;
		case 192:
			bytesLength = 24;
			break;
		default:
			throw new JCEHandlerException("Unsupported key length: "
					+ keyLength + " bits");
		}
		return bytesLength;
	}

	public static void main(String[] args) throws JCEHandlerException {
		String hex = "4333364537353442";
		String key = "46B99DD30D9EE33808B3A4A13E6B4525";

		String result = "2ACF022EEC7B3E18";
		byte[] s = JCEHandler.encryptData(ISOUtil.hex2byte(hex),
				ISOUtil.hex2byte(key));

		System.out.println(ISOUtil.hexString(s));
		s = JCEHandler.decryptData(ISOUtil.hex2byte(result),
				ISOUtil.hex2byte(key));
		System.out.println(ISOUtil.hexString(s));

	}
	public static byte[] genECBMAC(byte[] data, String key) {
        byte[] clearTak = ISOUtil.hex2byte(key);
        data = ISOUtil.concat(data, new byte[8]);
        int len = data.length - data.length % 8;
        if (data.length != len) {
            data = ISOUtil.trim(data, len);
        } else {
            data = ISOUtil.trim(data, len - 8);
        }
        byte[] mac = new byte[8];
        byte[] tmp = new byte[8];
        byte[] block = new byte[8];
        for (int i = 0; i < data.length / 8; i++) {
            System.arraycopy(data, i * 8, tmp, 0, 8);
            block = ISOUtil.xor(block, tmp);
        }
        block = ISOUtil.hexString(block).getBytes();
        System.arraycopy(block, 0, tmp, 0, 8);
        mac = JCEHandler.encryptData(tmp, clearTak);
        System.arraycopy(block, 8, tmp, 0, 8);
        tmp = ISOUtil.xor(mac, tmp);
        mac = JCEHandler.encryptData(tmp, clearTak);
        block = ISOUtil.hexString(mac).getBytes();
        System.arraycopy(block, 0, mac, 0, 8);

        return mac;
    }
}
