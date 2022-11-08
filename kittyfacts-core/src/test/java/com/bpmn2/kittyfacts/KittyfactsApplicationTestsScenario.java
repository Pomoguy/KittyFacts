package com.bpmn2.kittyfacts;



import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.extension.mockito.DelegateExpressions;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import java.util.Map;
import java.util.Random;


import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.withVariables;
import static org.camunda.bpm.extension.mockito.DelegateExpressions.autoMock;
import static org.mockito.Mockito.*;

@Deployment(resources = {"bpmn/kittyFacts.bpmn", "bpmn/sendContent.bpmn"})
public class KittyfactsApplicationTestsScenario {

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();


    ProcessScenario kittyApplication = mock(ProcessScenario.class);
    ProcessScenario sendApplication = mock(ProcessScenario.class);

    private Map<String, Object> variables;


    @Before
    public void defineHappyScenario() {

        variables = Variables.createVariables();


        when(kittyApplication.waitsAtServiceTask("GetCatFactDelegate")).thenReturn((task) ->
                task.complete(withVariables("fact", "SomeFact"))
        );

        byte[] b = new byte[20];
        new Random().nextBytes(b);
        when(kittyApplication.waitsAtServiceTask("GetPictureDelegate")).thenReturn((task) ->
                task.complete(withVariables("picture", b))
        );

        when(kittyApplication.runsCallActivity("Send_Success"))
                .thenReturn(Scenario.use(sendApplication));

        when(kittyApplication.runsCallActivity("Send_Error"))
                .thenReturn(Scenario.use(sendApplication));

        when(sendApplication.waitsAtServiceTask("CreateContentDelegate")).thenReturn((task) ->
                task.complete(withVariables("content", b))
        );

        when(sendApplication.waitsAtServiceTask("SendContentDelegate")).thenReturn((task) ->
                task.complete()
        );

        when(sendApplication.waitsAtServiceTask("SendFactToTelegram")).thenReturn((externalTask) -> {
                    //  assertThat(externalTask).hasTopicName("sendFactToTelegram");
                    externalTask.complete();
                }
        );

    }


    @Test
    public void testEmailPath() {

        autoMock("bpmn/kittyFacts.bpmn");
        autoMock("bpmn/sendContent.bpmn");

        variables.put("email", "some@mail.ru");

        Scenario.run(kittyApplication)
                .startByKey("KittyFact", variables)
                .execute();

        verify(kittyApplication).hasCompleted("Collect_Data");
        verify(kittyApplication, times(1)).hasFinished("KittyFact_EndEvent_1");
        verify(sendApplication, times(1)).hasFinished("SendContent_EndEvent_1");
    }

    @Test
    public void testTelegramPath() {

        autoMock("bpmn/kittyFacts.bpmn");
        autoMock("bpmn/sendContent.bpmn");

        variables.put("chatId", 12341);

        Scenario.run(kittyApplication)
                .startByKey("KittyFact", variables)
                .execute();

        verify(kittyApplication).hasCompleted("Collect_Data");
        verify(kittyApplication, times(1)).hasFinished("KittyFact_EndEvent_1");
        verify(sendApplication, times(1)).hasFinished("SendContent_EndEvent_2");
    }


    @Test
    public void testErrorEmailPath() {

        autoMock("bpmn/kittyFacts.bpmn");
        autoMock("bpmn/sendContent.bpmn");
        DelegateExpressions.registerJavaDelegateMock("getPictureDelegate").onExecutionThrowBpmnError("badContent", "test");

        variables.put("email", "some@mail.ru");


        Scenario.run(kittyApplication)
                .startByKey("KittyFact", variables)
                .execute();

        verify(kittyApplication, times(1)).hasFinished("KittyFact_EndEvent_2");
        verify(sendApplication, times(1)).hasFinished("SendContent_EndEvent_1");
    }


    @Test
    public void testErrorTelegramPath() {

        autoMock("bpmn/kittyFacts.bpmn");
        autoMock("bpmn/sendContent.bpmn");
        DelegateExpressions.registerJavaDelegateMock("getPictureDelegate").onExecutionThrowBpmnError("badContent", "test");

        variables.put("chatId", 12341);


        Scenario.run(kittyApplication)
                .startByKey("KittyFact", variables)
                .execute();

        verify(kittyApplication, times(1)).hasFinished("KittyFact_EndEvent_2");
        verify(sendApplication, times(1)).hasFinished("SendContent_EndEvent_2");
    }


}
