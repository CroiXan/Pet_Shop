package poc.petshop.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;

import poc.petshop.demo.model.Product;
import poc.petshop.demo.service.ProductServiceImpl;
import poc.petshop.demo.service.SellDetailServiceImpl;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired 
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productServiceImplMock;

    @MockBean
    private SellDetailServiceImpl sellDetailServiceImplMock;

    @Test
    public void createProductTest() throws Exception{

        Product product = new Product();
        //product.setId(null);
        product.setName("Saco de comida de Perro");
        product.setDescription("saco de 20Kg de comida para perros");
        product.setPurchasePrice(15000);
        product.setQuantity(30);

        when(productServiceImplMock.createProduct(any())).thenReturn(product);

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                .contentType(MediaType.APPLICATION_PROBLEM_JSON).content(gson.toJson(product)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.is("Saco de comida de Perro")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.quantity",Matchers.is(30)));
    }

}
