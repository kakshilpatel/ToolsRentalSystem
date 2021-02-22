package com.rentalsystem;

public class Tool {
	
	private String toolCode;
	private String brandName;
	ToolType toolType;
	
	public Tool(String toolCode) {
		super();
		this.toolCode = toolCode;
		this.setBrandNameAndToolType();
	}
	
	public String getToolCode() {
		return toolCode;
	}
	
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public ToolType getToolType() {
		return toolType;
	}
	
	public void setToolType(ToolType toolType) {
		this.toolType = toolType;
	}

	/**
	 * API is used to set brand name and tool type values based on given tool code
	 */
	private void setBrandNameAndToolType() {
		String toolCode = this.getToolCode();
		switch(toolCode) {
			case "LADW":
				this.setBrandName("Werner");
				this.setToolType(ToolType.LADDER);
				break;
			case "CHNS":
				this.setBrandName("Stihl");
				this.setToolType(ToolType.CHAINSAW);
				break;
			case "JAKR":
				this.setBrandName("Ridgid");
				this.setToolType(ToolType.JACKHAMMER);
				break;
			case "JAKD":
				this.setBrandName("DeWalt");
				this.setToolType(ToolType.JACKHAMMER);
				break;
		}
	}
}
