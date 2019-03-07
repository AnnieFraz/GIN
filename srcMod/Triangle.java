package cscu9a2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.*;
import java.awt.Color;

/**
 This is an initial starter version of a Java application that should be
 extended in stages, eventually allowing the user to enter product sales figures
 for a number of products, and display them in a table with %ages, a ranking and a pie chart.
 This starter code just has storage for one product, and allows that product's sales figure
 to be updated and displayed. The display is plain, with no font changes and
 no border lines for the table. All the processing is limited to just this one product,
 and must be adapted for a whole array of products.
 SBJ March 2016
 */

public class ProductChart extends JFrame implements ActionListener
{
    /**
     * Frame constants
     */
    private static final int FRAME_X = 200;
    private static final int FRAME_Y = 200;
    private static final int FRAME_WIDTH = 650;
    private static final int FRAME_HEIGHT = 600;

    /**
     * The maximum permitted number of products
     */
    private final int MAX_PRODUCTS = 10; //ten products

    /**
     * These arrays holds all the sales data:
     * Element 0 is unused, so array sizes are MAX_PRODUCTS+1.
     * The product number (from 1 to MAX_PRODUCTS) is the index in the arrays
     * where the product's data is held.
     * Sales figures are counted quantities, so int.
     */
    private final String[] products = {"", "Bread", "Chicken", "Soup", "Eggs", "Peas", "Potatoes", "Crisps", "Sugar", "Coffee", "Tea"}; // products initially available
    private String[] productName;  // The name of each product
    private int[] productSales;    // The number of sales of each product
    private float[] percentage;    // The proportion of total sales for each product
    private int totalSales;        // Always the current total sales
    private int[] ranks; // Holds the rankings from 1 to 10
    private Color[] colors = {Color.black, Color.yellow, Color.blue, Color.gray, Color.green, Color.red, Color.orange, Color.pink, Color.darkGray, Color.cyan, Color.magenta}; // holds colors to be used in pie chart

    /**
     * Display area layout constants
     */
    private final int DISPLAY_WIDTH = 600;
    private final int DISPLAY_HEIGHT = 500;
    private final int PRODUCT_X = 30;   // Start of product number column
    private final int NAME_X = 75;      // Start of product name column
    private final int SALES_X = 375;    // Start of sales column
    private final int AGE_X = 440; // Start of age column
    private final int RANK_X = 500; //Start of sales rankings column
    private final int KEY_X = 560; //Start of pie chart keys column

    /**
     * The main launcher method:

     * and make the applications visible.
     * @param args Unused
     */
    public static void main(String[] args)
    {
        ProductChart frame = new ProductChart();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Product Sales Chart "); //title of window/application
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null); //location centered to screen
        frame.initializeSalesData();
        frame.createGUI();
        frame.setVisible(true);
    }

    /**
     * The GUI components
     */
    private JTextField productSalesEntryField;  // For entry of new product sales figures
    private JButton updateSalesButton;          // To request update of a sales figure
    private JButton clearSalesButton; //button to clear all the sales figures
    private JPanel displayArea;                 // Graphics area for drawing the sales table
    private JTextField productNumberEntryField; //For entry of product number

    /**
     * Helper method to build the GUI
     */
    public void createGUI()
    {
        // Standard window set up
        Container window = getContentPane();
        window.setLayout(new FlowLayout());
        window.setBackground(Color.lightGray);

        //Product number entry label and text field to enter the number of the product
        JLabel productNumberEntryLabel = new JLabel("Product number:");
        productNumberEntryField = new JTextField(5);
        window.add(productNumberEntryLabel);
        window.add(productNumberEntryField);

        // Product sales entry label and text field
        JLabel productSalesEntryLabel = new JLabel("Product sales:");
        productSalesEntryField = new JTextField(5);
        window.add(productSalesEntryLabel);
        window.add(productSalesEntryField);

        // Button to add new sales figure
        updateSalesButton = new JButton("Update sales");
        updateSalesButton.addActionListener(this);
        window.add(updateSalesButton);

        //button to clear all sales figures
        clearSalesButton = new JButton("Clear all sales");
        clearSalesButton.addActionListener(this);
        window.add(clearSalesButton);

        // The drawing area for displaying all data
        displayArea = new JPanel()
        {
            // paintComponent is called automatically when a screen refresh is needed
            public void paintComponent(Graphics g)
            {
                // g is a cleared panel area
                super.paintComponent(g); // Paint the panel's background
                paintScreen(g);          // Then the required graphics
            }
        };

        //sets the display area size and colour and then adds it to the window
        displayArea.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        displayArea.setBackground(Color.white);
        window.add(displayArea);
    }

    /**
     * Initializes product arrays:
     * A set of product names,
     * With sales initially 0.
     *
     * Note: In the arrays, the first element is unused, so Bread is at index 1
     */
    public void initializeSalesData()
    {
        productName = new String[MAX_PRODUCTS+1]; //array to hold the product names
        productSales = new int[MAX_PRODUCTS+1]; //array to hold product sales figures
        percentage = new float[MAX_PRODUCTS+1]; //array to hold percentages of sales figures
        ranks = new int[MAX_PRODUCTS+1]; //array to hold ranks

        //productName and productSales array indexes are assigned to strings/numbers using a for loop
        for(int i = 1; i < 11; i++) {
            productName[i] = products[i];
            productSales[i] = 0;
            percentage[i] = 0;
            ranks[i] = 0;
        }
    }

    /**
     * Event handler for button clicks.
     *
     * One action so far:
     * o Update the sales figure for a product
     */
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == updateSalesButton)
        {
            //sales are updated by calling the updateSalesAction method

            updateSalesAction();
            rankIt(productSales);

            // And refresh the display
            repaint();
        } else if (event.getSource() == clearSalesButton) //checks if the button clicked is the clear sales button
        {
            //uses Arrays library to quickly fill the arrays back to 0
            Arrays.fill(productSales, 0);
            Arrays.fill(percentage, 0);
            Arrays.fill(ranks, 0);
            totalSales = 0; //totalSales resetted back to 0 too, so the percentages can be recalculated corrected next time
            repaint(); //repaints the display area to show the sales figures at 0 again

        }
    }

    /**
     * Action updating a sales figure: the new sales figure is fetched
     * from a text fields, parsed (converted to an int), and action is taken.
     */
    public void updateSalesAction()
    {
        try { //try used here to make sure the user enters correct data

            //Fetch the product number
            int productNumberEntered  = Integer.parseInt(productNumberEntryField.getText());
            // Fetch the new sales details
            int newSales = Integer.parseInt(productSalesEntryField.getText());

            //if statement checks for invalid data entered into field
            if (newSales < 0) //checks for invalid data, if product number is less than 1 or more than 10 and if newSales data is below 0
            {
                JOptionPane.showMessageDialog(null, "New sales figure needs to be more than or equal to 0 (zero)."); //displays a message to end-user in a new window to notify them of the invalid input
            }

            //if statement to check if the product sales number has already been inputted by the user before
            if(productSales[productNumberEntered] > 0) {
                totalSales -= productSales[productNumberEntered]; //subtracts the old product sales figure, of the product number entered, from the total sales
            }
            totalSales += newSales; //totalSales is equal to itself plus the newSales entry that has been entered by the user

            // Update the sales tables by taking the product number entered and making that the index of the product sales array and that array value is assigned to the sales figure entered in new sales entry field
            productSales[productNumberEntered] = newSales;

            //%age is updated by using a for loop to loop through percentage array to assign it to the correct percentage
            for(int i = 1; i < 11; i++)
            {
                percentage[i] = (float) (productSales[i]*100)/totalSales; //percentage is calculated for each product
            }

            //rankIt(productSales);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //catches exception for if the user enters a non-integer value and for array index out of bounds

            JOptionPane.showMessageDialog(null, "Invalid data inputted. Please make sure the product number is between, inclusive, 1 and 10. Also, make sure the new sales data is more than 0 (zero)."); //displays a message to end-user in a new window to notify them of the invalid input

        }
    }

    /**
     * Redraw all the sales data on the given graphics area
     */
    public void paintScreen(Graphics g)
    {
        // Draw a table of the sales data, with columns for the product number,
        // product name, and sales
        //setFont is used to set the font of the text after the setFont is called upon 'g'

        //Heading
        g.setFont(new Font("default", Font.BOLD, 18));
        g.drawString("Product sales data:", 20, 20);

        // Table column headers
        g.setFont(new Font("default", Font.BOLD, 14));
        g.drawString("No", PRODUCT_X, 60);
        g.drawString("Name", NAME_X, 60);
        g.drawString("Sales", SALES_X, 60);
        g.drawString("%age", AGE_X, 60);
        g.drawString("Rank", RANK_X, 60);

        //y and yDiff values declared and initialised
        int y = 60;    // The y coordinate for each line
        int yDiff = 20; //difference in y to cause a new line to be formed

        //table borders drawn
        g.drawLine(25, 45, 25, 265); //left border of table
        g.drawLine(545, 45, 545, 265);//right border of table
        g.drawLine(60, 45, 60, 265);//column line between no and name
        g.drawLine(365, 45, 365, 265);//column line between name and sales
        g.drawLine(425, 45, 425, 265);//column line between sales and %age
        g.drawLine(485, 45, 485, 265); //column line between %age and rank
        g.drawLine(25, 45, 545, 45); //top border of table

        //row lines inside table with bottom border of table
        for(int i = 1; i<12; i++) {
            g.drawLine(25, 45+yDiff*i, 545, 45+yDiff*i);
        }

        g.setFont(new Font("default", Font.PLAIN, 14)); //setting font of table data back to plain 14 size
        // The table of sales data
        //for loop to display all the product numbers, names, sales figures, %age figures
        for(int i = 1; i<11; i++) {
            g.drawString(Integer.toString(i), PRODUCT_X, y+yDiff*i); //y+yDiff*i allows for a new line for the data to be displayed on
            g.drawString(productName[i], NAME_X, y+yDiff*i);
            g.drawString(Integer.toString(productSales[i]), SALES_X, y+yDiff*i);
            g.drawString(Integer.toString((int)Math.round(percentage[i])), AGE_X, y+yDiff*i);
            g.drawString(Integer.toString(ranks[i]), RANK_X, (y+yDiff*i));
        }

        //angle = (salesOfSpecificProduct / totalSales) * (360)
        //draw the pie chart
        if(totalSales > 0) {
            double curValue = 0.0D; //holds current product sales value
            int startAngle = 0; //holds the starting angle
            for (int i = 1; i < productSales.length; i++) {
                startAngle = (int) (curValue * 360 / totalSales); //start angle calculated
                int arcAngle = (int) (productSales[i] * 360 / totalSales); //arc angle calculated
                g.setColor(colors[i]); //sets colours according to the product pie chart colour key
                g.fillArc(130, 300, 300, 180, startAngle, arcAngle); //creates the arcs for the slices
                curValue += productSales[i]; //current value is increased by adding the product sale's value just used
            }
            g.setColor(colors[0]); //sets colour of the two arcs below
            g.drawArc(130, 300, 300, 180, 0, 360); //draws outline of pie chart
            g.drawArc(130, 315, 300, 145, 0, -180); //arc makes the piechart look 3D with wedges that are coloured the same as their respective pie chart slice

            //for loop to display pie chart keys
            for(int i = 1; i < colors.length; i++)
            {
                g.setColor(colors[i]);
                g.fillOval(KEY_X, (y+yDiff*i)-10, 15, 10); //creates an oval shape filled with the colour above the statement to act as a pie chart key
            }
        }
    }

    /**
     * The sales rank (popularity) should be calculated for each product and displayed in an extra column: the highest
     sales product should have rank 1, then next highest has rank 2, and so on. Products with equal sales should have
     equal rank (see the preview for an example). When all sales are 0 then all ranks should be 0. This is a reasonably
     challenging algorithm design. Of course, this should be re-displayed every time that a sales figure is updated.
     */
    public void rankIt(int[] originalArray)
    {
        int index = 1; //index for when rank is assigned to a value

        java.util.ArrayList<Integer> orderedNoDuplicates = new ArrayList<Integer>(); //arrayList to hold the ordered values from the product sales array but without duplicates and in descending order

        //for loop to add values from the product sales array to the list above
        for (int i = 1; i < originalArray.length; i++)
        {
            orderedNoDuplicates.add(originalArray[i]);
        }

        //hashset allows the list above to be recreated but without duplicates
        Set<Integer> hashSet = new HashSet<>();
        hashSet.addAll(orderedNoDuplicates);
        orderedNoDuplicates.clear();
        orderedNoDuplicates.addAll(hashSet);
        orderedNoDuplicates = new ArrayList<Integer>(hashSet); //recreates the orderedNoDuplicates list as a copy of the hashSet
        Collections.sort(orderedNoDuplicates, Collections.reverseOrder()); //sorts the list in reverse order, descending order

        //for loop to assign the rank of the sales products
        for(int i = 0; i < orderedNoDuplicates.size(); i++)
        {
            for(int j = 1; j < originalArray.length; j++)
            {
                if(orderedNoDuplicates.get(i) == originalArray[j])
                {
                    ranks[j] = index;
                }
            }
            index++; //increments index by one when the originalArray has been searched for the value from the list
        }
    }
}