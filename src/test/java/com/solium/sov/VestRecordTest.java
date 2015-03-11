package com.solium.sov;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class VestRecordTest {
    private VestRecord vestRecord;
    private String employeeId = "001B";

    @BeforeMethod
    private void beforeEachTest() {
        vestRecord = new VestRecord(employeeId, new Date(20120101), 1000, 0.45);
    }

    @Test
    public void shouldCalculatePositiveGain() {
        // Given
        Date marketDate = new Date(20120102);
        double marketPrice = 1.00;

        // When
        double result = vestRecord.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result).isEqualTo(550.00);
    }

    @Test
    public void shouldCalculateZeroGainIfNothingIsVested() {
        // Given
        Date marketDate = new Date(20110101);
        double marketPrice = 1.00;

        // When
        double result = vestRecord.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void shouldCalculateZeroForNegativeGain() {
        // Given
        Date marketDate = new Date(20120102);
        double marketPrice = 0.30;

        // When
        double result = vestRecord.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void shouldCalculateGainWithPerformanseBonus() {
        // Given
        Date marketDate = new Date(20140101);
        double marketPrice = 1.00;

        PerformanceRecord performanceRecord = new PerformanceRecord(employeeId, new Date(20120110), 1.5);
        vestRecord.add(performanceRecord);

        // When
        double result = vestRecord.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result).isEqualTo(825.00);
    }

    @Test
    public void shouldCalculateGainWithMultiplePerformanseBonuses() {
        // Given
        Date marketDate = new Date(20140101);
        double marketPrice = 1.00;

        PerformanceRecord performanceRecord1 = new PerformanceRecord(employeeId, new Date(20120101), 1.5);
        PerformanceRecord performanceRecord2 = new PerformanceRecord(employeeId, new Date(20120301), 2);
        vestRecord.add(performanceRecord1);
        vestRecord.add(performanceRecord2);

        // When
        double result = vestRecord.calculateGainFor(marketDate, marketPrice);

        // Then
        assertThat(result).isEqualTo(1650.00);
    }
}
