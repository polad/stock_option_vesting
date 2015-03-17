package com.solium.sov;

import org.testng.annotations.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class RecordFactoryTest {
    @Test
    public void shouldBuildVestRecord() {
        // Given
        String employeeId = "001B";
        String vestDate = "20120101";
        String numOfUnits = "1000";
        String grantPrice = "0.45";

        String[] recordArray = new String[]{ "VEST", employeeId, vestDate, numOfUnits, grantPrice };

        RecordFactory recordFactory = new RecordFactory();

        // When
        EmployeeAwareRecord result = recordFactory.build(recordArray);

        // Then
        assertThat(result).isInstanceOf(VestRecord.class);
        assertThat(result.getEmployeeId()).isEqualTo(employeeId);
        assertThat(((VestRecord)result).getVestDate()).isEqualTo(new Date(Integer.parseInt(vestDate)));
        assertThat(((VestRecord)result).getNumOfUnits()).isEqualTo(Integer.parseInt(numOfUnits));
        assertThat(((VestRecord)result).getGrantPrice()).isEqualTo(Double.parseDouble(grantPrice));
    }

    @Test
    public void shouldBuildPerformanceRecord() {
        // Given
        String employeeId = "001B";
        String bonusEffectiveDate = "20130102";
        String bonusMultiplier = "0.45";

        Date marketDate = new Date(20140101);

        String[] recordArray = new String[]{ "PERF", employeeId, bonusEffectiveDate, bonusMultiplier };

        RecordFactory recordFactory = new RecordFactory();

        // When
        EmployeeAwareRecord result = recordFactory.build(recordArray);

        // Then
        assertThat(result).isInstanceOf(PerformanceRecord.class);
        assertThat(result.getEmployeeId()).isEqualTo(employeeId);
        assertThat(((PerformanceRecord)result).getBonusMultiplierFor(marketDate)).isEqualTo(Double.parseDouble(bonusMultiplier));
    }
}
