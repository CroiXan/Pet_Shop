package poc.petshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.petshop.demo.model.SellDetail;

public interface SellDetailRepository extends JpaRepository<SellDetail,Long>{

}
