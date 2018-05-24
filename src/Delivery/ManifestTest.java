package Delivery;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import GUI.CSVFormatException;
import GUI.StockException;
import Stock.Item;
import Stock.Stock;

/**
 * Tests the Manifest Constructions by manually creating an output for it to test against.
 * 
 * @author Alex Holm
 *
 */

class ManifestTest {
	
	String icecream[] = {"ice cream", "2", "3", "225", "400", "-10"};
	String cheese[] = {"cheese", "2", "3", "225", "300", "-10"};
	String rice[] = {"rice", "2", "3", "225", "100", "-10"};
	
	String biscuits[] = {"biscuits", "2", "3", "100", "300"};
	
	
	@Test
	void testConstructManifest() throws CSVFormatException, StockException {
		
		
		List<String> testResults = new ArrayList<>();
		Stock testStoreInvetory = new Stock();
		Stock itemsToOrder = new Stock();
		
		testResults.add(">Refrigerated");
		testResults.add("ice cream,400");
		testResults.add("cheese,300");
		testResults.add("rice,100");
		testResults.add(">Ordinary");
		testResults.add("biscuits,300");
		
		Item itemIceCream = new Item(icecream);
		Item itemCheese = new Item(cheese);
		Item itemRice= new Item(rice);
		Item itemBiscuits = new Item(biscuits);
		
		
		testStoreInvetory.addItem(itemIceCream);
		testStoreInvetory.addItem(itemCheese);
		testStoreInvetory.addItem(itemRice);
		testStoreInvetory.addItem(itemBiscuits);
		
		for (Item item: testStoreInvetory.getItems()) {
			if (item.reorder()) {
				for (int i = 0; i < item.getReorderAmount(); i++) {
					itemsToOrder.addItem(item);
				}
			}
		}
		
	
		Manifest manifest = new Manifest(itemsToOrder);
		
		List<String> actualResults = new ArrayList<>();
		
		for (Truck truck : manifest.getFleet()) {
			List<String> cargos = truck.getCargo();
			for (String output : cargos) {
				actualResults.add(output);
			}
		}
		
		assertEquals(testResults, actualResults);
	}


}
