package com.bpmn2.kittyfacts.service;

import org.springframework.beans.factory.annotation.Value;


import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


import javax.ws.rs.Consumes;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;


@Component
public class PicsumPhotosClient {

    @Value("${url.picsum}")
    private String url;


    private final RestTemplate restTemplate = new RestTemplate();

    @Consumes("image/jpeg")
    public byte[] getPicsumPhoto() throws IOException {

        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        byte[] imageBytes = restTemplate.getForObject(url + "/1000/1000", byte[].class);
        return imageBytes;
        
    }

}
