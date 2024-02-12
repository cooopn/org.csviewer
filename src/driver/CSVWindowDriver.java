package driver;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import gui.BottomPanel;
import gui.HelpPanelWithHtmlContents;
import gui.ImagePanel;

// Main class responsible for driving the CSV Viewer application
public class CSVWindowDriver extends JFrame {
    // Tabbed pane to hold different panels
    private final JTabbedPane pane = new JTabbedPane();
    private JPanel contentPane;
    private BottomPanel bPanel = new BottomPanel(); // Panel at the bottom
    private ImagePanel iPanel = new ImagePanel(); // Panel for displaying images
    private HelpPanelWithHtmlContents welcomePanel = new HelpPanelWithHtmlContents(); // Panel for the "Welcome" tab
    
    // Constructor
    public CSVWindowDriver() {
        super("CSViewer for Analysts - V1.1"); // Set window title
        this.setIconImage(new ImageIcon("html/image/monkeyIcon.png").getImage()); // Set window icon
        
        // Create a panel for the left column
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BorderLayout());
        ImageIcon csvIconImage = new ImageIcon("images/monkeyIcon.png");
        
        // Add the welcome panel to the tabbed pane
        pane.addTab("Welcome", welcomePanel);
        leftColumn.add(pane); // Add the tabbed pane to the left column
        leftColumn.add(bPanel, BorderLayout.SOUTH); // Add the bottom panel to the left column
        
        // Create a panel for the measure panel on the right
        JPanel measurePanel = new JPanel();
        measurePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Measure Data for Selected Animal"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        // Create a split pane to hold the measure panel and image panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, measurePanel, iPanel);
        splitPane.setResizeWeight(0.75); // Set resize weight for split pane
        
        // Create a panel for the right column and add the split pane to it
        JPanel rightColumn = new JPanel(new GridLayout(1, 1));
        rightColumn.add(splitPane);
        
        // Add left and right columns to the frame
        add(leftColumn, BorderLayout.CENTER);
        add(rightColumn, BorderLayout.EAST);
        
        // Create a menu bar
        JMenu jmHelp = new JMenu("Help");
        JMenuItem jmiWelcome = new JMenuItem("Intro");
        JMenuItem jmiMeasure = new JMenuItem("Measure");
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String helpTopic = e.getActionCommand();
                // If "Intro" is clicked, switch to the "Welcome" tab
                if (helpTopic.equals("Intro")) {
                    pane.setSelectedComponent(welcomePanel);
                }
                // If "Measure" is clicked, switch to the "Measure" tab
                else if (helpTopic.equals("Measure")) {
                    // Add code to switch to the "Measure" tab if available
                    // pane.setSelectedComponent(measurePanel);
                }
            }
        };
        
        // Add action listeners to menu items
        jmiWelcome.addActionListener(al);
        jmiMeasure.addActionListener(al);
        
        // Add menu items to the help menu
        jmHelp.add(jmiWelcome);
        jmHelp.add(jmiMeasure);
        
        // Create a menu bar and add the help menu to it
        JMenuBar mb = new JMenuBar();
        mb.add(jmHelp);
        this.setJMenuBar(mb); // Set the menu bar for the frame
        
        setVisible(true); // Make the frame visible
        pack(); // Adjust the size of the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Set default close operation
    }
    
    // Main method to start the application
    public static void main(String[] args) {
        startCSViewer();
    }
    
    // Method to start the CSV Viewer application
    public static void startCSViewer() {
        new CSVWindowDriver(); // Create an instance of the main driver class
    }
}
