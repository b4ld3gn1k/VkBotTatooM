package ru.portfolio;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.photos.responses.*;
import com.vk.api.sdk.queries.upload.UploadPhotoMessageQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Portfolio {

    public void getWorks(VkApiClient vk, UserActor actor, Integer userId) {

        try {
            GetMessagesUploadServerResponse serverResponse = vk.photos().getMessagesUploadServer(actor).execute();
            String uri = String.valueOf(serverResponse.getUploadUrl());
            List<UploadPhotoMessageQuery> paths = new ArrayList<>();
            paths.add(vk.upload().photoMessage(uri, new File("tattoPhoto/1.jpg")));
            paths.add(vk.upload().photoMessage(uri, new File("tattoPhoto/2.jpg")));
            paths.add(vk.upload().photoMessage(uri, new File("tattoPhoto/3.jpg")));
            paths.add(vk.upload().photoMessage(uri, new File("tattoPhoto/4.jpg")));
            for (int i = 0; i < paths.size(); i++) {
                MessageUploadResponse uploadResponse = paths.get(i).execute();
                List<SaveMessagesPhotoResponse> photoList = vk.photos().saveMessagesPhoto(actor, uploadResponse.getPhoto())
                        .server(uploadResponse.getServer())
                        .hash(uploadResponse.getHash())
                        .execute();

                SaveMessagesPhotoResponse photo = photoList.get(0);
                String attachid = "photo" + photo.getOwnerId() + "_" + photo.getId();
                vk.messages().send(actor).attachment(attachid).userId(userId).randomId(new Random().nextInt(10000)).execute();
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

}
