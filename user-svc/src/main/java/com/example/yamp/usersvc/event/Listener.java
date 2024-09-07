package com.example.yamp.usersvc.event;

import com.example.yamp.usersvc.cache.AuthSvcRepository;
import com.example.yamp.usersvc.dto.kafka.AccountMessingDto;
import com.example.yamp.usersvc.dto.kafka.Action;
import com.example.yamp.usersvc.persistence.entity.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@KafkaListener(topics = "account-update")
@Component
@AllArgsConstructor
@Slf4j
public class Listener {
    private AuthSvcRepository authSvcRepository ;
    private ObjectMapper objectMapper;

    @KafkaHandler(isDefault = true)
    private void accountUpdate(ConsumerRecord<UUID,String> message) throws JsonProcessingException {
        log.debug("Received message: {}", message);
        UUID accountUuid = message.key();
        AccountMessingDto messingDto = objectMapper.readValue(message.value(), AccountMessingDto.class);
        Action action = messingDto.action();
        if(action.equals(Action.DELETE) || action.equals(Action.UPDATE)) {
            log.debug("Received Action: {}", action);
            AccountMessingDto accountMessingDto = objectMapper.readValue(message.value(), AccountMessingDto.class);
            Account account = authSvcRepository.findById(accountUuid).orElse(null);
            if(account!=null && account.getLastModifiedAt().isBefore(accountMessingDto.lastModifiedAt())) {
                log.debug("Delete account with uuid: {}", accountUuid);
                authSvcRepository.delete(account);
            }
        }
    }
}
