package com.bpmn2.kittyfacts.delegate;

import com.bpmn2.kittyfacts.model.CatFact;
import com.bpmn2.kittyfacts.service.CatFactNinjaClient;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Component("getCatFactDelegate")
public class GetCatFactDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger(GetCatFactDelegate.class.getName());

    @Autowired
    CatFactNinjaClient catFactNinjaClient;

    CatFact catFact;


    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            delegateExecution.setVariable("fact", catFactNinjaClient.getCatFact().getFact());
        } catch (Exception exception) {
            throw new BpmnError("badContent", exception.getMessage());
        }
    }
}
