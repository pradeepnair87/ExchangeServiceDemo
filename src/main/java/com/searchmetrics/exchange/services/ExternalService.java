package com.searchmetrics.exchange.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchmetrics.exchange.config.Constants;
import com.searchmetrics.exchange.constants.Currency;
import com.searchmetrics.exchange.domain.ExchangeRate;
import com.searchmetrics.exchange.domain.external.CoindeskResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;

@Service
@Log4j2
public class ExternalService {
    private RestTemplate restTemplate;
    private String latestPriceUrl;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ExternalService(RestTemplate restTemplate, Constants constants) {
        this.restTemplate = restTemplate;
        this.latestPriceUrl = constants.getLatestPriceUrl();
    }

     public String callServer(String url) {
        String jsonString = restTemplate.getForObject(url, String.class);
        log.debug(jsonString);
        return jsonString;
    }


    public ExchangeRate callExternalExchangeService() {
        String jsonString = callServer(latestPriceUrl);
        CoindeskResponse coindeskResponse = null;
        ExchangeRate exchangeRate = null;
        try {
            coindeskResponse = mapper.readValue(jsonString, CoindeskResponse.class);
            exchangeRate = convertResponseToExchangeRate(coindeskResponse, Currency.BTC, Currency.USD);
        } catch (Exception e) {
            log.warn("Could not convert string to json {}", e);

        }
        return exchangeRate;
    }

    private ExchangeRate convertResponseToExchangeRate(CoindeskResponse coindeskResponse, Currency from, Currency to) {
        ExchangeRate rate = null;
        long time;
        try {
            time = coindeskResponse.getTime().getTimeStamp();
        } catch (ParseException e) {
            time = System.currentTimeMillis();
        }
        if (coindeskResponse != null && coindeskResponse.getBpi() != null && coindeskResponse.getBpi().get(to.getCode()) != null) {
            rate = ExchangeRate.builder()
                    .exchangeRate(coindeskResponse.getBpi().get(to.getCode()).getFloatRate())
                    .fromCurrency(from.getCode())
                    .toCurrency(to.getCode())
                    .timestamp(time)
                    .build();
        }

        return rate;
    }

}
