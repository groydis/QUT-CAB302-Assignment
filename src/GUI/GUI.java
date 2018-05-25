package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Delivery.Manifest;
import Stock.Item;
import Stock.Stock;
import Stock.Store;
/**
 *  
 *@author Alex Holm
 *
 **/
public class GUI extends JFrame implements Observer, ActionListener
{
	
	/**
	 * I Have no idea what I've done but this is a generated serial 
	 * instead of a default
	**/
	private static final long serialVersionUID = 4465181114406422996L;
	
	//Height and Width of the application window
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	
	
	
	JFrame mainFrame = new JFrame();
	JTabbedPane pane = new JTabbedPane();
	
	
	//Store and Inventory Variables
    JPanel inventoryTab  = new JPanel();
    private static JLabel storeNameLabel = new JLabel();
    private static JLabel storeCapitalLabel = new JLabel();
    
    //Names of the columns in the Table 
    String[] inventoryColumnNames
    = {"Name", "Cost", "Price", "Reorder Point", "Reorder Amount", "Temperature", "Quantity"};
   //Array of Data for items
    public Object[][]data
    = {{"Name", "Cost", "Price", "Reorder Point", "Reorder Amount", "Temperature", "Quantity"}};
    
    JTable inventoryTable = new JTable(data, inventoryColumnNames);
    
    //Document Variables
    JPanel documentTab = new JPanel();
    
    ImageIcon folderIcon = new ImageIcon("images/OpenFolder.png", "Open the File Chooser");
    
    
    //Items for the properties section. With A label, text area, a filechooser
    //icon and buttons
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
	

	//this is to initialize components and build the GUI
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
	 * 
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
	 * 
	 */
	public void actionPerformed(ActionEvent action) {
		if(action.getSource() == itemPropertiesChooseButton) {
			int returnVal = itemPropertiesChooser.showOpenDialog(GUI.this);

		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	  
		        
		      }
		}
		//this SAVES a file
		else if (action.getSource() == exportManifestChooseButton) {
		      int returnVal = exportManifestChooser.showSaveDialog(GUI.this);
		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		        
		      }
		}
		else if (action.getSource() == importManifestChooseButton) {
			int returnVal = importManifestChooser.showOpenDialog(GUI.this);

		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		        
		      }
		}
		else if (action.getSource() == salesLogChooseButton) {
			int returnVal = salesLogChooser.showOpenDialog(GUI.this);

		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		        
		      }
		} 
		else if (action.getSource() == itemPropertiesButton) {
			if (itemPropertiesTextArea.getText().trim().length() != 0) {
				
					
					
				DefaultTableModel dtm = new DefaultTableModel(0, 0);
				dtm.addColumn("Name");
				dtm.addColumn("Manufacturing Cost");
				dtm.addColumn("Price");
				dtm.addColumn("Reorder Point");
				dtm.addColumn("Reorder Amount");
				dtm.addColumn("Storage Temp");
				dtm.addColumn("Quantity");
				
				dtm.addRow(new Object[] {"Name", "Cost", "Price", "Reorder Point", "Reorder Amount", "Temperature", "Quantity"});
				
				
				
				inventoryTable.setModel(dtm);
				

			}
			
		}
		else if (action.getSource() == importManifestButton) {
			if (importManifestTextArea.getText().trim().length() != 0) {

			} 
			
		}
		else if (action.getSource() == exportManifestButton) {
			if (exportManifestTextArea.getText().trim().length() != 0) {

			}
		}
		else if (action.getSource() == salesLogButton) {
			if (salesLogTextArea.getText().trim().length() != 0) {

			} 
		}
	}
	
	/*
	 * The main function. Creates a store, creates inventory and gets the name and capital
	 */
  public static void main(String[] args) {

        JFrame.setDefaultLookAndFeelDecorated(true);
        new GUI();
    }

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	/*
	 * Used to show errors and display exceptions
	 */
	public void ShowError(String title, String error) {
		JOptionPane.showMessageDialog(mainFrame,
			    error,
			    title,
			    JOptionPane.WARNING_MESSAGE);
	}
}

