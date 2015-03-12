package com.solium.sov;

import org.testng.annotations.Test;

import java.util.Date;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class InputLoaderTest {
    @Test
    public void shouldLoadFromInput() {
        // Given
        String fileContent = "2\r\n"
                +"VEST,001B,20120101,1000,0.45\r\n"
                +"VEST,002B,20120101,1500,0.45\r\n"
                +"20140101,1.00";

        Date marketDate = new Date(20140101);
        double marketPrice = 1.00;

        EmployeeAwareRecord employeeAwareRecord1 = mock(EmployeeAwareRecord.class);
        EmployeeAwareRecord employeeAwareRecord2 = mock(EmployeeAwareRecord.class);

        RecordFactory recordFactory = mock(RecordFactory.class);
        given(recordFactory.build(any(String[].class)))
                .willReturn(employeeAwareRecord1)
                .willReturn(employeeAwareRecord2);

        GainCalculator gainCalculator = mock(GainCalculator.class);

        InputLoader loader = new InputLoader(gainCalculator, recordFactory);

        Scanner inputScanner = new Scanner(fileContent);

        // When
        loader.load(inputScanner);

        // Then
        verify(gainCalculator).add(employeeAwareRecord1);
        verify(gainCalculator).add(employeeAwareRecord2);
        verify(gainCalculator).calculateGainFor(marketDate, marketPrice);
    }

    @Test
    public void shouldThrowExceptionIfInputIsInvalid() {
        // Given
        String fileContent = "2\r\n"
                +"20140101,1.00";

        RecordFactory recordFactory = mock(RecordFactory.class);

        GainCalculator gainCalculator = mock(GainCalculator.class);

        InputLoader loader = new InputLoader(gainCalculator, recordFactory);

        Scanner inputScanner = new Scanner(fileContent);

        // When
        try {
            loader.load(inputScanner);
        } catch (RuntimeException e) {
            assertThat(e)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Invalid input provided");
        }

        // Then
        verify(gainCalculator, never()).add(any(EmployeeAwareRecord.class));
        verify(gainCalculator, never()).calculateGainFor(any(Date.class), anyDouble());
    }
}
