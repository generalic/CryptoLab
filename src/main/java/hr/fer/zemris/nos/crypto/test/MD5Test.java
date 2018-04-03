package hr.fer.zemris.nos.crypto.test;

import hr.fer.zemris.nos.crypto.util.Util;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by generalic on 28/04/17.
 */
public class MD5Test {
    public static void main(String[] args)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String message = "kako je ovo dobra poruka bracoo moj";
        byte[] bytesOfMessage = message.getBytes(StandardCharsets.UTF_8);

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(bytesOfMessage);
        String out = Util.byteToHex(bytes);

        System.out.println(Arrays.equals(bytes, Util.hexToByte(out)));
        System.out.println(out);
    }
}
