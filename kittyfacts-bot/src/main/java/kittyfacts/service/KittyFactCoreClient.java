package kittyfacts.service;


import kittyfacts.model.ExternalTaskRq;
import kittyfacts.model.ExternalTaskRs;
import kittyfacts.model.ProcessInstance;
import kittyfacts.model.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class KittyFactCoreClient {


    private final RestTemplate restTemplate = new RestTemplate();


    @Value("${url.kittyfacts}")
    private String url;

    @Value("${camunda.topicname}")
    private String topicName;


    @PostMapping("/start")
    public void startProcess(
            @RequestParam String email,
            @RequestParam Long chatId
    ) {
        Map<String, Object> variables = new HashMap<>();
        Map<String, String> emailMap = new HashMap<>();
        Map<String, String> chatIdMap = new HashMap<>();
        emailMap.put("value", email);
        emailMap.put("type", "String");
        chatIdMap.put("value", chatId.toString());
        chatIdMap.put("type", "Long");
        variables.put("email", emailMap);

        variables.put("chatId", chatIdMap);
        ProcessInstance processInstance = new ProcessInstance(variables);
        ResponseEntity<ProcessInstance> response = restTemplate.postForEntity(url + "/engine-rest/process-definition/key/KittyFact/start", processInstance, ProcessInstance.class);

    }

    @PostMapping("/fetchAndLock")
    public ExternalTaskRs[] fetchAndLockExternalTask() {

        Topic topic = new Topic();
        topic.setTopicName(topicName);
        topic.setLockDuration(5000);
        ExternalTaskRq externalTaskRq = new ExternalTaskRq();
        externalTaskRq.setWorkerId("TelegramWorker");
        externalTaskRq.setMaxTasks(10);
        externalTaskRq.setTopics(new Topic[]{topic});

        ResponseEntity<ExternalTaskRs[]> response = restTemplate.postForEntity(url + "/engine-rest/external-task/fetchAndLock", externalTaskRq, ExternalTaskRs[].class);

        return response.getBody();
    }

    @GetMapping("/getVariables")
    public String getVariables(@PathVariable String id, @PathVariable String varName) {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/engine-rest/process-instance/" + id + "/variables/" + varName + "?deserializeValue=true", String.class);

        return response.getBody();
    }


    @PostMapping("/complete")
    public void completeExternalTask(@PathVariable String id) {
        ExternalTaskRq externalTaskRq = new ExternalTaskRq();
        externalTaskRq.setWorkerId("TelegramWorker");
        ResponseEntity<String> response = restTemplate.postForEntity(url + "/engine-rest/external-task/" + id + "/complete",externalTaskRq, String.class);

    }


}
