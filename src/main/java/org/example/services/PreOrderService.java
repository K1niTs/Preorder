package org.example.services;

import org.example.models.PreOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PreOrderService {
    List<PreOrder> getAllPreOrders();
    Optional<PreOrder> getPreOrderById(Long id);
    PreOrder createPreOrder(PreOrder preOrder);
    PreOrder updatePreOrder(Long id, PreOrder preOrder);
    void deletePreOrder(Long id);
}
