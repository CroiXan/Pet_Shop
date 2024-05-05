package poc.petshop.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
import com.google.gson.GsonBuilder;

import poc.petshop.demo.model.LocalDateTimeTypeAdapter;
import poc.petshop.demo.model.SellDetail;
import poc.petshop.demo.service.ProductServiceImpl;
import poc.petshop.demo.service.SellDetailServiceImpl;

@WebMvcTest(SellDetailController.class)
public class SellDetailControllerTest {

    @Autowired 
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productServiceImplMock;

    @MockBean
    private SellDetailServiceImpl sellDetailServiceImplMock;

    @Test
    public void updateSellDetailTest() throws Exception{
        SellDetail sellDetail = new SellDetail();
        sellDetail.setId(100L);
        sellDetail.setIdProduct(1L);
        sellDetail.setSellDateTime(LocalDateTime.parse("2024-05-05T10:30:00"));
        sellDetail.setSellPrice(10000);

        when(sellDetailServiceImplMock.existsSellDetailById(any())).thenReturn(true);
        when(productServiceImplMock.existsProductById(any())).thenReturn(true);
        when(sellDetailServiceImplMock.updateSellDetail(any(),any())).thenReturn(sellDetail);

        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();
        
        mockMvc.perform(MockMvcRequestBuilders.put("/sellDetail/100")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(sellDetail)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.sellDateTime",Matchers.is("2024-05-05T10:30:00")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.sellPrice",Matchers.is(10000)));

    }
}
