package com.solium.sov;

import java.util.*;

public class GainCalculator {
    private EmployeeGainCalculatorFactory calculatorFactory;
    private Map<String, EmployeeGainCalculator> employeeGainCalculators = new HashMap<String, EmployeeGainCalculator>();

    public GainCalculator(EmployeeGainCalculatorFactory calculatorFactory) {
        this.calculatorFactory = calculatorFactory;
    }

    public void add(EmployeeAware employeeAware) {
        if (employeeAware instanceof VestRecord) {
            add((VestRecord) employeeAware);
        } else if (employeeAware instanceof PerformanceRecord) {
            add((PerformanceRecord) employeeAware);
        }
    }

    public void add(VestRecord vestRecord) {
        getEmployeeGainCalculatorFor(vestRecord).add(vestRecord);
    }

    public void add(PerformanceRecord performanceRecord) {
        getEmployeeGainCalculatorFor(performanceRecord).add(performanceRecord);
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

    public SortedMap<String, Double> calculateGainFor(Date marketDate, double marketPrice) {
        SortedMap<String, Double> result = new TreeMap<String, Double>();
        for (String employeeId : employeeGainCalculators.keySet()) {
            EmployeeGainCalculator calculator = employeeGainCalculators.get(employeeId);
            result.put(employeeId, calculator.calculateGainFor(marketDate, marketPrice));
        }
        return result;
    }
}
