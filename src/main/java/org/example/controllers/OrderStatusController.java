package org.example.controllers;

import org.example.models.OrderStatus;
import org.springframework.web.bind.annotation.*;
import org.example.services.DTO.OrderStatusDTO;
import org.example.services.OrderStatusService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-statuses")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @GetMapping
    public List<OrderStatusDTO> getAllOrderStatuses() {
        List<OrderStatus> statuses = orderStatusService.getAllOrderStatuses();
        return statuses.stream()
                .map(status -> new OrderStatusDTO(status.getId(), status.getStatus()))
                .toList();
    }

    @GetMapping("/{id}")
    public OrderStatusDTO getOrderStatusById(@PathVariable Long id) {
        Optional<OrderStatus> status = orderStatusService.getOrderStatusById(id);
        if (status.isEmpty()) {
            throw new RuntimeException("Order status not found");
        }
        OrderStatus s = status.get();
        return new OrderStatusDTO(s.getId(), s.getStatus());
    }

    @PostMapping
    public OrderStatusDTO createOrderStatus(@RequestBody OrderStatusDTO orderStatusDTO) {
        OrderStatus status = new OrderStatus(null, orderStatusDTO.getStatus());
        OrderStatus savedStatus = orderStatusService.createOrderStatus(status);
        return new OrderStatusDTO(savedStatus.getId(), savedStatus.getStatus());
    }

    @PutMapping("/{id}")
    public OrderStatusDTO updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusDTO orderStatusDTO) {
        OrderStatus status = new OrderStatus(id, orderStatusDTO.getStatus());
        OrderStatus updatedStatus = orderStatusService.updateOrderStatus(id, status);
        return new OrderStatusDTO(updatedStatus.getId(), updatedStatus.getStatus());
    }

    @DeleteMapping("/{id}")
    public void deleteOrderStatus(@PathVariable Long id) {
        orderStatusService.deleteOrderStatus(id);
    }
}
