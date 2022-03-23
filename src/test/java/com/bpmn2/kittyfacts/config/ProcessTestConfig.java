package com.bpmn2.kittyfacts.config;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;




public class ProcessTestConfig {
    public static ProcessEngine processEngine = StandaloneInMemProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();
}
