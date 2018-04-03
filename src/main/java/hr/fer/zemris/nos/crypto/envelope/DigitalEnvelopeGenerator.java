package hr.fer.zemris.nos.crypto.envelope;

import hr.fer.zemris.nos.crypto.asymetric.IRSAProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAUtil;
import hr.fer.zemris.nos.crypto.rw.Writer;
import hr.fer.zemris.nos.crypto.symetric.AESUtil;
import hr.fer.zemris.nos.crypto.symetric.IAESProvider;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PublicKey;
import org.apache.commons.io.IOUtils;

/**
 * Created by generalic on 03/05/17.
 */
public class DigitalEnvelopeGenerator {

    private final Writer writer;

    public DigitalEnvelopeGenerator(Path envelopeFile)
        throws IOException {
        this.writer = new Writer(envelopeFile);
    }

    public void create(Path inputFile, PublicKey publicKey) throws IOException {
        String input = IOUtils.toString(Files.newInputStream(inputFile), StandardCharsets.UTF_8);

        writer.open();
        writer.setDescription("Envelope");
        writer.setFileName(inputFile.toString());
        writer.setMethod("AES | RSA");
        writer.setKeyLength("0x0100 | 0x0400");

        //String key = AESKeyGenerator.getKey();
        String key = "MyCustomAESKey01"; // 128 bit key

        IAESProvider aes = new AESUtil();
        String encryptedText = aes.encrypt(key, input);

        IRSAProvider rsa = new RSAUtil();
        String encryptedKey = rsa.encrypt(publicKey, key);

        writer.setEnvelopeData(encryptedText);
        writer.setCryptKey(encryptedKey);
        writer.close();
    }
}
