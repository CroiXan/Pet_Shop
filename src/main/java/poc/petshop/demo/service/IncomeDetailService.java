package poc.petshop.demo.service;

import java.util.List;
import java.util.Optional;

import poc.petshop.demo.model.IncomeDetail;

public interface IncomeDetailService {
    List<IncomeDetail> getIncomeDetails();
    Optional<IncomeDetail> getIncomeDetailById(Long id);
    IncomeDetail createIncomeDetail(IncomeDetail incomeDetail);
    IncomeDetail updateIncomeDetail(Long id, IncomeDetail incomeDetail);
    void deleteIncomeDetail(Long id);
}
