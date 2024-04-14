package poc.petshop.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "producto")
public class Product {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    @Column(name="id_producto")
    private Long id;

    @Column(name="nombre")
    private String name;

    @Column(name="descripcion")
    private String description;

    @Column(name="purchase_price")
    private int purchasePrice;

    @Column(name="quantity")
    private int quantity;

    public Product(Long id, String name, String description, int purchasePrice, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
