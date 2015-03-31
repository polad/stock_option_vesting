package com.recipize.sov.employeegaincalculator;

import com.recipize.sov.EmployeeAware;

public class EmployeeGainCalculatorFactory {
    public EmployeeGainCalculator build(EmployeeAware employeeAware) {
        return new EmployeeGainCalculator(employeeAware.getEmployeeId());
    }
}
