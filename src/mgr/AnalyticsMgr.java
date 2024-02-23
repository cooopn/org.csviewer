package mgr;

import java.awt.Color;
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
import measure.BoneMeasureMgr;
import measure.MeasureDataAccessor;
import measure.MeasureDataOrganizer;
import search.AnimalFamilyInfoAccessor;
import search.AnimalFamilyOrganizer;
import search.SearchMgr;

public class AnalyticsMgr {
	//private AnimalFamilyOrganizer afOrg;
	//private MeasureDataOrganizer mdOrg;
	private SearchMgr sMgr;
	private BoneMeasureMgr bmMgr;
	
	public AnalyticsMgr(SearchMgr sMgr, BoneMeasureMgr bmMgr) {
		//AnimalFamilyInfoAccessor afi = new AnimalFamilyInfoAccessor();
		//afOrg = new AnimalFamilyOrganizer(afi);
		//mdOrg = new MeasureDataOrganizer(new MeasureDataAccessor());
		this.bmMgr = bmMgr;
		this.sMgr = sMgr;
	}

	public void testXyPlotTab(MainContentPaneWithTabs tabbedPane) {
		// TODO Auto-generated method stub
		DataFrame<Object> other = bmMgr.selectMeasures().dropna();
		DataFrame<Object> selected = sMgr.selectAnimals();
		DataFrame<Object> dataset = selected.joinOn(other, JoinType.INNER, 0);
		System.out.println(dataset.columns());
		
		// test with the first selected measure key for now
		dataset = dataset.retain("CS_Tattoo", "EAAD", bmMgr.getSelectedKeys()[0]);
		dataset.dropna();
		System.out.println(dataset.columns());
		System.out.println(dataset.length());
		addXyPlotTab(dataset, tabbedPane);
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
