package ru;

import ru.core.InitBot;

public class Start {
    public static void main(String[] args) {
        InitBot initBot = new InitBot();
        try {
            initBot.testMsg();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
