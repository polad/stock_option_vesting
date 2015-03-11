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

        List<VestRecord> stockRecords = new ArrayList<VestRecord>();

        VestRecord record1 = mock(VestRecord.class);
        given(record1.getEmployeeId()).willReturn("001B");
        given(record1.calculateGainFor(marketDate, marketPrice)).willReturn(1300D);

        VestRecord record2 = mock(VestRecord.class);
        given(record2.getEmployeeId()).willReturn("002B");
        given(record2.calculateGainFor(marketDate, marketPrice)).willReturn(1325D);

        stockRecords.add(record1);
        stockRecords.add(record2);

//        stockRecords.add(new VestRecord(new Object[]{ "VEST", "001B", 20120101, 1000, 0.45 }));
//        stockRecords.add(new VestRecord(new Object[]{ "VEST", "002B", 20120101, 1500, 0.45 }));
//        stockRecords.add(new VestRecord(new Object[]{ "VEST", "002B", 20130101, 1000, 0.50 }));
//        stockRecords.add(new VestRecord(new Object[]{ "VEST", "001B", 20130101, 1500, 0.50 }));
//        stockRecords.add(new VestRecord(new Object[]{ "VEST", "003B", 20130101, 1000, 0.50 }));

        GainCalculator calculator = new GainCalculator(marketDate, marketPrice);

        // When
        SortedMap<String, Double> result = calculator.calculate(stockRecords);

        // Then
        assertThat(result)
                .hasSize(2)
                .containsEntry("001B", 1300.00)
                .containsEntry("002B", 1325.00);
//                .containsEntry("003B", 500.00);

        verify(record1).calculateGainFor(marketDate, marketPrice);
        verify(record2).calculateGainFor(marketDate, marketPrice);
    }

    @Test
    public void shouldCalculateTotalGainWithBonusPerformance() {
        // Given
//        Date marketDate = new Date(20140101);
//        double marketPrice = 1.0;
//
//        List<VestRecord> stockRecords = new ArrayList<VestRecord>();
//
//        VestRecord record1 = mock(VestRecord.class);
//        given(record1.getEmployeeId()).willReturn("001B");
//        given(record1.getVestDate()).willReturn(new Date(20120102));
//        given(record1.calculateGainFor(marketDate, marketPrice)).willReturn(550D);
//
//        VestRecord record2 = mock(VestRecord.class);
//        given(record2.getEmployeeId()).willReturn("002B");
//        given(record2.getVestDate()).willReturn(new Date(20120102));
//        given(record2.calculateGainFor(marketDate, marketPrice)).willReturn(550D);
//
//        PerformanceRecord record3 = mock(PerformanceRecord.class);
//        given(record3.getEmployeeId()).willReturn("001B");
//        given(record3.getBonusEffectiveDate()).willReturn(new Date(20130102));
//        given(record3.getBonusMultiplier()).willReturn(1.5);
//
//        stockRecords.add(record1);
//        stockRecords.add(record2);
//        //stockRecords.add(record3);
//
//        GainCalculator calculator = new GainCalculator(marketDate, marketPrice);
//
//        // When
//        SortedMap<String, Double> result = calculator.calculate(stockRecords);
//
//        // Then
//        assertThat(result)
//                .hasSize(2)
//                .containsEntry("001B", 825D)
//                .containsEntry("002B", 550D);
//
//        verify(record1).calculateGainFor(marketDate, marketPrice);
//        verify(record2).calculateGainFor(marketDate, marketPrice);
//        verify(record3).calculateGainFor(record1, marketDate, marketPrice);
    }
}
