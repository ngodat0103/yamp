package com.github.ngodat0103.yamp.productsvc.service;
import com.github.ngodat0103.yamp.productsvc.CustomerTestWithMockUser;
import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.ProductMapper;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.ProductMapperImpl;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDto;
import com.github.ngodat0103.yamp.productsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.productsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Product;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.ProductRepository;
import com.github.ngodat0103.yamp.productsvc.service.impl.ProductServiceImpl;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;
import java.util.UUID;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("unit-test")
public class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductDto productDto;
    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        this.productRepository = Mockito.mock(ProductRepository.class);
        this.categoryRepository = Mockito.mock(CategoryRepository.class);
        ProductMapper productMapper = new ProductMapperImpl();
        Locale locale = new Locale("vi", "VN");
        Slugify slugify = Slugify.builder()
                .locale(locale)
                .lowerCase(true)
                .build();
        this.productService = new ProductServiceImpl(productRepository, categoryRepository, slugify, productMapper);
        this.category = new Category();
        category.setUuid(UUID.randomUUID());
        category.setName("Category test");
        category.setSlugName("category-test");
        this.product = new Product();
        product.setUuid(UUID.randomUUID());
        product.setName("Product test");
        product.setSlugName("product-test");
        product.setCategory(category);
        this.productDto = productMapper.toProductDto(product);
    }

    @Test
    @DisplayName("Call service but not contain Authentication object")
    @Disabled("This test is disabled because it is not relevant to the current implementation")
    public void givenNoAuthentication_whenCreateProduct_thenThrowIllegalArgumentException() {
        Exception exceptionForCreate = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(productDto);
        });
        Exception exceptionForUpdate = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(UUID.randomUUID(), productDto);
        });
        Assertions.assertAll(
                () -> Assertions.assertEquals("Authentication object is required", exceptionForCreate.getMessage()),
                () -> Assertions.assertEquals("Authentication object is required", exceptionForUpdate.getMessage())
        );
    }

    @CustomerTestWithMockUser
    @DisplayName("Given a ProductDto, when creating a product, then throw ConflictException if the product name already exists")
    public void givenProductDto_whenCreateProduct_thenThrowConflictException() {
        given(productRepository.existsByName(productDto.getName())).willReturn(true);
        given(categoryRepository.findCategoryBySlugName(productDto.getCategorySlug())).willReturn(java.util.Optional.of(category));
        Exception exception = Assertions.assertThrows(ConflictException.class, () -> {
            productService.createProduct(productDto);
        });
        String expectedMessage = String.format("Product with name: \"%s\" already exists", productDto.getName());
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        then(productRepository).should().existsByName(productDto.getName());
    }

    @CustomerTestWithMockUser
    @DisplayName("Given a ProductDto, when creating a product, then throw NotFoundException if the category does not exist")
    public void givenProductDto_whenCreateProduct_thenThrowNotFoundException() {
        given(categoryRepository.findCategoryBySlugName(productDto.getCategorySlug())).willReturn(java.util.Optional.empty());
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            productService.createProduct(productDto);
        });
        Assertions.assertEquals("Category with slugName: " + productDto.getCategorySlug() + " not found", exception.getMessage());
        then(categoryRepository).should().findCategoryBySlugName(productDto.getCategorySlug());
    }

    @CustomerTestWithMockUser
    @DisplayName("Given a ProductDto, when creating a product, then return a ProductDto")
    public void givenProductDto_whenCreateProduct_thenReturnProductDto() {
        given(productRepository.existsByName(productDto.getName())).willReturn(false);
        given(categoryRepository.findCategoryBySlugName(productDto.getCategorySlug())).willReturn(java.util.Optional.of(category));
        given(productRepository.save(any(Product.class))).willReturn(product);
        ProductDto productDto = productService.createProduct(this.productDto);
        Assertions.assertNotNull(productDto);
        then(productRepository).should().existsByName(productDto.getName());
        then(categoryRepository).should().findCategoryBySlugName(productDto.getCategorySlug());
        then(productRepository).should().save(any(Product.class));
    }

    @CustomerTestWithMockUser
    @DisplayName("Given a ProductDto, when updating a product, then throw NotFoundException if the product does not exist")
    public void givenProductDto_whenUpdateProduct_thenThrowNotFoundException() {
        given(productRepository.findById(product.getUuid())).willReturn(java.util.Optional.empty());
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            productService.updateProduct(product.getUuid(), productDto);
        });
        Assertions.assertEquals("Product with uuid: " + product.getUuid() + " not found", exception.getMessage());
    }

    @CustomerTestWithMockUser
    @DisplayName("Given a ProductDto, when updating a product, then throw NotFoundException if the category does not exist")
    public void givenProductDto_whenUpdateProduct_thenThrowNotFoundExceptionIfCategoryNotExists() {
        given(productRepository.findById(product.getUuid())).willReturn(java.util.Optional.of(product));
        given(categoryRepository.findCategoryBySlugName(productDto.getCategorySlug())).willReturn(java.util.Optional.empty());
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            productService.updateProduct(product.getUuid(), productDto);
        });
        Assertions.assertEquals("Category with slugName: " + productDto.getCategorySlug() + " not found", exception.getMessage());
    }

    @CustomerTestWithMockUser
    @DisplayName("Given a ProductDto, when updating a product, then return a ProductDto")
    public void givenProductDto_whenUpdateProduct_thenReturnProductDto() {
        given(productRepository.findById(product.getUuid())).willReturn(java.util.Optional.of(product));
        given(categoryRepository.findCategoryBySlugName(productDto.getCategorySlug())).willReturn(java.util.Optional.of(category));
        given(productRepository.save(any(Product.class))).willReturn(product);
        ProductDto productDto = productService.updateProduct(product.getUuid(), this.productDto);
        Assertions.assertNotNull(productDto);
        then(productRepository).should().findById(product.getUuid());
        then(categoryRepository).should().findCategoryBySlugName(productDto.getCategorySlug());
        then(productRepository).should().save(any(Product.class));
    }

    @Test
    @DisplayName("Given a product UUID, when deleting a product, then throw NotFoundException if the product does not exist")
    public void givenProductUuid_whenDeleteProduct_thenThrowNotFoundException() {
        given(productRepository.findById(product.getUuid())).willReturn(java.util.Optional.empty());
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            productService.deleteProduct(product.getUuid());
        });
        Assertions.assertEquals("Product with uuid: " + product.getUuid() + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Given a product UUID, when deleting a product, then delete the product")
    public void givenProductUuid_whenDeleteProduct_thenDeleteProduct() {
        given(productRepository.findById(product.getUuid())).willReturn(java.util.Optional.of(product));
        productService.deleteProduct(product.getUuid());
        then(productRepository).should().delete(product);
    }

    @Test
    @DisplayName("Given productSlug, when get product by slugName, then throw NotFoundException if the product does not exist")
    public void givenProductSlug_whenGetProductBySlug_thenThrowNotFoundException() {
        given(productRepository.findBySlugName(product.getSlugName())).willReturn(java.util.Optional.empty());
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            productService.getProduct(product.getSlugName());
        });
        Assertions.assertEquals("Product with slugName: " + product.getSlugName() + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Given slugName, when get product by slugName, then return a ProductDto")
    public void givenProductSlug_whenGetProductBySlug_thenReturnProductDto() {
        given(productRepository.findBySlugName(productDto.getSlugName())).willReturn(java.util.Optional.of(product));
        ProductDto productDtoResponse = productService.getProduct(product.getSlugName());
        Assertions.assertNotNull(productDtoResponse);
        Assertions.assertEquals(product.getName(), productDtoResponse.getName());
        Assertions.assertEquals(product.getUuid().toString(), productDtoResponse.getUuid());
        Assertions.assertEquals(product.getCategory().getSlugName(), productDtoResponse.getCategorySlug());
        Assertions.assertEquals(product.getSlugName(), productDtoResponse.getSlugName());
        then(productRepository).should().findBySlugName(productDtoResponse.getSlugName());
    }

    @Test
    @DisplayName("Given product UUID, when get product by UUID, then throw NotFoundException if the product does not exist")
    public void givenProductUuid_whenGetProductByUuid_thenThrowNotFoundException() {
        given(productRepository.findById(product.getUuid())).willReturn(java.util.Optional.empty());
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            productService.getProduct(product.getUuid());
        });
        Assertions.assertEquals("Product with uuid: " + product.getUuid() + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Given product UUID, when get product by UUID, then return a ProductDto")
    public void givenProductUuid_whenGetProductByUuid_thenReturnProductDto() {
        given(productRepository.findById(product.getUuid())).willReturn(java.util.Optional.of(product));
        ProductDto productDto = productService.getProduct(product.getUuid());
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(product.getName(), productDto.getName());
        Assertions.assertEquals(product.getUuid().toString(), productDto.getUuid());
        Assertions.assertEquals(product.getCategory().getSlugName(), productDto.getCategorySlug());
        Assertions.assertEquals(product.getSlugName(), productDto.getSlugName());
        then(productRepository).should().findById(product.getUuid());
    }

    @Test
    @DisplayName("Given nothing, when get all products, then return a list of ProductDto")
    public void givenNothing_whenGetAllProducts_thenReturnListOfProductDto() {
        PageRequest pageRequest = PageRequest.of(0, 100);
        PageImpl<Product> pageImpl = new PageImpl<>(java.util.List.of(product), pageRequest, 1);

        given(productRepository.findAll(pageRequest)).willReturn(pageImpl);
        given(productRepository.count()).willReturn(1L);

        PageDto<ProductDto> pageDto = productService.getProducts(PageRequest.of(0, 100));
        Assertions.assertNotNull(pageDto);
        Assertions.assertEquals(pageRequest.getPageNumber(), pageDto.getPage());
        Assertions.assertEquals(pageRequest.getPageSize(), pageDto.getSize());
        Assertions.assertEquals(pageImpl.getTotalElements(), pageDto.getTotalElements());

        Assertions.assertEquals(1, pageDto.getData().size());

        pageDto.getData().forEach(productDto -> {
            Assertions.assertEquals(product.getName(), productDto.getName());
            Assertions.assertEquals(product.getUuid().toString(), productDto.getUuid());
            Assertions.assertEquals(product.getCategory().getSlugName(), productDto.getCategorySlug());
            Assertions.assertEquals(product.getSlugName(), productDto.getSlugName());
        });
        then(productRepository).should().findAll(pageRequest);
        then(productRepository).should().count();
    }
}