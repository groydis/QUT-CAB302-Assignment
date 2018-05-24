package Stock;

import java.text.DecimalFormat;

/** 
 * This class is used for creating a store that will be used by the GUI
 * The Store class holds the store name, total capital and has functions for accessing the capital and updating it.
 * 
 * @author Alex Holm
 *
 */

public class Store {
	
	private String storeName;
	private double capital;
	
	/**
	 * Constructs a store object.
	 * 
	 * @param storeName The name of the store.
	 * @param capital The total starting capital allocated to the store.
	 */
	public Store() {
		this.storeName = "SuperMart";
		this.capital = 100000;
	}
	
	/**
	 * Returns the store name.
	 * 
	 * @return String the store name as a String.
	 */
	public String getStoreName() {
		return storeName;
	}
	
	/**
	 * Sets the stores capital to the passed in amount.
	 * 
	 * @param capital the new store capital.
	 */
	public void setCapital(double capital) {
		this.capital = capital;
	}
	
	/**
	 * The stores current capital.
	 * 
	 * @return double The stores current capital.
	 */
	public double getCapital() {
		return this.capital;
	}
	
	/**
	 * The store capital as a formatted string eg $100,000.00 or $40,000.00.
	 * 
	 * @return String the store capital as a String.
	 */
	public String capitalToString() {
		DecimalFormat decimalFormat = new DecimalFormat("#00,000.00");
		String capital = decimalFormat.format(this.capital);
		return capital;
	}
	

	
	
	
	

}
