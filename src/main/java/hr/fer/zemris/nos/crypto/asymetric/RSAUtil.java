package hr.fer.zemris.nos.crypto.asymetric;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by generalic on 03/05/17.
 */
public class RSAUtil implements IRSAProvider {
    /**
     * Encrypt Data
     *
     * @throws IOException
     */
    public String encrypt(Key key, String data) {
        try {
            byte[] dataToEncrypt = data.getBytes();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(dataToEncrypt);
            return Base64.encodeBase64String(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Encrypt Data
     *
     * @throws IOException
     */
    public String decrypt(Key key, String encryptedData) {
        try {
            byte[] data = Base64.decodeBase64(encryptedData);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            final byte[] decryptedData = cipher.doFinal(data);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
