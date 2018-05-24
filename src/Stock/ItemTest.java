package Stock;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import GUI.StockException;


/**
 * This class tests the Item class and ensures that all functions associated with
 * the Item class work as intended.
 * 
 * @author Greyden Scott
 *
 */

public class ItemTest {
	
	Item testItem;
	
	String itemRice[] = {"rice", "2", "3", "225", "300"};
	String itemBoogers[] = {"boogers", "2", "3", "225", "300", "-10"};
	
	@Before
	public void setupItem() {
		testItem = null;
	}
	
	@Test
	public void testConstruction() throws StockException {
		testItem = new Item(itemRice);
	}

	
	@Test
	public void testItemName() throws StockException {
		testItem = new Item(itemRice);
		assertEquals("rice", testItem.getName());
	}
	
	@Test
	public void testManufacturingCost() throws StockException {
		testItem = new Item(itemRice);
		assertEquals(2, testItem.getManufacturingCost());
	}
	
	@Test
	public void testSellPrice() throws StockException {
		testItem = new Item(itemRice);
		assertEquals(3, testItem.getSellPrice());
	}
	
	@Test
	public void testReOrderPoint() throws StockException {
		testItem = new Item(itemRice);
		assertEquals(225, testItem.getReorderpoint());
	}
	
	@Test
	public void testReOrderAmount() throws StockException {
		testItem = new Item(itemRice);

		assertEquals(300, testItem.getReorderAmount());
	}
	
	@Test
	public void testStorageTempNormalItem() throws StockException {
		testItem = new Item(itemRice);

		int actualStorageTemp = testItem.getStorageTemp();
		assertEquals(24, actualStorageTemp);
	}
	
	@Test
	public void testStorageTempColdItem() throws StockException {
		testItem = new Item(itemBoogers);
		assertEquals(-10, testItem.getStorageTemp());
	}
	
	@Test
	public void testQuantity() throws StockException {
		testItem = new Item(itemRice);
		
		testItem.setQuantity(50);

		assertEquals(50, testItem.getQuantity());
		
	}
	
	public void testReorderFalse() throws StockException {
		testItem = new Item(itemRice);
		testItem.setQuantity(500);
		assertEquals(false, testItem.reorder());
	}
	
	public void testReorderTrue() throws StockException {
		testItem = new Item(itemRice);
		assertEquals(true, testItem.reorder());
	}

}
