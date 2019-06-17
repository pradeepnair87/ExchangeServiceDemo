package com.searchmetrics.exchange.services;

import com.searchmetrics.exchange.cache.ExchangeCache;
import com.searchmetrics.exchange.config.Constants;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Profile("test")
@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    public ExchangeCache cahe() {
        return Mockito.mock(ExchangeCache.class);
    }

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return Mockito.mock(RestTemplate.class);
    }/*
    @Bean
    @Primary
    public Constants constants() {
        return Mockito.mock(Constants.class);
    }
*/


}
