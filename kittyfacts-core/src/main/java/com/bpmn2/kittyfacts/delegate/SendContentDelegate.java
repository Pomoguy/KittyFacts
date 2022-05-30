package com.bpmn2.kittyfacts.delegate;


import com.bpmn2.kittyfacts.service.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Component("sendContentDelegate")
public class SendContentDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger(SendContentDelegate.class.getName());

    @Autowired
    private EmailService emailService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String content = new String((byte[]) delegateExecution.getVariable("content"));
        emailService.sendEmail((String) delegateExecution.getVariable("email"), content);
    }
}
