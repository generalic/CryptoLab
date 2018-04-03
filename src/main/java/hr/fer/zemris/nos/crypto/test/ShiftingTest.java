package hr.fer.zemris.nos.crypto.test;

/**
 * Created by generalic on 09/05/17.
 * crypto
 */
public class ShiftingTest {
    public static void main(String[] args) {
        int n = 0b100;

        int r = n >>> 1;
        System.out.println(r);
        System.out.println(Integer.toBinaryString(r));

        int r2 = n >> 1;
        System.out.println(r2);
        System.out.println(Integer.toBinaryString(r2));

        int o = 5 % 2;
        System.out.println(o);

        int o2 = 5 & 0x1;
        System.out.println(o2);

        int b1 = 64 >> 6;
        System.out.println(b1);

        int b2 = 64 >>> 6;
        System.out.println(b2);

        int number = -1 << 5;
        System.out.println(Integer.toBinaryString(number));

        int shifted = number >> 1;
        System.out.println(Integer.toBinaryString(shifted));

        int shifted2 = number >>> 1;
        System.out.println(Integer.toBinaryString(shifted2));
    }
}
