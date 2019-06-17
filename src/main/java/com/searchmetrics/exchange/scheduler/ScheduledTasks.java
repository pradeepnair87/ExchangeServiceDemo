package com.searchmetrics.exchange.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchmetrics.exchange.cache.ExchangeCache;
import com.searchmetrics.exchange.config.Constants;
import com.searchmetrics.exchange.constants.Currency;
import com.searchmetrics.exchange.domain.ExchangeRate;
import com.searchmetrics.exchange.services.ExternalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ScheduledTasks {

    private ObjectMapper mapper = new ObjectMapper();
    private ExternalService service;
    private String latestPriceUrl;
    private ExchangeCache cache;

    @Autowired
    public ScheduledTasks(ExternalService service, Constants constants, ExchangeCache cache) {
        this.service = service;
        this.latestPriceUrl = constants.getLatestPriceUrl();
        this.cache=cache;
        addToCache();
    }


    @Scheduled(cron = "${config.exchange.check.period.cron}")
    public void addToCache() {
        log.info("Schedules Tasks Running");
        ExchangeRate exchangeRate = service.callExternalExchangeService();
        if (exchangeRate != null)
            cache.putValue(Currency.USD, exchangeRate);

    }


}
