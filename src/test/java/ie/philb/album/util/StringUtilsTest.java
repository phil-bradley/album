/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ie.philb.album.util;

import static ie.philb.album.util.StringUtils.hasValue;
import static ie.philb.album.util.StringUtils.isBlank;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class StringUtilsTest {

    // hasValue
    @Test
    void givenNull_whenCallHasValue_expectFalse() {
        assertFalse(hasValue(null));
    }

    @Test
    void givenEmptyString_whenCallHasValue_expectFalse() {
        assertFalse(hasValue(""));
    }

    @Test
    void givenSpaces_whenCallHasValue_expectFalse() {
        assertFalse(hasValue(" "));
    }

    @Test
    void givenString_whenCallHasValue_expectTrue() {
        assertTrue(hasValue("abc"));
    }

    // isBlank
    @Test
    void givenNull_whenCallIsBlank_expectTrue() {
        assertTrue(isBlank(null));
    }

    @Test
    void givenEnptyString_whenCallIsBlank_expectTrue() {
        assertTrue(isBlank(""));
    }

    @Test
    void givenSpaces_whenCallIsBlank_expectTrue() {
        assertTrue(isBlank(" "));
    }

    @Test
    void givenString_whenCallIsBlank_expectFalse() {
        assertFalse(isBlank("abc"));
    }

    // Truncate
    @Test
    void givenNull_whenCallTruncate_expectNull() {
        assertNull(StringUtils.truncate(null, 0));
    }

    @Test
    void givenString_whenCallTruncateWithZeroLength_expectEmpty() {
        assertEquals("", StringUtils.truncate("abc", 0));
    }

    @Test
    void givenString_whenCallTruncate_expectTruncatedToLength() {
        assertEquals("ab", StringUtils.truncate("abc", 2));
    }

    @Test
    void givenStringShorterThanTruncateLength_whenCallTruncate_expectNotTruncatedToLength() {
        assertEquals("abc", StringUtils.truncate("abc", 20));
    }

    @Test
    void givenStringLengthEqualsTruncateLength_whenCallTruncate_expectNotTruncatedToLength() {
        assertEquals("abc", StringUtils.truncate("abc", 3));
    }
}
