package mgr;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import gui.MainContentPaneWithTabs;
import joinery.DataFrame;
import joinery.DataFrame.JoinType;
import mgr.measure.BoneMeasureMgr;
import mgr.measure.MeasureDataAccessor;
import mgr.measure.MeasureDataOrganizer;
import mgr.search.AnimalFamilyInfoAccessor;
import mgr.search.AnimalFamilyOrganizer;
import mgr.search.SearchMgr;

public class AnalyticsMgr {
	public static int EAAD = 0;
	public static int X_IS_1 = 1;
	
	//private AnimalFamilyOrganizer afOrg;
	//private MeasureDataOrganizer mdOrg;
	private SearchMgr sMgr;
	private BoneMeasureMgr bmMgr;
	private int xyPlotMode = EAAD;
	
	public AnalyticsMgr(SearchMgr sMgr, BoneMeasureMgr bmMgr) {
		//AnimalFamilyInfoAccessor afi = new AnimalFamilyInfoAccessor();
		//afOrg = new AnimalFamilyOrganizer(afi);
		//mdOrg = new MeasureDataOrganizer(new MeasureDataAccessor());
		this.bmMgr = bmMgr;
		this.sMgr = sMgr;
	}

	public void setXyPlotMode(int mode) {
		this.xyPlotMode  = mode;
	}

	public void buildXyPlotTab(MainContentPaneWithTabs tabbedPane) {
		// TODO Auto-generated method stub
		DataFrame<Object> measures = bmMgr.selectMeasures();
		DataFrame<Object> animals = sMgr.selectAnimals();
		DataFrame<Object> dataset = animals.joinOn(measures, JoinType.INNER, 0);
		System.out.println(dataset.columns() + " (" + dataset.length() + ")");
		
		int len = bmMgr.getSelectedKeys().length + 2;
		String[] colsToInclude = new String[len];
		System.arraycopy(bmMgr.getSelectedKeys(), 0, colsToInclude, 0, 
				bmMgr.getSelectedKeys().length);
		colsToInclude[len-2] = "CS_Tattoo";
		colsToInclude[len-1] = "EAAD";
		dataset = dataset.retain(colsToInclude);
		System.out.println(dataset.columns() + " (" + dataset.length() + ")");
		dataset = dataset.dropna();
		
		addXyPlotAsTab(dataset, tabbedPane);
	}
	
	private void addXyPlotAsTab(DataFrame<Object> df, MainContentPaneWithTabs tabbedPane) {
	    JFreeChart chart;
	    XYDataset dataset;
	    String title, xCaption;
	    
	    if (this.xyPlotMode == EAAD){
	    	dataset = createDatasetForEAAD(df);  
	    	title = "Estimated Age At Death";
	    	xCaption = "EAAD";
	    }
	    else {
	    	dataset = createDatasetForFirstAsX(df);
	    	xCaption = bmMgr.getSelectedKeys()[0];
	    	title = xCaption;
	    }

	    chart = ChartFactory.createScatterPlot("Measure vs. " + title,   
	            xCaption, "Measure", dataset);  

        //Changes background color  
        XYPlot plot = (XYPlot)chart.getPlot();  
        plot.setBackgroundPaint(Color.LIGHT_GRAY);  		          
        //plot.setBackgroundPaint(new Color(255,228,196));  		          
         
        // Create Panel  
        ChartPanel panel = new ChartPanel(chart);  
		tabbedPane.addTab("XY Plot", panel);
	}

	private XYDataset createDatasetForFirstAsX(DataFrame<Object> df) {
	    XYSeriesCollection dataset = new XYSeriesCollection();  
	    Set<Object> columnLabels = df.columns();
	    Map<String, Integer> keyToIndex = new HashMap<>();
	    int index=0;
	    for (Object key : columnLabels) {
	    	keyToIndex.put(key.toString(), index++);
	    	System.out.println(index + "::" + key.toString());
	    }
	    
	    int indexOfX = keyToIndex.get(bmMgr.getSelectedKeys()[0]);
	    System.out.println(xyPlotMode + "==>" + indexOfX);
	    
	    String var = "Measure";
	    XYSeries series1;
	    for (String s : bmMgr.getSelectedKeys()) {
	    	if (s.equals(bmMgr.getSelectedKeys()[0]))
	    		continue;
	    	var = s;
	    // Measure (EAAD,Measure) series  
		    series1 = new XYSeries(var);  
		    index = keyToIndex.get(var);
		    double x, y;
		    for (int i = 0; i<df.length(); i++) {
		    	x = (Double)(df.get(i, indexOfX));
		    	y = (Double)(df.get(i, index));
		    	System.out.println(i + ": (" + x + ", " + y + ")");
		    	series1.add(x, y);
		    	//series1.add((Double)(dfCase2.get(i, "EAAD")), (Double)(dfCase2.get(i,  "F3")));
		    }
	    
		    dataset.addSeries(series1);
	    }
	    
		return dataset;
	}

	private XYDataset createDatasetForEAAD(DataFrame<Object> df) {
	    XYSeriesCollection dataset = new XYSeriesCollection();  
	    Set<Object> columnLabels = df.columns();
	    Map<String, Integer> keyToIndex = new HashMap<>();
	    int index=0;
	    for (Object key : columnLabels) {
	    	keyToIndex.put(key.toString(), index++);
	    	System.out.println(index + "::" + key.toString());
	    }
	    
	    int indexOfX = keyToIndex.get("EAAD");
	    if (xyPlotMode == 1)
	    	indexOfX = keyToIndex.get(bmMgr.getSelectedKeys()[0]);
	    System.out.println(xyPlotMode + "==>" + indexOfX);
	    
	    String var = "Measure";
	    XYSeries series1;
	    for (Object c : columnLabels) {
	    	if (c.toString().equals("EAAD") || c.toString().equals("CS_Tattoo"))
	    		continue;
	    	var = c.toString();
	    // Measure (EAAD,Measure) series  
		    series1 = new XYSeries(var);  
		    index = keyToIndex.get(var);
		    double x, y;
		    for (int i = 0; i<df.length(); i++) {
		    	x = (Double)(df.get(i, indexOfX));
		    	y = (Double)(df.get(i, index));
		    	System.out.println(i + ": (" + x + ", " + y + ")");
		    	series1.add(x, y);
		    	//series1.add((Double)(dfCase2.get(i, "EAAD")), (Double)(dfCase2.get(i,  "F3")));
		    }
	    
		    dataset.addSeries(series1);
	    }
	    
		return dataset;
	}

	public void testXyPlotTab(MainContentPaneWithTabs tabbedPane) {
		// TODO Auto-generated method stub
		DataFrame<Object> other = bmMgr.selectMeasures();
		System.out.println(other.columns());
		//printDf(other);
		//other = other.dropna();
		DataFrame<Object> selected = sMgr.selectAnimals();
		System.out.println(selected.columns());
		DataFrame<Object> dataset = selected.joinOn(other, JoinType.INNER, 0);
		System.out.println(dataset.columns());
		
		// test with the first selected measure key for now
		dataset = dataset.retain("CS_Tattoo", "EAAD", bmMgr.getSelectedKeys()[0]);
		dataset = dataset.dropna();
		System.out.println(dataset.columns());
		System.out.println(dataset.length());
		addXyPlotTab(dataset, tabbedPane);
	}

	private void printDf(DataFrame<Object> other) {
		System.out.println(other.columns());
		for (int i=0; i<other.length(); i++)
			System.out.println(other.row(i));
	}

	private void addXyPlotTab(DataFrame<Object> df, 
			MainContentPaneWithTabs tabbedPane) {
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
	    // Measure (EAAD,Measure) series  
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
