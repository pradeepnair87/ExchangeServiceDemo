package com.searchmetrics.exchange.domain.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalBpi {
    private Map<String,String> bpi;
}
