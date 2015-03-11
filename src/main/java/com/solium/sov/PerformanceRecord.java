package com.solium.sov;

import java.util.Date;

public class PerformanceRecord implements EmployeeAware {
    private String employeeId;
    private Date bonusEffectiveDate;
    private double bonusMultiplier;

    public PerformanceRecord(String employeeId, Date bonusEffectiveDate, double bonusMultiplier) {
        this.employeeId = employeeId;
        this.bonusEffectiveDate = bonusEffectiveDate;
        this.bonusMultiplier = bonusMultiplier;
    }

    public boolean appliesTo(VestRecord record) {
        return record.belongsTo(this)
                && !record.getVestDate().after(bonusEffectiveDate);
    }

    public boolean belongsTo(EmployeeAware employeeAware) {
        return employeeAware.getEmployeeId().equals(employeeId);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public double getBonusMultiplierFor(Date marketDate) {
        double result = 1;
        if (marketDate.after(bonusEffectiveDate)) {
            result = bonusMultiplier;
        }
        return result;
    }
}
