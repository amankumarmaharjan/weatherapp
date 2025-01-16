package com.weather.application.testTempalte;

import org.junit.jupiter.api.extension.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.*;

public class CalculatorTestTemplateInvocationContextProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of(
                new CalculatorTestInvocationContext(1, 2, 3),
                new CalculatorTestInvocationContext(2, 3, 5),
                new CalculatorTestInvocationContext(3, 5, 8)
        );
    }

    private static class CalculatorTestInvocationContext implements TestTemplateInvocationContext {
        private final int a;
        private final int b;
        private final int expectedSum;

        CalculatorTestInvocationContext(int a, int b, int expectedSum) {
            this.a = a;
            this.b = b;
            this.expectedSum = expectedSum;
        }

        @Override
        public String getDisplayName(int invocationIndex) {
            return String.format("add(%d, %d) = %d", a, b, expectedSum);
        }

        @Override
        public List<Extension> getAdditionalExtensions() {
            return Collections.singletonList(new ParameterResolver() {
                @Override
                public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
                    return parameterContext.getParameter().getType() == int.class;
                }

                @Override
                public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
                    if (parameterContext.getIndex() == 0) {
                        return a;
                    } else if (parameterContext.getIndex() == 1) {
                        return b;
                    } else if (parameterContext.getIndex() == 2) {
                        return expectedSum;
                    }
                    throw new ParameterResolutionException("Unknown parameter index");
                }
            });
        }
    }
}
