package poc.petshop.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import poc.petshop.demo.model.SellDetail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SellDetailRepositoryTest {

    @Autowired
    private SellDetailRepository sellDetailRepository;

    @Test
    public void updateSellDetail(){
        
        SellDetail sellDetail = new SellDetail();
        sellDetail.setId(200L);
        sellDetail.setIdProduct(2L);
        sellDetail.setSellDateTime(LocalDateTime.parse("2024-05-05T10:30:00"));
        sellDetail.setSellPrice(5000);

        SellDetail savedSell = sellDetailRepository.save(sellDetail);

        savedSell.setSellPrice(6500);
        savedSell.setSellDateTime(LocalDateTime.parse("2024-05-05T12:00:00"));

        SellDetail updatedSell = sellDetailRepository.save(savedSell);

        assertNotNull(updatedSell.getId());

        assertEquals(updatedSell.getId(),savedSell.getId());
        assertEquals(updatedSell.getIdProduct(),savedSell.getIdProduct());
        assertEquals(updatedSell.getSellDateTime(),savedSell.getSellDateTime());
        assertEquals(updatedSell.getSellPrice(),savedSell.getSellPrice());

    }

}
