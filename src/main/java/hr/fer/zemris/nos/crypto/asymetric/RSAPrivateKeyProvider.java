package hr.fer.zemris.nos.crypto.asymetric;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;

/**
 * Created by generalic on 03/05/17.
 */
public class RSAPrivateKeyProvider {

    private PrivateKey privateKey;

    public RSAPrivateKeyProvider(String privateKeyPath) throws IOException {
        this.privateKey = readPrivateKeyFromFile(privateKeyPath);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * read Public Key From File
     *
     * @throws IOException
     */
    private PrivateKey readPrivateKeyFromFile(String fileName) throws IOException {
        try (
            FileInputStream fis = new FileInputStream(new File(fileName));
            ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();

            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(rsaPrivateKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
