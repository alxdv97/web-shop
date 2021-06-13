package ru.alxdv.nfostore.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @KafkaListener(topics = "order", groupId = "${spring.kafka.consumer.group-id}")
    private void listenGroupStore(String msg){
        System.out.println("Received Message in group store: " + msg);
    }
}
