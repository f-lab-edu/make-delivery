package com.flab.makedel.service;

import com.flab.makedel.dao.DeliveryDAO;
import com.flab.makedel.dto.PushMessageDTO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PushService {

    @Value("${firebase.config.path}")
    private final String firebaseConfigPath;
    private final DeliveryDAO deliveryDAO;

    public PushService(@Value("${firebase.config.path}") String firebaseConfigPath,
        DeliveryDAO deliveryDAO) {
        this.firebaseConfigPath = firebaseConfigPath;
        this.deliveryDAO = deliveryDAO;
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

    public void sendMessageToStandbyRidersInSameArea(String address, PushMessageDTO pushMessage)
        throws FirebaseMessagingException {
        Set<String> tokenSet = deliveryDAO.selectStandbyRiderTokenList(address);
        List<Message> messages = tokenSet.stream().map(token -> Message.builder()
            .putData("title", pushMessage.getTitle())
            .putData("content", pushMessage.getContent())
            .putData("orderReceipt", pushMessage.getOrderReceipt().toString())
            .putData("createdAt", pushMessage.getCreatedAt())
            .setToken(token)
            .build())
            .collect(Collectors.toList());

        FirebaseMessaging.getInstance().sendAll(messages);
    }

}
