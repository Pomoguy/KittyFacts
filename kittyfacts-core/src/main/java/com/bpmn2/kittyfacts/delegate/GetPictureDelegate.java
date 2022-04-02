package com.bpmn2.kittyfacts.delegate;


import com.bpmn2.kittyfacts.model.KittyFact;
import com.bpmn2.kittyfacts.service.CataasClient;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component("getPictureDelegate")
public class GetPictureDelegate implements JavaDelegate {

    @Autowired
    CataasClient cataasClient;

    @Override
    public void execute(DelegateExecution delegateExecution) {

        try {
            ObjectValue objectValue = delegateExecution.getVariableTyped("kittyFact");
            KittyFact kittyFact = objectValue.getValue(KittyFact.class);
            kittyFact.setPicture(cataasClient.getCataas());
            delegateExecution.setVariable("kittyFact", kittyFact);
        } catch (Exception exception) {
            throw new BpmnError("badContent", exception.getMessage());
        }
    }
}
