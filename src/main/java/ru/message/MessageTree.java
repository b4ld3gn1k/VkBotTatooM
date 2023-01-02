package ru.message;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.messages.Message;
import ru.portfolio.Portfolio;

import java.util.List;
import java.util.Random;

public class MessageTree {

    ButtonsToUser buttons = new ButtonsToUser();

    public void messageTree(VkApiClient vkApi, UserActor actor, List<Message> messageList) {

        Portfolio portfolio = new Portfolio();
        Random random = new Random();

        messageList.forEach(message -> {
            try {
                switch (message.getText().toLowerCase()) {
                    case "работы мастера" -> {
                        vkApi.messages().send(actor)
                                .message("""
                                        Загружаю работы...
                                        """)
                                .userId(message.getFromId())
                                .randomId(random.nextInt(10000))
                                .execute();
                        portfolio.getWorks(vkApi, actor, message.getFromId());
                        vkApi.messages()
                                .send(actor).message("Ну как работы?\uD83D\uDE0A\nПонравились?\uD83D\uDE0A\uD83D\uDE4F")
                                .userId(message.getFromId())
                                .randomId(random.nextInt(10000))
                                .keyboard(buttons.getKeyboard())
                                .execute();
                    }

                    case "адресс студии" -> vkApi.messages().send(actor)
                            .message("Студия находится вот здесь")
                            .userId(message.getFromId())
                            .randomId(random.nextInt(10000))
                            .keyboard(buttons.getKeyboard())
                            .execute();

                    case "записаться на сеанс" -> vkApi.messages().send(actor)
                            .message("Сейчас посмотрим какие даты свободные...")
                            .userId(message.getFromId())
                            .randomId(random.nextInt(10000))
                            .keyboard(buttons.getKeyboard())
                            .execute();

                    case "основы по заживлению" -> vkApi.messages().send(actor)
                            .message("""
                                    Вот основыные правила по уходу за татуировкой:
                                    1. ...
                                    2. ...
                                    3. ...
                                    4. ...""")
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
                            buttons.setActionKeyboard();
                        }
                        vkApi.messages().send(actor)
                                .message("""
                                        Я не совсем понял тебя 😥
                                        Выбери пожалуйста из предложенного списка
                                        """)
                                .keyboard(buttons.getKeyboard())
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
