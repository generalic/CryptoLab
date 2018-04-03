package hr.fer.zemris.nos.crypto.envelope;

import hr.fer.zemris.nos.crypto.asymetric.IRSAProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAUtil;
import hr.fer.zemris.nos.crypto.rw.Reader;
import hr.fer.zemris.nos.crypto.symetric.AESUtil;
import hr.fer.zemris.nos.crypto.symetric.IAESProvider;
import java.io.IOException;
import java.nio.file.Path;
import java.security.PrivateKey;

/**
 * Created by generalic on 03/05/17.
 */
// TODO: 07/05/17 implement check
public class DigitalEnvelopeOpener {

    private final Reader reader;

    public DigitalEnvelopeOpener(Path envelopeFile) throws IOException {
        this.reader = new Reader(envelopeFile);
    }

    public String open(PrivateKey privateKey) {
        IRSAProvider rsa = new RSAUtil();
        String aesKey = rsa.decrypt(privateKey, reader.getCryptKey());

        IAESProvider aes = new AESUtil();
        return aes.decrypt(aesKey, reader.getEnvelopeData());
    }
}
