package com.github.ngodat0103.yamp.productsvc.service.impl;
import com.github.ngodat0103.yamp.productsvc.dto.PageDto;
import com.github.ngodat0103.yamp.productsvc.dto.mapper.ProductMapper;
import com.github.ngodat0103.yamp.productsvc.dto.product.ProductDto;
import com.github.ngodat0103.yamp.productsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Category;
import com.github.ngodat0103.yamp.productsvc.persistence.entity.Product;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.CategoryRepository;
import com.github.ngodat0103.yamp.productsvc.persistence.repository.ProductRepository;
import com.github.ngodat0103.yamp.productsvc.service.ProductService;
import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.ngodat0103.yamp.productsvc.Util.*;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final  ProductRepository productRepository ;
    private final CategoryRepository categoryRepository;
    private final Slugify slugify;
    private final UriTemplate uriTemplate = UriTemplate.of("/api/v1/product/products/{placeholder}");
    private final UriTemplate productQueryUriTemplate = UriTemplate.of("/api/v1/product/products{?uuid}");
    private final UriTemplate uriTemplateCategory = UriTemplate.of("/api/v1/category/categories/{placeholder}");
    ProductMapper productMapper;
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findCategoryBySlugName(productDto.getCategorySlug())
                .orElseThrow(notFoundExceptionSupplier(log,"Category","slugName",productDto.getCategorySlug()));
        if(productRepository.existsByName(productDto.getName())){
           throwConflictException(log,"Product","name",productDto.getName());
        }

        if(productRepository.existsBySlugName(slugify.slugify(productDto.getName()))){
            throwConflictException(log,"Product","slugName",slugify.slugify(productDto.getName()));
        }


        Product product = productMapper.toEntity(productDto,getAccountUuidFromAuthentication());
        product.setCategory(category);
        product.setSlugName(slugify.slugify(productDto.getName()));
        product = productRepository.save(product);
        return productMapper.toProductDto(product);
    }

    @Override
    public ProductDto updateProduct(UUID productUuid, ProductDto productDto) {
        Product currentProduct = productRepository.findById(productUuid)
                .orElseThrow(notFoundExceptionSupplier(log,"Product","uuid",productUuid));
        Category newCategory = categoryRepository.findCategoryBySlugName(productDto.getCategorySlug())
                .orElseThrow(notFoundExceptionSupplier(log,"Category","slugName",productDto.getCategorySlug()));

        currentProduct.setCategory(newCategory);
        currentProduct.setName(productDto.getName());
        currentProduct.setDescription(productDto.getDescription());
        currentProduct.setSlugName(slugify.slugify(productDto.getName()));

        currentProduct.setLastModifiedBy(getAccountUuidFromAuthentication());
        currentProduct.setLastModifiedAt(LocalDateTime.now());
        currentProduct = productRepository.save(currentProduct);

        return productMapper.toProductDto(currentProduct);
    }


    @Override
    public void deleteProduct(UUID productUuid) {
        Product product = productRepository.findById(productUuid)
                .orElseThrow(notFoundExceptionSupplier(log,"Product","uuid",productUuid));
        productRepository.delete(product);
    }

    @Override
    public ProductDto getProduct(String productSlugName) {
        Product product = productRepository.findBySlugName(productSlugName).orElseThrow(() -> {
            String message = "Product with slugName: "+ productSlugName+" not found";
            log.debug(message);
            return new NotFoundException(message);
        });

        ProductDto productDto = productMapper.toProductDto(product);
        addLinks(productDto,productSlugName);
        return productDto;
    }

    @Override
    public ProductDto getProduct(UUID productUuid) {
        Product product = productRepository.findById(productUuid).orElseThrow(notFoundExceptionSupplier(log,"Product","uuid",productUuid));
        ProductDto productDto = productMapper.toProductDto(product);
        addLinks(productDto,product.getSlugName());
        return productDto;
    }

    @Override
    public PageDto<ProductDto> getProducts(PageRequest pageRequest) {
       Set<ProductDto> productDtos =  productRepository.findAll(pageRequest).stream()
               .map(account -> {
                   ProductDto productDto = productMapper.toProductDto(account);
                   addLinks(productDto,account.getSlugName());
                   return productDto;
               })
               .collect(Collectors.toUnmodifiableSet());

         int totalElements = (int) productRepository.count();
         return PageDto.<ProductDto>builder()
            .page(pageRequest.getPageNumber())
            .size(pageRequest.getPageSize())
            .totalElements(totalElements)
            .totalPages((totalElements / pageRequest.getPageSize()))
            .data(productDtos)
            .build();
    }
    private void addLinks(ProductDto productDto, String slugName) {
        Link updateLink = Link.of(uriTemplate.expand(productDto.getUuid()).toString(),"update")
                .withTitle("Update product")
                .withType("application/json");
        Link deleteLink = Link.of(uriTemplate.expand(productDto.getUuid()).toString(),"delete")
                .withTitle("Delete product")
                .withType("application/json");
        Link slugLink = Link.of(uriTemplate.expand(slugName).toString(),"slugName")
                .withTitle("Get product by slugName")
                .withType("application/json");
        Link categoryLink = Link.of("/api/v1/category/categories/"+productDto.getCategorySlug(),"category")
                .withTitle("Get category by slugName")
                .withType("application/json");

       Link uuidLink = Link.of(productQueryUriTemplate.expand(productDto.getUuid()).toString(),"uuid")
                .withTitle("Get product by uuid")
                .withType("application/json");
        productDto.add(uuidLink ,slugLink, updateLink,deleteLink,categoryLink);
    }
}
