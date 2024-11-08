package org.example.controllers;

import org.example.models.Product;
import org.springframework.web.bind.annotation.*;
import org.example.services.DTO.ProductDTO;
import org.example.services.ProductService;
import org.example.messaging.RabbitMQSender;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final RabbitMQSender rabbitMQSender;  // Добавляем отправитель RabbitMQ

    public ProductController(ProductService productService, RabbitMQSender rabbitMQSender) {
        this.productService = productService;
        this.rabbitMQSender = rabbitMQSender;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getSize(),
                        product.getStock(),
                        product.getSalesCount()))
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSize(),
                product.getStock(),
                product.getSalesCount());
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        Product product = new Product(
                null,
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getSize(),
                productDTO.getStock(),
                productDTO.getSalesCount()
        );
        Product savedProduct = productService.createProduct(product);

        // Отправка сообщения в очередь RabbitMQ после создания продукта
        rabbitMQSender.sendMessage(savedProduct);

        return new ProductDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getSize(),
                savedProduct.getStock(),
                savedProduct.getSalesCount()
        );
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product product = new Product(
                id,
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getSize(),
                productDTO.getStock(),
                productDTO.getSalesCount()
        );
        Product updatedProduct = productService.updateProduct(id, product);

        // Отправка сообщения в очередь RabbitMQ после обновления продукта
        rabbitMQSender.sendMessage(updatedProduct);

        return new ProductDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getSize(),
                updatedProduct.getStock(),
                updatedProduct.getSalesCount()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        // Отправка сообщения в очередь RabbitMQ после удаления продукта
        Product deletedProduct = new Product(id, null, null, 0, null, 0, 0);
        rabbitMQSender.sendMessage(deletedProduct);
    }

    // Маршрут для получения продуктов в ценовом диапазоне
    @GetMapping("/price-range")
    public List<ProductDTO> getProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return products.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getSize(),
                        product.getStock(),
                        product.getSalesCount()))
                .toList();
    }

    // Маршрут для получения топ-продуктов по продажам
    @GetMapping("/top-sellers")
    public List<ProductDTO> getTopSellingProducts() {
        System.out.println("Getting top selling products");
        List<Product> topSellingProducts = productService.getTopSellingProducts();
        return topSellingProducts.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getSize(),
                        product.getStock(),
                        product.getSalesCount()))
                .toList();
    }
}
