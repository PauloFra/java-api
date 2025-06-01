package com.example.javaapi.service;

import com.example.javaapi.model.Product;
import com.example.javaapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("19.99"));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void findAllShouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> result = productService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnProductWhenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void saveShouldSetDatesAndSaveProduct() {
        Product newProduct = new Product();
        newProduct.setName("New Product");
        newProduct.setPrice(new BigDecimal("29.99"));

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.save(newProduct);

        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isNotNull();
        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    void deleteByIdShouldCallRepositoryDeleteById() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
} 