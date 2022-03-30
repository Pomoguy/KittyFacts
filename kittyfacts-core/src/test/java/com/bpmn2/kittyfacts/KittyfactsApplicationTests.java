package com.bpmn2.kittyfacts;

import com.bpmn2.kittyfacts.config.ProcessTestConfig;


import com.bpmn2.kittyfacts.stub.DelegateStub;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
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
    public void StartProcess(){


        ProcessInstance pi = processEngineRule.getRuntimeService()
                .startProcessInstanceByKey(PROCESS_KEY,
                        withVariables("email", "test@mail.com"));

        assertThat(pi).isNotNull();
        assertThat(pi).hasVariables("email");
        assertThat(pi).isStarted();

    }

    @Test
    @Deployment(resources = {"bpmn/kittyFacts.bpmn"})
    public void HappyPath(){

        ProcessInstance pi = processEngineRule.getRuntimeService()
                .startProcessInstanceByKey(PROCESS_KEY,
                        withVariables("email", "test@mail.com"));

        assertThat(pi).isNotNull();
        assertThat(pi).hasVariables("email");
        assertThat(pi).isWaitingAt("StartEvent_1");
        execute(job());
        assertThat(pi).hasPassed("GetCatFactDelegate");
        assertThat(pi).hasPassed("GetPictureDelegate");
        assertThat(pi).hasPassed("CreateContentDelegate");
        assertThat(pi).hasPassed("SendContentDelegate");
        assertThat(pi).hasVariables("email","fact","picture","content");
        assertThat(pi).isEnded();

    }


}
