package com.weather.application.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class MyExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        // This method is invoked after the test instance has been created but before any test method is invoked.
        // You can perform any custom initialization or setup here.
        // For demonstration purposes, let's just print a message.
        System.out.println("Test instance post-processing for: " + testInstance.getClass().getSimpleName());
    }
}
