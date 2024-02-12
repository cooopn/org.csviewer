package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import mgr.HelpPanelContentBuilder;
import mgr.HelpPanelContentModel;

public class HelpPanelWithHtmlContents extends JPanel {

    private JTextPane htmlPane; // Text pane to display HTML content
    private HelpPanelContentModel theModel; // Model to manage HTML pages
    private HelpPanelContentBuilder theBuilder = new HelpPanelContentBuilder(); // Builder to create the model
    private JButton prevButton; // Button to navigate to the previous page
    private JButton nextButton; // Button to navigate to the next page
    private JTextField pageNumberField; // Text field to input the page number
    private int currentBtnIndex = 0; // Index of the currently displayed page

    // Constructor
    public HelpPanelWithHtmlContents() {
        setLayout(new BorderLayout()); // Use BorderLayout for the panel

        // Initialize the text pane
        htmlPane = new JTextPane();
        htmlPane.setContentType("text/html");
        htmlPane.setEditable(false); // Ensure the text pane is not editable

        // Create a scroll pane to contain the text pane
        JScrollPane jspHtml = new JScrollPane(htmlPane);

        // Retrieve the help panel content model from the directory
        theModel = theBuilder.retrievePagesFromDir("html/Welcome");
        int pageCount = theModel.pageCount(); // Get the number of pages in the model

        // Create a panel for navigation buttons
        JPanel buttonPanel = new JPanel(); 

        // Create the previous button and add action listener
        prevButton = new JButton("Prev");
        prevButton.addActionListener(new NavigationHandler());
        buttonPanel.add(prevButton); 

        // Create the next button and add action listener
        nextButton = new JButton("Next");
        nextButton.addActionListener(new NavigationHandler());
        buttonPanel.add(nextButton); 

        // Create the text field for page number input
        pageNumberField = new JTextField(5);
        buttonPanel.add(pageNumberField); 

        // Create the "Go To" button and add action listener
        JButton goToButton = new JButton("Go To");
        goToButton.addActionListener(new GoToPageHandler());
        buttonPanel.add(goToButton); 

        // Set preferred size for the text pane
        jspHtml.setPreferredSize(new Dimension(500, 300));
        // Add the text pane to the center of the panel
        add(jspHtml, BorderLayout.CENTER); 
        // Add the button panel to the bottom of the panel
        add(buttonPanel, BorderLayout.SOUTH); 

        // Show the first page when the panel is initialized
        if (pageCount > 0) {
            showPage(0); // Show the first page
        }
    }

    // Method to display the page with the given index
    private void showPage(int index) {
        htmlPane.setText(theModel.getPageByIndex(index)); // Set the text of the text pane to the content of the page
        currentBtnIndex = index; // Update the index of the currently displayed page
    }

    // ActionListener for navigation buttons
    class NavigationHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // If previous button is clicked and there are pages before the current page
            if (e.getSource() == prevButton && currentBtnIndex > 0) {
                currentBtnIndex--; // Decrement the index
                showPage(currentBtnIndex); // Show the previous page
            } 
            // If next button is clicked and there are pages after the current page
            else if (e.getSource() == nextButton && currentBtnIndex < theModel.pageCount() - 1) {
                currentBtnIndex++; // Increment the index
                showPage(currentBtnIndex); // Show the next page
            }
        }
    }

    // ActionListener for "Go To" button
    class GoToPageHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Parse the page number input from the text field
                int pageIndex = Integer.parseInt(pageNumberField.getText()) - 1;
                // If the page number is valid
                if (pageIndex >= 0 && pageIndex < theModel.pageCount()) {
                    currentBtnIndex = pageIndex; // Update the index
                    showPage(currentBtnIndex); // Show the specified page
                }
            } catch (NumberFormatException ex) {
                // Handle invalid input (non-numeric input)
            }
        }
    }
}
