package com.solium.sov.employeegaincalculator;

import com.solium.sov.EmployeeAware;

public class EmployeeGainCalculatorFactory {
    public EmployeeGainCalculator build(EmployeeAware employeeAware) {
        return new EmployeeGainCalculator(employeeAware.getEmployeeId());
    }
}
