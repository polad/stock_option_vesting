package com.solium.sov;

import java.util.*;

public class EmployeeGainCalculator implements EmployeeAware {
    private String employeeId;
    private Set<VestRecord> vestRecords = new HashSet<VestRecord>();
    private Set<PerformanceRecord> performanceRecords = new HashSet<PerformanceRecord>();

    public EmployeeGainCalculator(String employeeId) {
        this.employeeId = employeeId;
    }

    public void add(EmployeeAwareRecord record) {
        if (record instanceof VestRecord) {
            add((VestRecord) record);
        } else if (record instanceof PerformanceRecord) {
            add((PerformanceRecord) record);
        }
    }

    public void add(VestRecord vestRecord) {
        if (vestRecord.belongsTo(this)) {
            applyAllPerformanceRecordsTo(vestRecord);
            vestRecords.add(vestRecord);
        }
    }

    public void add(PerformanceRecord performanceRecord) {
        if (performanceRecord.belongsTo(this)) {
            applyToAllVestRecords(performanceRecord);
            performanceRecords.add(performanceRecord);
        }
    }

    private void applyAllPerformanceRecordsTo(VestRecord vestRecord) {
        for (PerformanceRecord performanceRecord : performanceRecords) {
            vestRecord.add(performanceRecord);
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
