package hr.fer.zemris.nos.crypto.hash;

import hr.fer.zemris.nos.crypto.util.Util;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;
import org.apache.commons.codec.binary.Base64;

public class MD5Impl implements IMD5Provider {

    private static final int A0 = 0x67452301;
    private static final int B0 = (int) 0xEFCDAB89L;
    private static final int C0 = (int) 0x98BADCFEL;
    private static final int D0 = 0x10325476;

    //s specifies the per-round shift amounts
    //Use binary integer part of the sines of integers (Radians) as constants:
    //s[ 0..15] := { 7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22 }
    //s[16..31] := { 5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20 }
    //s[32..47] := { 4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23 }
    //s[48..63] := { 6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21 }
    //private static final int[] SHIFT_AMOUNTS = {
    //    7, 12, 17, 22,
    //    5, 9, 14, 20,
    //    4, 11, 16, 23,
    //    6, 10, 15, 21
    //};

    private static final int[] SHIFT_AMOUNTS = {
        7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
        5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
        4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
        6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
    };

    //(Or just use the following precomputed table):
    //K[ 0.. 3] := { 0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee }
    //K[ 4.. 7] := { 0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501 }
    //K[ 8..11] := { 0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be }
    //K[12..15] := { 0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821 }
    //K[16..19] := { 0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa }
    //K[20..23] := { 0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8 }
    //K[24..27] := { 0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed }
    //K[28..31] := { 0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a }
    //K[32..35] := { 0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c }
    //K[36..39] := { 0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70 }
    //K[40..43] := { 0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05 }
    //K[44..47] := { 0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665 }
    //K[48..51] := { 0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039 }
    //K[52..55] := { 0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1 }
    //K[56..59] := { 0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1 }
    //K[60..63] := { 0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391 }
    private static final int[] K = IntStream.range(0, 64)
        .map(i -> (int) (long) ((1L << 32) * Math.abs(Math.sin(i + 1))))
        .toArray();

    private static byte[] computeMD5(byte[] message) {
        int messageLenBytes = message.length;
        int numBlocks = ((messageLenBytes + 8) >>> 6) + 1;
        int totalLen = numBlocks << 6;
        byte[] paddingBytes = new byte[totalLen - messageLenBytes];
        paddingBytes[0] = (byte) 0x80;

        long messageLenBits = (long) messageLenBytes << 3;
        for (int i = 0; i < 8; i++) {
            paddingBytes[paddingBytes.length - 8 + i] = (byte) messageLenBits;
            messageLenBits >>>= 8;
        }

        int a = A0;
        int b = B0;
        int c = C0;
        int d = D0;
        int[] M = new int[16];
        for (int i = 0; i < numBlocks; i++) {
            int index = i << 6;
            for (int j = 0; j < 64; j++, index++) {
                M[j >>> 2] = ((int) ((index < messageLenBytes) ? message[index]
                    : paddingBytes[index - messageLenBytes]) << 24) | (M[j >>> 2] >>> 8);
            }
            int originalA = a;
            int originalB = b;
            int originalC = c;
            int originalD = d;
            for (int j = 0; j < 64; j++) {
                int div16 = j >>> 4;
                int f = 0;
                int bufferIndex = j;
                switch (div16) {
                    case 0:
                        f = (b & c) | (~b & d);
                        break;

                    case 1:
                        f = (b & d) | (c & ~d);
                        // & (number - 1) is the same as: n % number, mod function
                        bufferIndex = (bufferIndex * 5 + 1) & 0x0F;
                        break;

                    case 2:
                        f = b ^ c ^ d;
                        bufferIndex = (bufferIndex * 3 + 5) & 0x0F;
                        break;

                    case 3:
                        f = c ^ (b | ~d);
                        bufferIndex = (bufferIndex * 7) & 0x0F;
                        break;
                }
                int temp = b + Integer.rotateLeft(a + f + M[bufferIndex] + K[j], SHIFT_AMOUNTS[j]);
                a = d;
                d = c;
                c = b;
                b = temp;
            }

            a += originalA;
            b += originalB;
            c += originalC;
            d += originalD;
        }

        byte[] md5 = new byte[16];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int n = (i == 0) ? a : ((i == 1) ? b : ((i == 2) ? c : d));
            for (int j = 0; j < 4; j++) {
                md5[count++] = (byte) n;
                n >>>= 8;
            }
        }
        return md5;
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            sb.append(String.format("%02X", b[i] & 0xFF));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //String[] testStrings = {
        //    "", "a", "abc", "message digest", "abcdefghijklmnopqrstuvwxyz",
        //    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
        //    "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
        //};
        //for (String s : testStrings)
        //    System.out.println("0x" + toHexString(computeMD5(s.getBytes())) + " <== \"" + s + "\"");

        String message = "kako je ovo dobra poruka bracoo moj";
        byte[] bytesOfMessage = message.getBytes(StandardCharsets.UTF_8);
        byte[] crypted1 = computeMD5(bytesOfMessage);
        System.out.println(Util.byteToHex(crypted1));

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] crypted2 = md.digest(bytesOfMessage);
        System.out.println(Util.byteToHex(crypted2));
    }

    @Override
    public String getMD5(byte[] data) {
        byte[] bytes = computeMD5(data);
        return Base64.encodeBase64String(bytes);
    }
}