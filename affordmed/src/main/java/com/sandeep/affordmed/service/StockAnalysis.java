package com.sandeep.affordmed.service;

import com.sandeep.affordmed.model.AveragePriceResponse;
import com.sandeep.affordmed.model.StockPrice;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockAnalysis {

    public AveragePriceResponse calculateAverage(List<StockPrice> prices, int minutes) {
        Instant cutoff = Instant.now().minus(Duration.ofMinutes(minutes));

        List<StockPrice> filtered = prices.stream()
                .filter(p -> Instant.parse(p.getLastUpdatedAt()).isAfter(cutoff))
                .toList();

        double avg = filtered.stream().mapToDouble(StockPrice::getPrice).average().orElse(0.0);

        return new AveragePriceResponse(avg, filtered);
    }

    public double calculateCorrelation(List<StockPrice> stock1, List<StockPrice> stock2) {
        // Align by timestamp
        Map<String, Double> map1 = stock1.stream()
                .collect(Collectors.toMap(StockPrice::getLastUpdatedAt, StockPrice::getPrice));

        Map<String, Double> map2 = stock2.stream()
                .collect(Collectors.toMap(StockPrice::getLastUpdatedAt, StockPrice::getPrice));

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

        return (denomX == 0 || denomY == 0) ? 0.0 : (numerator / Math.sqrt(denomX * denomY));
    }

}

