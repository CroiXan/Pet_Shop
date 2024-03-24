package poc.petshop.demo;

import java.time.LocalDateTime;

public class IncomeDetail {
    private int id;
    private LocalDateTime calcDateTime;
    private String period;
    private double income;
    private double earning;
    
    public IncomeDetail(LocalDateTime calcDateTime, String period, double income, double earning) {
        this.calcDateTime = calcDateTime;
        this.period = period;
        this.income = income;
        this.earning = earning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    
}
