package org.example.graphQL;

import org.example.models.Product;
import org.example.services.ProductService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsMutation;

import java.util.List;

@DgsComponent
public class ProductDataFetcher {

    private final ProductService productService;

    public ProductDataFetcher(ProductService productService) {
        this.productService = productService;
    }

    @DgsQuery
    public List<Product> allProducts() {
        return productService.getAllProducts();
    }

    @DgsQuery
    public Product productById(Long id) {
        return productService.getProductById(id).orElse(null);
    }

    @DgsMutation
    public Product createProduct(String name, String description, double price, String size, int stock, int salesCount) {
        Product product = new Product(null, name, description, price, size, stock, salesCount);
        return productService.createProduct(product);
    }

    @DgsMutation
    public Product updateProduct(Long id, String name, String description, double price, String size, int stock, int salesCount) {
        Product product = new Product(id, name, description, price, size, stock, salesCount);
        return productService.updateProduct(id, product);
    }

    @DgsMutation
    public boolean deleteProduct(Long id) {
        try {
            productService.deleteProduct(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
