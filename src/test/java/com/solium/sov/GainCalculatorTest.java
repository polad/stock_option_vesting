package com.solium.sov;

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

        VestRecord vestRecord1 = mock(VestRecord.class);
        given(vestRecord1.getEmployeeId()).willReturn("001B");

        VestRecord vestRecord2 = mock(VestRecord.class);
        given(vestRecord2.getEmployeeId()).willReturn("002B");

        PerformanceRecord performanceRecord = mock(PerformanceRecord.class);
        given(performanceRecord.getEmployeeId()).willReturn("002B");

        EmployeeGainCalculatorFactory calculatorFactory = mock(EmployeeGainCalculatorFactory.class);
        given(calculatorFactory.build(vestRecord1)).willReturn(employeeGainCalculator1);
        given(calculatorFactory.build(vestRecord2)).willReturn(employeeGainCalculator2);

        GainCalculator calculator = new GainCalculator(calculatorFactory);
        calculator.add(vestRecord1);
        calculator.add(vestRecord2);
        calculator.add(performanceRecord);

        // When
        SortedMap<String, Double> result = calculator.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result)
                .hasSize(2)
                .containsEntry("001B", 550.00)
                .containsEntry("002B", 825.00);

        verify(employeeGainCalculator1).add(vestRecord1);
        verify(employeeGainCalculator1).calculateGainFor(marketDate, marketPrice);

        verify(employeeGainCalculator2).add(vestRecord2);
        verify(employeeGainCalculator2).add(performanceRecord);
        verify(employeeGainCalculator2).calculateGainFor(marketDate, marketPrice);
    }
}
