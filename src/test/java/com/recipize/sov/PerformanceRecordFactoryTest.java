package com.recipize.sov;

import org.testng.annotations.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class PerformanceRecordFactoryTest {
    @Test
    public void shouldBuildPerformanceRecord() {
        // Given
        String employeeId = "001B";
        String bonusEffectiveDate = "20130102";
        String bonusMultiplier = "0.45";

        Date marketDate = new Date(20140101);

        String[] recordArray = new String[]{ "PERF", employeeId, bonusEffectiveDate, bonusMultiplier };

        PerformanceRecordFactory recordFactory = new PerformanceRecordFactory();

        // When
        PerformanceRecord result = (PerformanceRecord) recordFactory.build(recordArray);

        // Then
        assertThat(result.getEmployeeId()).isEqualTo(employeeId);
        assertThat(result.getBonusMultiplierFor(marketDate)).isEqualTo(Double.parseDouble(bonusMultiplier));
    }
}
