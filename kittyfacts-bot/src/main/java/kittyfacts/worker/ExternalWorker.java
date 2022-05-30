package kittyfacts.worker;



import kittyfacts.bot.KittyfactsBot;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;


@Configuration
@ExternalTaskSubscription("sendFactToTelegram")
public class ExternalWorker implements ExternalTaskHandler {

    private final static Logger LOGGER = Logger.getLogger(ExternalWorker.class.getName());

    @Autowired
    KittyfactsBot kittyfactsBot;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        Map<String, Object> variables = externalTask.getAllVariables();

        String fact = (String) variables.get("fact");
        byte[] picture = (byte[]) variables.get("picture");
        Long chatId = (Long) variables.get("chatId");
        try {
            kittyfactsBot.formAndSendKittyFact(chatId, fact, picture);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
        externalTaskService.complete(externalTask);
    }
}
