package com.searchmetrics.exchange.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchmetrics.exchange.config.Constants;
import com.searchmetrics.exchange.constants.Currency;
import com.searchmetrics.exchange.domain.HistoricalStats;
import com.searchmetrics.exchange.domain.external.HistoricalBpi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Log4j2
public class HistoricalDataService {

    private ObjectMapper mapper = new ObjectMapper();
    private ExternalService service;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Constants config;

    @Autowired
    public HistoricalDataService(ExternalService service,Constants config){
        this.service=service;
        this.config=config;
    }

    public HistoricalStats getHistoricalStats(Date fromData, Date endDate, String toCurrency) throws IOException{
        String url = calculateDynamicUrl( fromData,  endDate);
        String jsonString = service.callServer(url);
        if(toCurrency==null){
            toCurrency = Currency.USD.getCode();
        }
        return convertToHistoricalData(jsonString,Currency.BTC.getCode(),toCurrency);
    }

    private HistoricalStats convertToHistoricalData(String json,String fromCurrency, String toCurrency ) throws IOException{
        HistoricalBpi stats = mapper.readValue(json, HistoricalBpi.class);
        return HistoricalStats.builder()
                .dailyRatesMap(stats.getBpi())
                .toCurrency(toCurrency)
                .fromCurrency(fromCurrency)
                .build();

    }

    private String calculateDynamicUrl(Date startDate, Date endDate){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(config.getHistoricalDataUrl())
                .queryParam("start", dateFormat.format(startDate))
                .queryParam("end", dateFormat.format(endDate))
                .queryParam("currency", "USD");
       return builder.toUriString();

    }
}
