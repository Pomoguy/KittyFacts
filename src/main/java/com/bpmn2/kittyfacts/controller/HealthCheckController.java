package com.bpmn2.kittyfacts.controller;

import com.bpmn2.kittyfacts.model.CatFact;
import com.bpmn2.kittyfacts.service.CatFactNinjaClient;
import com.bpmn2.kittyfacts.service.PicsumPhotosClient;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
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
    PicsumPhotosClient picsumPhotosClient;

    @GetMapping("/check/catfact")
    public CatFact healthCheckCatFact() {
        return catFactNinjaClient.getCatFact();

    }


    @GetMapping("/check/picsum")
    @Procedure("image/jpeg")
    public void healthCheckPicksum(HttpServletResponse response) throws IOException {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(picsumPhotosClient.getPicsumPhoto());
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(bis, response.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger("pomoguy").info("Error writing file to output stream.", ex);
            throw new RuntimeException("IOError writing file to output stream");
        }


    }

}
