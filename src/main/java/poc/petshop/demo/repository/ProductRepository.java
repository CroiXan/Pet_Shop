package poc.petshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.petshop.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

}
