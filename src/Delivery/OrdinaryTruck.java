package Delivery;

import java.util.ArrayList;

import java.util.List;

import GUI.StockException;
import Stock.Item;
import Stock.Stock;

/**
 * Creates an truck that is not temperature controlled
 * Extends the abstract Truck class
 * 
 * @author Greyden Scott
 *
 */

public class OrdinaryTruck extends Truck {
	
	/**
	 * Constructs an Non temperature controlled truck
	 * 
	 * @param cargo Stock to be loaded onto the truck
	 */
	public OrdinaryTruck(Stock cargo) {
		super(cargo);
	}

	/**
	 * Returns the trucks maximum item capacity.
	 * 
	 * @return Cargo capacity of 1000 items.
	 */
	@Override
	public int getCapacity() {
		return 1000;
	}

	/**
	 * Returns the operating cost of the truck.
	 * 
	 * @return Cost in dollars equal to 750 + 0.25q where q is the total quantity of
	 * items in the cargo.
	 */
	@Override
	public double getCost() {
		return 750 + (0.25 * getInventory().size());
	}

	/**
	 * Cargo. Temperature controlled items cannot be stored in an ordinary truck’s cargo, only dry goods.
	 * 
	 * @return returns a List of constructed strings to be processed into a manifest file. The strings are the item names and quantity to be reordered.
	 * @throws StockException 
	 */
	@Override
	public List<String> getCargo() throws StockException {
		List<String> cargoOutput = new ArrayList<String>();
		cargoOutput.add(">Ordinary");
		for (Item item : getInventory()) {
			String output = item.getName() + "," + cargo().getTotalItem(item);
			if (!cargoOutput.contains(output)) {
				cargoOutput.add(output);
			}
		}
		return cargoOutput;
	}


}
