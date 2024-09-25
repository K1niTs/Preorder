package org.example.services;

import java.util.List;
import java.util.Optional;

import org.example.models.OrderStatus;
import org.springframework.stereotype.Service;
import org.example.repository.OrderStatusRepository;

@Service
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    public List<OrderStatus> getAllOrderStatuses() {
        return orderStatusRepository.findAll();
    }

    public Optional<OrderStatus> getOrderStatusById(Long id) {
        return orderStatusRepository.findById(id);
    }

    public OrderStatus createOrderStatus(OrderStatus status) {
        return orderStatusRepository.save(status);
    }

    public OrderStatus updateOrderStatus(Long id, OrderStatus updatedStatus) {
        OrderStatus existingStatus = orderStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order status not found"));
        existingStatus.setStatus(updatedStatus.getStatus());
        return orderStatusRepository.save(existingStatus);
    }

    public void deleteOrderStatus(Long id) {
        orderStatusRepository.deleteById(id);
    }
}
