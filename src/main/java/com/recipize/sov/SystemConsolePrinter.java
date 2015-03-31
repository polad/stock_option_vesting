package com.recipize.sov;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SystemConsolePrinter {
    public void print(Map<String, Double> map) {
        SortedMap<String, Double> sortedMap = new TreeMap<String, Double>(map);
        DecimalFormat formatter = new DecimalFormat("0.00");
        for (String employeeId : sortedMap.keySet()) {
            System.out.println(employeeId + "," + formatter.format(sortedMap.get(employeeId)));
        }
    }
}
