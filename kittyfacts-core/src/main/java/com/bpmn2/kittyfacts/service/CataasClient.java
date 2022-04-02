package com.bpmn2.kittyfacts.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.Consumes;
import java.io.IOException;


@Component
public class CataasClient {

    @Value("${url.cataas}")
    private String url;


    private final RestTemplate restTemplate = new RestTemplate();

    @Consumes("image/jpeg")
    public byte[] getCataas() throws IOException {

        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        byte[] imageBytes = restTemplate.getForObject(url + "/cat", byte[].class);
        return imageBytes;

    }

}
