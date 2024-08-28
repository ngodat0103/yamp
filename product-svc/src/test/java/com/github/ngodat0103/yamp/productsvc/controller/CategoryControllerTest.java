//package com.github.ngodat0103.yamp.productsvc.unit_test;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.ngodat0103.yamp.productsvc.ProductSvcApplication;
//import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
//import com.github.ngodat0103.yamp.productsvc.exception.ConflictException;
//import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
//import com.github.tomakehurst.wiremock.client.WireMock;
//import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
//import com.nimbusds.jose.Algorithm;
//import com.nimbusds.jose.JWSAlgorithm;
//import com.nimbusds.jose.JWSHeader;
//import com.nimbusds.jose.crypto.RSASSASigner;
//import com.nimbusds.jose.jwk.KeyUse;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
//import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
//import static java.lang.String.format;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = ProductSvcApplication.class)
//@AutoConfigureMockMvc
//public class CategoryControllerTest {
//
//    @MockBean
//    private  CategoryService categoryService;
//    @Autowired
//    private MockMvc mockMvc;
//    private final ObjectMapper objectMapper= new ObjectMapper();
//    private final CategoryDto categoryDtoRequest = CategoryDto.builder().
//            name("Category 1")
//            .build();
//
//    private final UUID createBy = UUID.fromString("c883aeb3-8868-4753-8edd-19e2148b1dd5");
//    private final UUID updateBy = createBy;
//    private final CategoryDto categoryDtoResponse = CategoryDto.builder()
//            .name("Category 1")
//            .uuid(UUID.fromString("c883aeb3-8868-4753-8edd-19e2148b1dd5"))
//            .parentCategoryUuid(UUID.randomUUID())
//            .build();
//
//    @RegisterExtension
//    final static WireMockExtension wireMockServer = WireMockExtension.newInstance()
//    .options(wireMockConfig().dynamicPort())
//            .build();
//
//    private RSAKey rsaKey;
//    private static final String KEY_ID="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzZ";
//
//
//    @DynamicPropertySource
//    static void wireMockProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",wireMockServer::baseUrl);
//    }
//
//    @BeforeEach
//    public void beforeEach() throws Exception {
//        // generate an RSA Key Pair
//        rsaKey = new RSAKeyGenerator(2048)
//                .keyUse(KeyUse.SIGNATURE)
//                .algorithm(new Algorithm("RS256"))
//                .keyID(KEY_ID)
//                .generate();
//
//        var rsaPublicJWK = rsaKey.toPublicJWK();
//        var jwkResponse = format("{\"keys\": [%s]}", rsaPublicJWK.toJSONString());
//
//        // return mock JWK response
//        wireMockServer.stubFor(WireMock.get("/")
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                        .withBody(jwkResponse)));
//    }
//
//
//    @Test
//    public void testCreateNewCategoryWhenNotExist() throws Exception {
//        when(categoryService.createCategory(categoryDtoRequest)).thenReturn(categoryDtoResponse);
//        mockMvc.perform(post("/api/v1/category")
//                        .header("Authorization", "Bearer " + getSignedJwt())
//                .contentType(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//                .content(objectMapper.writeValueAsString(categoryDtoRequest)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value(categoryDtoRequest.getName()))
//                .andExpect(jsonPath("$.uuid").exists())
//                .andExpect(jsonPath("$.parentCategoryUuid").exists());
//    }
//
//
//    @Test
//    public void testCreateNewCategoryWhenNotHaveAdminRole() throws Exception {
//        when(categoryService.createCategory(categoryDtoRequest)).thenReturn(categoryDtoResponse);
//        mockMvc.perform(post("/api/v1/category")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//                        .content(objectMapper.writeValueAsString(categoryDtoRequest)))
//                .andExpect(status().isForbidden());
//    }
//
//
//
//    @Test
//    @WithMockUser(username = "c883aeb3-8868-4753-8edd-19e2148b1dd5", roles = {"ADMIN"})
//    public void testCreateNewCategoryWhenExist() throws Exception {
//        when(categoryService.createCategory(categoryDtoRequest)).thenThrow(new ConflictException("Category already exists"));
//        mockMvc.perform(post("/api/v1/category")
//                        .with(jwt())
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(categoryDtoRequest)))
//                .andExpect(status().isConflict())
//                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
//                .andExpect(jsonPath("$.title").value("Already exists"))
//                .andExpect(jsonPath("$.status").value(409))
//                .andExpect(jsonPath("$.detail").value("Category already exists"));
//
//    }
//
//    private String getSignedJwt() throws Exception {
//        var signer = new RSASSASigner(rsaKey);
//        var claimsSet = new JWTClaimsSet.Builder()
//                .claim("roles", List.of("ROLE_ADMIN"))
//                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
//                .build();
//        var signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256)
//                .keyID(rsaKey.getKeyID()).build(), claimsSet);
//        signedJWT.sign(signer);
//        return signedJWT.serialize();
//    }
//}
