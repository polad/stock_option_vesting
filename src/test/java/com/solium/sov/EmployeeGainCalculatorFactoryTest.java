package com.solium.sov;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EmployeeGainCalculatorFactoryTest {
    @Test
    public void shouldBuildGainCalculatorForEmployeeAware() {
        // Given
        EmployeeAware employeeAware = mock(EmployeeAware.class);
        given(employeeAware.getEmployeeId()).willReturn("001B");

        EmployeeGainCalculatorFactory factory = new EmployeeGainCalculatorFactory();

        // When
        EmployeeGainCalculator calculator = factory.build(employeeAware);

        // Then
        assertThat(calculator.belongsTo(employeeAware)).isTrue();
    }
}
