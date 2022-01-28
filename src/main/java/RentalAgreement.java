import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RentalAgreement {

    private String code;
    private String type;
    private String brand;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BigDecimal dailyCharge;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private int discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(BigDecimal preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(BigDecimal finalCharge) {
        this.finalCharge = finalCharge;
    }

    public void print() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        NumberFormat USDFormat = NumberFormat.getCurrencyInstance(new Locale("en", "us"));
        USDFormat.setRoundingMode(RoundingMode.HALF_UP);

        StringBuilder sb = new StringBuilder();
        sb.append("Tool code: ").append(code).append("\n");
        sb.append("Tool type: ").append(type).append("\n");
        sb.append("Tool brand: ").append(brand).append("\n");
        sb.append("Rental days: ").append(rentalDays).append("\n");
        sb.append("Check out date: ").append(checkoutDate.format(dateFormat)).append("\n");
        sb.append("Due date: ").append(dueDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))).append("\n");
        sb.append("Daily rental charge: ").append(USDFormat.format(dailyCharge)).append("\n");
        sb.append("Charge days: ").append(chargeDays).append("\n");
        sb.append("Pre-discount charge: ").append(USDFormat.format(preDiscountCharge)).append("\n");
        sb.append("Discount percent: Percent ").append(discountPercent).append("%").append("\n");
        sb.append("Discount amount: ").append(USDFormat.format(discountAmount)).append("\n");
        sb.append("Final charge: ").append(USDFormat.format(finalCharge)).append("\n");

        System.out.println(sb);
    }
}
