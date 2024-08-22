package com.github.ngodat0103.yamp.gateway.security.oauth2client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientId;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;





@Slf4j
public class RedisReactiveOAuth2AuthorizedClientService implements ReactiveOAuth2AuthorizedClientService {
    private final ReactiveRedisOperations<Object,Object> redisOperations;

    public RedisReactiveOAuth2AuthorizedClientService(ReactiveRedisOperations<Object, Object> redisOperations) {
        this.redisOperations = redisOperations;
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T extends OAuth2AuthorizedClient> Mono<T> loadAuthorizedClient(String clientRegistrationId, String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        OAuth2AuthorizedClientId authorizedClientId = new OAuth2AuthorizedClientId(clientRegistrationId, principalName);
        return (Mono<T>) redisOperations
                .opsForValue()
                .get(authorizedClientId)
                .cast(OAuth2AuthorizedClient.class)
                .doOnSuccess(c -> log.debug("Load authorized client {} for principal {}", c, principalName))
                .doOnError(e -> log.error("Failed to load authorized client for principal {}", principalName, e));
    }

    @Override
    public Mono<Void> saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        Assert.notNull(authorizedClient, "authorizedClient cannot be null");
        Assert.notNull(principal, "principal cannot be null");
        String registrationId = authorizedClient.getClientRegistration().getRegistrationId();
        String principalName = principal.getName();
        OAuth2AuthorizedClientId authorizedClientId = new OAuth2AuthorizedClientId(registrationId, principalName);
     return redisOperations
             .opsForValue()
             .set(authorizedClientId, authorizedClient)
             .filter(Boolean::booleanValue)
             .doOnNext(b -> log.debug("Save authorized client {} for principal {}", authorizedClient, principal))
             .doOnError(e -> log.error("Failed to save authorized client {} for principal {}", authorizedClient, principal, e))
             .then() ;
    }

    @Override
    public Mono<Void> removeAuthorizedClient(String clientRegistrationId, String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        OAuth2AuthorizedClientId authorizedClientId = new OAuth2AuthorizedClientId(clientRegistrationId, principalName);
        return redisOperations
                .opsForValue()
                .delete(authorizedClientId)
                .filter(Boolean::booleanValue)
                .doOnNext(b -> log.debug("Remove authorized client {} for principal {}", clientRegistrationId, principalName))
                .doOnError(e -> log.error("Failed to remove authorized client {} for principal {}", clientRegistrationId, principalName, e))
                .then();
    }
}
