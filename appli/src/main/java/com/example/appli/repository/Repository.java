package com.example.appli.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.example.appli.configuration.CustomProperties;

public class Repository {

    @Autowired
    CustomProperties properties;
    
    String baseUrlApi;

    RestTemplate restTemplate;
    
    public Repository(CustomProperties customProperties){
        properties = customProperties;
        baseUrlApi = properties.getApiURL();
        restTemplate = new RestTemplate();
    }
    
    public Repository(){}
}