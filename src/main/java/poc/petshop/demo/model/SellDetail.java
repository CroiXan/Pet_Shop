
package poc.petshop.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "venta")
public class SellDetail {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    @Column(name="id_venta")
    private Long id;

    @Column(name="id_producto")
    private Long idProduct;

    @Column(name="sell_price")
    private int sellPrice;

    @Column(name="sell_date")
    private LocalDateTime sellDateTime;

    public SellDetail() {
    }

    public SellDetail(int sellPrice, LocalDateTime sellDateTime) {
        this.sellPrice = sellPrice;
        this.sellDateTime = sellDateTime;
    }

    public SellDetail(Long id, Long idProduct, int sellPrice, LocalDateTime sellDateTime) {
        this.id = id;
        this.idProduct = idProduct;
        this.sellPrice = sellPrice;
        this.sellDateTime = sellDateTime;
    }

    public SellDetail(Long id, int sellPrice, LocalDateTime sellDateTime) {
        this.id = id;
        this.sellPrice = sellPrice;
        this.sellDateTime = sellDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public LocalDateTime getSellDateTime() {
        return sellDateTime;
    }

    public void setSellDateTime(LocalDateTime sellDateTime) {
        this.sellDateTime = sellDateTime;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    
}
