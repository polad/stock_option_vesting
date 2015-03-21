package com.solium.sov;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class StockOptionVesting {
    public static void main(String[] args) {
        EmployeeGainCalculatorFactory employeeGainCalculatorFactory = new EmployeeGainCalculatorFactory();
        GainCalculator gainCalculator = new GainCalculator(employeeGainCalculatorFactory);
        RecordFactory recordFactory = new RecordFactory();
        InputLoader loader = new InputLoader(gainCalculator, recordFactory);

        try {
            String[] marketInfo = loader.loadRecordsAndGetMarketInfo(new Scanner(System.in));
            Date marketDate = new Date(Integer.parseInt(marketInfo[0]));
            double marketPrice = Double.parseDouble(marketInfo[1]);
            Map<String, Double> result = gainCalculator.calculateGainFor(marketDate, marketPrice);
            new SystemConsolePrinter().print(result);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
