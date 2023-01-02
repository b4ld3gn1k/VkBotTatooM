package ru.core;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.message.MessageTree;
import ru.portfolio.Portfolio;

import java.util.Date;
import java.util.List;


public class InitBot {
    private static final String key = "vk1.a.usi04YenfFKy67JQ1o3zCPQufZrJmyRTeTFqn7qrEBok7BwP6OKRA0uPOOXVrnHz40qrm654JxEk9jZS2FRFQiVpEplI8vM6KGI1wY0S_uJTzZ6vdQT-ACtZJtfES87IHGskWDY2YXzXtQ2aS936tQF3saFiQB3mIxyb_pduI1PXnYLiYuPBwXWFgKJlXTu4X3wAlyDrq4Z4fcHXQkRrUA";
    private static final int id = 197471266;

    private static Logger logger;

    TransportClient transportClient = new HttpTransportClient();
    VkApiClient vk = new VkApiClient(transportClient);
    UserActor userActor = new UserActor(id, key);

    MessageTree msgTree = new MessageTree();

    public void startBot() throws ClientException, ApiException, InterruptedException {
        logger = LogManager.getRootLogger();

        Integer ts = vk.messages().getLongPollServer(userActor).execute().getTs();



        while (true) {

            MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(userActor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();

            msgTree.messageTree(vk, userActor, messages);

            getMsgAndId(messages);

            ts = vk.messages().getLongPollServer(userActor).execute().getTs();
            Thread.sleep(500);
        }
    }

    private void getMsgAndId(List<Message> msg) {
        msg.forEach(message ->
            System.out.println("ID пользователя: " + message.getPeerId() + " | "
                    + "Отправленное сообщение: " + message.getText() + " | "
                    + "Время отправки сообщения: " + new Date(System.currentTimeMillis())));
    }

}
