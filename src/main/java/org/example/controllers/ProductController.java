package org.example.controllers;

import org.example.models.Product;
import org.springframework.web.bind.annotation.*;
import org.example.services.DTO.ProductDTO;
import org.example.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getSize(), product.getStock()))
                .toList();
    }
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getSize(),product.getStock());  // добавляем полеproduct.getStock()   // добавляем поле
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        Product product = new Product(
                null,
                productDTO.getName(), productDTO.getDescription(),
                productDTO.getPrice(), productDTO.getSize(),
                productDTO.getStock()
        );
        Product savedProduct = productService.createProduct(product);
        return new ProductDTO(
                savedProduct.getId(), savedProduct.getName(), savedProduct.getDescription(), savedProduct.getPrice(), savedProduct.getSize(), savedProduct.getStock()
        );
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product product = new Product(
                id, productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(),
                productDTO.getSize(),
                productDTO.getStock()
        );
        Product updatedProduct = productService.updateProduct(id, product);
        return new ProductDTO(
                updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getPrice(),
                updatedProduct.getSize(),
                updatedProduct.getStock()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
