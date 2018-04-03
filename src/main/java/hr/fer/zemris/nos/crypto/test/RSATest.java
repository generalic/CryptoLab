package hr.fer.zemris.nos.crypto.test;

import hr.fer.zemris.nos.crypto.asymetric.RSAPrivateKeyProvider;
import hr.fer.zemris.nos.crypto.asymetric.IRSAProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAPublicKeyProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAUtil;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by generalic on 03/05/17.
 */
public class RSATest {

    private static final String PUBLIC_KEY_FILE = "pubkey.asc";
    private static final String PRIVATE_KEY_FILE = "privkey.asc";

    public static void main(String[] args) throws IOException {
        String aesKey = "MyCustomAESKey01";
        System.out.println(aesKey);

        RSAPublicKeyProvider rsaPublicKeyProvider = new RSAPublicKeyProvider(PUBLIC_KEY_FILE);
        RSAPrivateKeyProvider rsaPrivateKeyProvider = new RSAPrivateKeyProvider(PRIVATE_KEY_FILE);

        //Encrypt Data using Public Key
        PublicKey publicKey = rsaPublicKeyProvider.getPublicKey();
        PrivateKey privateKey = rsaPrivateKeyProvider.getPrivateKey();

        IRSAProvider rsa = new RSAUtil();

        String en = rsa.encrypt(privateKey, aesKey);

        //Decrypt Data using Private Key
        String decryptedData = rsa.decrypt(publicKey, en);
        System.out.println(decryptedData);
    }
}
