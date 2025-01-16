package com.weather.application.testTempalte;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTests {

    private final Calculator calculator = new Calculator();

    @TestTemplate
    @ExtendWith(CalculatorTestTemplateInvocationContextProvider.class)
    public void addTest(int a, int b, int expectedSum) {
        assertEquals(expectedSum, calculator.add(a, b),
                () -> String.format("Expected %d + %d to equal %d", a, b, expectedSum));
    }


    @TestTemplate
    @ExtendWith(MyTestTemplateInvocationContextProvider.class)
    void testTemplate(String parameter) {
        assertEquals(3, parameter.length());
    }
}
