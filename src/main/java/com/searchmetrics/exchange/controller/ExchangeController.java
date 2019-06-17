package com.searchmetrics.exchange.controller;

import com.searchmetrics.exchange.constants.Currency;
import com.searchmetrics.exchange.domain.ExchangeRate;
import com.searchmetrics.exchange.domain.HistoricalStats;
import com.searchmetrics.exchange.services.ExchangeRateService;
import com.searchmetrics.exchange.services.HistoricalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;


@RestController
public class ExchangeController {

    private ExchangeRateService service;

    private HistoricalDataService statsService;
    @Autowired
    public ExchangeController(ExchangeRateService exchangeRateService,HistoricalDataService statsService){
        this.service=exchangeRateService;
        this.statsService=statsService;
    }
    @RequestMapping(value= "/latest-price/{fromCurrency}/{toCurrency}",method = RequestMethod.GET)
    public ResponseEntity<ExchangeRate> latestPrice(@PathVariable(name="fromCurrency", required=true) Currency fromCurrency,
                                              @PathVariable(name="toCurrency", required=true) Currency toCurrency)
    {
        return new ResponseEntity<ExchangeRate>(service.getLatestPrice(Currency.BTC,Currency.USD), HttpStatus.OK);
    }


    @RequestMapping(value= "/historical-data",method = RequestMethod.GET)
    public ResponseEntity<HistoricalStats> historicalData(@RequestParam(value = "startDate", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") @Valid Date startDate,
                                              @RequestParam(value = "endDate" , required=true ) @DateTimeFormat(pattern="yyyy-MM-dd") @Valid Date endDate,
                                                          @RequestParam(name="toCurrency", required=false) String toCurrency) throws IOException
    {

        return new ResponseEntity<HistoricalStats>(statsService.getHistoricalStats(startDate,endDate,toCurrency), HttpStatus.OK);
    }
}
