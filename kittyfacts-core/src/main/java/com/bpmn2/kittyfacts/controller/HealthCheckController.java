package com.bpmn2.kittyfacts.controller;

import com.bpmn2.kittyfacts.model.CatFact;
import com.bpmn2.kittyfacts.service.CatFactNinjaClient;
import com.bpmn2.kittyfacts.service.CataasClient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;



@RestController
public class HealthCheckController {


    @Autowired
    CatFactNinjaClient catFactNinjaClient;


    @Autowired
    CataasClient cataasClient;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/check/catfact")
    public CatFact healthCheckCatFact() {
        return catFactNinjaClient.getCatFact();

    }

    @GetMapping("/check/cataas")
    @Procedure("image/jpeg")
    public void healthCheckCataas(HttpServletResponse response) throws IOException {

            ByteArrayInputStream bis = new ByteArrayInputStream(cataasClient.getCataas());
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(bis, response.getOutputStream());

    }

    @GetMapping("/check/fail")
    @Procedure("image/jpeg")
    public void healthCheckFail(HttpServletResponse response) throws IOException {

        File inputFile = resourceLoader.getResource("classpath:/static/sry.jpg").getFile();
        ByteArrayInputStream bis = new ByteArrayInputStream(FileUtils.readFileToByteArray(inputFile));
        IOUtils.copy(bis, response.getOutputStream());

    }



}
