package hr.fer.zemris.nos.crypto.asymetric;

import java.security.Key;

/**
 * Created by generalic on 05/05/17.
 */
public interface IRSAProvider {
    String encrypt(Key key, String data);

    String decrypt(Key key, String encryptedData);
}
