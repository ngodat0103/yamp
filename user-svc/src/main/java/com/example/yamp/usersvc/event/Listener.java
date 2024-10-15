// package com.example.yamp.usersvc.event;
//
// import com.example.yamp.usersvc.cache.AuthSvcRepository;
// import com.example.yamp.usersvc.dto.kafka.AccountTopicContent;
// import com.example.yamp.usersvc.dto.kafka.Action;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import java.util.UUID;
// import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.kafka.clients.consumer.ConsumerRecord;
// import org.springframework.kafka.annotation.KafkaHandler;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;
//
// @KafkaListener(topics = "auth-svc-topic")
// @Service
// @AllArgsConstructor
// @Slf4j
// public class Listener {
//  private final AuthSvcRepository authSvcRepository;
//  private final ObjectMapper objectMapper;
//
//  private final CustomerService customerService;
//
//  @KafkaHandler(isDefault = true)
//  private void authsvcHandler(ConsumerRecord<UUID, String> message) throws JsonProcessingException
// {
//    UUID accountUuid = message.key();
//    AccountTopicContent accountTopicContent =
//        objectMapper.readValue(message.value(), AccountTopicContent.class);
//    Action action = accountTopicContent.action();
//    log.debug("Received message from authsvc-topic: {}", accountTopicContent);
//
//    if (action.equals(Action.CREATE)) {
//      customerService.create(accountUuid, accountTopicContent);
//      return;
//    }
//    if (action.equals(Action.DELETE) || action.equals(Action.UPDATE)) {
//      authSvcRepository
//          .findById(accountUuid)
//          .ifPresent(
//              account -> {
//                authSvcRepository.delete(account);
//                log.debug("Account with uuid {} deleted from cache", accountUuid);
//              });
//    }
//  }
// }
