package hr.fer.zemris.nos.crypto.signature;

import hr.fer.zemris.nos.crypto.asymetric.IRSAProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAUtil;
import hr.fer.zemris.nos.crypto.hash.IMD5Provider;
import hr.fer.zemris.nos.crypto.hash.MD5Util;
import hr.fer.zemris.nos.crypto.rw.Reader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import org.apache.commons.io.IOUtils;

/**
 * Created by generalic on 03/05/17.
 */
// TODO: 07/05/17 implement check
public class DigitalSignatureChecker {

    private final Reader reader;

    public DigitalSignatureChecker(Path digitalSignatureFile) throws IOException {
        this.reader = new Reader(digitalSignatureFile);
    }

    public boolean check(PublicKey publicKey) throws IOException {
        Path inputFile = Paths.get(reader.getFileName());
        String input = IOUtils.toString(Files.newInputStream(inputFile), StandardCharsets.UTF_8);

        IMD5Provider md5 = new MD5Util();
        String hash = md5.getMD5(input.getBytes(StandardCharsets.UTF_8));

        IRSAProvider rsa = new RSAUtil();
        String decryptedHash = rsa.decrypt(publicKey, reader.getSignature());

        return hash.equals(decryptedHash);
    }

    public String getFileName() {
        return reader.getFileName();
    }
}
