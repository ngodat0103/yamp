package org.example.authservice.vault;

import org.example.authservice.config.HcpVaultConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Component
@ConditionalOnBean(HcpVaultConfiguration.class)
public class HcpVault {
    private final HcpVaultConfiguration hcpVaultConfiguration;
    private final WebClient webClient;
    public HcpVault( HcpVaultConfiguration hcpVaultConfiguration,WebClient webClient) {
        this.hcpVaultConfiguration = hcpVaultConfiguration;
        this.webClient =webClient;
    }
    public String getSecret() {
    ResponseEntity<SecretResponseHcpVault> responseEntity = webClient.get().
                uri(hcpVaultConfiguration.getJwkSecretPath()).
                retrieve().
                toEntity(SecretResponseHcpVault.class).
                block();

    assert responseEntity!=null;
    if(responseEntity.getStatusCode().is2xxSuccessful()){
        SecretResponseHcpVault secretResponseHcpVault = responseEntity.getBody();
        assert  secretResponseHcpVault !=null ;
        return responseEntity.getBody().getSecrets().get(0).getVersion().getValue();
    }
    throw new RuntimeException("Failed to get secret from HCP vault");
    }




}
