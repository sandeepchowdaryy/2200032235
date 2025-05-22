package com.sandeep.affordmed.controller;

import com.sandeep.affordmed.model.CorrelationResponse;
import com.sandeep.affordmed.model.StockPrice;
import com.sandeep.affordmed.service.StockAnalysis;
import com.sandeep.affordmed.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CorrelationController {
    @Autowired
    private StockService apiService;
    @Autowired private StockAnalysis analysisService;;

    @GetMapping("/stockcorrelation")
    public CorrelationResponse getCorrelation(
            @RequestParam int minutes,
            @RequestParam String ticker1,
            @RequestParam String ticker2
    ) {
        System.out.println("Request received for stock correlation.");
        System.out.println("Ticker1: " + ticker1);
        System.out.println("Ticker2: " + ticker2);

        // Fetch stock prices
        List<StockPrice> s1 = apiService.getStockPrices(ticker1, minutes);
        List<StockPrice> s2 = apiService.getStockPrices(ticker2, minutes);

        // Check if data is fetched
        if (s1.isEmpty() || s2.isEmpty()) {
            System.out.println("No stock prices found for the provided tickers.");
        }

        double corr = analysisService.calculateCorrelation(s1, s2);

        double avg1 = s1.stream().mapToDouble(StockPrice::getPrice).average().orElse(0.0);
        double avg2 = s2.stream().mapToDouble(StockPrice::getPrice).average().orElse(0.0);

        Map<String, Object> stocks = new HashMap<>();
        stocks.put(ticker1, Map.of(
                "averagePrice", avg1,
                "priceHistory", s1
        ));
        stocks.put(ticker2, Map.of(
                "averagePrice", avg2,
                "priceHistory", s2
        ));

        System.out.println("Correlation: " + corr);
        System.out.println("Returning response...");

        return new CorrelationResponse(corr, stocks);
    }

}

