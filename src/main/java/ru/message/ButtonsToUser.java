package ru.message;

import com.vk.api.sdk.objects.messages.*;

import java.util.ArrayList;
import java.util.List;

public class ButtonsToUser {

    Keyboard keyboard = new Keyboard();
    List<List<KeyboardButton>> buttons = new ArrayList<>();
    List<KeyboardButton> button = new ArrayList<>();
    List<KeyboardButton> locationButton = new ArrayList<>();

    Keyboard startKeyboard = new Keyboard();
    List<List<KeyboardButton>> StartButtons = new ArrayList<>();
    List<KeyboardButton> StartButton = new ArrayList<>();

    public void setActionKeyboard() {

        button.add(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel("Помощь")
                        .setType(TemplateActionTypeNames.TEXT))
                .setColor(KeyboardButtonColor.PRIMARY));

        button.add(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel("Запись")
                        .setType(TemplateActionTypeNames.TEXT))
                .setColor(KeyboardButtonColor.PRIMARY));

        locationButton.add(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel("Где находится студия")
                        .setType(TemplateActionTypeNames.TEXT))
                .setColor(KeyboardButtonColor.POSITIVE));

        buttons.add(button);
        buttons.add(locationButton);
        keyboard.setButtons(buttons);
    }

    public void setStartKeyboard() {

        StartButton.add(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel(".start")
                        .setType(TemplateActionTypeNames.TEXT))
                .setColor(KeyboardButtonColor.DEFAULT));

        StartButtons.add(StartButton);
        startKeyboard.setButtons(StartButtons);
    }

    public Keyboard getKeyboard() {
        return keyboard.setInline(true);
    }

    public Keyboard getStartKeyboard() {
        return startKeyboard.setOneTime(true);
    }

}
