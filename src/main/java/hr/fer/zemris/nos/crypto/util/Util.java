package hr.fer.zemris.nos.crypto.util;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * This class contains util methods used while digesting and de/encrypting files.
 *
 * @author Boris
 * @version 1.0
 */
public final class Util {

    /**
     * Returns string representation of a byte array.
     *
     * @param bytes
     * bytes to represent
     * @return string representation
     */
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private Util() {
    }

    /**
     * Returns byte representation of hex-encoded {@link String}.<br>
     * <b>NOTE: expects 128bit input</b>.
     *
     * @param s string to decode
     * @return byte representation
     */
    public static byte[] hexToByte(final String s) {
        final int len = s.length();
        final byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                .digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String byteToHex(final byte[] bytes) {
        final char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            final int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }

    /**
     * Reads a line from standard input.
     *
     * @return read line
     */
    @SuppressWarnings("resource")
    public static String readLine() {
        String line = "";
        final Scanner sc = new Scanner(new InputStreamReader(new BufferedInputStream(
            System.in), StandardCharsets.UTF_8));
        line = sc.nextLine();
        return line;
    }
}
