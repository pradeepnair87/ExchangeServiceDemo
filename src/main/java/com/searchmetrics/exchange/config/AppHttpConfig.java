package com.searchmetrics.exchange.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@Slf4j
public class AppHttpConfig {

    @Autowired
    private HttpHostsConfiguration httpHostConfiguration;

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        printHttpConnectionConfigurations();
        PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
        result.setMaxTotal(this.httpHostConfiguration.getMaxTotal());
        result.setDefaultMaxPerRoute(this.httpHostConfiguration.getDefaultMaxPerRoute());
        return result;
    }

    private void printHttpConnectionConfigurations(){
        log.info("-----------rest http connection configurations-------------");
        log.info("max-total =>"+this.httpHostConfiguration.getMaxTotal());
        log.info("default-max-per-route =>"+this.httpHostConfiguration.getDefaultMaxPerRoute());
        log.info("connection-request-timeout =>"+this.httpHostConfiguration.getConnectionRequestTimeout());
        log.info("connection-timeout =>"+this.httpHostConfiguration.getConnectionTimeout());
        log.info("socket-timeout =>"+this.httpHostConfiguration.getSocketTimeout());
        log.info("-----------------------------------------------------------");
    }

    @Bean
    public RequestConfig requestConfig() {
        RequestConfig result = RequestConfig.custom()
                .setConnectionRequestTimeout(httpHostConfiguration.getConnectionRequestTimeout())
                .setConnectTimeout(httpHostConfiguration.getConnectionTimeout())
                .setSocketTimeout(httpHostConfiguration.getSocketTimeout())
                .build();
        return result;
    }

    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager, RequestConfig requestConfig) {
        CloseableHttpClient result = HttpClientBuilder
                .create()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        return result;
    }
    @Primary
    @Bean
    public RestTemplate restTemplate(HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate =new RestTemplate(requestFactory);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return new RestTemplate(requestFactory);

    }
}

