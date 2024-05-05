package poc.petshop.demo.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import poc.petshop.demo.model.IncomeDetail;
import poc.petshop.demo.model.ParsedInt;
import poc.petshop.demo.model.ParsedLong;
import poc.petshop.demo.model.Product;
import poc.petshop.demo.model.SellDetail;

public class ServiceUtils {

    public ParsedLong validateLong(String intAsStr,String paramName){

        ParsedLong parsedLong = new ParsedLong();

        try {

            parsedLong.setSuccess(true);
            Long parsedInt = Long.parseLong(intAsStr);

            if(parsedInt < 0){
                parsedLong.setSuccess(false);
                parsedLong.setErrorMessage(paramName + " no puede ser negativo");
            }

            parsedLong.setResultLong(parsedInt);
        } catch (Exception e) {
            parsedLong.setErrorMessage(paramName + " no valido");
            parsedLong.setSuccess(false);
        }

        return parsedLong;

    }

    public ParsedInt validateYear(String yearStr){
        
        ParsedInt parsedInt = new ParsedInt();
        ParsedLong parsedLong = new ParsedLong();

        if(yearStr.length() == 4){
            parsedLong = validateLong(yearStr,"año");
            if(parsedLong.isSuccess()){
                parsedInt.setResultInt(Long.valueOf(parsedLong.getResultLong()).intValue());
                parsedInt.setSuccess(true);
            }else{
                parsedInt.setErrorMessage(parsedLong.getErrorMessage());
                parsedInt.setSuccess(false);
            }
        }else{
            parsedInt.setErrorMessage("valor de año no valido");
            parsedInt.setSuccess(false);
        }

        return parsedInt;

    }

    public ParsedInt validateMonth(String monthStr){

        int monthLength = monthStr.length();
        ParsedInt parsedInt = new ParsedInt();
        ParsedLong parsedLong = new ParsedLong();

        if( monthLength == 1 || monthLength == 2){
            parsedLong = validateLong(monthStr,"mes");
            if (parsedLong.isSuccess()) {
                parsedInt.setResultInt(Long.valueOf(parsedLong.getResultLong()).intValue());
                parsedInt.setSuccess(true);
                if(parsedInt.getResultInt() < 1 || parsedInt.getResultInt() > 12){
                    parsedInt.setSuccess(false);
                    parsedInt.setErrorMessage("numero de mes no valido");
                }
            }else{
                parsedInt.setErrorMessage(parsedLong.getErrorMessage());
                parsedInt.setSuccess(false);
            }
        }else if(monthLength == 0){
            parsedInt.setResultInt(0);
            parsedInt.setSuccess(true);
        }else{
            parsedInt.setSuccess(false);
            parsedInt.setErrorMessage("valor de mes no valido");
        }

        return parsedInt;

    }

    public ParsedInt validateDay(String dayStr){

        int dayLength = dayStr.length();
        ParsedInt parsedInt = new ParsedInt();
        ParsedLong parsedLong = new ParsedLong();

        if(dayLength == 1 || dayLength == 2){
            parsedLong = validateLong(dayStr,"dia");
            if (parsedLong.isSuccess()) {
                parsedInt.setResultInt(Long.valueOf(parsedLong.getResultLong()).intValue());
                parsedInt.setSuccess(true);
                if(parsedInt.getResultInt() < 1 || parsedInt.getResultInt() > 31){
                    parsedInt.setSuccess(false);
                    parsedInt.setErrorMessage("numero de dia no valido");
                }
            } else {
                parsedInt.setErrorMessage(parsedLong.getErrorMessage());
                parsedInt.setSuccess(false);
            }
        }else if(dayLength == 0){
            parsedInt.setResultInt(0);
            parsedInt.setSuccess(true);
        }else{
            parsedInt.setSuccess(false);
            parsedInt.setErrorMessage("valor de dia no valido");
        }

        return parsedInt;

    }

    public IncomeDetail calcEarning(Product product,int year, int month, int day, List<SellDetail> sellDetails){
        double income = 0.0;
        double earning = 0.0;
        String period = year+"";

        if (month > 0) {
            period += "-" + month;
        }

        if(year > 0 && month > 0 && day > 0){
            try {

                LocalDate searchDate = LocalDate.of(year, month, day);
                period += "-" + day;

                for (SellDetail sellDetail : sellDetails) {
                    if (sellDetail.getIdProduct() == product.getId() || product == null) {
                        if (sellDetail.getSellDateTime().toLocalDate().equals(searchDate)) {
                            income += sellDetail.getSellPrice();
                            earning += sellDetail.getSellPrice() * 0.81 - product.getPurchasePrice();
                        }
                    }
                }

            } catch (Exception e) {
                return null;
            }
        }else{

            for (SellDetail sellDetail : sellDetails) {
                if (sellDetail.getIdProduct() == product.getId() || product == null) {
                    if ( sellDetail.getSellDateTime().getYear() == year && ( month == 0 || sellDetail.getSellDateTime().getMonthValue() == month ) ) {
                        income += sellDetail.getSellPrice();
                        earning += sellDetail.getSellPrice() * 0.81 - product.getPurchasePrice();
                    }
                }
            }

        }

        DecimalFormat format = new DecimalFormat("#.##");
        
        return (new IncomeDetail(1L,product.getId(),LocalDateTime.now(), period, income,Double.parseDouble(format.format(earning))));
        
    }
    
}
