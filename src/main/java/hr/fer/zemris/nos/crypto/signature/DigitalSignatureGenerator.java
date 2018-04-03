package hr.fer.zemris.nos.crypto.signature;

import hr.fer.zemris.nos.crypto.asymetric.IRSAProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAUtil;
import hr.fer.zemris.nos.crypto.hash.IMD5Provider;
import hr.fer.zemris.nos.crypto.hash.MD5Impl;
import hr.fer.zemris.nos.crypto.hash.MD5Util;
import hr.fer.zemris.nos.crypto.rw.Writer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import org.apache.commons.io.IOUtils;

/**
 * Created by generalic on 03/05/17.
 * crypto
 */
public class DigitalSignatureGenerator {

    private final Writer writer;

    public DigitalSignatureGenerator(Path signatureFile)
        throws IOException {
        this.writer = new Writer(signatureFile);
    }

    public void create(Path inputFile, PrivateKey privateKey) throws IOException {
        String input = IOUtils.toString(Files.newInputStream(inputFile), StandardCharsets.UTF_8);

        writer.open();
        writer.setDescription("Signature");
        writer.setFileName(inputFile.toString());
        writer.setMethod("MD5 | RSA");
        writer.setKeyLength("0xA0 | 0x0400");

        IMD5Provider md5 = new MD5Util();
        //IMD5Provider md5 = new MD5Impl();
        String hash = md5.getMD5(input.getBytes(StandardCharsets.UTF_8));

        IRSAProvider rsa = new RSAUtil();
        String signature = rsa.encrypt(privateKey, hash);

        writer.setSignature(signature);
        writer.close();
    }
}
