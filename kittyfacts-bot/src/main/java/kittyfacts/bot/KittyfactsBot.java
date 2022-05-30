package kittyfacts.bot;

import org.apache.commons.codec.binary.Base64;

import org.camunda.community.rest.client.api.ProcessDefinitionApi;
import org.camunda.community.rest.client.dto.ProcessInstanceWithVariablesDto;
import org.camunda.community.rest.client.dto.StartProcessInstanceDto;
import org.camunda.community.rest.client.dto.VariableValueDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class KittyfactsBot extends TelegramLongPollingBot {

    @Value("${telegram.bot-name}")
    String botName;
    @Value("${telegram.bot-token}")
    String botToken;

    @Value("${camunda.bpm.processkey}")
    String processKey;

    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;


    @Autowired
    private ProcessDefinitionApi processDefinitionApi;


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    ;

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        long chatId = message.getChatId();
        try {
            if (Objects.equals(message.getText(), "Get interesting fact")) {
                Map<String, VariableValueDto> variables = new HashMap<>();
                variables.put("email", new VariableValueDto().value("any@mail.com").type("String"));
                variables.put("chatId", new VariableValueDto().value(chatId).type("Long"));

                processDefinitionApi.startProcessInstanceByKey(
                        processKey, new StartProcessInstanceDto().variables(variables));

                sendNotification(chatId, getRandomString());
            } else {
                String responseText = message.hasText() ? getRandomString() : "I don't understand, i'm kitty :(";
                sendNotification(chatId, responseText);
            }
        } catch (TelegramApiException | ApiException e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(Long chatId, String message) throws TelegramApiException {
        SendMessage responseMessage = new SendMessage(chatId.toString(), message);
        responseMessage.enableMarkdown(true);
        responseMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());

        execute(responseMessage);
    }

    public void formAndSendKittyFact(Long chatId, String fact, byte[] picture) throws TelegramApiException, IOException {


        SendPhoto kittyfact = new SendPhoto();
        kittyfact.setChatId(chatId.toString());
        //byte[] imgBytesAsBase64 = Base64.decodeBase64(picture);
        kittyfact.setPhoto(new InputFile(new ByteArrayInputStream(picture), "kitty.png"));
        kittyfact.setCaption(fact);
        execute(kittyfact);
    }


    private String getRandomString() {
        int random = (int) (Math.random() * 3);
        switch (random) {
            case 1:
                return "Hmm, let me think...meow";
            case 2:
                return "Can you stroke me first? Prrr...";
            case 3:
                return "Don't touch me, shhh";
            default:
                return "Meow";
        }
    }


}
