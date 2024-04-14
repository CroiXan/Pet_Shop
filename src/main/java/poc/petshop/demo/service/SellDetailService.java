package poc.petshop.demo.service;

import java.util.List;
import java.util.Optional;

import poc.petshop.demo.model.SellDetail;

public interface SellDetailService {
    List<SellDetail> getSellDetails();
    Optional<SellDetail> getSellDetailById(Long id);
    SellDetail createSellDetail(SellDetail sellDetail);
    SellDetail updateSellDetail(Long id, SellDetail sellDetail);
    void deleteSellDetail(Long id);
}
