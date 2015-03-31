package com.recipize.sov;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemConsolePrinterTest {
    private SystemConsolePrinter printer;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeMethod
    private void beforeEachTest() {
        printer = new SystemConsolePrinter();
        output.reset();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void shouldPrintResultNumbersInTwoDecimalPlaces() {
        // Given
        Map<String, Double> result = new HashMap<String, Double>();
        result.put("001B", 855.0);

        // When
        printer.print(result);

        // Then
        assertThat(output.toString()).isEqualTo("001B,855.00\n");
    }

    @Test
    public void shouldPrintResultsSortedByKey() {
        // Given
        Map<String, Double> result = new HashMap<String, Double>();
        result.put("002B", 855.00);
        result.put("001B", 550.00);

        // When
        printer.print(result);

        // Then
        assertThat(output.toString()).isEqualTo("001B,550.00\n002B,855.00\n");
    }
}
