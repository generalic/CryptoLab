package hr.fer.zemris.nos.crypto.asymetric;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * Created by generalic on 03/05/17.
 */
public class RSAKeysGenerator {

    public static final String PUBLIC_KEY_FILE = "pubkey.asc";
    public static final String PRIVATE_KEY_FILE = "privkey.asc";

    private static final int KEY_SIZE = 1024;

    private String publicKeyPath;
    private String privateKeyPath;

    public RSAKeysGenerator(String publicKeyPath, String privateKeyPath) {
        this.publicKeyPath = publicKeyPath;
        this.privateKeyPath = privateKeyPath;
    }

    public void generate() throws NoSuchAlgorithmException, InvalidKeySpecException,
        IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(KEY_SIZE); //1024 used for normal securities

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPublicKeySpec =
            keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec rsaPrivateKeySpec =
            keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        //System.out.println("PubKey Modulus : " + rsaPublicKeySpec.getModulus());
        //System.out.println("PubKey Exponent : " + rsaPublicKeySpec.getPublicExponent());
        //System.out.println("PrivKey Modulus : " + rsaPrivateKeySpec.getModulus());
        //System.out.println("PrivKey Exponent : " + rsaPrivateKeySpec.getPrivateExponent());

        saveKey(
            publicKeyPath,
            rsaPublicKeySpec.getModulus(),
            rsaPublicKeySpec.getPublicExponent()
        );
        saveKey(
            privateKeyPath,
            rsaPrivateKeySpec.getModulus(),
            rsaPrivateKeySpec.getPrivateExponent()
        );
    }

    /**
     * Save Files
     *
     * @throws IOException
     */
    private void saveKey(String fileName, BigInteger mod, BigInteger exp) throws IOException {
        try (
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos))
        ) {
            oos.writeObject(mod);
            oos.writeObject(exp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
