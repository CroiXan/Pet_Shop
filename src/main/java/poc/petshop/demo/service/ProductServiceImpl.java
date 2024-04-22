package poc.petshop.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.petshop.demo.model.ParsedLong;
import poc.petshop.demo.model.Product;
import poc.petshop.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public boolean existsProductById(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public ParsedLong validateLong(String intAsStr,String paramName){

        ParsedLong parsedLong = new ParsedLong();

        try {

            parsedLong.setSuccess(true);
            Long parsedInt = Long.parseLong(intAsStr);

            if(parsedInt < 0){
                parsedLong.setSuccess(false);
                parsedLong.setErrorMessage(paramName + " no puede ser negativo");
            }

            parsedLong.setResultLong(parsedInt);
        } catch (Exception e) {
            parsedLong.setErrorMessage(paramName + " no valido");
            parsedLong.setSuccess(false);
        }

        return parsedLong;
    }
}
