package hr.fer.zemris.nos.crypto.asymetric;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

/**
 * Created by generalic on 03/05/17.
 */
public class RSAPublicKeyProvider {

    private PublicKey publicKey;

    public RSAPublicKeyProvider(String publicKeyPath) throws IOException {
        this.publicKey = readPublicKeyFromFile(publicKeyPath);
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * read Public Key From File
     *
     * @return PublicKey
     * @throws IOException
     */
    private PublicKey readPublicKeyFromFile(String fileName) throws IOException {
        try (
            FileInputStream fis = new FileInputStream(new File(fileName));
            ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            return keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
