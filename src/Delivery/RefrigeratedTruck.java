package Delivery;

import java.util.ArrayList;
import java.util.List;

import GUI.StockException;
import Stock.Item;
import Stock.Stock;


/**
 * Creates an truck that is temperature controlled
 * Extends the abstract Truck class
 * 
 * @author Greyden Scott
 *
 */

public class RefrigeratedTruck extends Truck {
	
	private int temperature;
	
	
	/**
	 * Constructs an temperature controlled truck
	 * 
	 * @param cargo Stock to be loaded onto the truck
	 */
	public RefrigeratedTruck (Stock cargo) {
		super(cargo);
	}
	
	
	/**
	 * Returns the trucks set temperature.
	 * 
	 * @return temperature
	 */
	public int getTemperature() {	
		return temperature;
	}
	
	/**
	 * Sets the trucks temperature based on the inventory.
	 * Checks to see which item requires the lowest colling and sets the temperature as that value.
	 * 
	 */
	public void setTemperature() {
		int temp = 10;
		for (Item item : getInventory()) {
			if (item.getStorageTemp() < temp) {
				temp = item.getStorageTemp();
			}
		}
		temperature = temp;
	}

	/**
	 * Returns the trucks maximum item capacity.
	 * 
	 * @return Cargo capacity of 800 items.
	 */
	@Override
	public int getCapacity() {
		return 800;
	}

	/**
	 * Returns the operating cost of the truck.
	 * 
	 * @return Cost in dollars equal to 900 + 200 × 0.7T/5 where T is the truck’s temperature in °C.
	 */
	@Override
	public double getCost() {
		// TODO Auto-generated method stub
		setTemperature();
		return 900 + 200 * Math.pow(0.7, getTemperature()/5.0);
	}
	
	/**
	 * Temperature in °C that maintains a safe temperature for the truck’s cargo. This is equal to the temperature of the item in the cargo with the coldest safe temperature. The allowed temperature range is from -20°C inclusive to 10°C inclusive.
	 * 
	 * @return returns a List of constructed strings to be processed into a manifest file. The strings are the item names and quantity to be reordered.
	 * @throws StockException 
	 */
	@Override
	public List<String> getCargo() throws StockException {
		List<String> cargoOutput = new ArrayList<String>();
		cargoOutput.add(">Refrigerated");
		for (Item item : getInventory()) {
			String output = item.getName() + "," + cargo().getTotalItem(item);
			if (!cargoOutput.contains(output)) {
				cargoOutput.add(output);
			}
		}
		return cargoOutput;
	}

}
