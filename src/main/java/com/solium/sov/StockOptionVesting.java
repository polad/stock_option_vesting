package com.solium.sov;

import java.util.Scanner;

public class StockOptionVesting {
    public static void main(String[] args) {
        EmployeeGainCalculatorFactory employeeGainCalculatorFactory = new EmployeeGainCalculatorFactory();
        GainCalculator gainCalculator = new GainCalculator(employeeGainCalculatorFactory);
        RecordFactory recordFactory = new RecordFactory();
        InputLoader loader = new InputLoader(gainCalculator, recordFactory);

        try {
            loader.load(new Scanner(System.in));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
