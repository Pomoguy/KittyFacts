package com.bpmn2.kittyfacts.delegate;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component("createContentDelegate")
public class CreateContentDelegate implements JavaDelegate {

    @Autowired
    Configuration configuration;


    @Override
    public void execute(DelegateExecution delegateExecution) throws TemplateException, IOException {


        Map<String, Object> model = new HashMap<>();
        model.put("email", delegateExecution.getVariable("email"));
        model.put("fact", delegateExecution.getVariable("fact"));
        byte[] imgBytesAsBase64 = Base64.encodeBase64((byte[]) delegateExecution.getVariable("picture"));
        String imgDataAsBase64 = new String(imgBytesAsBase64);
        String imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;

        model.put("imgAsBase64", imgAsBase64);
        Template template = configuration.getTemplate("email.ftl");
        byte[] html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model).getBytes(StandardCharsets.UTF_8);
        delegateExecution.setVariable("content", html);

    }
}
