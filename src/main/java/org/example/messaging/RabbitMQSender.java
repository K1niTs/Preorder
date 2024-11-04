package org.example.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitMQSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Product product) {
        try {
            // Сериализация продукта в JSON строку
            String productJson = objectMapper.writeValueAsString(product);
            // Отправка сериализованного продукта в очередь "productQueue"
            rabbitTemplate.convertAndSend("productQueue", productJson);
            System.out.println("Sent message: " + productJson);
        } catch (JsonProcessingException e) {
            // Обработка ошибки сериализации
            e.printStackTrace();
        }
    }
}
