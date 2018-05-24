package Delivery;

import java.util.List;

import GUI.StockException;
import Stock.Item;
import Stock.Stock;

/**
 * An abstract class for the two truck types.
 * 
 * @author Greyden Scott
 *
 */

public abstract class Truck {
	
	private Stock cargo;
	private List<Item> inventory;
	
	/**
	 * Constructs a truck, sets the passed inventory as the trucks cargo.
	 * 
	 * @param inventory cargo inside the truck..
	 */
	public Truck(Stock inventory) {
		this.cargo = inventory;
		this.inventory = inventory.getItems();
	}
	
	/**
	 * Returns the cargo as a List<Item>.
	 * 
	 * @return cargo as List<Item>.
	 */
	public List<Item> getInventory() {
		return this.inventory;
	}
	
	/**
	 * Returns the trucks cargo/ store items.
	 * 
	 * @return Trucks cargo.
	 */
	public Stock cargo() {
		return this.cargo;
	}
	
	/**
	 * Returns the maximum capacity of the truck.
	 * 
	 * @return Maximum capacity of items stored on truck
	 */
	public abstract int getCapacity();
		
	/**
	 * Cost to operate the truck.
	 * 
	 * @return cost as Double.
	 */
	public abstract double getCost();
	
	/**
	 * The cargo on board the truck in a List<String>.
	 * 
	 * @return List<String> of cargo
	 */
	public abstract List<String> getCargo() throws StockException;
	

}
