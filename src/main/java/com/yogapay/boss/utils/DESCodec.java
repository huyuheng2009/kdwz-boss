package com.yogapay.boss.utils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.jpos.iso.ISOUtil;
/**
 *
 * @author Administrator
 */
public class DESCodec {
    public static String  decode(String target){
    	String result = null;
    	Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
    	
    	try {
    		result = new String(cipher.doFinal(ISOUtil.hex2byte(target)));
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    public static String encode(String target){
    	String result = null;
    	Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
    	try {
			result = ISOUtil.hexString(cipher.doFinal(target.getBytes()));
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    public static Cipher getCipher(int mode) {
    	DESKeySpec keySpec;
        Cipher cipher = null;
		try {
	    	keySpec = new DESKeySpec(getPassword());
			cipher = Cipher.getInstance("DES");
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        cipher.init(mode, keyFactory.generateSecret(keySpec));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return cipher;
    }

    //密码长度必须是8的倍数
    public static byte[] getPassword() {
    	byte[] psw = null;
    	try {
    		psw = "passfordesnishiwode&0082".getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
    	return psw;
    }

    public static void main(String[]args)
    {
        String strmi=encode("测试信息");
        System.out.println(strmi);
        System.out.println(decode(strmi));
    }
}