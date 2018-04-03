package hr.fer.zemris.nos.crypto.util;

/**
 * Created by generalic on 07/05/17.
 * crypto
 */
public class KeyPair {

    private String publicKeyFile;
    private String privateKeyFile;

    public KeyPair(String publicKeyFile, String privateKeyFile) {
        this.publicKeyFile = publicKeyFile;
        this.privateKeyFile = privateKeyFile;
    }

    public String getPublicKeyFile() {
        return publicKeyFile;
    }

    public String getPrivateKeyFile() {
        return privateKeyFile;
    }
}
