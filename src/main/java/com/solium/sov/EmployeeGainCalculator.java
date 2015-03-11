package com.solium.sov;

import java.util.*;

public class EmployeeGainCalculator implements EmployeeAware {
    private String employeeId;
    private Set<VestRecord> vestRecords = new HashSet<VestRecord>();
    private Set<PerformanceRecord> performanceRecords = new HashSet<PerformanceRecord>();

    public EmployeeGainCalculator(String employeeId) {
        this.employeeId = employeeId;
    }

    public void add(VestRecord vestRecord) {
        if (vestRecord.belongsTo(this)) {
            applyAllPerformanceRecordsTo(vestRecord);
            vestRecords.add(vestRecord);
        }
    }

    private void applyAllPerformanceRecordsTo(VestRecord vestRecord) {
        for (PerformanceRecord performanceRecord : performanceRecords) {
            vestRecord.add(performanceRecord);
        }
    }

    public void add(PerformanceRecord performanceRecord) {
        if (performanceRecord.belongsTo(this)) {
            performanceRecords.add(performanceRecord);
            applyToAllVestRecords(performanceRecord);
        }
    }

    private void applyToAllVestRecords(PerformanceRecord performanceRecord) {
        for (VestRecord vestRecord : vestRecords) {
            vestRecord.add(performanceRecord);
        }
    }

    public double calculateGainFor(Date marketDate, double marketPrice) {
        double result = 0;
        for (VestRecord vestRecord : vestRecords) {
            result += vestRecord.calculateGainFor(marketDate, marketPrice);
        }
        return result;
    }

    public boolean belongsTo(EmployeeAware employeeAware) {
        return employeeAware.getEmployeeId().equals(employeeId);
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
