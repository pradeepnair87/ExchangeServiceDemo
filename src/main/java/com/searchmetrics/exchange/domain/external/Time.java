package com.searchmetrics.exchange.domain.external;

import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class Time {

   private String updated;
   private String updatedISO;
    private String updateduk;
   private static DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss z");
   private long timeStamp;

   public long getTimeStamp() throws ParseException{
       if(timeStamp==0) {
           Date d = formatter.parse(updated);
           timeStamp = d.getTime();
       }
       return timeStamp;
   }
}
