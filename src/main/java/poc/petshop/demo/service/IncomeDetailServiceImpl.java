package poc.petshop.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import poc.petshop.demo.model.IncomeDetail;
import poc.petshop.demo.repository.IncomeDetailRepository;

public class IncomeDetailServiceImpl implements IncomeDetailService{

    @Autowired
    private IncomeDetailRepository incomeDetailRepository;

    @Override
    public IncomeDetail createIncomeDetail(IncomeDetail incomeDetail) {
        return incomeDetailRepository.save(incomeDetail);
    }

    @Override
    public void deleteIncomeDetail(Long id) {
        incomeDetailRepository.deleteById(id);
    }

    @Override
    public Optional<IncomeDetail> getIncomeDetailById(Long id) {
        return incomeDetailRepository.findById(id);
    }

    @Override
    public List<IncomeDetail> getIncomeDetails() {
        return incomeDetailRepository.findAll();
    }

    @Override
    public IncomeDetail updateIncomeDetail(Long id, IncomeDetail incomeDetail) {
        if (incomeDetailRepository.existsById(id)) {
            incomeDetail.setId(id);
            return incomeDetailRepository.save(incomeDetail);
        }
        return null;
    }

    @Override
    public boolean existsIncomeDetailById(Long id) {
        return incomeDetailRepository.existsById(id);
    }

}
