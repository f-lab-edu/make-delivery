package com.flab.makedel.service;

import com.flab.makedel.dao.DeliveryDAO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class PushService {

    private final String firebaseConfigPath;

    public PushService(@Value("${firebase.config.path}") String firebaseConfigPath) {
        this.firebaseConfigPath = firebaseConfigPath;
    }

    @PostConstruct
    public void init() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
            .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

    }

    public void sendMessages(List<Message> messages) {
        FirebaseMessaging.getInstance().sendAllAsync(messages);
    }


}
