package com.searchmetrics.exchange.cache;

import com.searchmetrics.exchange.constants.Currency;
import com.searchmetrics.exchange.domain.ExchangeRate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ExchangeCache {

    /**
     * Change this to Map<Currency,Map<Currency,ExchangeRate>, to handle any type of currency
     */
    private  Map<Currency,ExchangeRate> cache = new ConcurrentHashMap<>();

    public  ExchangeRate getValue(Currency key){
        return cache.get(key); // create a copy and return the copied object
    }

    public  void  putValue(Currency key,ExchangeRate rate){
         cache.put(key,rate);
    }

}
