package hr.fer.zemris.nos.crypto.symetric;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by generalic on 05/05/17.
 */
public class AESKeyGenerator {
    public static String getKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // for example
            SecretKey secretKey = keyGen.generateKey();
            return new String(secretKey.getEncoded(), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        String key = getKey();
        //String out = AESUtil.encrypt(key, "bok kak si");
        //
        //System.out.println(AESUtil.decrypt(key, out));
    }
}
