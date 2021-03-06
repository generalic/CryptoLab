package hr.fer.zemris.nos.crypto.test;

import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by generalic on 27/04/17.
 */
public class AESTest {
    public static String encrypt(String key, String value) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            IvParameterSpec iv = new IvParameterSpec(secretKeySpec.getEncoded());

            int length = secretKeySpec.getEncoded().length;
            System.out.println(length);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String encrypted) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            IvParameterSpec iv = new IvParameterSpec(secretKeySpec.getEncoded());

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        String key = "MyCustomAESKey01"; // 128 bit key
        System.out.println(key.toCharArray().length);
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);

        String encryptedText = encrypt(key, "kako je ovo dobra poruka bracoo moj");
        String decryptedText = decrypt(key, encryptedText);

        System.out.println(encryptedText.getBytes(StandardCharsets.UTF_8).length);

        System.out.println(encryptedText);
        System.out.println(decryptedText);
    }
}
