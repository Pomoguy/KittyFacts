package com.bpmn2.kittyfacts.delegate;



import com.bpmn2.kittyfacts.utils.LetterConstructor;
import freemarker.template.TemplateException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.logging.Logger;


@Component("createContentDelegate")
public class CreateContentDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger(CreateContentDelegate.class.getName());

    LetterConstructor letterConstructor;


    @Override
    public void execute(DelegateExecution delegateExecution) throws TemplateException, IOException {
        byte[] html;

        if (!delegateExecution.hasVariable("badContent")) {
            html = letterConstructor.constructKittyFactLetterContent(delegateExecution);
        } else {
            html = letterConstructor.constructFailedLetterContent(delegateExecution);
        }
        delegateExecution.setVariable("content", html);
    }





}
