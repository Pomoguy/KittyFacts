package com.bpmn2.kittyfacts.delegate;

import com.bpmn2.kittyfacts.model.CatFact;
import com.bpmn2.kittyfacts.service.CatFactNinjaClient;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("getCatFactDelegate")
public class GetCatFactDelegate implements JavaDelegate {

    @Autowired
    CatFactNinjaClient catFactNinjaClient;

    CatFact catFact;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        catFact = catFactNinjaClient.getCatFact();
        delegateExecution.setVariable("fact", catFact.getFact());

    }
}
