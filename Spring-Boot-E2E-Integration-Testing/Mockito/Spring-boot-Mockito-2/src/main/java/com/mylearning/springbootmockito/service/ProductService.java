package com.mylearning.springbootmockito.service;

import com.mylearning.springbootmockito.entity.Product;
import com.mylearning.springbootmockito.repository.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long l) {
        return productRepository.findById(l);
    }


    // just to understand unit testing using mockito
    public Product createProduct(Product product) {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public String deleteProduct2(long id) {
        productRepository.deleteById(id);
        return "Deleted product with id: " + id;
    }

    // Real implementation throws an exception
    public void deleteProduct3(Long id) {
        throw new UnsupportedOperationException("Cannot delete");
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.deleteById(productId); // void method
    }


    private boolean validate(Long id) {
        if (id < 0)
            return false;
        else return true;
    }

    public Product addProduct(Product product) {
        if (validate(product.getId())) {
            return productRepository.save(product);
        } else
            throw new RuntimeException("Product Id cannot be negative");
    }

    public void findProductById(long l) {
        productRepository.findById(l);
    }

    public Product getProductById(Long id) {
        // Imagine this calls a database and returns a product.
        return new Product("Product Name", id);
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }


    public Product findProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    //Test this using inorder

    public void checkAndAddProduct(Long id, Product product) {
        // Check if the product exists
        Product existingProduct = findProduct(id);
        if (existingProduct == null) {
            addProduct(product);
        }
    }
}
