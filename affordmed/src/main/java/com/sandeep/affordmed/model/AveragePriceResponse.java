package com.sandeep.affordmed.model;

import java.util.List;

public class AveragePriceResponse {
    private double averageStockPrice;
    private List<StockPrice> priceHistory;

    public AveragePriceResponse(double averageStockPrice, List<StockPrice> priceHistory) {
        this.averageStockPrice = averageStockPrice;
        this.priceHistory = priceHistory;
    }

    public double getAverageStockPrice() {
        return averageStockPrice;
    }

    public void setAverageStockPrice(double averageStockPrice) {
        this.averageStockPrice = averageStockPrice;
    }

    public List<StockPrice> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<StockPrice> priceHistory) {
        this.priceHistory = priceHistory;
    }
}

