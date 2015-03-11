package com.solium.sov;

import java.util.Date;

public class RecordFactory {
    public EmployeeAware build(String[] recordArray) {
        if (isOfTypeVestRecord(recordArray)) {
            return buildVestRecord(recordArray);
        } else if (isOfTypePerformanceRecord(recordArray)) {
            return buildPerformanceRecord(recordArray);
        }
        return null;
    }

    private EmployeeAware buildVestRecord(String[] recordArray) {
        return new VestRecord(
                recordArray[1],
                new Date(Integer.parseInt(recordArray[2])),
                Integer.parseInt(recordArray[3]),
                Double.parseDouble(recordArray[4])
        );
    }

    private EmployeeAware buildPerformanceRecord(String[] recordArray) {
        return new PerformanceRecord(
                recordArray[1],
                new Date(Integer.parseInt(recordArray[2])),
                Double.parseDouble(recordArray[3])
        );
    }

    private boolean isOfTypeVestRecord(Object[] obj) {
        return obj[0].equals("VEST") && obj.length == 5;
    }

    private boolean isOfTypePerformanceRecord(Object[] obj) {
        return obj[0].equals("PERF") && obj.length == 4;
    }
}
