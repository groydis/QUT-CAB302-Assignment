package Stock;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the Store class and ensures that all functions associated with
 * the Store class work as intended.
 * 
 * @author Greyden Scott
 *
 */

public class StoreTest {
	
	Store testStore;
	String storeName = "SuperMart";
	double capital = 100000.00;
	
	
	@Before 
	public void Initialize() {
		testStore = null;
	}
	
	@Test
	public void testConstruction() {
		testStore = new Store();
	}
	
	@Test
	public void testName() {
		testStore = new Store();
		String testName = storeName;
		String actualName = testStore.getStoreName();
		assertEquals(testName, actualName);
	}
	
	@Test
	public void testSetCapital() {
		testStore = new Store();
		double testCapital = 80000.00;
		testStore.setCapital(80000.00);
		double actualCapital = testStore.getCapital();
		assertEquals(testCapital, actualCapital, 0.0);
	}

	@Test
	public void testGetCapital() {

		testStore = new Store();
		double testCapital = 100000.00;
		double actualCapital = testStore.getCapital();
		assertEquals(testCapital, actualCapital, 0.0);
	}
	
	@Test 
	public void testCapitalToString() {
		testStore = new Store();
		String testString = "100,000.00";
		String actualString = testStore.capitalToString();
		assertEquals(testString, actualString);
	}

}
