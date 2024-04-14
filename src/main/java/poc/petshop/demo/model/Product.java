package poc.petshop.demo.model;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name="purchasePrice")
    private int purchasePrice;

    @Column(name="quantity")
    private int quantity;

    private List<SellDetail> sellDetailsList = new ArrayList<>();
    private List<IncomeDetail> incomeDetailList = new ArrayList<>();

    public Product(Long id, String name, String description, int purchasePrice, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
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

    public List<SellDetail> getsellDetailsList() {
        return sellDetailsList;
    }

    public void setsellDetailsList(List<SellDetail> sellDetailsList) {
        this.sellDetailsList = sellDetailsList;
    }

    public void addSellDetail(SellDetail sellDetail){
        
        int size = this.sellDetailsList.size();

        if (size < this.quantity) {
            Long id = 0L;

            if(size > 0){
                id = sellDetailsList.get(size-1).getId() + 1;
            }
            
            sellDetail.setId(id);
    
            this.sellDetailsList.add(sellDetail);
        }
        
    }

    public List<IncomeDetail> getIncomeDetailList() {
        return incomeDetailList;
    }

    public IncomeDetail addIncomeDetail(IncomeDetail incomeDetail){
        incomeDetail.setId(this.incomeDetailList.size()+1L);
        this.incomeDetailList.add(incomeDetail);
        return incomeDetail;
    }

    public List<SellDetail> getSellDetailsList() {
        return sellDetailsList;
    }

    public void setSellDetailsList(List<SellDetail> sellDetailsList) {
        this.sellDetailsList = sellDetailsList;
    }

    public void setIncomeDetailList(List<IncomeDetail> incomeDetailList) {
        this.incomeDetailList = incomeDetailList;
    }

}
