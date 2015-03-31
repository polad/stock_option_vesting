package com.recipize.sov;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class VestRecord implements EmployeeAwareRecord {
    private String employeeId;
    private Date vestDate;
    private int numOfUnits;
    private double grantPrice;
    private Set<PerformanceRecord> performanceRecords = new HashSet<PerformanceRecord>();

    public VestRecord(String employeeId, Date vestDate, int numOfUnits, double grantPrice) {
        this.employeeId = employeeId;
        this.vestDate = vestDate;
        this.numOfUnits = numOfUnits;
        this.grantPrice = grantPrice;
    }

    public double calculateGainFor(Date marketDate, double marketPrice) {
        double result = 0;
        if (marketDate.after(vestDate) && (marketPrice > grantPrice)) {
            result = Math.round(calculateNumOfUnits(marketDate) * calculateFinalPrice(marketPrice));
        }
        return result;
    }

    private double calculateFinalPrice(double marketPrice) {
        return marketPrice - grantPrice;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Date getVestDate() {
        return vestDate;
    }

    public int getNumOfUnits() {
        return numOfUnits;
    }

    public double getGrantPrice() {
        return grantPrice;
    }

    public boolean belongsTo(EmployeeAware employeeAware) {
        return employeeAware.getEmployeeId().equals(employeeId);
    }

    public void add(PerformanceRecord performanceRecord) {
        if (performanceRecord.appliesTo(this)) {
            performanceRecords.add(performanceRecord);
        }
    }

    private double calculateNumOfUnits(Date marketDate) {
        int result = numOfUnits;
        double multiplier = 1;
        for (PerformanceRecord performanceRecord : performanceRecords) {
            multiplier *= performanceRecord.getBonusMultiplierFor(marketDate);
        }
        return result*multiplier;
    }
}