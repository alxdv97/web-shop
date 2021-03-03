package ru.alxdv.nfoshop.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.alxdv.nfoshop.dto.ProductDTO;
import ru.alxdv.nfoshop.entity.Product;
import ru.alxdv.nfoshop.mapper.ProductMapper;
import ru.alxdv.nfoshop.service.ProductService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductMapper mapper;

    @MockBean
    private ProductService service;

    private ProductDTO productDTO;

    private String productJson;


    @Before
    public void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("product");
        productDTO.setDescription("product description");
        productDTO.setPrice(100.0);

        productJson = "{\n" +
                "    \"name\": \"product\",\n" +
                "    \"description\": \"product description\",\n" +
                "    \"price\": \"100.0\"\n" +
                "}";

        Product product = Product.builder()
                .id(1L)
                .name("product")
                .description("product description")
                .price(100.0)
                .build();

        when(mapper.toDTO(any())).thenReturn(productDTO);
        when(mapper.toEntity(any())).thenReturn(product);

        when(service.getAllProducts()).thenReturn(List.of(product,
                product, product));

        when(service.getProduct(anyLong())).thenReturn(product);

        when(service.createProduct(any(Product.class))).thenReturn(product);

        when(service.updateProduct(any(Product.class))).thenReturn(product);

        doNothing().when(service).deleteProductById(anyLong());
    }

    @Test
    public void getAllProductsTest() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("[*]").isArray())
                .andReturn();
    }

    @Test
    public void getProductTest() throws Exception {
        mockMvc.perform(get("/api/products/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(productDTO.getId()))
                .andReturn();
    }

    @Test
    public void createProductTest() throws Exception {
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(productDTO.getId().toString()))
                .andReturn();
    }

    @Test
    public void updateProductTest() throws Exception {
        mockMvc.perform(put("/api/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(productDTO.getId().toString()))
                .andReturn();
    }

    @Test
    public void deleteProductTest() throws Exception {
        mockMvc.perform(delete("/api/products/{id}","1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
