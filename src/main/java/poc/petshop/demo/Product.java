package poc.petshop.demo;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private String description;
    private int purchasePrice;
    private int quantity;
    private List<SellDetail> sellListDetails = new ArrayList<>();

    public Product(int id, String name, String description, int purchasePrice, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.quantity = Math.abs(quantity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<SellDetail> getSellListDetails() {
        return sellListDetails;
    }

    public void setSellListDetails(List<SellDetail> sellListDetails) {
        this.sellListDetails = sellListDetails;
    }

    public void addSellDetail(SellDetail sellDetail){
        
        int size = this.sellListDetails.size();

        if (size < this.quantity) {
            int id = 0;

            if(size > 0){
                id = sellListDetails.get(size-1).getId() + 1;
            }
            
            sellDetail.setId(id);
    
            this.sellListDetails.add(sellDetail);
        }
        
    }

}
