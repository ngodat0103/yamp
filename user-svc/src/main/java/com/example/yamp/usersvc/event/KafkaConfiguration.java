package com.example.yamp.usersvc.event;

import com.example.yamp.usersvc.cache.AuthSvcRepository;
import com.example.yamp.usersvc.persistence.entity.Account;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@Component
@AllArgsConstructor
public class KafkaConfiguration {
    private AuthSvcRepository authSvcRepository ;


    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
    }

    @Bean
    RecordMessageConverter converter() {
        return new JsonMessageConverter();
    }
    @KafkaListener(topics = "account-update",groupId = "user-svc")
    public void listen(Model model){
        System.out.println("Received: " + model);

        if(model.action().equals(Action.UPDATE)) {
            Account account = authSvcRepository.findById(model.uuid()).orElse(null);
            assert account != null;
            if(model.lastModifiedAt().isAfter(account.getLastModifiedAt())) {
                authSvcRepository.delete(account);
            }
        }

    }
}
