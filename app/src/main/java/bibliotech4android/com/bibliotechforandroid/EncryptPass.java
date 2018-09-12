package bibliotech4android.com.bibliotechforandroid;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EncryptPass {
    final private static char[] hexArray = "0123456789ABCDEF"
            .toCharArray();
    private static Random rnd = new Random();
    private static Integer randomNo = rnd.nextInt(100000);
    private String SALT = randomNo.toString();



    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public String hashPassword(String pass, String salt) {
        try {
            MessageDigest md = MessageDigest
                    .getInstance("SHA-256");
            md.update(salt.getBytes());
            md.update(pass.getBytes());

            byte[] out = md.digest();
            return bytesToHex(out);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getRandomSALT() {
        return SALT;
    }
}
