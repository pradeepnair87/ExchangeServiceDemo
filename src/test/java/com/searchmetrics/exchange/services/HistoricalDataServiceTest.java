package com.searchmetrics.exchange.services;

import com.searchmetrics.exchange.Application;
import com.searchmetrics.exchange.config.Constants;
import com.searchmetrics.exchange.constants.Currency;
import com.searchmetrics.exchange.domain.HistoricalStats;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class HistoricalDataServiceTest {


    @Mock
    private ExternalService externalService;
    @Autowired
    private Constants constants;

    @Test
    public void getHistoricalStats() throws Exception{
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Date startDate =formatter.parse("2019-06-10");
        Date endDate =formatter.parse("2019-06-15");
        File offerContract = new ClassPathResource("historical-data-json.txt").getFile();
        String json = new Scanner(offerContract).useDelimiter("\\Z").next();
        Mockito.when(externalService.callServer(ArgumentMatchers.anyString())).thenReturn(json);

        Map<String,String> map = new HashMap<>();
        map.put("2019-06-01","8553.1267");
        map.put("2019-06-02","8737.3617");
        map.put( "2019-06-03","8105.13");
        map.put( "2019-06-04","7677.3983");
        HistoricalStats stats= HistoricalStats.builder()
                .dailyRatesMap(map)
                .toCurrency("USD")
                .fromCurrency("BTC")
                .build();
        HistoricalDataService service = new HistoricalDataService(externalService,constants);
        HistoricalStats respStats= service.getHistoricalStats(startDate,endDate, Currency.USD.getCode());
        assertEquals(respStats.getDailyRatesMap(), map);
        assertEquals(respStats.getFromCurrency(), "BTC");
        assertEquals(respStats.getToCurrency(), "USD");
    }
}
