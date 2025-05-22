package com.sandeep.affordmed.util;

import com.sandeep.affordmed.model.StockPrice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CorrelationCalculator {

        public double calculateCorrelation(List<StockPrice> stock1, List<StockPrice> stock2) {
            // Align timestamps
            Map<String, Double> map1 = stock1.stream().collect(Collectors.toMap(
                    StockPrice::getLastUpdatedAt, StockPrice::getPrice));

            Map<String, Double> map2 = stock2.stream().collect(Collectors.toMap(
                    StockPrice::getLastUpdatedAt, StockPrice::getPrice));

            List<Double> x = new ArrayList<>();
            List<Double> y = new ArrayList<>();

            for (String ts : map1.keySet()) {
                if (map2.containsKey(ts)) {
                    x.add(map1.get(ts));
                    y.add(map2.get(ts));
                }
            }

            if (x.size() < 2) return 0.0;

            double meanX = x.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            double meanY = y.stream().mapToDouble(Double::doubleValue).average().orElse(0);

            double numerator = 0, denomX = 0, denomY = 0;

            for (int i = 0; i < x.size(); i++) {
                double dx = x.get(i) - meanX;
                double dy = y.get(i) - meanY;
                numerator += dx * dy;
                denomX += dx * dx;
                denomY += dy * dy;
            }

            return numerator / Math.sqrt(denomX * denomY);
        }

    }
