/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
class MathUtilsTest {

    @Test
    public void givenValuesWithGcd_expectedGcd() {

        int x = 12;
        int y = 8;
        int gcd = MathUtils.gcd(x, y);
        assertEquals(4, gcd);
    }

    @Test
    void giveMutuallyPrime_expectGcdIsOne() {
        int x = 5;
        int y = 23;
        int gcd = MathUtils.gcd(x, y);
        assertEquals(1, gcd);
    }

    @Test
    void givenValuesWithGcd_expectLcmLessThanProduct() {
        int x = 4;
        int y = 6;
        int lcm = MathUtils.lcm(x, y);
        assertEquals(12, lcm);
    }

    @Test
    void givenValuesWithoutGcd_expectLcmIsProduct() {
        int x = 5;
        int y = 3;
        int lcm = MathUtils.lcm(x, y);
        assertEquals(15, lcm);
    }
}
