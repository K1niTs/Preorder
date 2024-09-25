package org.example;

import org.example.models.Customer;
import org.example.models.OrderStatus;
import org.example.models.PreOrder;
import org.example.models.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.example.services.CustomerService;
import org.example.services.OrderStatusService;
import org.example.services.PreOrderService;
import org.example.services.ProductService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PreOrderService preOrderService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderStatusService orderStatusService;

    public DataInitializer(PreOrderService preOrderService, ProductService productService,
                           CustomerService customerService, OrderStatusService orderStatusService) {
        this.preOrderService = preOrderService;
        this.productService = productService;
        this.customerService = customerService;
        this.orderStatusService = orderStatusService;
    }

    @Override
    public void run(String... args) throws Exception {
        Product item1 = new Product(null, "T-shirt", "Basic white T-shirt", 19.99, "M", 50);
        Product item2 = new Product(null, "Jeans", "Blue denim jeans", 39.99, "L", 30);

        Product savedItem1 = productService.createProduct(item1);
        Product savedItem2 = productService.createProduct(item2);

        OrderStatus pendingStatus = new OrderStatus(null, "Pending");
        OrderStatus confirmedStatus = new OrderStatus(null, "Confirmed");

        OrderStatus savedPendingStatus = orderStatusService.createOrderStatus(pendingStatus);
        OrderStatus savedConfirmedStatus = orderStatusService.createOrderStatus(confirmedStatus);

        Customer customer1 = new Customer(null, "John Doe", "johndoe@example.com", "123 Elm Street");
        Customer customer2 = new Customer(null, "Jane Smith", "janesmith@example.com", "456 Oak Avenue");

        Customer savedCustomer1 = customerService.createCustomer(customer1);
        Customer savedCustomer2 = customerService.createCustomer(customer2);

        PreOrder preOrder1 = new PreOrder(null, savedItem1, 2, savedCustomer1, savedPendingStatus);
        PreOrder preOrder2 = new PreOrder(null, savedItem2, 1, savedCustomer2, savedConfirmedStatus);

        preOrderService.createPreOrder(preOrder1);
        preOrderService.createPreOrder(preOrder2);

    }
}
