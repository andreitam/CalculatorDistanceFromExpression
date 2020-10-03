package ro.siit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import java.beans.Beans;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for the Calculator class.
 *
 * @author  Andrei Tamasanu
 * @version 1.0
 * @since   2020-10-03
 */
class CalculatorTest {

    //Test with different correct expressions with equal result
    @ParameterizedTest
    @ValueSource(strings = {"10 cm + 1 m - 10 mm", "10cm+1m-10 mm", "590 mm + 3 dm + 20 cm","1m+90mm","500+500+ 90"})
    void getDistanceTest(String arg) {
        assertEquals("1090 mm",new Calculator(arg).getDistance());
    }
    //Test with empty string
    @ParameterizedTest
    @EmptySource
    void getDistanceExceptionEmpty(String arg) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Calculator(arg).getDistance());
        assertEquals("Wrong input string.String is empty.", exception.getMessage());
    }
    //Test with wrong characters
    @ParameterizedTest
    @ValueSource(strings = {"10 cm + 1 m - 10 mm@", "#10cm+1m-10 mm=", "&590 mm + 3 dm + 20 cm","(1m+90mm)"})
    void getDistanceWrongCharacters(String arg) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Calculator(arg).getDistance());
        assertEquals("Wrong input string.Only digits, letters, + and - signs are permitted", exception.getMessage());
    }
    //Test with incorrect units
    @ParameterizedTest
    @ValueSource(strings = {"10 cm + 1 m - 10 um", "10ccm+1m-10 mm", "590 mmm + 3 dmx + 20 cm","1mw+90mm"})
    void getDistanceWrongUnits(String arg) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Calculator(arg).getDistance());
        assertEquals("Wrong input string.Unit not found. Only m dm cm mm are permitted", exception.getMessage());
    }
}