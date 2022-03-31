package com.bpmn2.kittyfacts.delegate;

import com.bpmn2.kittyfacts.model.KittyFact;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;



import org.springframework.stereotype.Component;

@Component("initProcessDelegate")
public class InitProcessDelegate implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

        KittyFact kittyFact = new KittyFact((String) delegateExecution.getVariable("email"));

        delegateExecution.setVariable("kittyFact", kittyFact);
    }
}
