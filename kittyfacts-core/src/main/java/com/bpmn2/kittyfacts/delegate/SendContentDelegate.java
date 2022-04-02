package com.bpmn2.kittyfacts.delegate;

import com.bpmn2.kittyfacts.model.KittyFact;
import com.bpmn2.kittyfacts.service.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("sendContentDelegate")
public class SendContentDelegate implements JavaDelegate {


    @Autowired
    private EmailService emailService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        KittyFact kittyFact = (KittyFact) delegateExecution.getVariable("kittyFact");
        String content = new String((byte[]) delegateExecution.getVariable("content"));
        emailService.sendEmail(kittyFact.getEmail(), content);
    }
}
