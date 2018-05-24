package Delivery;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import GUI.CSVFormatException;
import GUI.StockException;
import Stock.Item;
import Stock.Stock;


/**
 * Tests the construction of an Ordinary Truck and the functions associated with that truck type
 * 
 * @author Alex Holm
 *
 */

public class OrdinaryTruckTest {
	
	Stock cargo = new Stock();
	Truck truck;
	String itemRice[] = {"rice", "2", "1", "225", "300", "0"};
	String itemCake[] = {"cake", "4", "1", "300", "400", "0", "5"};
	String itemBoogers[] = {"boogers", "5", "4", "400", "500", "0", "-10"};
	
	
	List<String> testCargoOutput = new ArrayList<>();
	

	@Test
	public void testGetCapacity() throws StockException {
		truck = new OrdinaryTruck(cargo);
		assertEquals(1000, truck.getCapacity());
	}
	
	@Test
	public void testGetCost() throws StockException {
		Item rice = new Item(itemRice);
		Item cake = new Item(itemCake);
		Item boogers = new Item(itemBoogers);
		
		cargo.addItem(rice);
		cargo.addItem(cake);
		cargo.addItem(boogers);
		
		truck = new OrdinaryTruck(cargo);
		
		double cost = 750 + (0.25 * cargo.getItems().size());
		
		assertEquals(cost, truck.getCost(), 0.0);
	}

	
	@Test 
	public void testGetCargo() throws CSVFormatException, StockException {
		Item rice = new Item(itemRice);
		Item cake = new Item(itemCake);
		Item boogers = new Item(itemBoogers);
		
		cargo.addItem(rice);
		cargo.addItem(cake);
		cargo.addItem(boogers);

		testCargoOutput.add(">Ordinary");
		testCargoOutput.add("rice,1");
		testCargoOutput.add("cake,1");
		testCargoOutput.add("boogers,1");
		
		truck = new OrdinaryTruck(cargo);
		
		assertEquals(testCargoOutput, truck.getCargo());
	}
}
