package com.bpmn2.kittyfacts.stub;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DelegateStub implements JavaDelegate {

    String type;

    public DelegateStub(String type) {
        this.type = type;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        switch (type) {
            case "fact":
                delegateExecution.setVariable("fact", "SomeFact");
            case "picture":
                delegateExecution.setVariable("picture", "SomePicture");
            case "content":
                delegateExecution.setVariable("content", "SomeContent");
            case "email":
                //do nothing
        }
    }
}
