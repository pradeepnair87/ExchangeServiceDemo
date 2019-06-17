package com.searchmetrics.exchange.domain.external;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class CoindeskResponse {

   //Powered by CoinDesk
   private Map<String,CurrencyRate> bpi;
   private String chartName;
   private String disclaimer;
   private Time time;

}
