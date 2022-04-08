package com.bpmn2.kittyfacts.stub;


import com.bpmn2.kittyfacts.model.KittyFact;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;

public class DelegateStub implements JavaDelegate {

    String type;

    public DelegateStub(String type) {
        this.type = type;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ObjectValue objectValue =  delegateExecution.getVariableTyped("kittyFact");
        KittyFact kittyFact = objectValue.getValue(KittyFact.class);
        switch (type) {
            case "fact":
                kittyFact.setFact("Somefact");
                delegateExecution.setVariable("kittyFact", kittyFact);
                break;
            case "picture":
                kittyFact.setPicture(type.getBytes());
                delegateExecution.setVariable("kittyFact", kittyFact);
                break;
            case "content":
                delegateExecution.setVariable("content", "SomeContent");
                break;
            case "email":
                //do nothing
            case "exception":
                throw new BpmnError("badContent");
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

    }
}
