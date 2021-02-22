package com.rentalsystem;

import java.math.BigDecimal;

/**
 * Used enum to declare different tool type specification as Tool type have set of properties
 * which is defined according to tool type so using enum it is defined as a constant and used for
 * calculation based on given tool type and we can add new tool type by adding just the new type here.
 * No need to change any rental calculation logic change for new tool type.
 */
		
public enum ToolType {
	
	LADDER("Ladder", BigDecimal.valueOf(1.99), true, true, false),
	CHAINSAW("Chainsaw", BigDecimal.valueOf(1.49), true, false, true),
	JACKHAMMER("Jackhammer", BigDecimal.valueOf(2.99), true, false, false);
	
	String toolTypeName;
	BigDecimal dailyCharge;
	boolean weekdayCharge;
	boolean weekendCharge;
	boolean holidayCharge;
	
	private ToolType(String toolTypeName, BigDecimal dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
		this.toolTypeName = toolTypeName;
		this.dailyCharge = dailyCharge;
		this.weekdayCharge = weekdayCharge;
		this.weekendCharge = weekendCharge;
		this.holidayCharge = holidayCharge;
	}
}
