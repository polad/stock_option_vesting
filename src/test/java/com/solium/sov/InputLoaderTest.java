package com.solium.sov;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class InputLoaderTest {
    private InputLoader loader;
    private GainCalculator gainCalculator;
    private RecordFactory recordFactory;

    @BeforeMethod
    private void beforeEachTest() {
        gainCalculator = mock(GainCalculator.class);
        recordFactory = mock(RecordFactory.class);
        loader = new InputLoader(gainCalculator, recordFactory);
    }

    @Test
    public void shouldLoadFromInput() {
        // Given
        String fileContent = "2\r\n"
                +"VEST,001B,20120101,1000,0.45\r\n"
                +"VEST,002B,20120101,1500,0.45\r\n"
                +"20140101,1.00";

        EmployeeAwareRecord employeeAwareRecord1 = mock(EmployeeAwareRecord.class);
        EmployeeAwareRecord employeeAwareRecord2 = mock(EmployeeAwareRecord.class);

        given(recordFactory.build(any(String[].class)))
                .willReturn(employeeAwareRecord1)
                .willReturn(employeeAwareRecord2);

        Scanner inputScanner = new Scanner(fileContent);

        // When
        loader.loadRecordsAndGetMarketInfo(inputScanner);

        // Then
        verify(gainCalculator).add(employeeAwareRecord1);
        verify(gainCalculator).add(employeeAwareRecord2);
    }

    @Test
    public void shouldThrowExceptionIfInvalidNumberOfRecordsProvidedInTheInput() {
        // Given
        String fileContent = "\r\n"
                +"VEST,001B,20120101,1000,0.45\r\n"
                +"VEST,002B,20120101,1500,0.45\r\n"
                +"20140101,1.00";

        EmployeeAwareRecord employeeAwareRecord1 = mock(EmployeeAwareRecord.class);
        EmployeeAwareRecord employeeAwareRecord2 = mock(EmployeeAwareRecord.class);

        given(recordFactory.build(any(String[].class)))
                .willReturn(employeeAwareRecord1)
                .willReturn(employeeAwareRecord2);

        Scanner inputScanner = new Scanner(fileContent);

        // When
        try {
            loader.loadRecordsAndGetMarketInfo(inputScanner);
        } catch (RuntimeException e) {
            assertThat(e)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Invalid number of records provided");
        }

        // Then
        verify(gainCalculator, never()).add(any(EmployeeAwareRecord.class));
    }

    @Test
    public void shouldThrowExceptionIfNoRecordsProvidedInTheInput() {
        // Given
        String fileContent = "2\r\n";

        Scanner inputScanner = new Scanner(fileContent);

        // When
        try {
            loader.loadRecordsAndGetMarketInfo(inputScanner);
        } catch (RuntimeException e) {
            assertThat(e)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("No line found");
        }

        // Then
        verify(gainCalculator, never()).add(any(EmployeeAwareRecord.class));
    }

    @Test
    public void shouldThrowExceptionIfInvalidMarketInfoProvidedInTheInput() {
        // Given
        String fileContent = "2\r\n"
                +"VEST,001B,20120101,1000,0.45\r\n"
                +"VEST,002B,20120101,1500,0.45\r\n"
                +",1.00";

        EmployeeAwareRecord employeeAwareRecord1 = mock(EmployeeAwareRecord.class);
        EmployeeAwareRecord employeeAwareRecord2 = mock(EmployeeAwareRecord.class);

        given(recordFactory.build(any(String[].class)))
                .willReturn(employeeAwareRecord1)
                .willReturn(employeeAwareRecord2);

        Scanner inputScanner = new Scanner(fileContent);

        // When
        try {
            loader.loadRecordsAndGetMarketInfo(inputScanner);
        } catch (RuntimeException e) {
            assertThat(e)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Invalid market info provided");
        }
    }
}
