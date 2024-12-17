package com.genpact.onlineShoppingApp.service;

import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //get product by name
    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    //add product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    //delete product
    public boolean deleteProduct(ObjectId id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    //update product
    public Product updateProduct(ObjectId id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            product.setId(id);
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    //get product by id
    public Product getProductById(ObjectId id) {
        return productRepository.findById(id).orElse(null);
    }

    // reduce quantity
    public Product reduceQuantity(ObjectId id, int cartQuantity) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null && product.getQuantity() >= cartQuantity) {
            product.setQuantity(product.getQuantity() - cartQuantity);
            return productRepository.save(product);
        }
        return null;
    }
}
