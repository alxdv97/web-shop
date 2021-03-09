package ru.alxdv.nfoshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alxdv.nfoshop.dto.ProductDTO;
import ru.alxdv.nfoshop.entity.Order;
import ru.alxdv.nfoshop.entity.Product;
import ru.alxdv.nfoshop.mapper.ProductMapper;
import ru.alxdv.nfoshop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository repo;

    @MockBean
    private ProductMapper mapper;

    private List<Product> productDB;

    private Order order;

    @Before
    public void setUp(){
        order = Order.builder().build();

        ProductDTO productDTO = ProductDTO.builder()
                .price(10.0)
                .name("product1")
                .description("product 1 description")
                .build();

        Product product1 = Product.builder()
                .id(1L)
                .price(10.0)
                .name("product1")
                .description("product 1 description")
                .orders(Set.of(order))
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .price(20.0)
                .name("product2")
                .description("product 2 description")
                .orders(Set.of(order))
                .build();

        Product product3 = Product.builder()
                .id(3L)
                .price(30.0)
                .name("product3")
                .description("product 3 description")
                .orders(Set.of(order))
                .build();

        when(mapper.toDTO(any())).thenReturn(productDTO);
        when(mapper.toEntity(any())).thenReturn(product1);
        doNothing().when(mapper).updateFromDtoToEntity(any(), any());

        productDB = new ArrayList<>(List.of(product1, product2, product3));

        when(repo.findAll()).thenReturn(productDB);

        when(repo.getOne(anyLong())).thenAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            return getProductFromDB(id);
        });

        doAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            Product productFromDB = getProductFromDB(id);
            productDB.remove(productFromDB);
            return null;
        }).when(repo).deleteById(anyLong());

        when(repo.save(any(Product.class))).thenAnswer(i -> {
            Product productToSave = i.getArgument(0, Product.class);
            productDB.add(productToSave);
            return productToSave;
        });
    }

    @Test
    public void getAllProductsTest(){
        List<ProductDTO> allProducts = service.getAllProducts();

        assertNotNull(allProducts);
        assertEquals(3L, allProducts.size());
    }

    @Test
    public void createProductTest(){
        ProductDTO product4 = ProductDTO.builder()
                .price(40.0)
                .name("product4")
                .description("product 4 description")
                .build();

        Long productId = service.createProduct(product4);

        assertNotNull(productId);
        assertEquals(4, productDB.size());
    }

    @Test
    public void getProductTest(){
        ProductDTO product = service.getProduct(1L);

        assertNotNull(product);
        assertEquals("product1", product.getName());
    }

    @Test
    public void updateProductTest(){
        Long updatedProductId = service.updateProduct(ProductDTO.builder()
                .price(300.0)
                .name("product3Updated")
                .description("product 3 updated")
                .build(), 3L);

        assertNotNull(updatedProductId);
        assertEquals(3L, updatedProductId.longValue());
    }

    @Test
    public void deleteProductTest(){
        service.deleteProductById(3L);

        assertEquals(2L, productDB.size());
    }


    private Product getProductFromDB(Long id){
        return productDB.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .get();
    }

}
