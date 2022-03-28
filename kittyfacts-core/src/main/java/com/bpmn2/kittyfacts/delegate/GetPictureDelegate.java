package com.bpmn2.kittyfacts.delegate;



import com.bpmn2.kittyfacts.service.CataasClient;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("getPictureDelegate")
public class GetPictureDelegate implements JavaDelegate {

    @Autowired
    CataasClient cataasClient;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        delegateExecution.setVariable("picture", cataasClient.getCataas());

    }
}
