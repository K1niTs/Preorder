package org.example.services.DTO;

public class PreOrderDTO {
    private Long id;
    private ProductDTO item;
    private int quantity;
    private String customerName;
    private String status;


    public PreOrderDTO() {}

    public PreOrderDTO(Long id, ProductDTO item, int quantity, String customerName, String status) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.customerName = customerName;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProductDTO getItem() { return item; }
    public void setItem(ProductDTO item) { this.item = item; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
