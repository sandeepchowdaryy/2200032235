package com.sandeep.affordmed.model;

import java.time.LocalDateTime;

public class StockPrice {
    private double price;
    private String lastUpdatedAt;

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "StockPrice{" +
                "price=" + price +
                ", lastUpdatedAt='" + lastUpdatedAt + '\'' +
                '}';
    }
}
