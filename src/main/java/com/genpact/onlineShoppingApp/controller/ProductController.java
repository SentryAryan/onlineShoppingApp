package com.genpact.onlineShoppingApp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;

import org.bson.types.ObjectId;
import java.util.List;

import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "https://online-shooping-app-frontend.vercel.app/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{name}")
    public Product getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity) throws IOException {

        try {
            // Upload image to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            // Create new Product object
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setImageUrl(imageUrl);
            product.setQuantity(quantity);

            return ResponseEntity.ok(productService.addProduct(product));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable ObjectId id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Product not found");
        }
    }

    // update product this also has a image
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable ObjectId id, @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name, @RequestParam("description") String description,
            @RequestParam("price") double price, @RequestParam("quantity") int quantity) throws IOException {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setImageUrl(imageUrl);
            product.setQuantity(quantity);
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // get product by id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable ObjectId id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(404).body("Product not found");
        }
    }

    // reduce quantity
    @PutMapping("/reduce-quantity/{id}/{cartQuantity}")
    public ResponseEntity<?> reduceQuantity(@PathVariable ObjectId id, @PathVariable int cartQuantity) {
        Product updatedProduct = productService.reduceQuantity(id, cartQuantity);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.badRequest().body("Could not reduce quantity. Check if product exists and has sufficient stock.");
    }
}
