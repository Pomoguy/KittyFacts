package kittyfacts.service;


import com.jayway.restassured.path.json.JsonPath;
import kittyfacts.bot.KittyfactsBot;
import kittyfacts.model.ExternalTaskRs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Service
public class ExternalWorkService {
    @Autowired
    KittyFactCoreClient kittyFactCoreClient;

    @Autowired
    KittyfactsBot kittyfactsBot;

    @Scheduled(initialDelayString = "${scheduler.delay}", fixedDelayString = "${scheduler.delay}")
    public void doExternalWork() throws InterruptedException, TelegramApiException, IOException {

        ExternalTaskRs[] externalTasksRs = kittyFactCoreClient.fetchAndLockExternalTask();
        if (externalTasksRs.length == 0) {
            TimeUnit.SECONDS.sleep(5);
        } else {
            for (ExternalTaskRs externalTaskRs : externalTasksRs) {
                String processId = externalTaskRs.getProcessInstanceId();
                String id = externalTaskRs.getId();
                String json = kittyFactCoreClient.getVariables(processId, "kittyFact");
                String fact = JsonPath.from(json).getString("value.fact");
                byte[] picture = JsonPath.from(json).getString("value.picture").getBytes();
                Long chatId = JsonPath.from(json).getLong("value.chatId");
                kittyfactsBot.formAndSendKittyFact(chatId, fact, picture);
                TimeUnit.SECONDS.sleep(1);
                kittyFactCoreClient.completeExternalTask(id);
            }
        }
    }

}
