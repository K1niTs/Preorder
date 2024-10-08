package org.example.graphQL;

import org.example.models.OrderStatus;
import org.example.services.OrderStatusService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsMutation;

import java.util.List;

@DgsComponent
public class OrderStatusDataFetcher {

    private final OrderStatusService orderStatusService;

    public OrderStatusDataFetcher(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @DgsQuery
    public List<OrderStatus> allOrderStatuses() {
        return orderStatusService.getAllOrderStatuses();
    }

    @DgsQuery
    public OrderStatus orderStatusById(Long id) {
        return orderStatusService.getOrderStatusById(id).orElse(null);
    }

    @DgsMutation
    public OrderStatus createOrderStatus(String status, String description) {
        OrderStatus orderStatus = new OrderStatus(null, status);
        return orderStatusService.createOrderStatus(orderStatus);
    }

    @DgsMutation
    public OrderStatus updateOrderStatus(Long id, String status, String description) {
        OrderStatus orderStatus = new OrderStatus(id, status);
        return orderStatusService.updateOrderStatus(id, orderStatus);
    }

    @DgsMutation
    public boolean deleteOrderStatus(Long id) {
        try {
            orderStatusService.deleteOrderStatus(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
