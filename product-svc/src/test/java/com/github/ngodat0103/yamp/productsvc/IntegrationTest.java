package com.github.ngodat0103.yamp.productsvc;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoResponse;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDtoResponse;
import com.github.slugify.Slugify;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTest {

  static PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:16.3-bullseye");

  @BeforeAll
  static void beforeAll() {
    postgreSQLContainer.start();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }

  @AfterAll
  static void afterAll() {
    postgreSQLContainer.stop();
  }

  @Autowired private TestRestTemplate testRestTemplate;

  private final Slugify slugify =
      Slugify.builder().lowerCase(true).locale(new Locale("vn", "VN")).build();

  private final CategoryDtoRequest categoryDtoRequest =
      CategoryDtoRequest.builder().name("Test Category").parentCategoryUuid(null).build();

  private final ProductDtoRequest productDtoRequest =
      ProductDtoRequest.builder()
          .name("Test Product")
          .description("Test Description")
          .categorySlug("test-category")
          .build();

  @BeforeEach
  void setUp() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
    this.testRestTemplate
        .getRestTemplate()
        .setInterceptors(
            Collections.singletonList(
                (request, body, execution) -> {
                  request.getHeaders().addAll(headers);
                  return execution.execute(request, body);
                }));
  }

  @Test
  void contextLoads() {}

  @Test
  @DisplayName("Given nothing when post category then return category")
  @Order(1)
  void testCreateCategory() {
    var responseEntity =
        testRestTemplate.postForEntity(
            "/categories", categoryDtoRequest, CategoryDtoResponse.class);
    var body = responseEntity.getBody();
    Assertions.assertNotNull(responseEntity);
    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertNotNull(body);
    Assertions.assertEquals(categoryDtoRequest.getName(), body.getName());
    Assertions.assertEquals(slugify.slugify(categoryDtoRequest.getName()), body.getSlugName());
    Assertions.assertNull(body.getThumbnailUrl());
    Assertions.assertNull(body.getParentCategoryUuid());
  }

  @Test
  @DisplayName("Given already have category when post category then return exception")
  @Order(2)
  void givenAlreadyHaveCategory_whenPostCategory_thenReturnException() {
    var responseEntity =
        testRestTemplate.postForEntity("/categories", categoryDtoRequest, ProblemDetail.class);
    var problemDetail = responseEntity.getBody();
    Assertions.assertNotNull(responseEntity);
    Assertions.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    Assertions.assertNotNull(problemDetail);
    Assertions.assertEquals("Already exists", problemDetail.getTitle());
    Assertions.assertEquals(
        "https://problems-registry.smartbear.com/already-exists",
        problemDetail.getType().toString());
    Assertions.assertNotNull(problemDetail.getInstance());
    Assertions.assertEquals("/categories", problemDetail.getInstance().toString());
    Assertions.assertEquals(
        "Category with name: \"Test Category\" already exists", problemDetail.getDetail());
    Assertions.assertEquals(HttpStatus.CONFLICT.value(), problemDetail.getStatus());
  }

  @Test
  @DisplayName("Given nothing when get categories then return categories")
  @Order(3)
  void givenNothing_whenGetCategories_thenReturnCategories() {
    var parameter = new ParameterizedTypeReference<PageDto<CategoryDtoResponse>>() {};
    var responseEntity =
        this.testRestTemplate.exchange("/categories/all", HttpMethod.GET, null, parameter);
    var body = responseEntity.getBody();
    Assertions.assertNotNull(responseEntity);
    Assertions.assertNotNull(body);
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(1, body.getTotalElements());
    Assertions.assertEquals(0, body.getTotalPages());
    Assertions.assertEquals(0, body.getPage());
    Assertions.assertEquals(100, body.getSize());

    Set<CategoryDtoResponse> content = body.getData();
    Assertions.assertNotNull(content);
    Assertions.assertEquals(1, content.size());
    CategoryDtoResponse categoryDtoResponse = content.stream().findFirst().orElseThrow();
    Assertions.assertNotNull(categoryDtoResponse);
    Assertions.assertEquals(categoryDtoRequest.getName(), categoryDtoResponse.getName());
    Assertions.assertEquals(
        slugify.slugify(categoryDtoRequest.getName()), categoryDtoResponse.getSlugName());
    Assertions.assertNull(categoryDtoResponse.getThumbnailUrl());
    Assertions.assertNull(categoryDtoResponse.getParentCategoryUuid());

    Link slugNameLink = categoryDtoResponse.getLink("slugName").orElseThrow();
    Assertions.assertNotNull(slugNameLink);
    Assertions.assertAll(
        () ->
            Assertions.assertEquals(
                "/api/v1/product/categories/" + categoryDtoResponse.getSlugName(),
                slugNameLink.getHref()),
        () -> Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, slugNameLink.getType()),
        () -> Assertions.assertEquals("Get category by slugName", slugNameLink.getTitle()));
  }

  @Test
  @DisplayName("Given nothing when post product then return product")
  @Order(4)
  void testCreateProduct() {
    var responseEntity =
        testRestTemplate.postForEntity("/products", productDtoRequest, ProductDtoResponse.class);
    var body = responseEntity.getBody();
    Assertions.assertNotNull(responseEntity);
    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertNotNull(body);
    Assertions.assertEquals(productDtoRequest.getName(), body.getName());
    Assertions.assertEquals(productDtoRequest.getDescription(), body.getDescription());
    Assertions.assertEquals(productDtoRequest.getCategorySlug(), body.getCategorySlug());
  }

  @Test
  @DisplayName("Given already have product when post product then return exception")
  @Order(5)
  void givenAlreadyHaveProduct_whenPostProduct_thenReturnException() {
    var responseEntity =
        testRestTemplate.postForEntity("/products", productDtoRequest, ProblemDetail.class);
    var problemDetail = responseEntity.getBody();
    Assertions.assertNotNull(responseEntity);
    Assertions.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    Assertions.assertNotNull(problemDetail);
    Assertions.assertEquals("Already exists", problemDetail.getTitle());
    Assertions.assertEquals(
        "https://problems-registry.smartbear.com/already-exists",
        problemDetail.getType().toString());
    Assertions.assertNotNull(problemDetail.getInstance());
    Assertions.assertEquals("/products", problemDetail.getInstance().toString());
    Assertions.assertEquals(
        "Product with name: \"Test Product\" already exists", problemDetail.getDetail());
    Assertions.assertEquals(HttpStatus.CONFLICT.value(), problemDetail.getStatus());
  }

  @Test
  @DisplayName("Given nothing when get products then return products")
  @Order(6)
  void givenNothing_whenGetProducts_thenReturnProducts() {
    var parameter = new ParameterizedTypeReference<PageDto<ProductDtoResponse>>() {};
    var responseEntity =
        this.testRestTemplate.exchange("/products/all", HttpMethod.GET, null, parameter);
    var body = responseEntity.getBody();
    Assertions.assertNotNull(responseEntity);
    Assertions.assertNotNull(body);
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(1, body.getTotalElements());
    Assertions.assertEquals(0, body.getTotalPages());
    Assertions.assertEquals(0, body.getPage());
    Assertions.assertEquals(100, body.getSize());

    Set<ProductDtoResponse> content = body.getData();
    Assertions.assertNotNull(content);
    Assertions.assertEquals(1, content.size());
    ProductDtoResponse productDtoResponse = content.stream().findFirst().orElseThrow();
    Assertions.assertNotNull(productDtoResponse);
    Assertions.assertEquals(productDtoRequest.getName(), productDtoResponse.getName());
    Assertions.assertEquals(
        productDtoRequest.getDescription(), productDtoResponse.getDescription());
    Assertions.assertEquals(
        productDtoRequest.getCategorySlug(), productDtoResponse.getCategorySlug());
  }
}
