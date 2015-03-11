package com.solium.sov;

public class EmployeeGainCalculatorFactory {
    public EmployeeGainCalculator build(EmployeeAware employeeAware) {
        return new EmployeeGainCalculator(employeeAware.getEmployeeId());
    }
}
