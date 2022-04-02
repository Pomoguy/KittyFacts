package com.bpmn2.kittyfacts;

import com.bpmn2.kittyfacts.config.ProcessTestConfig;



import com.bpmn2.kittyfacts.stub.DelegateStub;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.mockito.DelegateExpressions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.junit.MockitoJUnitRunner;


import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;


@RunWith(MockitoJUnitRunner.class)
public class KittyfactsApplicationTests {

    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule(ProcessTestConfig.processEngine);


    String PROCESS_KEY = "KittyFact";


    @Before
    public void Setup() {
        DelegateExpressions.autoMock("bpmn/kittyFacts.bpmn");
        //
        Mocks.register("getCatFactDelegate", new DelegateStub("fact"));
        Mocks.register("getPictureDelegate", new DelegateStub("picture"));
        Mocks.register("createContentDelegate", new DelegateStub("content"));
        Mocks.register("sendContentDelegate", new DelegateStub("email"));


    }

    @After
    public void Teardown() {
        Mocks.reset();
    }

    @Test
    @Deployment(resources = {"bpmn/kittyFacts.bpmn"})
    public void StartProcess() {


        ProcessInstance pi = processEngineRule.getRuntimeService()
                .startProcessInstanceByKey(PROCESS_KEY,
                        withVariables("email", "test@mail.com"));

        assertThat(pi).isNotNull();
        assertThat(pi).hasVariables("email");
        assertThat(pi).isStarted();

    }

    @Test
    @Deployment(resources = {"bpmn/kittyFacts.bpmn", "bpmn/sendEmail.bpmn"})
    public void HappyPath() {

        ProcessInstance pi = processEngineRule.getRuntimeService()
                .startProcessInstanceByKey(PROCESS_KEY,
                        withVariables("email", "test@mail.com"));

        assertThat(pi).isNotNull();
        assertThat(pi).hasVariables("email");
        assertThat(pi).isWaitingAt("StartEvent_1");
        execute(job());
        assertThat(pi).hasPassed("GetCatFactDelegate");
        assertThat(pi).hasPassed("GetPictureDelegate");
        assertThat(pi).hasVariables("kittyFact");
        assertThat(pi).isWaitingAt("Send_email_Error");
        execute(processEngineRule
                .getManagementService()
                .createJobQuery()
                .active()
                .processDefinitionKey("SendEmail")
                .singleResult());
        assertThat(pi).hasPassed("Send_Email_Success");
        /*
        Так и не получилось запилить проверку прохождения шагов внутри вызванной активити
        */
        assertThat(pi).isEnded();
    }

    @Test
    @Deployment(resources = {"bpmn/kittyFacts.bpmn", "bpmn/sendEmail.bpmn"})
    public void ErrorPath() {

        Mocks.register("getPictureDelegate", new DelegateStub("exception"));

        ProcessInstance pi = processEngineRule.getRuntimeService()
                .startProcessInstanceByKey(PROCESS_KEY,
                        withVariables("email", "test@mail.com"));

        assertThat(pi).isNotNull();
        assertThat(pi).hasVariables("email");
        assertThat(pi).isWaitingAt("StartEvent_1");
        execute(job());
        assertThat(pi).hasPassed("GetCatFactDelegate");
        assertThat(pi).hasVariables("kittyFact");
        assertThat(pi).isWaitingAt("Send_email_Error");
        execute(processEngineRule
                .getManagementService()
                .createJobQuery()
                .active()
                .processDefinitionKey("SendEmail")
                .singleResult());
        assertThat(pi).hasPassed("Send_email_Error");

        assertThat(pi).isEnded();
    }


}
