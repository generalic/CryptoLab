package hr.fer.zemris.nos.crypto.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by generalic on 05/05/17.
 */
public class MD5Util implements IMD5Provider {
    @Override
    public String getMD5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(data);
            return Base64.encodeBase64String(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
