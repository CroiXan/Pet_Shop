package poc.petshop.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import poc.petshop.demo.model.Product;
import poc.petshop.demo.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepositoryMock;

    @Test
    public void updateProductTest(){

        Product product = new Product();
        product.setName("Peine para remover pelo");
        product.setDescription("peine removedor de pelo para ropa");
        product.setPurchasePrice(2000);
        product.setQuantity(50);

        when(productRepositoryMock.save(any())).thenReturn(product);
        when(productRepositoryMock.existsById(any())).thenReturn(true);

        product.setPurchasePrice(3000);
        product.setQuantity(60);

        Product createdProduct = productService.updateProduct(product.getId(),product);

        assertNotNull(createdProduct);

        assertEquals("Peine para remover pelo", createdProduct.getName());
        assertEquals("peine removedor de pelo para ropa", createdProduct.getDescription());
        assertEquals(3000, createdProduct.getPurchasePrice());
        assertEquals(60, createdProduct.getQuantity());

    }

}
