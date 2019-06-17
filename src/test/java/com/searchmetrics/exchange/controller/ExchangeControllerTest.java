package com.searchmetrics.exchange.controller;

import com.searchmetrics.exchange.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:application-test.properties")
public class ExchangeControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private GenericWebApplicationContext webApplicationContext;
    private ClientAndServer mockServer;

    @Before
    public void getContext() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        assertNotNull(mockMvc);
        mockServer = startClientAndServer(18344);
    }
    @After
    public void tearDown() throws Exception {
        mockServer.stop();
    }

    @Test
    public void getLatestPrice() throws Exception {
        this.mockMvc.perform(get("/latest-price/BTC/USD").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());

    }
    @Test
    public void getLatestPriceDetails() throws Exception {
        this.mockMvc.perform(get("/latest-price/BTC/USD").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from-currency", is("BTC")))
                .andExpect(jsonPath("$.to-currency", is("USD")))
                .andExpect(jsonPath("$.exchange-rate", notNullValue()));

    }
    @Test
    public void getLatestPrice404() throws Exception {
        this.mockMvc.perform(get("/latest-price/BTC/CYN").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void getHistoricalPrice() throws Exception {
        this.mockMvc.perform(get("/historical-data/?startDate=2019-06-01&endDate=2019-06-15").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());

    }
    @Test
    public void getHistoricalPriceDetails() throws Exception {
        this.mockMvc.perform(get("/historical-data/?startDate=2019-06-01&endDate=2019-06-15").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from-currency", is("BTC")))
                .andExpect(jsonPath("$.to-currency", is("USD")))
                .andExpect(jsonPath("$.daily-exchange-price", notNullValue()));

    }
    @Test
    public void getHistoricalPrice4xxError() throws Exception {
        this.mockMvc.perform(get("/historical-data/?startDate=2019-06-01&endDate=2019/06/15").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is4xxClientError());

    }




}
