package com.bpmn2.kittyfacts.listener;

import com.bpmn2.kittyfacts.model.ChannelType;
import com.bpmn2.kittyfacts.model.KittyFact;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("initProcessListener")
public class InitProcessListener implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        KittyFact kittyFact = new KittyFact((String) delegateExecution.getVariable("email"));
        if (delegateExecution.hasVariable("chatId")) {
            kittyFact.setChatId((Long) delegateExecution.getVariable("chatId"));
            kittyFact.setChannelType(ChannelType.TELEGRAM);
        } else {
            kittyFact.setChannelType(ChannelType.EMAIL);
        }

        ObjectValue objectValue = Variables.objectValue(kittyFact).serializationDataFormat("application/json").create();
        delegateExecution.setVariable("kittyFact", objectValue);
    }
}
