package util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    private static String md5(String src){
        return DigestUtils.md2Hex(src);
    }

    private static final String salt = "zz_flash";

    //第一层MD5
    private static String inputPassFormPass(String inputPass){
        String str = salt.charAt(0)  + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    //第二层
    private static String formPassToDBPass(String formPass, String salt){
        String str = salt.charAt(0)  + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String src, String salt){
        String s1 = inputPassFormPass(src);
        return formPassToDBPass(s1,salt);
    }
}
