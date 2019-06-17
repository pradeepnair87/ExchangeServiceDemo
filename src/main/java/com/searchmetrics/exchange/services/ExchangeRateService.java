package com.searchmetrics.exchange.services;

import com.searchmetrics.exchange.cache.ExchangeCache;
import com.searchmetrics.exchange.constants.Currency;
import com.searchmetrics.exchange.domain.ExchangeRate;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateService {

    private ExchangeCache cache;

    public ExchangeRateService(ExchangeCache cache){
        this.cache=cache;
    }
    public ExchangeRate getLatestPrice(Currency from, Currency to) {
        ExchangeRate rate = cache.getValue(to);
        return rate;

    }
}

