package com.solium.sov;

import java.util.*;

public class GainCalculator {
    private Date marketDate;
    private double marketPrice;

    public GainCalculator(Date marketDate, double marketPrice) {
        this.marketDate = marketDate;
        this.marketPrice = marketPrice;
    }

    public SortedMap<String, Double> calculate(List<VestRecord> stockRecords) {
        SortedMap<String, Double> result = new TreeMap<String, Double>();
        for (VestRecord record : stockRecords) {
            String employeeId = record.getEmployeeId();
            Double gain = 0.00;
            if (result.containsKey(employeeId)) {
                gain = result.get(employeeId);
            }
            gain += record.calculateGainFor(marketDate, marketPrice);
            result.put(employeeId, gain);
        }
        return result;
    }
}
