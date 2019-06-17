package com.searchmetrics.exchange.services;

import com.searchmetrics.exchange.Application;
import com.searchmetrics.exchange.config.Constants;
import com.searchmetrics.exchange.domain.ExchangeRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ExternalServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Autowired
    private Constants constants;


    @Test
    public void testCallExternalService()throws Exception{
        File offerContract = new ClassPathResource("latest-price-json.txt").getFile();
        String json = new Scanner(offerContract).useDelimiter("\\Z").next();
        Mockito.when(restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice/USD.json",String.class))
                .thenReturn(json);
        ExternalService service = new ExternalService(restTemplate,constants);
        ExchangeRate rate = service.callExternalExchangeService();
        assertEquals(rate.getToCurrency(), "USD");
        assertEquals(rate.getExchangeRate(), new BigDecimal(9348));
        assertEquals(rate.getFromCurrency(), "BTC");

    }

}
