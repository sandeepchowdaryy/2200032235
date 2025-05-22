package com.sandeep.affordmed.controller;

import com.sandeep.affordmed.model.AveragePriceResponse;
import com.sandeep.affordmed.model.StockPrice;
import com.sandeep.affordmed.service.StockAnalysis;
import com.sandeep.affordmed.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired private StockService stockService;

    @GetMapping("/{ticker}")
    public AveragePriceResponse getAveragePrice(
            @PathVariable String ticker,
            @RequestParam int minutes,
            @RequestParam String aggregation
    ) {
        List<StockPrice> prices = stockService.getStockPrices(ticker, minutes);
        double average = prices.stream()
                .mapToDouble(StockPrice::getPrice)
                .average()
                .orElse(0.0);

        return new AveragePriceResponse(average, prices);
    }
}




