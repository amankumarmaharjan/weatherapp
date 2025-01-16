package com.weather.application.testTempalte.testfactory;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactorialTest {

    // Method to test
    public long factorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> factorialTests() {
        // Test cases defined as an array of inputs and expected outputs
        int[][] testData = {
                {0, 1},
                {1, 1},
                {2, 2},
                {3, 6},
                {4, 24},
                {5, 120}
        };

        // Generating a dynamic test for each set of data
        return Arrays.stream(testData)
                .map(data -> {
                    int input = data[0];
                    long expected = data[1];
                    return DynamicTest.dynamicTest("Factorial of " + input,
                            () -> assertEquals(expected, factorial(input)));
                }).toList();
    }
}
