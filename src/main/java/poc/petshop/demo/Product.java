package poc.petshop.demo;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private String description;
    private int purchasePrice;
    private int quantity;
    private List<SellDetail> sellDetailsList = new ArrayList<>();
    private List<IncomeDetail> incomeDetailList = new ArrayList<>();

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

    public List<SellDetail> getsellDetailsList() {
        return sellDetailsList;
    }

    public void setsellDetailsList(List<SellDetail> sellDetailsList) {
        this.sellDetailsList = sellDetailsList;
    }

    public void addSellDetail(SellDetail sellDetail){
        
        int size = this.sellDetailsList.size();

        if (size < this.quantity) {
            int id = 0;

            if(size > 0){
                id = sellDetailsList.get(size-1).getId() + 1;
            }
            
            sellDetail.setId(id);
    
            this.sellDetailsList.add(sellDetail);
        }
        
    }

    public List<SellDetail> getSellDetailsList() {
        return sellDetailsList;
    }

    public List<IncomeDetail> getIncomeDetailList() {
        return incomeDetailList;
    }

    public IncomeDetail addIncomeDetail(IncomeDetail incomeDetail){
        incomeDetail.setId(this.incomeDetailList.size()+1);
        this.incomeDetailList.add(incomeDetail);
        return incomeDetail;
    }

}
