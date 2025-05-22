package com.sandeep.affordmed.service;

import com.sandeep.affordmed.model.StockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class StockService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://20.244.56.144/evaluation-service/stocks";

    @Autowired
    public StockService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<StockPrice> getStockPrices(String ticker, int minutes) {
        String url = BASE_URL + "/" + ticker + "?minutes=" + minutes;

        ResponseEntity<StockPrice[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // no headers needed here
                StockPrice[].class
        );

        // Log the raw response for debugging
        System.out.println("Raw response: " + Arrays.toString(response.getBody()));

        // Filter out malformed entries if necessary (e.g., remove '•' or invalid characters)
        List<StockPrice> prices = Arrays.asList(response.getBody());
        prices.forEach(price -> {
            if (price.getLastUpdatedAt().contains("•")) {
                System.out.println("Malformed entry: " + price);
                // Clean or skip these entries
            }
        });

        return prices;
    }

}

