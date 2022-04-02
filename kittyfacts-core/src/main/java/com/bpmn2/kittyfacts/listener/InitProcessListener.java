package com.bpmn2.kittyfacts.listener;

import com.bpmn2.kittyfacts.model.KittyFact;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.springframework.stereotype.Component;

@Component("initProcessListener")
public class InitProcessListener implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        KittyFact kittyFact = new KittyFact((String) delegateExecution.getVariable("email"));

        delegateExecution.setVariable("kittyFact", kittyFact);
    }
}
