package dftest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import gui.AnimalSetSelectionPanel;
import gui.MainContentPaneWithTabs;
import joinery.DataFrame;
import joinery.DataFrame.JoinType;
import measure.MeasureDataAccessor;
import measure.MeasureDataOrganizer;
import search.AnimalFamilyInfoAccessor;
import search.AnimalFamilyOrganizer;

public class MainDriver {
	private AnimalFamilyOrganizer afOrg;
	private MeasureDataOrganizer mdOrg;
	
	public MainDriver() {
		AnimalFamilyInfoAccessor afi = new AnimalFamilyInfoAccessor();
		afOrg = new AnimalFamilyOrganizer(afi);
		mdOrg = new MeasureDataOrganizer(new MeasureDataAccessor());
	}

	public static void main(String args[]) {
		MainDriver md = new MainDriver();
		JFrame f = new JFrame("CSViewer Main Test");
		AnimalSetSelectionPanel asPanel = new AnimalSetSelectionPanel();
		MainContentPaneWithTabs tabbedPane = new MainContentPaneWithTabs();
		tabbedPane.addTab("Animal Selection", asPanel);
		f.add(tabbedPane);
		JButton b = new JButton("XY Plot");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				md.testXyPlotTab(tabbedPane);
			}
		});

		//f.pack();
		f.add(b, BorderLayout.SOUTH);
		f.setSize(920, 650);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void testXyPlotTab(MainContentPaneWithTabs tabbedPane) {
		// TODO Auto-generated method stub
		DataFrame<Object> other = this.mdOrg.getMeasureWithEAAD("Go-Go").dropna();
		DataFrame<Object> selected = this.afOrg.getAnimalsByBSeasonRange(1960, 1963);
		DataFrame<Object> dataset = selected.joinOn(other, JoinType.INNER, 0);
		System.out.println(dataset.columns());
		dataset = dataset.retain("CS_Tattoo", "EAAD", "Go-Go");
		dataset.dropna();
		System.out.println(dataset.columns());
		System.out.println(dataset.length());
		addXyPlotTab(dataset, tabbedPane);
	}

	private void addXyPlotTab(DataFrame<Object> df, MainContentPaneWithTabs tabbedPane) {
	    XYDataset dataset = createDataset(df);  

	    JFreeChart chart = ChartFactory.createScatterPlot(  
	            "Measure vs. Estimated Age At Death",   
	            "EAAD", "Measure", dataset);  

        //Changes background color  
        XYPlot plot = (XYPlot)chart.getPlot();  
        plot.setBackgroundPaint(new Color(255,228,196));  		          
         
        // Create Panel  
        ChartPanel panel = new ChartPanel(chart);  
		tabbedPane.addTab("XY Plot", panel);
	}

	private XYDataset createDataset(DataFrame<Object> df) {
		// only one data series is created
	    XYSeriesCollection dataset = new XYSeriesCollection();  
	    Set<Object> columnLabels = df.columns();
	    String var = "Measure";
	    for (Object c : columnLabels) {
	    	if (c.toString().equals("EAAD") || c.toString().equals("CS_Tattoo"))
	    		continue;
	    	var = c.toString();
	    }
	    //Boys (Age,weight) series  
	    XYSeries series1 = new XYSeries(var);  
	    double x, y;
	    for (int i = 0; i<df.length(); i++) {
	    	x = (Double)(df.get(i, 1));
	    	y = (Double)(df.get(i, 2));
	    	System.out.println(i + ": (" + x + ", " + y + ")");
	    	series1.add(x, y);
	    	//series1.add((Double)(dfCase2.get(i, "EAAD")), (Double)(dfCase2.get(i,  "F3")));
	    }
	    
	    dataset.addSeries(series1);
		return dataset;
	}

	public void testXyPlotTab(MainContentPaneWithTabs tabbedPane, 
			DataFrame<Object> selectAnimals, DataFrame<Object> selectMeasures) {
		System.out.println(selectAnimals.columns() + " @" + selectAnimals.length());
		System.out.println(selectMeasures.columns() + " @" + selectMeasures.length());
		
		selectMeasures = selectMeasures.dropna();
		System.out.println(selectMeasures.columns() + " @" + selectMeasures.length());
		DataFrame<Object> dataset = selectAnimals.joinOn(selectMeasures, 
				JoinType.INNER, 0);		
		System.out.println(dataset.columns());
		System.out.println(mdOrg.getSelectedKeys()[0]);
		//dataset = dataset.retain("CS_Tattoo", "EAAD", "Go-Go");
		dataset = dataset.retain("CS_Tattoo", "EAAD", mdOrg.getSelectedKeys()[0]);
		dataset.dropna();
		addXyPlotTab(dataset, tabbedPane);
	}
	
}
