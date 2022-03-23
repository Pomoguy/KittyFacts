package com.bpmn2.kittyfacts.controller;

import com.bpmn2.kittyfacts.model.CatFact;
import com.bpmn2.kittyfacts.service.CatFactNinjaClient;
import com.bpmn2.kittyfacts.service.CataasClient;
import org.apache.commons.io.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;



@RestController
public class HealthCheckController {


    @Autowired
    CatFactNinjaClient catFactNinjaClient;


    @Autowired
    CataasClient cataasClient;

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

}
