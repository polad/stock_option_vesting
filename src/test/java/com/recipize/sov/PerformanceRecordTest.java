package com.recipize.sov;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PerformanceRecordTest {
    private PerformanceRecord performanceRecord;
    private String employeeId = "001B";
    private Date bonusEffectiveDate = new Date(20130102);
    private double bonusMultiplier = 1.5;

    @BeforeMethod
    private void beforeEachTest() {
        performanceRecord = new PerformanceRecord(employeeId, bonusEffectiveDate, bonusMultiplier);
    }

    @Test
    public void shouldBeApplicableIfVestDateIsNotAfterBonusEffectiveDate() {
        // Given
        VestRecord vestRecord = mock(VestRecord.class);
        given(vestRecord.belongsTo(performanceRecord)).willReturn(true);
        given(vestRecord.getVestDate()).willReturn(new Date(20130102));

        // When
        boolean result = performanceRecord.appliesTo(vestRecord);

        // Then
        assertThat(result).isTrue();

        verify(vestRecord).belongsTo(performanceRecord);
        verify(vestRecord).getVestDate();
    }

    @Test
    public void shouldApplyMultiplierIfMarketDateIsAfterBonusEffectiveDate() {
        // Given
        Date marketDate = new Date(20140101);

        // When
        double result = performanceRecord.getBonusMultiplierFor(marketDate);

        // Then
        assertThat(result).isEqualTo(bonusMultiplier);
    }
}
