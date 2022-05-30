package com.bpmn2.kittyfacts.listener;


import com.bpmn2.kittyfacts.model.ChannelType;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("initProcessListener")
public class InitProcessListener implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger(InitProcessListener.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {



        if (delegateExecution.hasVariable("chatId")) {

            delegateExecution.setVariable("channelType",ChannelType.TELEGRAM.toString());
        } else {
            delegateExecution.setVariable("channelType",ChannelType.EMAIL.toString());
        }
    }
}
