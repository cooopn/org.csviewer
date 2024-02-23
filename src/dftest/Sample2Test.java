package dftest;

import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import joinery.DataFrame;
import joinery.DataFrame.Predicate;

public class Sample2Test {

	public static void main(String[] args) throws IOException {
		DataFrame<Object> df = DataFrame.readCsv("data/qAjpPaper1Case2.txt");	
		//System.out.println(df);
		
		Iterator<Object> it;
		  it = df.columns().iterator();
		while (it.hasNext())
			System.out.print(it.next() + "\t");
		System.out.println();
		
		DataFrame<Object> df1 = df.select(new Predicate<Object>() {
		          @Override
		          public Boolean apply(List<Object> values) {
		        	  String sex = values.get(1).toString();
		              return sex.equalsIgnoreCase("F");
		          }
		          
		      }).select(new Predicate<Object>() {
		          @Override
		          public Boolean apply(List<Object> values) {
		        	  int pedigree = Integer.parseInt(values.get(5).toString());
		        	  
		              return pedigree > 4;
		          }
		          
		      });//.sortBy("pedigree");
		
		System.out.println(df1.size());
		System.out.println(df1.length());
		/*
		  Iterator<List<Object>>  itrow = df1.iterrows();
		while (itrow.hasNext()) {
			for (Object o : itrow.next()) 
				System.out.print(o + "\t");
			System.out.println();			
		}
		 */
		
		DataFrame<Object> dfMeasure = DataFrame.readCsv("data/qGeorgeMeasureJun23ForQ1083F.csv");
		  it = dfMeasure.columns().iterator();
		while (it.hasNext())
			System.out.print(it.next() + "\t");
		System.out.println();
		System.out.println(dfMeasure.size());
		System.out.println(dfMeasure.length());
		System.out.println(dfMeasure.dropna().length());
		System.out.println(dfMeasure.dropna().col("F3"));
		
		DataFrame f3 = new DataFrame();
		f3.add("AnimalID", dfMeasure.col("AnimalID"));
		f3.add("EAAD", dfMeasure.col("EAAD"));
		f3.add("F3", dfMeasure.col("F3"));
		DataFrame f4 = f3.dropna();
		System.out.println(f4.index());
		System.out.println(f4.col("AnimalID"));
		
		System.out.println(f4.row(2)/*.get(2)*/);
		/*
		f4.sortBy(new java.util.Comparator<List<Double>>() {

			@Override
			public int compare(List<Double> o1, List<Double> o2) {
				int diff = (o1.get(0) - o2.get(0)>0)?1:(-1);
				return diff;
			}
		});
		 */
		//f4.sortBy("EAAD");
		f4.plot(DataFrame.PlotType.SCATTER);
		System.out.println(f4.col("EAAD"));
		
		System.out.println(f4.columns());
		System.out.println(df1.columns());
		DataFrame dfCase2 = df1.joinOn(f4, DataFrame.JoinType.INNER, 0);
		System.out.println(dfCase2.size());
		System.out.println(dfCase2.length());
		System.out.println(dfCase2.columns());
		
	    XYDataset dataset = createDataset(dfCase2);  

	    JFreeChart chart = ChartFactory.createScatterPlot(  
	            "Measure vs. Estimated Age At Death",   
	            "EAAD", "Measure", dataset);  
	      
	          
	        //Changes background color  
	        XYPlot plot = (XYPlot)chart.getPlot();  
	        plot.setBackgroundPaint(new Color(255,228,196));  
	          
	         
	        // Create Panel  
	        ChartPanel panel = new ChartPanel(chart);  
	        JFrame example = new JFrame("Test Frame");
	        example.setContentPane(panel);  
		
	        example.setSize(800, 400);  
	        example.setLocationRelativeTo(null);  
	        example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
	        example.setVisible(true);  
	}

	private static XYDataset createDataset(DataFrame dfCase2) {
	    XYSeriesCollection dataset = new XYSeriesCollection();  
	    
	    //Boys (Age,weight) series  
	    XYSeries series1 = new XYSeries("Measure");  
	    double x, y;
	    for (int i = 0; i<dfCase2.length(); i++) {
	    	x = (Double)(dfCase2.get(i, 7));
	    	y = (Double)(dfCase2.get(i, 8));
	    	System.out.println(i + ": (" + x + ", " + y + ")");
	    	series1.add(x, y);
	    	//series1.add((Double)(dfCase2.get(i, "EAAD")), (Double)(dfCase2.get(i,  "F3")));
	    }
	    
	    dataset.addSeries(series1);
		return dataset;
	}

}
