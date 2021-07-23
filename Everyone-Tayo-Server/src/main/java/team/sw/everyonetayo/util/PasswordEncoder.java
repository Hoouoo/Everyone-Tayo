package team.sw.everyonetayo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {

    public static String encryptSHA256(String rawPassword) {
        String SHA = null;
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256"); // 이 부분을 SHA-1으로 바꿔도 된다!
            sh.update(rawPassword.getBytes());
            byte[] byteData = sh.digest();
            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return SHA;
    }

    public boolean matches(String rawPassword, String encryptedPassword){
        return encryptedPassword.equals(encryptSHA256(rawPassword));
    }
}
