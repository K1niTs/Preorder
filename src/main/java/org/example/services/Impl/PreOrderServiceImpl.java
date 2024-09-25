package org.example.services.Impl;

import org.example.models.PreOrder;
import org.example.services.PreOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PreOrderServiceImpl implements PreOrderService {

    private List<PreOrder> preOrders = new ArrayList<>();
    private long currentId = 1L;

    @Override
    public List<PreOrder> getAllPreOrders() {
        return preOrders;
    }

    @Override
    public Optional<PreOrder> getPreOrderById(Long id) {
        return preOrders.stream().filter(po -> po.getId().equals(id)).findFirst();
    }

    @Override
    public PreOrder createPreOrder(PreOrder preOrder) {
        preOrder.setId(currentId++);
        preOrders.add(preOrder);
        return preOrder;
    }

    @Override
    public PreOrder updatePreOrder(Long id, PreOrder preOrder) {
        Optional<PreOrder> existingPreOrder = getPreOrderById(id);
        if (existingPreOrder.isPresent()) {
            preOrders.remove(existingPreOrder.get());
            preOrder.setId(id);
            preOrders.add(preOrder);
            return preOrder;
        }
        return null;
    }

    @Override
    public void deletePreOrder(Long id) {
        preOrders.removeIf(po -> po.getId().equals(id));
    }
}
