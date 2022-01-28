import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTests {

    private Register register;
    private Tool CHNS;
    private Tool LADW;
    private Tool JAKD;
    private Tool JAKR;

    @BeforeEach
    void setup(){
        Set<LocalDate> holidays = new HashSet<>();
        holidays.add(LocalDate.of(2015, 7, 4));
        holidays.add(LocalDate.of(2020, 7, 4));
        holidays.add(LocalDate.of(2015, 9, 1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)));

        Map<String, Tool> tools = new HashMap<>();
        ToolType ladder = new ToolType("Jackhammer", new BigDecimal("1.99"), true, true, false);
        ToolType chainsaw = new ToolType("Jackhammer", new BigDecimal("1.49"), true, false, true);
        ToolType jackhammer = new ToolType("Jackhammer", new BigDecimal("2.99"), true, false, false);
        CHNS = new Tool("CHNS", chainsaw, "Stihl");
        LADW = new Tool("LADW", ladder, "Werner");
        JAKD = new Tool("JAKD", jackhammer, "DeWalt");
        JAKR = new Tool("JAKR", jackhammer, "Ridgid");
        tools.put("CHNS", CHNS);
        tools.put("LADW", LADW);
        tools.put("JAKD", JAKD);
        tools.put("JAKR", JAKR);

        register = new Register(tools, holidays);
    }

    @Test
    public void test1() {
        assertThrows(IllegalArgumentException.class, () ->
                register.checkout("JAKR", 5, 101, LocalDate.parse("2015-09-03")));
    }

    @Test
    public void test2() {
        Tool tool = LADW;
        String code = "LADW";
        int rentalDays = 3;
        int discount = 10;
        LocalDate date = LocalDate.parse("2020-07-02");
        RentalAgreement ra = register.checkout(code, rentalDays, discount, date);
        assertEquals(tool.getCode(), ra.getCode());
        assertEquals(tool.getType().getName(), ra.getType());
        assertEquals(tool.getBrand(), ra.getBrand());
        assertEquals(rentalDays, ra.getRentalDays());
        assertEquals(date, ra.getCheckoutDate());
        assertEquals(date.plusDays(rentalDays), ra.getDueDate());
        assertEquals(tool.getType().getDailyCharge(), ra.getDailyCharge());
        assertEquals(2, ra.getChargeDays());
        assertEquals(new BigDecimal("3.98"), ra.getPreDiscountCharge());
        assertEquals(discount, ra.getDiscountPercent());
        assertEquals(new BigDecimal("0.40"), ra.getDiscountAmount());
        assertEquals(new BigDecimal("3.58"), ra.getFinalCharge());
    }

    @Test
    public void test3() {
        Tool tool = CHNS;
        String code = "CHNS";
        int rentalDays = 5;
        int discount = 25;
        LocalDate date = LocalDate.parse("2015-07-02");
        RentalAgreement ra = register.checkout(code, rentalDays, discount, date);
        assertEquals(tool.getCode(), ra.getCode());
        assertEquals(tool.getType().getName(), ra.getType());
        assertEquals(tool.getBrand(), ra.getBrand());
        assertEquals(rentalDays, ra.getRentalDays());
        assertEquals(date, ra.getCheckoutDate());
        assertEquals(date.plusDays(rentalDays), ra.getDueDate());
        assertEquals(tool.getType().getDailyCharge(), ra.getDailyCharge());
        assertEquals(3, ra.getChargeDays());
        assertEquals(new BigDecimal("4.47"), ra.getPreDiscountCharge());
        assertEquals(discount, ra.getDiscountPercent());
        assertEquals(new BigDecimal("1.12"), ra.getDiscountAmount());
        assertEquals(new BigDecimal("3.35"), ra.getFinalCharge());
    }

    @Test
    public void test4() {
        Tool tool = JAKD;
        String code = "JAKD";
        int rentalDays = 6;
        int discount = 0;
        LocalDate date = LocalDate.parse("2015-09-03");
        RentalAgreement ra = register.checkout(code, rentalDays, discount, date);
        assertEquals(tool.getCode(), ra.getCode());
        assertEquals(tool.getType().getName(), ra.getType());
        assertEquals(tool.getBrand(), ra.getBrand());
        assertEquals(rentalDays, ra.getRentalDays());
        assertEquals(date, ra.getCheckoutDate());
        assertEquals(date.plusDays(rentalDays), ra.getDueDate());
        assertEquals(tool.getType().getDailyCharge(), ra.getDailyCharge());
        assertEquals(3, ra.getChargeDays());
        assertEquals(new BigDecimal("8.97"), ra.getPreDiscountCharge());
        assertEquals(discount, ra.getDiscountPercent());
        assertEquals(new BigDecimal("0.00"), ra.getDiscountAmount());
        assertEquals(new BigDecimal("8.97"), ra.getFinalCharge());
    }

    @Test
    public void test5() {
        Tool tool = JAKR;
        String code = "JAKR";
        int rentalDays = 9;
        int discount = 0;
        LocalDate date = LocalDate.parse("2015-07-02");
        RentalAgreement ra = register.checkout(code, rentalDays, discount, date);
        assertEquals(tool.getCode(), ra.getCode());
        assertEquals(tool.getType().getName(), ra.getType());
        assertEquals(tool.getBrand(), ra.getBrand());
        assertEquals(rentalDays, ra.getRentalDays());
        assertEquals(date, ra.getCheckoutDate());
        assertEquals(date.plusDays(rentalDays), ra.getDueDate());
        assertEquals(tool.getType().getDailyCharge(), ra.getDailyCharge());
        assertEquals(6, ra.getChargeDays());
        assertEquals(new BigDecimal("17.94"), ra.getPreDiscountCharge());
        assertEquals(discount, ra.getDiscountPercent());
        assertEquals(new BigDecimal("0.00"), ra.getDiscountAmount());
        assertEquals(new BigDecimal("17.94"), ra.getFinalCharge());
    }

    @Test
    public void test6() {
        Tool tool = JAKR;
        String code = "JAKR";
        int rentalDays = 4;
        int discount = 50;
        LocalDate date = LocalDate.parse("2020-07-02");
        RentalAgreement ra = register.checkout(code, rentalDays, discount, date);
        assertEquals(tool.getCode(), ra.getCode());
        assertEquals(tool.getType().getName(), ra.getType());
        assertEquals(tool.getBrand(), ra.getBrand());
        assertEquals(rentalDays, ra.getRentalDays());
        assertEquals(date, ra.getCheckoutDate());
        assertEquals(date.plusDays(rentalDays), ra.getDueDate());
        assertEquals(tool.getType().getDailyCharge(), ra.getDailyCharge());
        assertEquals(2, ra.getChargeDays());
        assertEquals(new BigDecimal("5.98"), ra.getPreDiscountCharge());
        assertEquals(discount, ra.getDiscountPercent());
        assertEquals(new BigDecimal("2.99"), ra.getDiscountAmount());
        assertEquals(new BigDecimal("2.99"), ra.getFinalCharge());
    }
}
