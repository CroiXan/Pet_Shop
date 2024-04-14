package poc.petshop.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "venta_detalle")
public class IncomeDetail {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    @Column(name="id_venta_detalle")
    private Long id;

    @Column(name="id_producto")
    private Long idProduct;

    @Column(name="calc_date")
    private LocalDateTime calcDateTime;

    @Column(name="periodo")
    private String period;

    @Column(name="income")
    private double income;
    
    @Column(name="earning")
    private double earning;

    @Column(name="sell_price")
    private double sellPrice;
    
    public IncomeDetail(LocalDateTime calcDateTime, String period, double income, double earning) {
        this.calcDateTime = calcDateTime;
        this.period = period;
        this.income = income;
        this.earning = earning;
    }

    public IncomeDetail(Long id, Long idProduct, LocalDateTime calcDateTime, String period, double income,
            double earning) {
        this.id = id;
        this.idProduct = idProduct;
        this.calcDateTime = calcDateTime;
        this.period = period;
        this.income = income;
        this.earning = earning;
    }

    public IncomeDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCalcDateTime() {
        return calcDateTime;
    }

    public void setCalcDateTime(LocalDateTime calcDateTime) {
        this.calcDateTime = calcDateTime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
    
}
