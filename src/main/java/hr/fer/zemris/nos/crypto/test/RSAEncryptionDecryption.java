package hr.fer.zemris.nos.crypto.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import java.util.Arrays;
import javax.crypto.Cipher;

/**
 * @author Anuj
 *         Blog www.goldenpackagebyanuj.blogspot.com
 *         RSA - Encrypt Data using Public Key
 *         RSA - Descypt Data using Private Key
 */
public class RSAEncryptionDecryption {

    private static final String PUBLIC_KEY_FILE = "pubkey.asc";
    private static final String PRIVATE_KEY_FILE = "privkey.asc";

    public static void main(String[] args) throws IOException {

        try {
            System.out.println("-------GENRATE PUBLIC and PRIVATE KEY-------------");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); //1024 used for normal securities
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println("Public Key - " + publicKey);
            System.out.println("Private Key - " + privateKey);

            //Pullingout parameters which makes up Key
            System.out.println("\n------- PULLING OUT PARAMETERS WHICH MAKES KEYPAIR----------\n");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPubKeySpec =
                keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAPrivateKeySpec rsaPrivKeySpec =
                keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            System.out.println("PubKey Modulus : " + rsaPubKeySpec.getModulus());
            System.out.println("PubKey Exponent : " + rsaPubKeySpec.getPublicExponent());
            System.out.println("PrivKey Modulus : " + rsaPrivKeySpec.getModulus());
            System.out.println("PrivKey Exponent : " + rsaPrivKeySpec.getPrivateExponent());

            //Share public key with other so they can encrypt data and decrypt thoses using private key(Don't share with Other)
            System.out.println("\n--------SAVING PUBLIC KEY AND PRIVATE KEY TO FILES-------\n");
            RSAEncryptionDecryption rsaObj = new RSAEncryptionDecryption();
            rsaObj.saveKeys(PUBLIC_KEY_FILE, rsaPubKeySpec.getModulus(),
                rsaPubKeySpec.getPublicExponent());
            rsaObj.saveKeys(PRIVATE_KEY_FILE, rsaPrivKeySpec.getModulus(),
                rsaPrivKeySpec.getPrivateExponent());

            String aesKey = "MyCustomAESKey01";

            //Encrypt Data using Public Key
            byte[] encryptedData = rsaObj.encryptData(aesKey);

            //Decrypt Data using Private Key
            rsaObj.decryptData(encryptedData);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save Files
     *
     * @throws IOException
     */
    private void saveKeys(String fileName, BigInteger mod, BigInteger exp) throws IOException {
        try (
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));
        ) {
            System.out.println("Generating " + fileName + "...");

            oos.writeObject(mod);
            oos.writeObject(exp);

            System.out.println(fileName + " generated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypt Data
     *
     * @throws IOException
     */
    private byte[] encryptData(String data) throws IOException {
        System.out.println("\n----------------ENCRYPTION STARTED------------");

        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;
        try {
            PublicKey pubKey = readPublicKeyFromFile(PUBLIC_KEY_FILE);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            encryptedData = cipher.doFinal(dataToEncrypt);
            System.out.println("Encryted Data: " + Arrays.toString(encryptedData));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------ENCRYPTION COMPLETED------------");
        return encryptedData;
    }

    /**
     * Encrypt Data
     *
     * @throws IOException
     */
    private void decryptData(byte[] data) throws IOException {
        System.out.println("\n----------------DECRYPTION STARTED------------");
        try {
            PrivateKey privateKey = readPrivateKeyFromFile(PRIVATE_KEY_FILE);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            final byte[] decryptedData = cipher.doFinal(data);
            System.out.println("Decrypted Data: " + new String(decryptedData));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------DECRYPTION COMPLETED------------");
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

            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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