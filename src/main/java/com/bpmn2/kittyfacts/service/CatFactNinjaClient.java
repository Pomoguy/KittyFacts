package com.bpmn2.kittyfacts.service;

import com.bpmn2.kittyfacts.model.CatFact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;



@Component
public class CatFactNinjaClient {

    @Value("${url.catfact}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();



    public CatFact getCatFact() {
        return restTemplate.getForObject(url + "/fact", CatFact.class);
    }



}
