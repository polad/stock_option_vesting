package com.solium.sov;

import java.util.Date;

public class VestRecordFactory implements EmployeeAwareRecordFactory {
    public EmployeeAwareRecord build(String[] recordArray) {
        try {
            return new VestRecord(
                    recordArray[1],
                    new Date(Integer.parseInt(recordArray[2])),
                    Integer.parseInt(recordArray[3]),
                    Double.parseDouble(recordArray[4])
            );
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid records provided");
        }
    }
}
