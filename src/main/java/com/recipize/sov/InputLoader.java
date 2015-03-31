package com.recipize.sov;

import java.util.Scanner;

public class InputLoader {
    private GainCalculator gainCalculator;
    private RecordFactory recordFactory;

    public InputLoader(GainCalculator gainCalculator, RecordFactory recordFactory) {
        this.gainCalculator = gainCalculator;
        this.recordFactory = recordFactory;
    }

    public String[] loadRecordsAndGetMarketInfo(Scanner inputScanner) {
        int numOfRecords = loadNumberOfRecords(inputScanner);
        loadRecords(numOfRecords, inputScanner);
        return loadMarketInfo(inputScanner);
    }

    private int loadNumberOfRecords(Scanner inputScanner) {
        try {
            return Integer.parseInt(inputScanner.nextLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number of records provided");
        }
    }

    private void loadRecords(int numOfRecords, Scanner inputScanner) {
        for (int i = 0; i < numOfRecords; i++) {
            String line = inputScanner.nextLine();
            String[] recordArray = line.split(",");
            EmployeeAwareRecord employeeAwareRecord = recordFactory.build(recordArray);
            if (employeeAwareRecord != null) {
                gainCalculator.add(employeeAwareRecord);
            }
        }
    }

    private String[] loadMarketInfo(Scanner inputScanner) {
        String marketInfoLine = inputScanner.nextLine();
        String[] result = marketInfoLine.split(",");
        if (result.length < 2 || result[0].equals("") || result[1].equals("")) {
            throw new RuntimeException("Invalid market info provided");
        }
        return result;
    }
}
