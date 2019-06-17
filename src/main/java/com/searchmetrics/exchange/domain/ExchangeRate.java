package com.searchmetrics.exchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ExchangeRate {

    @JsonProperty("from-currency")
    private String fromCurrency;
    @JsonProperty("to-currency")
    private String toCurrency;
    @JsonProperty("exchange-rate")
    private BigDecimal exchangeRate;
    @JsonProperty("time")
    private long timestamp;

}
