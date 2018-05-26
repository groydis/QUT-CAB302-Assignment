package GUI;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Delivery.Manifest;
import Delivery.OrdinaryTruck;
import Delivery.RefrigeratedTruck;
import Delivery.Truck;
import Stock.Item;
import Stock.Stock;
import Stock.Store;

/**
 * File Manager imports, exports manifests. Loads sales logs and Item Properties.
 * 
 * @author Greyden Scott
 *
 */
public class FileManager {
		
	/**
	 * This method parses a file into a List of Items line by line
	 *
	 * @param  fileName  File location to read Item Properties from
	 * @return List of Items produces by the Item Properties Document
	 * @throws CSVFormatException 
	 * @throws StockException 
	 */
	public static List<Item> ImportItemProperties(String fileName, Stock storeInventory) throws IOException, CSVFormatException, StockException {
		List<Item> items = new ArrayList<>();
		
		Path pathToFile = Paths.get(fileName);
		// Reads File
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			// Goes Line By Line
			String line = br.readLine();
			
			while (line != null) {
				// Splits contents via , stores in array
				String[] attributes = line.split(",");
				// Creates item with array
				Item item = new Item(attributes);
				// Adds item to inventory
				storeInventory.addItem(item);
				// Goes to next line.
				line = br.readLine();
			}
		} catch (IOException e) {
			throw new CSVFormatException("Import Item Properties Error : Invalid file format.");
		}
			
		
		return items;
	}
	
	/**
	 * This method parses a Sales Log and modifies the inventory of Items based 
	 * on the sales numbers.
	 *
	 * @param  fileName  File location of the Sales Log to be imported
	 * @throws CSVFormatException 
	 */
	public static void LoadSalesLog(String fileName, Stock storeInventory, Store store) throws CSVFormatException {
		double profit = 0;
		Path pathToFile = Paths.get(fileName);
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			String line = br.readLine();
			
			while (line != null) {
				// Going line by line, splits values into an array at the ,
				String[] attributes = line.split(",");
				// Looping through current inventory
				for (Item item : storeInventory.getItems()) {
					// Checks if item exists
					if (item.getName().equals(attributes[0])) {
						// Increases profit based on item sales
						for (int i = 0; i < Integer.parseInt(attributes[1]); i++) {
							profit += item.getSellPrice();
						}
						// Updates the quantity.
						int qty = item.getQuantity() - Integer.parseInt(attributes[1]);
						item.setQuantity(qty);
					}
				}
				// Goes to next line and repeats loop untill all lines are read
				line = br.readLine();
			}
		} catch (IOException e) {
			throw new CSVFormatException("Sales Log File Error : Invalid format.");
		} catch (NumberFormatException e) {
			throw new CSVFormatException("Sales Log File Error : Invlaid data provided.");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new CSVFormatException("Sales Log File Error : Missing Values in Sales Log File.");
		}
		store.setCapital(store.getCapital() + profit);
	}
	
	
	/**
	 * This method parses a Manifest  and modifies the inventory of Items based 
	 * on the numbers associated with the item in the manifest.
	 *
	 * @param  fileName  File location of the Manifest to be imported
	 * 
	 * @throws CSVFormatException 
	 * @throws DeliveryException 
	 */
	public static void LoadManifest(String fileName, Stock storeInventory, Store store) throws DeliveryException {
		
		double deduction = 0;
		boolean isColdTruck = false;
		
		Stock cargo = new Stock();
		
		Truck coldTruck = new RefrigeratedTruck(cargo);
		Truck ordinaryTruck = new OrdinaryTruck(cargo);
		
		List<Truck> incomingFleet = new ArrayList<>();
		
		Path pathToFile = Paths.get(fileName);
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			String line = br.readLine();
			
			while (line != null) {
				// Checks the truck type by reading the line in the document
				// Creates a truck based on that type with an empty cargo.
				// Sets a boolean variable to try if refrigerated truck
				if (line.equals(">Refrigerated")) {
					
					isColdTruck = true;
					
					cargo = new Stock();
					
					coldTruck = new RefrigeratedTruck(cargo);
					
					incomingFleet.add(coldTruck);
					
				} else if (line.equals(">Ordinary")) {
					
					isColdTruck = false;
					
					cargo = new Stock();
					
					ordinaryTruck = new OrdinaryTruck(cargo);
					
					incomingFleet.add(ordinaryTruck);
					
				} else {
					try {
						if (isColdTruck) {
							// Reads through rest of file, spliting file attributes by ,
							String[] attributes = line.split(",");
							for (Item item : storeInventory.getItems()) {
								try { 
									// Updates inventory of truck that was created and decreases capitol as items are added
									if (item.getName().equals(attributes[0])) {
										for (int i = 0; i < Integer.parseInt(attributes[1]); i++) {
											coldTruck.cargo().addItem(item);
											deduction += item.getManufacturingCost();
										}
										int qty = item.getQuantity() + Integer.parseInt(attributes[1]);
										item.setQuantity(qty);
									}
								} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
									throw new DeliveryException("Import Manifest Error : Invlaid data in file.");
									
								}
							}
						} else  {
							String[] attributes = line.split(",");
							for (Item item : storeInventory.getItems()) {
								try {
									if (item.getName().equals(attributes[0])) {
										for (int i = 0; i < Integer.parseInt(attributes[1]); i++) {
											ordinaryTruck.cargo().addItem(item);
											deduction += item.getManufacturingCost();
										}
										int qty = item.getQuantity() + Integer.parseInt(attributes[1]);
										item.setQuantity(qty);
									}
								} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
									throw new DeliveryException("Import Manifest Error : Invlaid data in file.");
									
								}
							}
						}
					} catch (NumberFormatException e) {
						throw new DeliveryException("Import Manifest Error : Invlaid data in file.");
						
					}
					
				}
				
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Calculaetes the cost of operating the truck and adds it too the deductions
		for (Truck truck : incomingFleet) {			
			deduction += truck.getCost();
		}
		
		// Decreases capital by deduction amount.
		store.setCapital(store.getCapital() - deduction );
	}
	
	/**
	 * This method parses a Manifest  and modifies the inventory of Items based 
	 * on the numbers associated with the item in the manifest.
	 *
	 * @param  fileName  File location of the Manifest to be exported
	 * @param storeInventory inventory of the store
	 * 
	 * @throws StockException 
	 * @throws DeliveryException 
	 */
	public static void ExportManifest(String fileName, Stock storeInventory) throws StockException, DeliveryException {
		List<Truck> fleet;
		// Checks to make sure inventory is generated
		if (storeInventory.getTotal() == 0) {
			throw new DeliveryException("Error generating manifest : no items in inventory.");
		} else {
			// Create a Stock class to store items that need re-ordering.
			Stock itemsToOrder = new Stock();
			// Check if items needs inventory need ordering and add to Stock.
			for (Item item: storeInventory.getItems()) {
				if (item.reorder()) {
					for (int i = 0; i < item.getReorderAmount(); i++) {
						itemsToOrder.addItem(item);
					}
				}
			}
			// Create a manifest with list of items that need ordering.
			Manifest manifest = new Manifest(itemsToOrder);
			fleet = manifest.getFleet();
			
			try {
				// Write manifest to file
				FileWriter fileWriter = new FileWriter(fileName);
				
				for (Truck truck : fleet) {
					List<String> cargos = truck.getCargo();
				
					for (String output : cargos) {
						fileWriter.append(output + "\n");
					}
				}
				fileWriter.close();
			} catch (IOException e) {
				throw new DeliveryException("Export Manifest Error : Unable to write to file.");
			}
		}
	}
	
}
