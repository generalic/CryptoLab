package hr.fer.zemris.nos.crypto.rw;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by generalic on 05/05/17.
 */
public class Reader {

    private static final String HEADER = "---BEGIN OS2 CRYPTO DATA---";
    private static final String FOOTER = "---END OS2 CRYPTO DATA---";

    private final List<String> lines;

    public Reader(final Path path) throws IOException {
        this.lines = Files.readAllLines(path, StandardCharsets.UTF_8)
            .stream()
            .filter(l -> !l.isEmpty())
            .filter(l -> !l.equals(HEADER))
            .filter(l -> !l.equals(FOOTER))
            .collect(Collectors.toList());
    }

    public String getDescription() {
        return getContent("Description");
    }

    public String getMethod() {
        return getContent("Method");
    }

    public String getKeyLength() {
        return getContent("Key Length");
    }

    public String getKey() {
        return getContent("Key");
    }

    public String getModulus() {
        return getContent("Modulus");
    }

    public String getPublicExponent() {
        return getContent("Public Exponent");
    }

    public String getPrivateExponent() {
        return getContent("Private Exponent");
    }

    public String getData() {
        return getContent("Data");
    }

    public String getSignature() {
        return getContent("Signature");
    }

    public String getFileName() {
        return getContent("File Name");
    }

    public String getEnvelopeData() {
        return getContent("Envelope Data");
    }

    public String getCryptKey() {
        return getContent("Envelope Crypt Key");
    }

    private String getContent(String contentName) {
        int index = lines.indexOf(contentName + ":");
        return lines.get(index + 1);
    }
}
