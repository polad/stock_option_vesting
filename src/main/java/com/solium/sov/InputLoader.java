package com.solium.sov;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;

public class InputLoader {
    private GainCalculator gainCalculator;
    private RecordFactory recordFactory;

    public InputLoader(GainCalculator gainCalculator, RecordFactory recordFactory) {
        this.gainCalculator = gainCalculator;
        this.recordFactory = recordFactory;
    }

    public void load(Scanner inputScanner) {
        try {
            int numOfRecords = Integer.parseInt(inputScanner.nextLine());
            loadRecords(numOfRecords, inputScanner);
            String marketInfoLine = inputScanner.nextLine();
            String[] marketInfo = marketInfoLine.split(",");
            printResults(calculateTotalGainsFor(marketInfo));
        } catch (NoSuchElementException e) {
            throwInvalidInputException();
        } catch (NumberFormatException e) {
            throwInvalidInputException();
        }
    }

    private void loadRecords(int numOfRecords, Scanner inputScanner) {
        for (int i=0; i<numOfRecords; i++) {
            String line = inputScanner.nextLine();
            String[] recordArray = line.split(",");
            EmployeeAwareRecord employeeAwareRecord = recordFactory.build(recordArray);
            if (employeeAwareRecord != null) {
                gainCalculator.add(employeeAwareRecord);
            }
        }
    }

    private SortedMap<String, Double> calculateTotalGainsFor(String[] marketInfo) {
        Date marketDate = new Date(Integer.parseInt(marketInfo[0]));
        double marketPrice = Double.parseDouble(marketInfo[1]);
        return gainCalculator.calculateGainFor(marketDate, marketPrice);
    }

    private void printResults(SortedMap<String, Double> result) {
        for (String employeeId : result.keySet()) {
            System.out.println(employeeId + "," + result.get(employeeId));
        }
    }

    private void throwInvalidInputException() {
        throw new RuntimeException("Invalid input provided");
    }
}
