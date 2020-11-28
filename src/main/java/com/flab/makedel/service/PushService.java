package com.flab.makedel.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PushService {

    @PostConstruct
    public void init() throws IOException {
        InputStream fileStream = new FileInputStream("firebaseSDK.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials
                .fromStream(fileStream))
            .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    public void sendMessages(List<Message> messages) {
        FirebaseMessaging.getInstance().sendAllAsync(messages);
    }


}
