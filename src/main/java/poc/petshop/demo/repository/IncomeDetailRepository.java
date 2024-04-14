package poc.petshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.petshop.demo.model.IncomeDetail;

public interface IncomeDetailRepository extends JpaRepository<IncomeDetail,Long> {

}
