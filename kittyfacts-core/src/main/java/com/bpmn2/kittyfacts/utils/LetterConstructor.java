package com.bpmn2.kittyfacts.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LetterConstructor {

    @Autowired
    Configuration configuration;


    public byte[] constructKittyFactLetterContent(DelegateExecution delegateExecution) throws IOException, TemplateException {

        Map<String, Object> model = new HashMap<>();
        model.put("email", delegateExecution.getVariable("email"));
        model.put("fact", delegateExecution.getVariable("fact"));
        String imgDataAsBase64 = (String) delegateExecution.getVariable("picture");
        String imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
        model.put("imgAsBase64", imgAsBase64);
        Template template = configuration.getTemplate("email.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model).getBytes(StandardCharsets.UTF_8);
    }

    public byte[] constructFailedLetterContent(DelegateExecution delegateExecution) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("email", delegateExecution.getVariable("email"));
        Template template = configuration.getTemplate("sry.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model).getBytes(StandardCharsets.UTF_8);
    }



}
