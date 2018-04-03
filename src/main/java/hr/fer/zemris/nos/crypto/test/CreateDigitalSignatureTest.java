package hr.fer.zemris.nos.crypto.test;

import hr.fer.zemris.nos.crypto.asymetric.RSAPrivateKeyProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAPublicKeyProvider;
import hr.fer.zemris.nos.crypto.signature.DigitalSignatureChecker;
import hr.fer.zemris.nos.crypto.signature.DigitalSignatureGenerator;
import hr.fer.zemris.nos.crypto.util.KeyPair;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by generalic on 07/05/17.
 * crypto
 */
public class CreateDigitalSignatureTest {

    private static final String PUBLIC_KEY_FILE = "pubkey";
    private static final String PRIVATE_KEY_FILE = "privkey";
    private static final String KEY_EXTENSION = ".asc";

    public static void main(String[] args)
        throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        Map<String, KeyPair> map = new HashMap<>();
        Stream.of("A", "B")
            .forEach(s -> {
                map.put(s, new KeyPair(
                    PUBLIC_KEY_FILE + s + KEY_EXTENSION,
                    PRIVATE_KEY_FILE + s + KEY_EXTENSION
                ));
            });

        RSAPublicKeyProvider publicKeyProviderA = new RSAPublicKeyProvider(map.get("A").getPublicKeyFile());
        RSAPrivateKeyProvider privateKeyProviderA = new RSAPrivateKeyProvider(map.get("A").getPrivateKeyFile());

        PublicKey publicKey = publicKeyProviderA.getPublicKey();
        PrivateKey privateKey = privateKeyProviderA.getPrivateKey();

        Path inputFile = Paths.get("text.txt");
        Path digitalSignatureFile = Paths.get("digital_signature.txt");

        DigitalSignatureGenerator digitalSignatureGenerator =
            new DigitalSignatureGenerator(digitalSignatureFile);

        digitalSignatureGenerator.create(inputFile, privateKey);

        DigitalSignatureChecker digitalSignatureChecker =
            new DigitalSignatureChecker(digitalSignatureFile);

        boolean correct = digitalSignatureChecker.check(publicKey);

        System.out.println(correct);
    }
}
