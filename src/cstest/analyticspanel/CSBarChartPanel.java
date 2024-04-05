package cstest.analyticspanel;

import java.io.IOException;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import joinery.DataFrame;
import mgr.measure.MeasureDataAccessor;
import mgr.measure.MeasureDataOrganizer;

public class CSBarChartPanel extends JFrame {
	private String[] catMeasures = {"Coronal suture",
			"Sagittal suture","Lamboidal suture"};
	private int[][] catData;
	private int[][] catFreq;
	
	public CSBarChartPanel(String title) {
		super(title);
		
    	prepareCatData(catMeasures);
    	
        JFreeChart barChart = ChartFactory.createBarChart(
                title,           
                "Category",            
                "Frequence",            
                createDataset(),          
                PlotOrientation.VERTICAL,           
                true, true, false);
                
             ChartPanel chartPanel = new ChartPanel( barChart );        
             chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
             this.setContentPane( chartPanel ); 
    	
	}

	private void prepareCatData(String[] catMeasures) {
   		MeasureDataOrganizer org = new MeasureDataOrganizer(
   				new MeasureDataAccessor());
	
		DataFrame<Object> df = org.getMeasureForKeys(catMeasures).dropna();
		System.out.println(df.columns());
		System.out.println(df.length());
		
		catData = new int[df.length()][catMeasures.length];
		catFreq = new int[6][catMeasures.length];
		
		int v;
		for (int r=0; r<df.length(); r++) {
			//System.out.print(df.get(r, 0).toString());
			for (int c=0; c<3; c++) {
				v = Integer.parseInt(df.get(r, c+1).toString());
				catData[r][c] = v; 
				catFreq[v][c]++;
				//System.out.print("\t" + v);
			}
			//System.out.println();
		}
		
		for (int c=0; c<catMeasures.length; c++) {
			System.out.print("\t" + catMeasures[c]);
		}
		System.out.println();

		for (int r=0; r<catFreq.length; r++) {
			System.out.print(r);
			for (int c=0; c<catMeasures.length; c++) 
				System.out.print("\t" + catFreq[r][c]);
			System.out.println();
		}
	}
	
	private CategoryDataset createDataset( ) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (int r=0; r<catFreq.length; r++) 
			for (int c=0; c<catMeasures.length; c++)
				dataset.setValue(catFreq[r][c], ""+r, catMeasures[c]);

		return dataset;
	}
	
    public static void main(String[] args) throws IOException {
        CSBarChartPanel demo = new CSBarChartPanel("CatBarChart Test");
        demo.pack();
        demo.setLocationRelativeTo(null);;
        demo.setVisible(true);
        /*
        */
    }
}
