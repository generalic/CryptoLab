package hr.fer.zemris.nos.crypto.symetric;

/**
 * Created by generalic on 05/05/17.
 */
public interface IAESProvider {
    String encrypt(String key, String value);
    String decrypt(String key, String encrypted);
}
