package com.solium.sov;

import com.solium.sov.employeegaincalculator.EmployeeGainCalculator;
import com.solium.sov.employeegaincalculator.EmployeeGainCalculatorFactory;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class GainCalculatorTest {
    @Test
    public void shouldCalculateTotalGain() {
        // Given
        Date marketDate = new Date(20140101);
        double marketPrice = 1.00;

        EmployeeGainCalculator employeeGainCalculator1 = mock(EmployeeGainCalculator.class);
        given(employeeGainCalculator1.getEmployeeId()).willReturn("001B");
        given(employeeGainCalculator1.calculateGainFor(marketDate, marketPrice)).willReturn(550.00);

        EmployeeGainCalculator employeeGainCalculator2 = mock(EmployeeGainCalculator.class);
        given(employeeGainCalculator2.getEmployeeId()).willReturn("002B");
        given(employeeGainCalculator2.calculateGainFor(marketDate, marketPrice)).willReturn(825.00);

        EmployeeAwareRecord record1 = mock(EmployeeAwareRecord.class);
        given(record1.getEmployeeId()).willReturn("001B");

        EmployeeAwareRecord record2 = mock(EmployeeAwareRecord.class);
        given(record2.getEmployeeId()).willReturn("002B");

        EmployeeGainCalculatorFactory calculatorFactory = mock(EmployeeGainCalculatorFactory.class);
        given(calculatorFactory.build(record1)).willReturn(employeeGainCalculator1);
        given(calculatorFactory.build(record2)).willReturn(employeeGainCalculator2);

        GainCalculator calculator = new GainCalculator(calculatorFactory);
        calculator.add(record1);
        calculator.add(record2);

        // When
        Map<String, Double> result = calculator.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result)
                .hasSize(2)
                .containsEntry("001B", 550.00)
                .containsEntry("002B", 825.00);

        verify(employeeGainCalculator1).add(record1);
        verify(employeeGainCalculator1).calculateGainFor(marketDate, marketPrice);

        verify(employeeGainCalculator2).add(record2);
        verify(employeeGainCalculator2).calculateGainFor(marketDate, marketPrice);
    }
}
