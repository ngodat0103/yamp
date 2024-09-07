package com.example.yamp.usersvc.event;

import com.example.yamp.usersvc.cache.AuthSvcRepository;
import com.example.yamp.usersvc.dto.kafka.Action;
import com.example.yamp.usersvc.persistence.entity.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@KafkaListener(topics = "account-update")
@Service
@AllArgsConstructor
@Slf4j
public class Listener {
    private AuthSvcRepository authSvcRepository ;
    private ObjectMapper objectMapper;

    @KafkaHandler(isDefault = true)
    private void accountUpdate(ConsumerRecord<UUID,String> message) throws JsonProcessingException {
        UUID accountUuid = message.key();
        Action action = objectMapper.readValue(message.value(), Action.class);
        if(action.equals(Action.DELETE) || action.equals(Action.UPDATE)) {
            log.debug("Received message from auth-svc: {}", action);
            authSvcRepository.findById(accountUuid).ifPresent(account -> {
                authSvcRepository.delete(account);
                log.debug("Account with uuid {} deleted from cache", accountUuid);
            });
        }
    }
}
