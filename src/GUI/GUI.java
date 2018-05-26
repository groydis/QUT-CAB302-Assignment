package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Stock.Item;
import Stock.Stock;
import Stock.Store;


/**
 * The GUI class creates an interface fit to human consumption that visualizes data about the store, 
 * loads and generates CSV files using the FileManager classs
 * 
 *@author Alex Holm
 *
 **/
public class GUI extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	//The File Name of particular files
	public static String itemPropertiesFileName;
	public static String importManifestFileName;
	public static String exportManifestFileName;
	public static String salesLogFileName;
	
	private static Stock storeInventory;
	private static Store store;
	
	
	
	JFrame mainFrame = new JFrame();
	JTabbedPane pane = new JTabbedPane();
	
	

    JPanel inventoryTab  = new JPanel();
    private static JLabel storeNameLabel = new JLabel();
    private static JLabel storeCapitalLabel = new JLabel();
    

    String[] inventoryColumnNames
    = {"Name", "Cost", "Price", "Reorder Point", "Reorder Amount", "Temperature", "Quantity"};
   //Array of Data for items
    public Object[][]data
    = {{"Name", "Cost", "Price", "Reorder Point", "Reorder Amount", "Temperature", "Quantity"}};
    
    JTable inventoryTable = new JTable(data, inventoryColumnNames);
    
    JPanel documentTab = new JPanel();
    ImageIcon folderIcon = new ImageIcon("images/OpenFolder.png", "Open the File Chooser");
  
    JLabel itemPropertiesLabel = new JLabel("Item Properties");
    JTextArea itemPropertiesTextArea = new JTextArea(1, 20);
    JFileChooser itemPropertiesChooser = new JFileChooser();
    JButton itemPropertiesChooseButton = new JButton(folderIcon);
    JButton itemPropertiesButton = new JButton("Load");

    JLabel importManifestLabel = new JLabel("Manifests");
    JTextArea importManifestTextArea = new JTextArea(1, 20);
    JFileChooser importManifestChooser = new JFileChooser();
    JButton importManifestChooseButton = new JButton(folderIcon);
    JButton importManifestButton = new JButton("Load");
    
    JLabel exportManifestLabel = new JLabel("Export Manifest");
    JTextArea exportManifestTextArea = new JTextArea(1, 20);
    JFileChooser exportManifestChooser = new JFileChooser();
    JButton exportManifestChooseButton = new JButton(folderIcon);
    JButton exportManifestButton = new JButton("Export");
    
    JLabel salesLogLabel = new JLabel("Sales Log");
    JTextArea salesLogTextArea = new JTextArea(1, 20);
    JFileChooser salesLogChooser = new JFileChooser();
    JButton salesLogChooseButton = new JButton(folderIcon);
    JButton salesLogButton = new JButton("Load");
    

    
    
	public GUI() {
        super("N9918205 + N9935924 CAB302 Assingment 2");
        loadLayout();
	}
	
	
	/**
	 * Initializes and sets up components for the GUI
	 */
	private void initComponents() {
		
		//Create main frame
		mainFrame = new JFrame("N9918205 + N9935924 CAB302 Assingment 2");
		mainFrame.setSize(HEIGHT, WIDTH);		
		mainFrame.setLayout(new GridBagLayout());
		//adding a pane to add tabs to
		mainFrame.add(pane);
	
		//the Inventory Tab which shows the Store's name and capital.
		//It will also display a table of items when the item properties are added
		pane.add("Store", inventoryTab);
      
        inventoryTab.setLayout(new BorderLayout());
        
        //Store name and capital labels
        inventoryTab.add(storeNameLabel, BorderLayout.PAGE_START);
        inventoryTab.add(storeCapitalLabel, BorderLayout.PAGE_END);
        
        //Makes the table unable to be editable 
        inventoryTable.setEnabled(false);
        inventoryTab.add(inventoryTable, BorderLayout.CENTER);
        
     
        //Creates another Tab, primarily used for
        pane.add("Documents", documentTab);
        
        //Allows the Text Areas to be editable
        itemPropertiesTextArea.setEditable(true);
        exportManifestTextArea.setEditable(true);
        importManifestTextArea.setEditable(true);
        salesLogTextArea.setEditable(true);
        
        //Sets the FileChoosers to navigate throguh files and directories
        itemPropertiesChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		exportManifestChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		importManifestChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		salesLogChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		//Sets the directory of the file choosers to the Home directory
	    itemPropertiesChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	    exportManifestChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	    importManifestChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        salesLogChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
	    //Adds and action listens. These action listeners should open the file chooser
        itemPropertiesChooseButton.addActionListener(this);
        importManifestChooseButton.addActionListener(this);
        exportManifestChooseButton.addActionListener(this);
        salesLogChooseButton.addActionListener(this);
      
        //These action listeners should read and load information. Aside from Export which saves files
        itemPropertiesButton.addActionListener(this);
        importManifestButton.addActionListener(this);
        exportManifestButton.addActionListener(this);
        salesLogButton.addActionListener(this);
        
        //Document Tab Layout setup. Layout done as a GridBag
        documentTab.setLayout(new GridBagLayout());
        GridBagConstraints docuTabLayout = new GridBagConstraints();
        docuTabLayout.fill = GridBagConstraints.HORIZONTAL;
        docuTabLayout.anchor = GridBagConstraints.WEST;

        //Setting the grid x and y coordinates to 0 and changing as required
        //0,0 is top left
        docuTabLayout.gridy = 0;
        docuTabLayout.gridx = 0;
        documentTab.add(itemPropertiesLabel, docuTabLayout);
        docuTabLayout.gridy = 1;
        documentTab.add(itemPropertiesTextArea, docuTabLayout);
        docuTabLayout.gridx = 1;
        documentTab.add(itemPropertiesChooseButton, docuTabLayout);
        docuTabLayout.gridx = 2;
        documentTab.add(itemPropertiesButton, docuTabLayout);
        
        docuTabLayout.gridy = 2;
        docuTabLayout.gridx = 0;
        documentTab.add(importManifestLabel, docuTabLayout);
        docuTabLayout.gridy = 3;
        documentTab.add(importManifestTextArea, docuTabLayout);
        docuTabLayout.gridx = 1;
        documentTab.add(importManifestChooseButton, docuTabLayout);
        docuTabLayout.gridx = 2;
        documentTab.add(importManifestButton, docuTabLayout);

        docuTabLayout.gridy = 4;
        docuTabLayout.gridx = 0;
        documentTab.add(exportManifestLabel, docuTabLayout);
        docuTabLayout.gridy = 5;
        documentTab.add(exportManifestTextArea, docuTabLayout);
        docuTabLayout.gridx = 1;
        documentTab.add(exportManifestChooseButton, docuTabLayout);
        docuTabLayout.gridx = 2;
        documentTab.add(exportManifestButton, docuTabLayout);
        
        docuTabLayout.gridy = 6;
        docuTabLayout.gridx = 0;
        documentTab.add(salesLogLabel, docuTabLayout);
        docuTabLayout.gridy = 7;
        documentTab.add(salesLogTextArea, docuTabLayout);
        docuTabLayout.gridx = 1;
        documentTab.add(salesLogChooseButton, docuTabLayout);
        docuTabLayout.gridx = 2;
        documentTab.add(salesLogButton, docuTabLayout);
        
        getContentPane().add(pane);
        
	}
	/*
	 * 
	 * This function sets height and width, while also loading the layout. Calling initComponents()
	 */
	private void loadLayout() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLocation(new Point(100, 100));
        pack();
        setVisible(true);
    }
	/*
	 * 
	 * Action Listener. When buttons are clicked, it does things.
	 * Such as opening FileChoosers, Saving/ Loading data
	 * Showing errors if the file is not selected or not appropriate
	 * @param action
	 */
	public void actionPerformed(ActionEvent action) {
		if(action.getSource() == itemPropertiesChooseButton) {
			int returnVal = itemPropertiesChooser.showOpenDialog(GUI.this);

		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	File file = itemPropertiesChooser.getSelectedFile();
		        itemPropertiesFileName = file.getAbsolutePath();
		        itemPropertiesTextArea.setText("");
		        itemPropertiesTextArea.append(itemPropertiesFileName);
			      
		        
		      }
		}
		else if (action.getSource() == exportManifestChooseButton) {
		      int returnVal = exportManifestChooser.showSaveDialog(GUI.this);
		      if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = exportManifestChooser.getSelectedFile();
				exportManifestFileName = file.getAbsolutePath();
				exportManifestTextArea.setText("");
				exportManifestTextArea.append(exportManifestFileName);
			      
		      }
		}
		else if (action.getSource() == importManifestChooseButton) {
			int returnVal = importManifestChooser.showOpenDialog(GUI.this);

		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	File file = importManifestChooser.getSelectedFile();
		        importManifestFileName = file.getAbsolutePath();
		        importManifestTextArea.setText("");
		        importManifestTextArea.append(importManifestFileName);
			      
		      }
		}
		else if (action.getSource() == salesLogChooseButton) {
			int returnVal = salesLogChooser.showOpenDialog(GUI.this);

		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	File file = salesLogChooser.getSelectedFile();
		        salesLogFileName = file.getAbsolutePath();
		        salesLogTextArea.setText("");
		        salesLogTextArea.append(salesLogFileName);
		      }
		} 
		else if (action.getSource() == itemPropertiesButton) {
			if (itemPropertiesTextArea.getText().trim().length() != 0) {
				try {
					FileManager.ImportItemProperties(itemPropertiesTextArea.getText(), storeInventory);
					// Updates quantity of items in order on Item Properties Import
					
					// Updates the table
					DefaultTableModel dtm = new DefaultTableModel(0, 0);
					dtm.addColumn("Name");
					dtm.addColumn("Manufacturing Cost");
					dtm.addColumn("Price");
					dtm.addColumn("Reorder Point");
					dtm.addColumn("Reorder Amount");
					dtm.addColumn("Storage Temp");
					dtm.addColumn("Quantity");
					
					dtm.addRow(new Object[] {"Name", "Cost", "Price", "Reorder Point", "Reorder Amount", "Temperature", "Quantity"});
					
					for (Item item: storeInventory.getItems()) {
						if (item.getStorageTemp() == 24) {
							dtm.addRow(new Object[] { item.getName(), item.getManufacturingCost(), item.getSellPrice(),
									item.getReorderpoint(), item.getReorderAmount(), " ", item.getQuantity() });
							
						} else {
							dtm.addRow(new Object[] { item.getName(), item.getManufacturingCost(), item.getSellPrice(),
								item.getReorderpoint(), item.getReorderAmount(), item.getStorageTemp(), item.getQuantity() });
						}
					}
					
					inventoryTable.setModel(dtm);
					ShowError("Item Properties Imported.", "Please review in store tab.");
				} catch (IOException e) {
					ShowError("Import Items Properties Error", e.toString());
					
				} catch (CSVFormatException e) {
					ShowError("Import Items Properties Error", e.toString());
					
				} catch (StockException e) {
					ShowError("Import Items Properties Error", e.toString());
				}

			} else {
				ShowError("Import Items Properties Error", "No file Selected");
			}
			storeCapitalLabel.setText("$" + store.capitalToString());
			
		}
		else if (action.getSource() == importManifestButton) {
			if (importManifestTextArea.getText().trim().length() != 0) {
				try {
					FileManager.LoadManifest(importManifestTextArea.getText(), storeInventory, store);
					// Updates Table based items imported.
					int index = 1;
					for (Item item : storeInventory.getItems()) {
						inventoryTable.getModel().setValueAt(item.getQuantity(), index, 6);
						index++;
					}
					storeCapitalLabel.setText("$" + store.capitalToString());
					ShowError("Manifest Imported.", "Please review in store tab.");
				} catch (DeliveryException e) {
					ShowError("Import Manifest Error", e.toString());
				}
			} else {
				ShowError("Import Manifest Error", "No file Selected");
			}
			
		}
		else if (action.getSource() == exportManifestButton) {
			if (exportManifestTextArea.getText().trim().length() != 0) {

				String path = exportManifestTextArea.getText();
				if (!path .endsWith(".csv")) {
					path += ".csv";
					try {
						FileManager.ExportManifest(path, storeInventory);
						ShowError("Manifest Exported.", exportManifestTextArea.getText());
					} catch (StockException e) {
						ShowError("Export Manifest Error", e.toString());
					} catch (DeliveryException e) {
						ShowError("Export Manifest Error", e.toString());
					}
			 	} else {
					try {
						FileManager.ExportManifest(path, storeInventory);
							ShowError("Manifest Exported.", exportManifestTextArea.getText());
						} catch (StockException e) {
							ShowError("Export Manifest Error", e.toString());
						} catch (DeliveryException e) {
							ShowError("Export Manifest Error", e.toString());
						}
			 	}

			} else {
				ShowError("Export Manifest Error", "No file Selected");
			}
		}
		else if (action.getSource() == salesLogButton) {
			if (salesLogTextArea.getText().trim().length() != 0) {
				try {
					FileManager.LoadSalesLog(salesLogTextArea.getText(), storeInventory, store);
					// Updates table based on Sales Log Imported.
					int index = 1;
					for (Item item : storeInventory.getItems()) {
						inventoryTable.getModel().setValueAt(item.getQuantity(), index, 6);
						index++;
					}
					storeCapitalLabel.setText("$" + store.capitalToString());
					ShowError("Sales Log Imported", "Please review in store tab.");
				} catch (CSVFormatException e) {
					ShowError("Import Sales Log Error", e.toString());
				}
				
			} else {
				ShowError("Sales Log Error", "No file Selected");
			}
		}
	}
	
	/*
	 * The main function. Creates a store, creates inventory and gets the name and capital
	 * @param args
	 */
  public static void main(String[] args) {
	  store = new Store();
	  storeInventory = new Stock();
	  storeNameLabel.setText("Store Name: " + store.getStoreName());
	  storeCapitalLabel.setText("$" + store.capitalToString());
	  JFrame.setDefaultLookAndFeelDecorated(true);
	  new GUI();
    }
  
	/**
	 * Used to show errors and display exceptions and successes.
	 * @param title
	 * @param error
	 */
	public void ShowError(String title, String error) {
		JOptionPane.showMessageDialog(mainFrame,
			    error,
			    title,
			    JOptionPane.WARNING_MESSAGE);
	}
}

