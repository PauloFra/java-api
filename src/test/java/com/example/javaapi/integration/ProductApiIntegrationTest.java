package com.example.javaapi.integration;

import com.example.javaapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testProductCrudOperations() throws Exception {
        // Create a product
        Product product = new Product();
        product.setName("Integration Test Product");
        product.setDescription("A product for integration testing");
        product.setPrice(new BigDecimal("99.99"));

        String productJson = objectMapper.writeValueAsString(product);

        // Test Create Product
        String createdProductJson = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Integration Test Product"))
                .andExpect(jsonPath("$.price").value(99.99))
                .andReturn().getResponse().getContentAsString();

        Product createdProduct = objectMapper.readValue(createdProductJson, Product.class);
        Long productId = createdProduct.getId();

        // Test Get All Products
        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Integration Test Product"));

        // Test Get Product by ID
        mockMvc.perform(get("/api/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Integration Test Product"));

        // Test Update Product
        product.setId(productId);
        product.setName("Updated Integration Test Product");
        product.setPrice(new BigDecimal("149.99"));

        mockMvc.perform(put("/api/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Integration Test Product"))
                .andExpect(jsonPath("$.price").value(149.99));

        // Test Delete Product
        mockMvc.perform(delete("/api/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify product is deleted
        mockMvc.perform(get("/api/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
} 