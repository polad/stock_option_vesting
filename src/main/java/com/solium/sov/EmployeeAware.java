package com.solium.sov;

public interface EmployeeAware {
    public boolean belongsTo(EmployeeAware employeeAware);
    public String getEmployeeId();
}
