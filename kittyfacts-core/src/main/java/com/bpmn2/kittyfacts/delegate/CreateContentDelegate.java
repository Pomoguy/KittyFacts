package com.bpmn2.kittyfacts.delegate;


import com.bpmn2.kittyfacts.model.KittyFact;
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
        byte[] html;
        KittyFact kittyFact = (KittyFact) delegateExecution.getVariable("kittyFact");

        if (!delegateExecution.hasVariable("badContent")) {
            html = constructKittyFactLetterContent(kittyFact);
        } else {
            html = constructFailedLetterContent(kittyFact);
        }
        delegateExecution.setVariable("content", html);
    }



    public byte[] constructKittyFactLetterContent(KittyFact kittyFact) throws IOException, TemplateException {

        Map<String, Object> model = new HashMap<>();
        model.put("email", kittyFact.getEmail());
        model.put("fact", kittyFact.getFact());
        String imgDataAsBase64 = new String(kittyFact.getPicture());
        String imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
        model.put("imgAsBase64", imgAsBase64);
        Template template = configuration.getTemplate("email.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model).getBytes(StandardCharsets.UTF_8);
    }

    public byte[] constructFailedLetterContent(KittyFact kittyFact) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("email", kittyFact.getEmail());
        Template template = configuration.getTemplate("sry.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model).getBytes(StandardCharsets.UTF_8);
    }


}
