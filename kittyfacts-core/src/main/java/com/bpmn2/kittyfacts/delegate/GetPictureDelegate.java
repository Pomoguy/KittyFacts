package com.bpmn2.kittyfacts.delegate;



import com.bpmn2.kittyfacts.service.CataasClient;
import org.apache.commons.io.FileUtils;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


@Component("getPictureDelegate")
public class GetPictureDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger(GetPictureDelegate.class.getName());

    @Autowired
    CataasClient cataasClient;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void execute(DelegateExecution delegateExecution) throws IOException {
        try {
            delegateExecution.setVariable("picture", cataasClient.getCataas());
        } catch (Exception exception) {

            File inputFile = resourceLoader.getResource("classpath:/static/sry.jpg").getFile();
            delegateExecution.setVariable("picture",FileUtils.readFileToByteArray(inputFile));
            throw new BpmnError("badContent", exception.getMessage());
        }
    }
}
