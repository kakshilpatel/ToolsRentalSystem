package com.rentalsystem;

public class Checkout {
	
	private String toolCode;
	private int rentalDayCount;
	private Double discountPercentage;
	private String checkoutDate;
	RentalAgreement rentalAgreement;
	
	public Checkout(String toolCode, int rentalDayCount, Double discountPercentage, String checkoutDate) {
		this.toolCode = toolCode;
		if(rentalDayCount < 1) {
			throw new IllegalArgumentException("Please provide rental day 1 or higher value.");
		}
		this.rentalDayCount = rentalDayCount;
		if(Double.compare(discountPercentage, 0) < 0 || Double.compare(discountPercentage, 100) > 0 ) {
			throw new IllegalArgumentException("Please provide discount values between 0-100.");
		}
		this.discountPercentage = discountPercentage;
		this.checkoutDate = checkoutDate;
		Tool tool = new Tool(this.getToolCode());
		rentalAgreement = new RentalAgreement(this, tool);
		rentalAgreement.print();
	}
	
	public String getToolCode() {
		return toolCode;
	}
	
	public int getRentalDayCount() {
		return rentalDayCount;
	}
	
	public Double getDiscountPercentage() {
		return discountPercentage;
	}
	
	public String getCheckoutDate() {
		return checkoutDate;
	}
}
