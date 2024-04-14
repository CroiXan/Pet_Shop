package poc.petshop.demo.service;

import java.util.List;
import java.util.Optional;

import poc.petshop.demo.model.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
