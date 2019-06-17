package com.searchmetrics.exchange.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class Constants {
    @Value("${config.exchange.server.latest-price.url}")
    private String latestPriceUrl;
    @Value("${config.exchange.server.historical-data.url}")
    private String historicalDataUrl;


}
