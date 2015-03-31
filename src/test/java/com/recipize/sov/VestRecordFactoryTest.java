package com.recipize.sov;

import org.testng.annotations.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class VestRecordFactoryTest {
    @Test
    public void shouldBuildVestRecord() {
        // Given
        String employeeId = "001B";
        String vestDate = "20120101";
        String numOfUnits = "1000";
        String grantPrice = "0.45";

        String[] recordArray = new String[]{ "VEST", employeeId, vestDate, numOfUnits, grantPrice };

        VestRecordFactory recordFactory = new VestRecordFactory();

        // When
        VestRecord result = (VestRecord) recordFactory.build(recordArray);

        // Then
        assertThat(result.getEmployeeId()).isEqualTo(employeeId);
        assertThat(result.getVestDate()).isEqualTo(new Date(Integer.parseInt(vestDate)));
        assertThat(result.getNumOfUnits()).isEqualTo(Integer.parseInt(numOfUnits));
        assertThat(result.getGrantPrice()).isEqualTo(Double.parseDouble(grantPrice));
    }
}
