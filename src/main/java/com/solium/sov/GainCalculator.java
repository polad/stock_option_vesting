package com.solium.sov;

import com.solium.sov.employeegaincalculator.EmployeeGainCalculator;
import com.solium.sov.employeegaincalculator.EmployeeGainCalculatorFactory;

import java.util.*;

public class GainCalculator {
    private EmployeeGainCalculatorFactory calculatorFactory;
    private Map<String, EmployeeGainCalculator> employeeGainCalculators = new HashMap<String, EmployeeGainCalculator>();

    public GainCalculator(EmployeeGainCalculatorFactory calculatorFactory) {
        this.calculatorFactory = calculatorFactory;
    }

    public void add(EmployeeAwareRecord employeeAwareRecord) {
        getEmployeeGainCalculatorFor(employeeAwareRecord).add(employeeAwareRecord);
    }

    private EmployeeGainCalculator getEmployeeGainCalculatorFor(EmployeeAware employeeAware) {
        String employeeId = employeeAware.getEmployeeId();
        if (employeeGainCalculators.containsKey(employeeId)) {
            return employeeGainCalculators.get(employeeId);
        } else {
            return createNewCalculatorFor(employeeAware);
        }
    }

    private EmployeeGainCalculator createNewCalculatorFor(EmployeeAware employeeAware) {
        EmployeeGainCalculator result = calculatorFactory.build(employeeAware);
        employeeGainCalculators.put(result.getEmployeeId(), result);
        return result;
    }

    public Map<String, Double> calculateGainFor(Date marketDate, double marketPrice) {
        Map<String, Double> result = new HashMap<String, Double>();
        for (String employeeId : employeeGainCalculators.keySet()) {
            EmployeeGainCalculator calculator = employeeGainCalculators.get(employeeId);
            result.put(employeeId, calculator.calculateGainFor(marketDate, marketPrice));
        }
        return result;
    }
}
