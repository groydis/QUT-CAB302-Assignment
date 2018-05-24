package Stock;

import GUI.CSVFormatException;
import GUI.StockException;

/**
*
* @author Alex Holm
* 
*/

public class Item {

	String itemName;
	int manufacturingCost; 
	int sellPrice;
	int reorderPoint, reorderAmount;
	int storageTemp;
	private int quantity = 0;
	
	/**
	 * Constructs and item based on the array of parameters passed in from the 
	 * item properties document.
	 * 
	 * @param  data Array of data parsed by the file reader
	 */
	public Item(String[] attirbutes) throws StockException {
		try {
			this.itemName = attirbutes[0];
			this.manufacturingCost = Integer.parseInt(attirbutes[1]);
			this.sellPrice = Integer.parseInt(attirbutes[2]);
			this.reorderPoint = Integer.parseInt(attirbutes[3]);
			this.reorderAmount = Integer.parseInt(attirbutes[4]);
			if (attirbutes.length == 6) {
				storageTemp = Integer.parseInt(attirbutes[5]);
			} else {
				storageTemp = 24;
			}
		} catch (NumberFormatException e) {
			throw new StockException("Error Creating Item : Invalid data passed to constructor.");
		}
	}
	

	/**
	 * This method is used for returning the items name.
	 *
	 * @return Returns Item name
	 */
	public String getName() {
		return this.itemName;
	}
	
	/**
	 * This method is used for returning the items manufacturing cost.
	 *
	 * @return Returns Item manufacturing cost
	 */
	public int getManufacturingCost() {
		return this.manufacturingCost;
	}
	
	/**
	 * This method is used for returning the items sell price.
	 *
	 * @return Returns Item sell price
	 */
	public int getSellPrice() {
		return this.sellPrice;
	}
	
	/**
	 * This method is used for returning the items reorder point.
	 *
	 * @return Returns Item reorder point
	 */
	public int getReorderpoint() {
		return this.reorderPoint;
	}
	
	/**
	 * This method is used for returning the items reorder amount.
	 *
	 * @return Returns Item reorder amount
	 */
	public int getReorderAmount() {
		return this.reorderAmount;
	}
	
	/**
	 * This method is used for returning the items storage temperature.
	 *
	 * @return Returns Item storage temperature
	 */
	public int getStorageTemp() {
		return this.storageTemp;
	}

	/**
	 * This method is used for returning the items quantity.
	 *
	 * @return Returns the quantity of the item which is stored in the inventory.
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * This method is used for setting the items quantity.
	 *
	 * @param quantity to be associated with the item.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * This method is used for returning the whether an item needs to be reordered,
	 * which is based off wether the items quantity is above or below the reorder point for 
	 * the item.
	 *
	 * @return Returns boolean value based on whether item needs to be reordered
	 */
	public Boolean reorder() {
		if (getQuantity() < this.reorderPoint) {
			return true;
		} 
		return false;
	}
	
	/**
	 * This is just testing
	 */
	
	public String toString() {
		String item = this.itemName + " " + 
				this.manufacturingCost + " " +
				this.sellPrice + " " +
				this.reorderPoint + " " +
				this.reorderAmount + " " +
				this.storageTemp + " " +
				this.getQuantity() + " "; 
		
		return item;
	}
	
}
