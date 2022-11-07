package ru.tatoobot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;

public class MainBot {
    private static final int VK_GROUP_ID = 197471266;
    private static final String GROUP_TOKEN = "vk1.a.usi04YenfFKy67JQ1o3zCPQufZrJmyRTeTFqn7qrEBok7BwP6OKRA0uPOOXVrnHz40qrm654JxEk9jZS2FRFQiVpEplI8vM6KGI1wY0S_uJTzZ6vdQT-ACtZJtfES87IHGskWDY2YXzXtQ2aS936tQF3saFiQB3mIxyb_pduI1PXnYLiYuPBwXWFgKJlXTu4X3wAlyDrq4Z4fcHXQkRrUA";

    public static void main(String[] args) throws ClientException, ApiException, InterruptedException
    {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        Random random = new Random();
        Keyboard keyboard = new Keyboard();

        List<List<KeyboardButton>> msgButtons = new ArrayList<>();
        List<KeyboardButton> firsLineButton = new ArrayList<>();
        firsLineButton.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Привет").setType(TemplateActionTypeNames.TEXT)).setColor(KeyboardButtonColor.PRIMARY));
        firsLineButton.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Свободные даты").setType(TemplateActionTypeNames.TEXT)).setColor(KeyboardButtonColor.DEFAULT));
        firsLineButton.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Партфолио").setType(TemplateActionTypeNames.TEXT)).setColor(KeyboardButtonColor.POSITIVE));
        firsLineButton.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Записаться").setType(TemplateActionTypeNames.TEXT)).setColor(KeyboardButtonColor.NEGATIVE));

        msgButtons.add(firsLineButton);
        keyboard.setButtons(msgButtons);

        GroupActor actor = new GroupActor(VK_GROUP_ID, GROUP_TOKEN);

        Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();

        while (true){
            MessagesGetLongPollHistoryQuery historyQuery =  vk.messages().getLongPollHistory(actor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            if (!messages.isEmpty()){
                messages.forEach(message -> {
                    System.out.println(message.toString());
                    try {
                        if (message.getText().equals("Привет")){
                            vk.messages().send(actor).message("Привет!").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                        else if (message.getText().equals("Кто я?")) {
                            vk.messages().send(actor).message("Ты хороший человек.").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                        else if (message.getText().equals("Кнопки")) {
                            vk.messages().send(actor).message("А вот и они").userId(message.getFromId()).randomId(random.nextInt(10000)).keyboard(keyboard).execute();
                        }
                        else {
                            vk.messages().send(actor).message("Я тебя не понял.").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                    }
                    catch (ApiException | ClientException e) {e.printStackTrace();}
                });
            }
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
            Thread.sleep(500);
        }

    }
}
