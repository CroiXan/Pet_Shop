package poc.petshop.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import poc.petshop.demo.model.SellDetail;
import poc.petshop.demo.repository.SellDetailRepository;

public class SellDetailServiceImpl implements SellDetailService{

    @Autowired
    private SellDetailRepository sellDetailRepository;

    @Override
    public SellDetail createSellDetail(SellDetail sellDetail) {
        return sellDetailRepository.save(sellDetail);
    }

    @Override
    public void deleteSellDetail(Long id) {
        sellDetailRepository.deleteById(id);
    }

    @Override
    public Optional<SellDetail> getSellDetailById(Long id) {
        return sellDetailRepository.findById(id);
    }

    @Override
    public List<SellDetail> getSellDetails() {
        return sellDetailRepository.findAll();
    }

    @Override
    public SellDetail updateSellDetail(Long id, SellDetail sellDetail) {
        if (sellDetailRepository.existsById(id)) {
            sellDetail.setId(id);
            return sellDetailRepository.save(sellDetail);
        }
        return null;
    }

}
