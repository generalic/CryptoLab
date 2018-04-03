package hr.fer.zemris.nos.crypto.test;

import hr.fer.zemris.nos.crypto.asymetric.RSAPrivateKeyProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAKeysGenerator;
import hr.fer.zemris.nos.crypto.asymetric.RSAPublicKeyProvider;
import hr.fer.zemris.nos.crypto.envelope.DigitalEnvelopeGenerator;
import hr.fer.zemris.nos.crypto.envelope.DigitalEnvelopeOpener;
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
public class CreateDigitalStampTest {

    private static final String KEY_EXTENSION = ".asc";
    private static final String PUBLIC_KEY_FILE = "pubkey";
    private static final String PRIVATE_KEY_FILE = "privkey";

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

        PublicKey publicKeyA = publicKeyProviderA.getPublicKey();
        PrivateKey privateKeyA = privateKeyProviderA.getPrivateKey();

        RSAPublicKeyProvider publicKeyProviderB = new RSAPublicKeyProvider(map.get("B").getPublicKeyFile());
        RSAPrivateKeyProvider privateKeyProviderB = new RSAPrivateKeyProvider(map.get("B").getPrivateKeyFile());

        PublicKey publicKeyB = publicKeyProviderB.getPublicKey();
        PrivateKey privateKeyB = privateKeyProviderB.getPrivateKey();

        Path inputFile = Paths.get("text.txt");
        Path digitalEnvelopeFile = Paths.get("digital_envelope.txt");
        Path digitalSignatureFile = Paths.get("digital_signature.txt");

        // stvori digitalnu omotnicu
        DigitalEnvelopeGenerator digitalEnvelopeGenerator =
            new DigitalEnvelopeGenerator(digitalEnvelopeFile);
        digitalEnvelopeGenerator.create(inputFile, publicKeyB);

        // potpisi digitalnu omotnicu s digitalnim potpisom = digitalni pecat
        DigitalSignatureGenerator digitalSignatureGenerator =
            new DigitalSignatureGenerator(digitalSignatureFile);
        digitalSignatureGenerator.create(digitalEnvelopeFile, privateKeyA);

        DigitalSignatureChecker digitalSignatureChecker =
            new DigitalSignatureChecker(digitalSignatureFile);

        // provjeri digitalni pecat
        boolean correct = digitalSignatureChecker.check(publicKeyA);
        System.out.println(correct);

        if (correct) {
            // otvori digitalni pecat
            DigitalEnvelopeOpener digitalEnvelopeOpener =
                new DigitalEnvelopeOpener(digitalEnvelopeFile);
            String data = digitalEnvelopeOpener.open(privateKeyB);

            System.out.println(data);
        }
    }

    private static void generateKeys() {
        Stream.of("A", "B")
            .forEach(s -> {
                try {
                    RSAKeysGenerator generator = new RSAKeysGenerator(
                        PUBLIC_KEY_FILE + s + KEY_EXTENSION,
                        PRIVATE_KEY_FILE + s + KEY_EXTENSION
                    );
                    generator.generate();
                } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
