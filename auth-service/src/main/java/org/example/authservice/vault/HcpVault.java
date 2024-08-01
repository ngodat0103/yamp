package org.example.authservice.vault;

import com.nimbusds.oauth2.sdk.ClientCredentialsGrant;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientSecretPost;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.ClientID;
import org.example.authservice.config.HcpVaultConfiguration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class HcpVault {

    private final String accessToken ;
    private final String jwkSecretPath;
    public HcpVault(HcpVaultConfiguration hcpVaultConfiguration) throws IOException, ParseException {
        this.jwkSecretPath =hcpVaultConfiguration.getJwkSecretPath();

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream hcpClientIdInputStream = classLoader.getResourceAsStream("hcpClientId");
        InputStream hcpClientSecretInputStream = classLoader.getResourceAsStream("hcpClientSecret");
        assert hcpClientIdInputStream != null;
        assert hcpClientSecretInputStream != null;
        String hcpClientId = new String(hcpClientIdInputStream.readAllBytes());
        String hcpClientSecret = new String(hcpClientSecretInputStream.readAllBytes());
        hcpClientIdInputStream.close();
        hcpClientSecretInputStream.close();
        ClientID clientID = new ClientID(hcpClientId);
        Secret clientSecret = new Secret(hcpClientSecret);
        ClientSecretPost clientSecretPost = new ClientSecretPost(clientID, clientSecret);


        TokenRequest tokenRequest = new TokenRequest(URI.create(hcpVaultConfiguration.getTokenEndPoint()), clientSecretPost, new ClientCredentialsGrant());
        TokenResponse tokenResponse = TokenResponse.parse(tokenRequest.toHTTPRequest().send());
        accessToken = tokenResponse.toSuccessResponse().getTokens().getAccessToken().getValue();

    }
    public String getSecret(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);
        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, URI.create(jwkSecretPath));
        ResponseEntity<SecretResponseHcp> responseEntity  = restTemplate.exchange(requestEntity, SecretResponseHcp.class);
        SecretResponseHcp secretResponseHcp = responseEntity.getBody();
        assert secretResponseHcp != null;
        return secretResponseHcp.getSecrets().get(0).getVersion().getValue();

    }




}
