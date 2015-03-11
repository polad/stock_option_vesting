package com.solium.sov;

import java.util.Date;

public interface CalculatableRecord {
    public double calculateGainFor(Date marketDate, double marketPrice);
}
