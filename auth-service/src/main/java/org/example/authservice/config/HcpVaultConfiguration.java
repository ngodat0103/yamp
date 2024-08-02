package org.example.authservice.config;

import com.nimbusds.oauth2.sdk.ParseException;
import lombok.Getter;
import lombok.Setter;
import org.example.authservice.vault.HcpVault;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Getter
@ConfigurationProperties(prefix = "hcp-vault")
@Configuration
@Setter
public class HcpVaultConfiguration{
    private String tokenEndPoint;
    private String jwkSecretPath;
    @Bean
    HcpVault hcpVault() throws IOException, ParseException {
        return new HcpVault(this);
    }

}