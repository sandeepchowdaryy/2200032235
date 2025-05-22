package com.sandeep.affordmed.model;

import java.util.Map;

public class CorrelationResponse {
    private double correlation;
    private Map<String, Object> stocks;

    // Constructor
    public CorrelationResponse(double correlation, Map<String, Object> stocks) {
        this.correlation = correlation;
        this.stocks = stocks;
    }

    // Getters and setters
    public double getCorrelation() {
        return correlation;
    }

    public void setCorrelation(double correlation) {
        this.correlation = correlation;
    }

    public Map<String, Object> getStocks() {
        return stocks;
    }

    public void setStocks(Map<String, Object> stocks) {
        this.stocks = stocks;
    }
}

