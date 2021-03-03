package ru.alxdv.nfoshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alxdv.nfoshop.entity.Order;
import ru.alxdv.nfoshop.entity.Product;
import ru.alxdv.nfoshop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository repo;

    private List<Product> productDB;

    private Order order;

    @Before
    public void setUp(){
        order = Order.builder().build();

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

        productDB = new ArrayList<>(List.of(product1, product2, product3));

        when(repo.findAll()).thenReturn(productDB);

        when(repo.getOne(anyLong())).thenAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            return getProductFromDB(id);
        });

        doAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            Product product = getProductFromDB(id);
            productDB.remove(product);
            return null;
        }).when(repo).deleteById(anyLong());

        when(repo.save(any(Product.class))).thenAnswer(i -> {
            Product product = i.getArgument(0, Product.class);
            productDB.add(product);
            return product;
        });
    }

    @Test
    public void getAllProductsTest(){
        List<Product> allProducts = service.getAllProducts();

        assertNotNull(allProducts);
        assertEquals(3L, allProducts.size());
    }

    @Test
    public void createProductTest(){
        Product product4 = Product.builder()
                .id(4L)
                .price(40.0)
                .name("product4")
                .description("product 4 description")
                .orders(Set.of(order))
                .build();

        Product product = service.createProduct(product4);

        assertNotNull(product);
        assertEquals(4, productDB.size());
    }

    @Test
    public void getProductTest(){
        Product product = service.getProduct(1L);

        assertNotNull(product);
        assertEquals(1L, product.getId().longValue());
    }

    @Test
    public void updateProductTest(){
        Product updatedProduct = service.updateProduct(Product.builder()
                .id(3L)
                .price(300.0)
                .name("product3Updated")
                .description("product 3 updated")
                .orders(Set.of(order))
                .build());

        assertNotNull(updatedProduct);
        assertEquals("product3Updated", updatedProduct.getName());
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
