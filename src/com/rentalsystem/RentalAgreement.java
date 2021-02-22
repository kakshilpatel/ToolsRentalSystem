package com.rentalsystem;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class RentalAgreement {
	
	private static Set<LocalDate> HOLIDAYS = new HashSet<>();
	private static final String DATE_FORMAT = "MM/dd/yy";
	private Checkout checkout;
	private Tool tool;
	private LocalDate dueDate;
	private int chargeDays;
	private BigDecimal preDiscountCharge;
	private BigDecimal discountAmount;
	private BigDecimal finalCharge;
	
	public RentalAgreement(Checkout checkout, Tool tool) {
		this.checkout = checkout;
		this.tool = tool;
		populateHolidays(checkout.getCheckoutDate());
		this.dueDate = calculateDueDate(checkout.getCheckoutDate(), checkout.getRentalDayCount());
		this.chargeDays = calculateChargeDays(checkout.getCheckoutDate(), this.dueDate);
		this.preDiscountCharge = calculatePreDiscountCharge(tool.toolType.dailyCharge, this.chargeDays);
		this.discountAmount = calculateDiscountAmount(this.preDiscountCharge, checkout.getDiscountPercentage());
		this.finalCharge = calculateFinalCharge(this.preDiscountCharge, this.discountAmount);
	}
	
	
	/**
	 * API is used to print the rental agreement details
	 */
	public void print() {
		StringBuilder outputStr = new StringBuilder();
		
		outputStr.append("-------------- Output ---------------");
		outputStr.append("\nTool code:" + tool.getToolCode());
		outputStr.append("\nTool type:"+ tool.getToolType().toolTypeName);
		outputStr.append("\nTool brand:"+ tool.getBrandName());
		outputStr.append("\nRental Days:"+ checkout.getRentalDayCount());
		outputStr.append("\nCheckout Date:"+ formateDateString(checkout.getCheckoutDate()));
		outputStr.append("\nDue Date:"+ formatOutputDate(dueDate));
		outputStr.append("\nDaily Rental Charge:"+ tool.getToolType().dailyCharge);
		outputStr.append("\nCharge Days:"+ chargeDays);
		outputStr.append("\nPre-discount Charge:"+ formatCurrencyVal(preDiscountCharge));
		outputStr.append("\nDiscount Percentage:"+ formatPercentageVal(checkout.getDiscountPercentage()));
		outputStr.append("\nDiscount amount:"+ formatCurrencyVal(discountAmount));
		outputStr.append("\nFinal Charge:"+ formatCurrencyVal(finalCharge));
		System.out.println(outputStr);
		System.out.println();
	}
	
	/**
	 * API is used to calculate discount amount according to given discount percentage
	 */
	public BigDecimal calculateDiscountAmount(BigDecimal preDiscountCharge, double discountPercentage) {
		final BigDecimal ONE_HUNDRED = new BigDecimal(100);
		return preDiscountCharge.multiply(BigDecimal.valueOf(discountPercentage)).divide(ONE_HUNDRED);
	}
	
	/**
	 * API is used to calculate charges based on number of rental days and daily charge of tool
	 */
	public BigDecimal calculatePreDiscountCharge(BigDecimal dailyCharge, int chargeDaysCount) {
		return dailyCharge.multiply(BigDecimal.valueOf(chargeDaysCount));
	}
	
	/**
	 * API is used to calculate final charge based on total charge minus discount amount
	 */
	public BigDecimal calculateFinalCharge(BigDecimal preDiscountCharge, BigDecimal discountAmount) {
		return preDiscountCharge.subtract(discountAmount);
	}

	/**
	 * API is used to calculate number of charge days based on tool type and number of rental days
	 */
	public int calculateChargeDays(String checkoutDateStr, LocalDate dueDateVal) {
		LocalDate checkoutDate = getCheckoutDate(checkoutDateStr);
		int chargeDays = 0;
		LocalDate rentDate = checkoutDate.plusDays(1);
		LocalDate endDate = dueDateVal.plusDays(1);
		
		while(rentDate.isBefore(endDate)) {
			DayOfWeek weekDay = rentDate.getDayOfWeek();
			if(!tool.toolType.holidayCharge  && HOLIDAYS.contains(rentDate)) {
				rentDate = rentDate.plusDays(1);
				continue;
			}
			if(!tool.toolType.weekendCharge  && (weekDay == DayOfWeek.SATURDAY || weekDay == DayOfWeek.SUNDAY)) {
				rentDate = rentDate.plusDays(1);
				continue;
			}
			chargeDays++;
			rentDate = rentDate.plusDays(1);
		}
		return chargeDays;
	}
	
	/**
	 * API is used to calculate due date of rental tools
	 */
	public LocalDate calculateDueDate(String checkoutDate, int rentalDayCount ) {
		return getCheckoutDate(checkoutDate).plusDays(rentalDayCount);
	}
	
	/**
	 * API is used to get checkout date in date object format
	 * @param checkoutDate checkout date in string format
	 * @return checkout date in date object
	 */
	private LocalDate getCheckoutDate(String checkoutDate) {
		checkoutDate = formateDateString(checkoutDate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return LocalDate.parse(checkoutDate, formatter);
	}
	
	/**
	 * API is used to format date to add leading zero in case of month/day contains single digit value
	 * @param checkoutDate checkout date
	 * @return formatted checkout date
	 */
	private String formateDateString(String checkoutDate) {
		String dateStr[] = checkoutDate.split("/");
		int monthVal = Integer.parseInt(dateStr[0]);
		int dateVal = Integer.parseInt(dateStr[1]);
		return (monthVal < 10 ? "0" + monthVal : ""+monthVal) + "/" +
				(dateVal < 10 ? "0" + dateVal : dateVal )+ "/" +dateStr[2];
	}
	
	/**
	 * API is used to populate holidays date for given year of checkout date
	 * @param checkoutDate checkout date
	 */
	public Set<LocalDate> populateHolidays(String checkoutDateString) {
		LocalDate checkoutDate = getCheckoutDate(checkoutDateString);
		LocalDate laborDayMonth = LocalDate.of(checkoutDate.getYear(), Month.SEPTEMBER, 1);
		LocalDate laborDayDate = laborDayMonth.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
		
		LocalDate independencyDayDate = LocalDate.of(checkoutDate.getYear(), Month.JULY, 4);
		DayOfWeek independencyDay = independencyDayDate.getDayOfWeek();
		if(independencyDay == DayOfWeek.SATURDAY) {
			independencyDayDate = independencyDayDate.minusDays(1);
		}
		if(independencyDay == DayOfWeek.SUNDAY) {
			independencyDayDate = independencyDayDate.plusDays(1);
		}
		HOLIDAYS.add(independencyDayDate);
		HOLIDAYS.add(laborDayDate);
		return HOLIDAYS;
	}
	
	/**
	 * API is used to format date in MM/dd/yy format to display in output
	 * @param date Date object
	 * @return formatted string date
	 */
	public String formatOutputDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return date.format(formatter);
	}
	
	/**
	 * API is used to get formatted currency value in $9999.99 format
	 * @param amount amount value
	 * @return formatted amount value
	 */
	public String formatCurrencyVal(BigDecimal amount) {
	    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
		return numberFormat.format(amount.doubleValue());
	}
	
	/**
	 * API is used to get formatted percentage values in 99% format
	 * @param amount percentage value
	 * @return formatted percentage value
	 */
	
	public String formatPercentageVal(double amount) {
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(0);
		return defaultFormat.format(amount/100);
	}

	public Checkout getCheckout() {
		return checkout;
	}

	public Tool getTool() {
		return tool;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public int getChargeDays() {
		return chargeDays;
	}

	public BigDecimal getPreDiscountCharge() {
		return preDiscountCharge;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public BigDecimal getFinalCharge() {
		return finalCharge;
	}
}
