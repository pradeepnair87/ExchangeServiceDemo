package com.searchmetrics.exchange.services;

import com.searchmetrics.exchange.Application;
import com.searchmetrics.exchange.cache.ExchangeCache;
import com.searchmetrics.exchange.constants.Currency;
import com.searchmetrics.exchange.domain.ExchangeRate;
import com.searchmetrics.exchange.services.ExchangeRateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ExchangeRateServiceTest {

    @Autowired
    private ExchangeCache cache;

    @Test
    public void  getLatestPriceFromCache(){
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .exchangeRate(new BigDecimal(9002.11))
                .fromCurrency(Currency.BTC.getCode())
                .toCurrency(Currency.USD.getCode())
                .timestamp(123123333)
                .build();
        Mockito.when(cache.getValue(Currency.USD)).thenReturn(exchangeRate);

        ExchangeRateService service = new ExchangeRateService(cache);
        ExchangeRate resp = service.getLatestPrice(Currency.BTC,Currency.USD);
        assertEquals(resp.getExchangeRate(), exchangeRate.getExchangeRate());
        assertEquals(resp.getFromCurrency(), exchangeRate.getFromCurrency());
        assertEquals(resp.getToCurrency(), exchangeRate.getToCurrency());

    }

}
