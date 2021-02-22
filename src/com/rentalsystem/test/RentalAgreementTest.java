package com.rentalsystem.test;


import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.gen5.api.DisplayName;
import org.junit.jupiter.api.Assertions;

import com.rentalsystem.Checkout;
import com.rentalsystem.RentalAgreement;
import com.rentalsystem.Tool;
import com.rentalsystem.ToolRentalSystem;
 

public class RentalAgreementTest {

	private RentalAgreement rentalAgreement;
	
	@Before
    public void initEach(){
		Checkout checkout = new Checkout("LADW", 5, (double) 25, "07/02/15");
		Tool tool = new Tool(checkout.getToolCode());
		rentalAgreement = new RentalAgreement(checkout, tool);
    }
	
	@Test                                               
    @DisplayName("Tool Rental System should work")   
    public void testCreateRentalAgreement() {
    	Assertions.assertEquals("$3.58",new ToolRentalSystem().createRentalAgreement("LADW", 10, 3, "7/2/20"));
    	Assertions.assertEquals("$3.35",new ToolRentalSystem().createRentalAgreement("CHNS", 25, 5, "7/2/15"));
    	Assertions.assertEquals("$8.97",new ToolRentalSystem().createRentalAgreement("JAKD", 0, 6, "9/3/15"));
    	Assertions.assertEquals("$14.95",new ToolRentalSystem().createRentalAgreement("JAKR", 0, 9, "7/2/15"));
    	Assertions.assertEquals("$1.50",new ToolRentalSystem().createRentalAgreement("JAKR", 50, 4, "7/2/20"));
    	Assertions.assertThrows(IllegalArgumentException.class,() -> new ToolRentalSystem().createRentalAgreement("JAKR", 101, 4, "7/2/20"));
    }
	
	@Test                                                                                                                               
     public void testPopulateHolidays() { 
		Set<LocalDate> HOLIDAYS_LIST = rentalAgreement.populateHolidays("07/02/15");
		LocalDate laborDate = LocalDate.of(2015, Month.JULY, 3);
		LocalDate indendencyDate = LocalDate.of(2015, Month.SEPTEMBER, 7);
		assertTrue(HOLIDAYS_LIST.contains(laborDate));
		assertTrue(HOLIDAYS_LIST.contains(indendencyDate));
	}
	
	@Test                                                                                                                               
    public void testCalculateDueDate() { 
		LocalDate dueDate = rentalAgreement.calculateDueDate("07/02/15",5);
		Assertions.assertEquals(dueDate, LocalDate.of(2015, Month.JULY, 7));
	}
	
	@Test                                                                                                                               
    public void testCalculateChargeDays() { 
		LocalDate dueDate = LocalDate.of(2015, Month.JULY, 7);                                         
		int chargeDays = rentalAgreement.calculateChargeDays("07/02/15",dueDate);
		Assertions.assertEquals(chargeDays, 4);
	}
	
	@Test                                                                                                                               
    public void testCalculatePreDiscountCharge() { 
		BigDecimal preDiscountCharge = rentalAgreement.calculatePreDiscountCharge(BigDecimal.valueOf(1.99),4);
		Assertions.assertEquals(preDiscountCharge, BigDecimal.valueOf(7.96));
	}
	
	@Test                                                                                                                               
    public void testCalculateDiscountAmount() { 
		BigDecimal discountAmount = rentalAgreement.calculateDiscountAmount(BigDecimal.valueOf(7.96) , 10);
		Assertions.assertEquals(discountAmount, BigDecimal.valueOf(0.796));
	} 
	
	@Test                                                                                                                               
    public void testCalculateFinalCharge() { 
		BigDecimal finalCharge = rentalAgreement.calculateFinalCharge(BigDecimal.valueOf(7.96) , BigDecimal.valueOf(0.796));
		Assertions.assertEquals(finalCharge, BigDecimal.valueOf(7.164));
	}
}
