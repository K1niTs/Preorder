package org.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;

    @OneToMany(mappedBy = "status")
    private List<PreOrder> preOrders;

    public OrderStatus() {}

    public OrderStatus(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<PreOrder> getPreOrders() { return preOrders; }
    public void setPreOrders(List<PreOrder> preOrders) { this.preOrders = preOrders; }
}
