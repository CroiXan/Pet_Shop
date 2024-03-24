
package poc.petshop.demo;

import java.time.LocalDateTime;

public class SellDetail {
    private int id;
    private int sellPrice;
    private LocalDateTime sellDateTime;

    public SellDetail(int sellPrice, LocalDateTime sellDateTime) {
        this.sellPrice = sellPrice;
        this.sellDateTime = sellDateTime;
    }

    public SellDetail(int id, int sellPrice, LocalDateTime sellDateTime) {
        this.id = id;
        this.sellPrice = sellPrice;
        this.sellDateTime = sellDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    
}
