package org.example.controllers;

import org.example.models.PreOrder;
import org.example.services.PreOrderService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/api/preorders")
public class PreOrderController {

    private final PreOrderService preOrderService;

    public PreOrderController(PreOrderService preOrderService) {
        this.preOrderService = preOrderService;
    }

    @GetMapping
    public List<EntityModel<PreOrder>> getAllPreOrders() {
        List<PreOrder> preOrders = preOrderService.getAllPreOrders();

        return preOrders.stream()
                .map(preOrder -> {
                    EntityModel<PreOrder> resource = EntityModel.of(preOrder);
                    resource.add(linkTo(methodOn(this.getClass()).getPreOrderById(preOrder.getId())).withSelfRel());
                    resource.add(linkTo(methodOn(this.getClass()).updatePreOrder(preOrder.getId(), preOrder)).withRel("update").withType("PUT").withMedia("application/json"));
                    resource.add(linkTo(methodOn(this.getClass()).deletePreOrder(preOrder.getId())).withRel("delete").withType("DELETE"));
                    resource.add(linkTo(methodOn(ProductController.class).getProductById(preOrder.getProduct().getId())).withRel("product"));
                    resource.add(linkTo(methodOn(CustomerController.class).getCustomerById(preOrder.getCustomer().getId())).withRel("customer"));
                    resource.add(linkTo(methodOn(OrderStatusController.class).getOrderStatusById(preOrder.getStatus().getId())).withRel("orderStatus"));
                    return resource;
                })
                .toList();
    }

    @GetMapping("/{id}")
    public EntityModel<PreOrder> getPreOrderById(@PathVariable Long id) {
        PreOrder preOrder = preOrderService.getPreOrderById(id)
                .orElseThrow(() -> new RuntimeException("PreOrder not found"));

        EntityModel<PreOrder> resource = EntityModel.of(preOrder);
        resource.add(linkTo(methodOn(this.getClass()).getPreOrderById(id)).withSelfRel());
        resource.add(linkTo(methodOn(this.getClass()).updatePreOrder(id, preOrder)).withRel("update").withType("PUT").withMedia("application/json"));
        resource.add(linkTo(methodOn(this.getClass()).deletePreOrder(id)).withRel("delete").withType("DELETE"));
        resource.add(linkTo(methodOn(ProductController.class).getProductById(preOrder.getProduct().getId())).withRel("product"));
        resource.add(linkTo(methodOn(CustomerController.class).getCustomerById(preOrder.getCustomer().getId())).withRel("customer"));
        resource.add(linkTo(methodOn(OrderStatusController.class).getOrderStatusById(preOrder.getStatus().getId())).withRel("orderStatus"));

        return resource;
    }

    @PostMapping
    public PreOrder createPreOrder(@RequestBody PreOrder preOrder) {
        return preOrderService.createPreOrder(preOrder);
    }

    @PutMapping("/{id}")
    public PreOrder updatePreOrder(@PathVariable Long id, @RequestBody PreOrder preOrder) {
        return preOrderService.updatePreOrder(id, preOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreOrder(@PathVariable Long id) {
        preOrderService.deletePreOrder(id);
        return ResponseEntity.noContent().build();
    }
}
