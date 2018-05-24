package Delivery;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import GUI.CSVFormatException;
import GUI.StockException;
import Stock.Item;
import Stock.Stock;


/**
 * Tests the construction of an Refrigerated Truck and the functions associated with that truck type
 * 
 * @author Alex Holm
 *
 */

class RefrigeratedTruckTest {

	Stock cargo = new Stock();
	RefrigeratedTruck truck;
	String itemRice[] = {"rice", "2", "1", "225", "300"};
	String itemCake[] = {"cake", "4", "1", "300", "400", "5"};
	String itemBoogers[] = {"boogers", "5", "4", "400", "500", "-10"};
	
	
	List<String> testCargoOutput = new ArrayList<>();
	

	@Test
	void testGetCapacity() {
		truck = new RefrigeratedTruck(cargo);
		assertEquals(800, truck.getCapacity());
	}
	
	@Test
	void testGetTemperature() throws StockException {
		Item rice = new Item(itemRice);
		Item cake = new Item(itemCake);
		Item boogers = new Item(itemBoogers);
		
		cargo.addItem(rice);
		cargo.addItem(cake);
		cargo.addItem(boogers);
		
		truck = new RefrigeratedTruck(cargo);
		
		int temp = 10;
		for (Item item : cargo.getItems()) {
			if (item.getStorageTemp() < temp) {
				temp = item.getStorageTemp();
			}
		}
		truck.setTemperature();
		assertEquals(temp, truck.getTemperature());
	}
	
	
	@Test
	void testGetCost() throws StockException {
		Item rice = new Item(itemRice);
		Item cake = new Item(itemCake);
		Item boogers = new Item(itemBoogers);
		
		cargo.addItem(rice);
		cargo.addItem(cake);
		cargo.addItem(boogers);
		
		truck = new RefrigeratedTruck(cargo);
		
		double cost = 900 + 200 * Math.pow(0.7, -10/5.0);
		
		assertEquals(cost, truck.getCost());
	}

	
	@Test 
	void testGetCargo() throws CSVFormatException, StockException {
		
		Item rice = new Item(itemRice);
		Item cake = new Item(itemCake);
		Item boogers = new Item(itemBoogers);
		
		cargo.addItem(rice);
		cargo.addItem(cake);
		cargo.addItem(boogers);

		testCargoOutput.add(">Refrigerated");
		testCargoOutput.add("rice,1");
		testCargoOutput.add("cake,1");
		testCargoOutput.add("boogers,1");
		
		truck = new RefrigeratedTruck(cargo);
		
		assertEquals(testCargoOutput, truck.getCargo());
	}
}
