package com.searchmetrics.exchange.constants;

import lombok.Getter;

@Getter
public enum Currency {

    USD("USD","U.S Dollars"),BTC("BTC","Bitcoin"),GBP("GBP","British Pound Sterling");

    private String code;
    private String description;
     Currency(String code, String description){
        this.code=code;
        this.description=description;
    }


}
