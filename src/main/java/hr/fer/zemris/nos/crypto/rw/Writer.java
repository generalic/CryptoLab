package hr.fer.zemris.nos.crypto.rw;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by generalic on 05/05/17.
 */
public class Writer {

    private static final String HEADER = "---BEGIN OS2 CRYPTO DATA---";
    private static final String FOOTER = "---END OS2 CRYPTO DATA---";

    private PrintWriter writer;
    private final Path path;

    public Writer(final Path path) {
        this.path = path;
    }

    public void setDescription(String description) {
        writeContent("Description", description);
    }

    public void setMethod(String method) {
        writeContent("Method", method);
    }

    public void setKeyLength(String keyLength) {
        writeContent("Key Length", keyLength);
    }

    public void setKey(String key) {
        writeContent("Key", key);
    }

    public void setModulus(String modulus) {
        writeContent("Modulus", modulus);
    }

    public void setPublicExponent(String publicExponent) {
        writeContent("Public Exponent", publicExponent);
    }

    public void setPrivateExponent(String privateExponent) {
        writeContent("Private Exponent", privateExponent);
    }

    public void setData(String data) {
        writeContent("Data", data);
    }

    public void setSignature(String signature) {
        writeContent("Signature", signature);
    }

    public void setFileName(String fileName) {
        writeContent("File Name", fileName);
    }

    public void setEnvelopeData(String envelopeData) {
        writeContent("Envelope Data", envelopeData);
    }

    public void setCryptKey(String cryptKey) {
        writeContent("Envelope Crypt Key", cryptKey);
    }

    public void open() throws IOException {
        this.writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8));

        writer.println(HEADER);
        writer.println();
    }

    public void close() {
        writer.println(FOOTER);
        writer.close();
    }

    private void writeContent(String contentName, String content) {
        writer.println(contentName + ":");
        writer.println(content);
        writer.println();
    }
}
