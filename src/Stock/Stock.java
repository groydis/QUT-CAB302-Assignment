package Stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;import GUI.CSVFormatException;
import GUI.StockException;

/** 
 * This class is used to represent A collection of items. 
 * Which Can be used for representing store inventory, stock orders, sales logs, and truck cargo.
 * 
 * @author Alex Holm
 *
 */

public class Stock {
	
	private List<Item> inventory;
	
	/**
	 * Constructs an empty Stock and sets up instantiates the inventory which is a List of items.
	 */
	public Stock () {
		this.inventory = new ArrayList<Item>();
		
		
	}
	
	/**
	 * Sorts the items based on the Temperature from lowest (eg -20) to highest (10).
	 */
	public void sortByTemp() {
		if (this.inventory.size() > 0) {
			Collections.sort(this.inventory, new Comparator<Item>() {
				@Override
				public int compare(final Item object1, final Item object2) {
					return  Integer.valueOf(object1.getStorageTemp()).compareTo(object2.getStorageTemp());
				}
			});
		}
		
	}
	
	/**
	 * Adds an item.
	 * 
	 * @param item The item to be added.
	 */
	public void addItem(Item item) {
		inventory.add(item);
	}
	
	
	/**
	 * This function provides access to the List<Item> that the items are stored in in the event
	 * direct access to the List is required.
	 * 
	 * @return The list that the inventory is stored in.
	 */
	public List<Item> getItems() {
		return this.inventory;
		
	}
	
	/**
	 *  Returns the total number of items in the Stock
	 *  
	 * @return Int Total number of items in the Stock
	 */
	public int getTotal() {
		return inventory.size();
	}
	
	
	/**
	 * Calculates the number of a particular items in the inventory.
	 * 
	 * @param item The item in which to find the number of.
	 * 
	 * @return the number of items of found.
	 * @throws StockException 
	 */
	public int getTotalItem(Item item) throws StockException {
		int total = 0;
		if (inventory.contains(item)) {
			for (Item i : inventory) {
				if (i.getName() == item.getName()) {
					total++;
				}
			}
		} else {
			throw new StockException("Stock error: No item exists");
		}
		return total;
	}

	/**
	 * Update the quantity of all items based on the passed in data
	 * 
	 * @param data An array of data Name, QTY
	 * @throws CSVFormatException 
	 */
	public void updateSales(String[] data) throws CSVFormatException {
		try {
			int quantity = Integer.parseInt(data[1]);
			
			for (Item item : getItems()) {
				if (item.getName().equals(data[0])) {
					int qty = item.getQuantity() - quantity;
					item.setQuantity(qty);
				}
			}
		} catch (NumberFormatException e) {
			throw new CSVFormatException("Error Updating Sales");
		}
		
		
	}


}
