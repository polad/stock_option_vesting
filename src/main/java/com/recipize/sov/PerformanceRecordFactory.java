package com.recipize.sov;

import java.util.Date;

public class PerformanceRecordFactory implements EmployeeAwareRecordFactory {
    public EmployeeAwareRecord build(String[] recordArray) {
        try {
            return new PerformanceRecord(
                    recordArray[1],
                    new Date(Integer.parseInt(recordArray[2])),
                    Double.parseDouble(recordArray[3])
            );
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid records provided");
        }
    }
}
