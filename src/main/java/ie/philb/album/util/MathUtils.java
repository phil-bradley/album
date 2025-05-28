/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

/**
 *
 * @author philb
 */
public class MathUtils {

    /**
     * Greatest common divisor (highest common factor), using Euclid algorithm
     * Returns 1 if there's no common divisor
     */
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * If both numbers are mutually prime then the gcd = 1 This means that the
     * lcm = a*b Otherwise, if we have a common divisor, we can divide the
     * product by gcd
     */
    public static int lcm(int a, int b) {
        int gcd = gcd(a, b);
        return (a * b) / gcd;
    }
}
