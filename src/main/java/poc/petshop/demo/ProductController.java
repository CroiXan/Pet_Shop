package poc.petshop.demo;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class ProductController {

    private List<Product> productList = new ArrayList<>();
    private ResponseEntity<ErrorMessage> error = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(),"producto no encontrado"));

    public ProductController() {

        productList.add(new Product(1, "plato verde", "plato plastico color verde", 2000, 100));
        productList.get(0).addSellDetail(new SellDetail(3000, LocalDateTime.of(2023, Month.DECEMBER, 15, 15, 35, 0)));
        productList.get(0).addSellDetail(new SellDetail(3000, LocalDateTime.of(2023, Month.DECEMBER, 15, 16, 20, 0)));
        productList.get(0).addSellDetail(new SellDetail(4000, LocalDateTime.of(2024, Month.FEBRUARY, 12, 15,40, 0)));
        productList.get(0).addSellDetail(new SellDetail(2500, LocalDateTime.of(2024, Month.MARCH, 19, 10, 00, 0)));
        productList.get(0).addSellDetail(new SellDetail(2500, LocalDateTime.of(2024, Month.MARCH, 19, 15, 51, 0)));
        productList.get(0).addSellDetail(new SellDetail(3200, LocalDateTime.of(2024, Month.MARCH, 20, 14, 13, 0)));
        productList.get(0).addSellDetail(new SellDetail(3200, LocalDateTime.of(2024, Month.MARCH, 20, 16,48, 0)));
        
        productList.add(new Product(2, "correa con collar gato", "correa confortable para gato incluye correa", 5000, 30));
        productList.get(1).addSellDetail(new SellDetail(10000, LocalDateTime.of(2023, Month.NOVEMBER, 15, 0, 0, 0)));
        productList.get(1).addSellDetail(new SellDetail(12000, LocalDateTime.of(2023, Month.DECEMBER, 20, 0, 0, 0)));
        productList.get(1).addSellDetail(new SellDetail(11500, LocalDateTime.of(2024, Month.JANUARY, 6, 0, 0, 0)));
        productList.get(1).addSellDetail(new SellDetail(10500, LocalDateTime.of(2024, Month.JANUARY, 18, 0, 0, 0)));
        productList.get(1).addSellDetail(new SellDetail(9000, LocalDateTime.of(2024, Month.FEBRUARY, 5, 0, 0, 0)));

        productList.add(new Product(3, "canil para perro mediano", "canil para perro, soporta 30 kg", 40000, 12));
        productList.get(2).addSellDetail(new SellDetail(70000, LocalDateTime.of(2023, Month.DECEMBER, 17, 0, 0, 0)));
        productList.get(2).addSellDetail(new SellDetail(85000, LocalDateTime.of(2024, Month.JANUARY, 4, 0, 0, 0)));
        productList.get(2).addSellDetail(new SellDetail(85000, LocalDateTime.of(2024, Month.JANUARY, 26, 0, 0, 0)));
        productList.get(2).addSellDetail(new SellDetail(78000, LocalDateTime.of(2024, Month.FEBRUARY, 24, 0, 0, 0)));
        productList.get(2).addSellDetail(new SellDetail(80000, LocalDateTime.of(2024, Month.MARCH, 10, 0, 0, 0)));

        productList.add(new Product(4, "casa de perro", "casa plastica de perro raza pequeña", 30000, 8));
        productList.get(3).addSellDetail(new SellDetail(75000, LocalDateTime.of(2023, Month.NOVEMBER, 23, 0, 0, 0)));
        productList.get(3).addSellDetail(new SellDetail(70500, LocalDateTime.of(2023, Month.NOVEMBER, 12, 0, 0, 0)));
        productList.get(3).addSellDetail(new SellDetail(55000, LocalDateTime.of(2023, Month.DECEMBER, 9, 0, 0, 0)));
        productList.get(3).addSellDetail(new SellDetail(80000, LocalDateTime.of(2024, Month.FEBRUARY, 28, 0, 0, 0)));
        productList.get(3).addSellDetail(new SellDetail(85000, LocalDateTime.of(2024, Month.FEBRUARY, 2, 0, 0, 0)));

    }
    
    @GetMapping("/product")
    public List<Product> getProducts(){
        return productList;
    }

    
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {

        int parsedId = validateInteger(id,"id");

        if(parsedId==-1){
            return error;
        }

        for (Product product : productList) {
            if (parsedId == product.getId()) {
                return ResponseEntity.ok(product);
            }
        }

        return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
    }

    @GetMapping("/product/{id}/sell/{price}")
    public ResponseEntity<?> addSellProduct(@PathVariable String id,@PathVariable String price) {
        
        int parsedId = validateInteger(id,"id");
        int parsedPrice = validateInteger(price,"price");

        if( parsedId == -1 || parsedPrice== -1 ){
            return error;
        }

        for (Product product : productList) {
            if (parsedId == product.getId()) {
                product.addSellDetail(new SellDetail(parsedPrice, LocalDateTime.now()));
                return ResponseEntity.ok(product);
            }
        }

        return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
    }

    @GetMapping("/product/{id}/sell/{price}/{date}")
    public ResponseEntity<?> addSellDetailProduct(@PathVariable String id,@PathVariable String price,@PathVariable String date) {
        
        int parsedId = validateInteger(id,"id");
        int parsedPrice = validateInteger(price,"price");
        LocalDateTime parsedDate = validateDate(date);

        if( parsedId == -1 || parsedPrice== -1 || parsedDate == null ){
            return error;
        }

        for (Product product : productList) {
            if (parsedId == product.getId()) {
                product.addSellDetail(new SellDetail(parsedPrice, parsedDate));
                return ResponseEntity.ok(product);
            }
        }

        return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
    }

    @GetMapping("/product/{id}/profit/{year}-{month}-{day}")
    public ResponseEntity<?> calcProfitByDate(@PathVariable String id,@PathVariable String year,@PathVariable String month,@PathVariable String day) {

        int parsedId = validateInteger(id,"id");
        int parsedYear = validateYear(year);
        int parsedMonth = validateMonth(month);
        int parsedDay = validateDay(day);

        if (parsedId == -1 ||parsedYear == -1 || parsedMonth == -1 || parsedDay == -1) {
            return error;
        }

        for (Product product : productList) {
            if (parsedId == product.getId()) {
                IncomeDetail incomeDetail = calcEarning(product, parsedYear, parsedMonth, parsedDay);
                return  ResponseEntity.ok(incomeDetail);
            }
        }

        return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
    }

    @GetMapping("/product/profit/{year}-{month}-{day}")
    public ResponseEntity<?> calcAllProfitByDate(@PathVariable String year,@PathVariable String month,@PathVariable String day) {

        int parsedYear = validateYear(year);
        int parsedMonth = validateMonth(month);
        int parsedDay = validateDay(day);
        String period = year + "";
        double allIncome = 0.0;
        double allEarning = 0.0;
        IncomeDetail localIncomeCalc = new IncomeDetail(LocalDateTime.now(), "", 0, 0);

        if (parsedYear == -1 || parsedMonth == -1 || parsedDay == -1) {
            return error;
        }

        if(parsedMonth > 0 ){
            period += "-"+month;
            if (parsedDay > 0) {
                period += "-"+day;
            }
        }

        localIncomeCalc.setPeriod(period);

        for (Product product : productList) {
            IncomeDetail incomeDetail = calcEarning(product, parsedYear, parsedMonth, parsedDay);
            allIncome += incomeDetail.getIncome();
            allEarning += incomeDetail.getEarning();
        }

        localIncomeCalc.setIncome(allIncome);
        localIncomeCalc.setEarning(allEarning);

        return  ResponseEntity.ok(localIncomeCalc);
    }

    private int validateInteger(String intAsStr,String paramName){
        try {
            int parsedInt = Integer.parseInt(intAsStr);

            if(parsedInt < 0){
                parsedInt = -1;
                error = buildResponseError(HttpStatus.BAD_REQUEST,paramName+" no puede ser negativo");
            }

            return parsedInt;
        } catch (Exception e) {
            error = buildResponseError(HttpStatus.BAD_REQUEST,paramName+" no valido");
            return -1;
        }
    }

    private LocalDateTime validateDate(String dateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            return LocalDateTime.of(LocalDate.parse(dateStr,formatter), LocalTime.of(0, 0));
        } catch (Exception e) {
            error = buildResponseError(HttpStatus.BAD_REQUEST,"formato de fecha no valido, ingresar YYYY-MM-DD");
        }
        return null;
    }

    private int validateYear(String yearStr){
        if(yearStr.length() == 4){
            return validateInteger(yearStr,"año");
        }else{
            error = buildResponseError(HttpStatus.BAD_REQUEST,"valor de año no valido");
        }
        return -1;
    }

    private int validateMonth(String monthStr){
        int monthLength = monthStr.length();

        if(monthLength == 0){
            return 0;
        }

        if( monthLength == 1 || monthLength == 2){
            int monthInt = validateInteger(monthStr,"mes");
            if(monthInt < 1 || monthInt > 12){
                monthInt = -1;
                error = buildResponseError(HttpStatus.BAD_REQUEST,"numero de mes no valido");
            }
            return monthInt;
        }else{
            error = buildResponseError(HttpStatus.BAD_REQUEST,"valor de mes no valido");
        }

        return -1;
    }

    private int validateDay(String dayStr){
        int dayLength = dayStr.length();

        if(dayLength == 0){
            return 0;
        }

        if(dayLength == 1 || dayLength == 2){
            int dayInt = validateInteger(dayStr,"dia");
            if(dayInt < 1 || dayInt > 31){
                dayInt = -1;
                error = buildResponseError(HttpStatus.BAD_REQUEST,"numero de dia no valido");
            }
            return dayInt;
        }else{
            error = buildResponseError(HttpStatus.BAD_REQUEST,"valor de dia no valido");
        }

        return -1;
    }

    private IncomeDetail calcEarning(Product product,int year, int month, int day){
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
                for (SellDetail sellDetail : product.getsellDetailsList()) {
                    if (sellDetail.getSellDateTime().toLocalDate().equals(searchDate)) {
                        income += sellDetail.getSellPrice();
                        earning += sellDetail.getSellPrice() * 0.81 - product.getPurchasePrice();
                    }
                }

            } catch (Exception e) {
                error = buildResponseError(HttpStatus.BAD_REQUEST,"fecha no valida");
                return null;
            }
        }else{
            for (SellDetail sellDetail : product.getsellDetailsList()) {
                
                if ( sellDetail.getSellDateTime().getYear() == year && ( month == 0 || sellDetail.getSellDateTime().getMonthValue() == month ) ) {
                    income += sellDetail.getSellPrice();
                    earning += sellDetail.getSellPrice() * 0.81 - product.getPurchasePrice();
                }
            }
        }

        DecimalFormat format = new DecimalFormat("#.##");
        
        return product.addIncomeDetail(new IncomeDetail(LocalDateTime.now(), period, income,Double.parseDouble(format.format(earning))));
        
    }
    
    private ResponseEntity<ErrorMessage> buildResponseError(HttpStatus status,String message){
        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),message));
    }
}

