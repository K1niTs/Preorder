package org.example.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.RabbitMQConfig;
import org.example.models.Product;
import org.example.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMQReceiver(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String productJson) {
        try {
            // Десериализация JSON строки обратно в объект Product
            Product product = objectMapper.readValue(productJson, Product.class);
            System.out.println("Received message from queue: " + product);

            // Логика обработки сообщения
            if (product.getId() == null) {
                // Если ID отсутствует, создаем новый продукт
                productService.createProduct(product);
                System.out.println("Created new product: " + product);
            } else {
                // Если ID присутствует, обновляем существующий продукт
                productService.updateProduct(product.getId(), product);
                System.out.println("Updated product with ID " + product.getId());
            }
        } catch (JsonProcessingException e) {
            // Обработка ошибки десериализации
            e.printStackTrace();
        }
    }
}
