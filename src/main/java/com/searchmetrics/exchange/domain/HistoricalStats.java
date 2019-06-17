package com.searchmetrics.exchange.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@Builder
public class HistoricalStats {

    @JsonProperty("from-currency")
    private  String fromCurrency;
    @JsonProperty("to-currency")
    private String toCurrency;
    @JsonProperty("daily-exchange-price")
    private Map<String,String> dailyRatesMap;

}
