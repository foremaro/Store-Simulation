import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.lang.*;
/***********************************************************************
 * GUI front end for a Store class 
 * 
 * @author Ron Foreman
 * @version 4/12/2015
 **********************************************************************/
public class StoreGUI extends JFrame implements ActionListener{

    /** main display text area */
    private JTextArea displayArea;

    /** Labels */
    private JLabel lblCashier, lblAvgArival, lblAvgService;

    /** Buttons */
    private JButton btnSimulate;

    /** checkbox */
    private JCheckBox chkDisplayQ;

    /** Text fields */
    private JTextField txtCashier, txtAvgArrival, txtAvgService;

    /** menu items */
    private JMenuBar menus;
    private JMenu fileMenu;
    private JMenuItem clearItem;
    private JMenuItem quitItem;

    /** new Store */
    private Store s;

    /*********************************************************************
    Main Method - intantiate and display the GUI
     *********************************************************************/
    public static void main(String arg[]){

        StoreGUI theGUI = new StoreGUI();
        theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        theGUI.setTitle("Ron's Shop");
        theGUI.pack();
        theGUI.setVisible(true);
    }

    /*********************************************************************
    Constructor - instantiates and displays all of the GUI commponents
     *********************************************************************/
    public StoreGUI(){
        // instantiate the new store
        s = new Store();

        // setup GridBay Layout
        setLayout(new GridBagLayout());
        GridBagConstraints loc = new GridBagConstraints();
        loc.anchor = GridBagConstraints.LINE_START;     
        loc.insets = new Insets(2, 2, 2, 2);

        // create the Results Area for the Center area
        displayArea = new JTextArea(20,40);
        JScrollPane scrollPane = new JScrollPane(displayArea);  
        loc.gridx = 0;
        loc.gridy = 0; 
        loc.gridwidth = 6;
        loc.gridheight = 6;
        add(scrollPane, loc);
        loc.gridwidth = 1;
        loc.gridheight = 1;  

        // buttons instantiated
        btnSimulate = new JButton("Simulate");
        btnSimulate.addActionListener(this);   

        // Labels instantiated
        lblCashier = new JLabel("Cashiers: ");
        lblAvgArival = new JLabel("Avg Arrival Time: ");
        lblAvgService = new JLabel("Avg Service Time: ");

        // checkbox instantiated
        chkDisplayQ = new JCheckBox("Display Queue");
        chkDisplayQ.setSelected(false);
        chkDisplayQ.addActionListener(this);

        // Text fields instantiated, actionlisteners registered
        txtCashier = new JTextField (5);
        txtCashier.addActionListener(this);

        txtAvgArrival = new JTextField (5);
        txtAvgArrival.addActionListener(this);

        txtAvgService = new JTextField (5);
        txtAvgService.addActionListener(this);

        // add labels and text fields to side of window 
        // cashier lbl
        loc.gridx = 6;
        loc.gridy = 0;
        add(lblCashier, loc);

        // cashier textfield
        loc.gridx = 7;
        loc.gridy = 0;
        add(txtCashier, loc);

        // avg arrival lbl
        loc.gridx = 6;
        loc.gridy = 1;
        add(lblAvgArival, loc);

        // avg arrival text field
        loc.gridx = 7;
        loc.gridy = 1;
        add(txtAvgArrival, loc);

        // avg service lbl
        loc.gridx = 6;
        loc.gridy = 2;
        add(lblAvgService, loc);

        // avg service textfield
        loc.gridx = 7;
        loc.gridy = 2;
        add(txtAvgService, loc);

        // checkbox centered
        loc.anchor = GridBagConstraints.CENTER;
        loc.gridx = 6;
        loc.gridwidth = 2;
        loc.gridy = 3;
        add(chkDisplayQ, loc);

        // simulate button
        loc.anchor = GridBagConstraints.CENTER;
        loc.gridx = 6;
        loc.gridwidth = 2;
        loc.gridy = 4;
        add(btnSimulate, loc);

        // set up File menus
        setupMenus();
        pack();

    }

    /*********************************************************************
    Respond to menu selections and button clicks

    @param e the button or menu item that was selected
     *********************************************************************/
    public void actionPerformed(ActionEvent e){        

        // user clears text
        if (e.getSource()== clearItem) {
            displayArea.setText("");
        }

        //  quit application if Quit menu item selected
        if (e.getSource() == quitItem){
            System.exit(1);

        }

        // display results in response to Count menu selected   
        if (e.getSource() == btnSimulate) {

            // if a textfield is left blank
            if (txtCashier.getText().equals("") == true) {
                JOptionPane.showMessageDialog(null, "Please enter number of cashiers");
                return;
            }
            else if (txtAvgArrival.getText().equals("")==true){
                JOptionPane.showMessageDialog(null, "Please enter average arrival time");
                return;
            }
            else if (txtAvgService.getText().equals("")==true) {
                JOptionPane.showMessageDialog(null, "Please enter average service time");
                return;
            }
            else{
                int numCash = Integer.parseInt(txtCashier.getText());
                double arrive = Double.parseDouble(txtAvgArrival.getText());
                double service = Double.parseDouble(txtAvgService.getText());
                boolean checked;
                if (chkDisplayQ.isSelected()==true) {           // NOT WORKING
                    checked = true;
                }
                else {
                    checked = false;
                }
                s.setParameters(numCash, service, arrive, checked);
                s.simulate();
                displayArea.append(s.getResults());
                displayArea.append("\n");
            }
        }

    }

    /*********************************************************************
    Set up the menu items
     *********************************************************************/
    private void setupMenus(){

        // create menu components
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        clearItem = new JMenuItem("Clear");

        // assign action listeners
        quitItem.addActionListener(this);
        clearItem.addActionListener(this);

        // display menu components
        fileMenu.add(clearItem);
        fileMenu.add(quitItem);   
        menus = new JMenuBar();

        menus.add(fileMenu);
        setJMenuBar(menus);
    }   
}

