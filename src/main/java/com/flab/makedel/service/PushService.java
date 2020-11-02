package com.flab.makedel.service;

import com.flab.makedel.dao.DeliveryDAO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PushService {

    @Value("${firebase.config.path}")
    private String FIREBASE_CONFIG_PATH;

    @Value("${fcm.rider.expire.second}")
    private long riderExpireSecond;

    private final DeliveryDAO deliveryDAO;

    @PostConstruct
    public void init() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials
                .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream()))
            .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    public void sendMessageToStandbyRidersInSameArea(String address) {
    }

}
