package com.github.ngodat0103.yamp.productsvc;

import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoRequest;
import com.github.ngodat0103.yamp.productsvc.dto.category.CategoryDtoResponse;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class IntegrationTest {

  private final CategoryDtoRequest categoryDtoRequest =
      CategoryDtoRequest.builder().name("Test Category").parentCategoryUuid(null).build();
  @Autowired private TestRestTemplate testRestTemplate;
  private final Slugify slugify =
      Slugify.builder().lowerCase(true).locale(new Locale("vn", "VN")).build();

  @Test
  void contextLoads() {}

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
}
