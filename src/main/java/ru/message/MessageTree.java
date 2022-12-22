package ru.message;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.messages.Message;

import java.util.List;
import java.util.Random;

public class MessageTree {

    ButtonsToUser buttons = new ButtonsToUser();

    public void messageTree(VkApiClient vkApi, UserActor actor, List<Message> messageList) {


        Random random = new Random();

        messageList.forEach(message -> {
            try {
                switch (message.getText().toLowerCase()) {
                    case "помощь" -> vkApi.messages().send(actor)
                            .message("Тебе нужна помощь?\nЭто я быстро подожди секунду...")
                            .userId(message.getFromId())
                            .randomId(random.nextInt(10000))
                            .keyboard(buttons.getKeyboard())
                            .execute();

                    case "где находится студия" -> vkApi.messages().send(actor)
                            .message("Вот тут находится студия")
                            .userId(message.getFromId())
                            .randomId(random.nextInt(10000))
                            .keyboard(buttons.getKeyboard())
                            .execute();

                    case "запись" -> vkApi.messages().send(actor)
                            .message("Сейчас посмотрим какие даты свободные...")
                            .userId(message.getFromId())
                            .randomId(random.nextInt(10000))
                            .keyboard(buttons.getKeyboard())
                            .execute();

                    case ".start" -> {
                        if (buttons.getKeyboard().getButtons() == null) {
                            buttons.setActionKeyboard();
                        }
                        vkApi.messages().send(actor)
                                .message("""
                                        Привет ✌
                                        Я бот для предварительной записи на тату сеанс.
                                        Вот то что я могу сделать для тебя на данный момент""")
                                .userId(message.getFromId())
                                .randomId(random.nextInt(10000))
                                .keyboard(buttons.getKeyboard())
                                .execute();
                    }
                    default -> {
                        if (buttons.getKeyboard().getButtons() == null) {
                            buttons.setStartKeyboard();
                        }
                        vkApi.messages().send(actor)
                                .message("""
                                        Привет ✌
                                        Нажми ".start" и мы начнем \uD83D\uDE09
                                        """)
                                .keyboard(buttons.getStartKeyboard())
                                .userId(message.getFromId())
                                .randomId(random.nextInt(10000))
                                .execute();
                    }
                }
            } catch (Exception ex) {
                System.out.println("Что то пошло не так при отправке MSG!" + " | " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}
