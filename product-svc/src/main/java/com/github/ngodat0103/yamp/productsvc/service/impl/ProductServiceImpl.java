package com.github.ngodat0103.yamp.productsvc.service.impl;
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
        return productMapper.toProductDto(product);
    }

    @Override
    public ProductDto getProduct(UUID productUuid) {
        return null;
    }

    @Override
    public Set<ProductDto> getProducts() {
       return  productRepository.findAll().stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toUnmodifiableSet());
    }
}
