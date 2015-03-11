package com.solium.sov;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmployeeGainCalculatorTest {
    private EmployeeGainCalculator employeeGainCalculator;
    private String employeeId = "001B";

    @BeforeMethod
    private void beforeEachTest() {
        employeeGainCalculator = new EmployeeGainCalculator(employeeId);
    }

    @Test
    public void shouldCalculateTotalGain() {
        // Given
        Date marketDate = new Date(20140101);
        double marketPrice = 1.00;

        VestRecord record1 = mock(VestRecord.class);
        given(record1.belongsTo(employeeGainCalculator)).willReturn(true);
        given(record1.calculateGainFor(marketDate, marketPrice)).willReturn(550.00);

        VestRecord record2 = mock(VestRecord.class);
        given(record2.belongsTo(employeeGainCalculator)).willReturn(true);
        given(record2.calculateGainFor(marketDate, marketPrice)).willReturn(750.00);

        employeeGainCalculator.add(record1);
        employeeGainCalculator.add(record2);

        // When
        Double result = employeeGainCalculator.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result).isEqualTo(1300.00);

        verify(record1).belongsTo(employeeGainCalculator);
        verify(record1).calculateGainFor(marketDate, marketPrice);

        verify(record2).belongsTo(employeeGainCalculator);
        verify(record2).calculateGainFor(marketDate, marketPrice);
    }

    @Test
    public void shouldCalculateTotalGainWithBonusPerformance() {
        // Given
        Date marketDate = new Date(20140101);
        double marketPrice = 1.00;

        VestRecord vestRecord1 = mock(VestRecord.class);
        given(vestRecord1.belongsTo(employeeGainCalculator)).willReturn(true);
        given(vestRecord1.calculateGainFor(marketDate, marketPrice)).willReturn(825.00);

        VestRecord vestRecord2 = mock(VestRecord.class);
        given(vestRecord2.belongsTo(employeeGainCalculator)).willReturn(true);
        given(vestRecord2.calculateGainFor(marketDate, marketPrice)).willReturn(750.00);

        PerformanceRecord performanceRecord = mock(PerformanceRecord.class);
        given(performanceRecord.belongsTo(employeeGainCalculator)).willReturn(true);

        employeeGainCalculator.add(vestRecord1);
        employeeGainCalculator.add(vestRecord2);
        employeeGainCalculator.add(performanceRecord);

        // When
        Double result = employeeGainCalculator.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result).isEqualTo(1575.00);

        verify(vestRecord1).add(performanceRecord);
        verify(vestRecord1).calculateGainFor(marketDate, marketPrice);

        verify(vestRecord2).add(performanceRecord);
        verify(vestRecord2).calculateGainFor(marketDate, marketPrice);
    }
}
