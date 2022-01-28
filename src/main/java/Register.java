import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public class Register {

    private final Map<String, Tool> tools;
    private final Set<LocalDate> holidays;

    public Register(Map<String, Tool> tools, Set<LocalDate> holidays) {
        this.tools = tools;
        this.holidays = holidays;
    }

    public RentalAgreement checkout(String code, int days, int discount, LocalDate rentalDate) {
        if (days < 1) throw new IllegalArgumentException("The number of rental days has to be more than zero");
        if (discount < 0 || discount > 100) throw new IllegalArgumentException("The discount has to be between 0% and 100%");

        Tool tool = tools.get(code);

        LocalDate dueDate = rentalDate.plusDays(days);
        int chargeDays = calculateChargeDays(tool, rentalDate, dueDate);
        BigDecimal preDiscountCharge = tool.getType().getDailyCharge().multiply(BigDecimal.valueOf(chargeDays)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal discountPercent = BigDecimal.valueOf(discount).divide(new BigDecimal("100"));
        BigDecimal discountAmount = preDiscountCharge.multiply(discountPercent).setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setCode(tool.getCode());
        rentalAgreement.setType(tool.getType().getName());
        rentalAgreement.setBrand(tool.getBrand());
        rentalAgreement.setRentalDays(days);
        rentalAgreement.setCheckoutDate(rentalDate);
        rentalAgreement.setDueDate(dueDate);
        rentalAgreement.setDailyCharge(tool.getType().getDailyCharge());
        rentalAgreement.setChargeDays(chargeDays);
        rentalAgreement.setPreDiscountCharge(preDiscountCharge);
        rentalAgreement.setDiscountPercent(discount);
        rentalAgreement.setDiscountAmount(discountAmount);
        rentalAgreement.setFinalCharge(finalCharge);

        rentalAgreement.print();

        return rentalAgreement;
    }

    private int calculateChargeDays(Tool tool, LocalDate rentalDate, LocalDate dueDate) {
        int chargeDays = 0;
        boolean hasWeekdayCharge = tool.getType().isWeekdayCharge();
        boolean hasWeekendCharge = tool.getType().isWeekendCharge();
        boolean hasHolidayCharge = tool.getType().isHolidayCharge();
        LocalDate startDate = rentalDate.plusDays(1);
        LocalDate endDate = dueDate.plusDays(1);
        while (startDate.isBefore(endDate)) {
            if (isHoliday(startDate) && !hasHolidayCharge) {
                startDate = startDate.plusDays(1);
                continue;
            }
            if (!isWeekend(startDate) && hasWeekdayCharge) {
                chargeDays++;
            } else if (isWeekend(startDate) && hasWeekendCharge) {
                chargeDays++;
            }
            startDate = startDate.plusDays(1);
        }
        return chargeDays;
    }

    private boolean isWeekend(LocalDate ld) {
        DayOfWeek day = ld.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(LocalDate ld) {
        return holidays.contains(ld);
    }
}
