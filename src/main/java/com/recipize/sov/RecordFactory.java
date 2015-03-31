package com.recipize.sov;

import java.util.HashMap;
import java.util.Map;

public class RecordFactory implements EmployeeAwareRecordFactory {
    private Map<String, EmployeeAwareRecordFactory> factories = new HashMap<String, EmployeeAwareRecordFactory>();

    public EmployeeAwareRecord build(String[] recordArray) {
        String recordType = recordArray[0];
        return getFactoryFor(recordType).build(recordArray);
    }

    private EmployeeAwareRecordFactory getFactoryFor(String recordType) {
        if (!factories.containsKey(recordType)) {
            throw new RuntimeException("Trying to build an unknown record type");
        }
        return factories.get(recordType);
    }

    public void addFactory(String recordType, EmployeeAwareRecordFactory factory) {
        factories.put(recordType, factory);
    }
}
