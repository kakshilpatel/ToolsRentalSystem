package com.rentalsystem;

import java.util.Scanner;

public class ToolRentalSystem {

	public static void main(String[] args) {
		ToolRentalSystem toolRentalSystem = new ToolRentalSystem();
	    
//		Take input in following ways in console using Scanner class
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Please enter Tool Code:");
//	    String toolCode = sc.nextLine();
//	    System.out.println("Please enter Discount Percentage:");
//	    int dicountPercentage = sc.nextInt();
//	    System.out.println("Please enter Rental Days:");
//	    int rentalDays = sc.nextInt();
//	    System.out.println("Please enter checkout date:");
//	    String checkoutDate = sc.next();
//	    sc.close();
//	    toolRentalSystem.createRentalAgreement(toolCode, dicountPercentage, rentalDays, checkoutDate);
	    
	    
		toolRentalSystem.createRentalAgreement("LADW", 10, 3, "7/2/20"); // 3.58
		toolRentalSystem.createRentalAgreement("CHNS", 25, 5, "7/2/15"); // 3.35
		toolRentalSystem.createRentalAgreement("JAKD", 0, 6, "9/3/15");	 // 8.97
		toolRentalSystem.createRentalAgreement("JAKR", 0, 9, "7/2/15");  // 14.95
		toolRentalSystem.createRentalAgreement("JAKR", 50, 4, "7/2/20"); // 1.50

//		toolRentalSystem.createRentalAgreement("JAKR", 101, 5, "9/3/15"); // you will get an exception with user friendly message because discount percent is not between 0-100
//		toolRentalSystem.createRentalAgreement("JAKR", 5, 0, "9/3/15"); // you will get an exception with user friendly message because rental day count is not between 1 or greater
	}
	
	public String createRentalAgreement(String toolCode, int discountPercentage, int rentalDayCount, String checkoutDate) {
		Checkout checkout = new Checkout(toolCode, rentalDayCount, (double)discountPercentage, checkoutDate);
		return checkout.rentalAgreement.formatCurrencyVal(checkout.rentalAgreement.getFinalCharge());
	}
}
