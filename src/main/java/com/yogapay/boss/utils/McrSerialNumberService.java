package com.yogapay.boss.utils;
public class McrSerialNumberService {
    //兼容一代校验方法
    public static String getVerify(String sNbid) {
        int remaining = 0;
        final int[] wi = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        final String[]  b_15 = new String[]{
            "0","1","2","3",
            "4","5","6","7",
            "8","9","A","B",
            "C","D","E","F"
        };
        if (sNbid.length() == 16) {
            sNbid = sNbid.substring(0, 15);
        }
        int[] ai = new int[16];
        if (sNbid.length() == 15) {
            int sum = 0;
            for (int i = 0; i < 15; i++) {
                //获得其ASCII值
                ai[i] = (byte)sNbid.charAt(i);
            }
            for (int i = 0; i < 15; i++) {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 16;
        }

        return b_15[remaining];
    }

    public static String[] generateSerialNumber(int number,Long[] seq) {
    	String[] array = new String[number];
        String code;
        long next_id;
        long uuid=System.currentTimeMillis();
        for(int i=0;i<number;i++){
            next_id=seq[i];
            code= DESCodec.encode(""+next_id+uuid).substring(0, 16);
            code = code.substring(0, 15)+getVerify(code);
            array[i] = code;
        }
        return array;
    }
    
    public static void main(String[] args){
    	String[] array = generateSerialNumber(3,new Long[]{1l});
    	System.out.println(array[0]+"-"+array[1]+"-"+array[2]);
    }
}
