package com.solium.sov;

import org.testng.annotations.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmployeeGainCalculatorTest {
    @Test
    public void shouldCalculateTotalGain() {
        // Given
        Date marketDate = new Date(20140101);
        double marketPrice = 1.00;

        String employeeId = "001B";

        EmployeeGainCalculator employeeGainCalculator = new EmployeeGainCalculator(employeeId);

//        VestRecord record1 = mock(VestRecord.class);
//        given(record1.getEmployeeId()).willReturn(employeeId);
//        given(record1.calculateGainFor(marketDate, marketPrice)).willReturn(550D);
//
//        VestRecord record2 = mock(VestRecord.class);
//        given(record2.getEmployeeId()).willReturn(employeeId);
//        given(record2.calculateGainFor(marketDate, marketPrice)).willReturn(750D);

        VestRecord record1 = new VestRecord(employeeId, new Date(20120101), 1000, 0.45);
        VestRecord record2 = new VestRecord(employeeId, new Date(20130101), 1500, 0.50);

        employeeGainCalculator.add(record1);
        employeeGainCalculator.add(record2);

        // When
        Double result = employeeGainCalculator.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result).isEqualTo(1300D);

//        verify(record1).calculateGainFor(marketDate, marketPrice);
//        verify(record2).calculateGainFor(marketDate, marketPrice);
    }

    @Test
    public void shouldCalculateTotalGainWithBonusPerformance() {
        // Given
        Date marketDate = new Date(20140101);
        double marketPrice = 1.00;

        String employeeId = "001B";

        EmployeeGainCalculator employeeGainCalculator = new EmployeeGainCalculator(employeeId);

//        VestRecord vestRecord1 = mock(VestRecord.class);
//        given(vestRecord1.getEmployeeId()).willReturn(employeeId);
//        given(vestRecord1.getVestDate()).willReturn(new Date(20120102));
//        given(vestRecord1.calculateGainFor(marketDate, marketPrice)).willReturn(550D);
//
//        VestRecord vestRecord2 = mock(VestRecord.class);
//        given(vestRecord2.getEmployeeId()).willReturn(employeeId);
//        given(vestRecord2.getVestDate()).willReturn(new Date(20131010));
//        given(vestRecord2.calculateGainFor(marketDate, marketPrice)).willReturn(750D);
//
//        PerformanceRecord performanceRecord = mock(PerformanceRecord.class);
//        given(performanceRecord.getEmployeeId()).willReturn(employeeId);
//        given(performanceRecord.getBonusEffectiveDate()).willReturn(new Date(20130102));
//        given(performanceRecord.getBonusMultiplier()).willReturn(1.5);
//        given(performanceRecord.calculateGainFor(marketDate, marketPrice)).willReturn(825D);
//        given(performanceRecord.calculateGainFor(marketDate, marketPrice)).willReturn(750D);

        VestRecord vestRecord1 = new VestRecord(employeeId, new Date(20120102), 1000, 0.45);
        VestRecord vestRecord2 = new VestRecord(employeeId, new Date(20131010), 1500, 0.50);
        PerformanceRecord performanceRecord = new PerformanceRecord(employeeId, new Date(20130102), 1.5);

        employeeGainCalculator.add(vestRecord1);
        employeeGainCalculator.add(vestRecord2);
        employeeGainCalculator.add(performanceRecord);

        // When
        Double result = employeeGainCalculator.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result).isEqualTo(1575D);

//        verify(vestRecord2).calculateGainFor(marketDate, marketPrice);
//        verify(performanceRecord).calculateGainFor(marketDate, marketPrice);
    }
}
