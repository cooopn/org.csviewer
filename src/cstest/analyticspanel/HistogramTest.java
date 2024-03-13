package cstest.analyticspanel;

import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;

import joinery.DataFrame;
import mgr.measure.MeasureDataAccessor;
import mgr.measure.MeasureDataOrganizer;

/**
 * A demo of the {@link HistogramDataset} class.
 * 
 * @author Jelai Wang, jelaiw AT mindspring.com
 */
public class HistogramTest extends JFrame {
	private double[] data;
	double min = 100, max = 0;

    /**
     * Creates a new demo.
     * 
     * @param title  the frame title.
     */
    public HistogramTest(String title) {
        super(title);    
        IntervalXYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setMouseZoomable(true, false);
        setContentPane(chartPanel);
    }
    
    public void setData(double[] data) {
    	this.data = data;
    }
    
    public void generateData() {
    	MeasureDataOrganizer org = new MeasureDataOrganizer(new MeasureDataAccessor());
		
		DataFrame<Object> df = org.getMeasureWithEAAD("Go-Go").dropna();
		System.out.println(df.columns());
		
		data = new double[df.length()];
		for (int i=0; i<df.length(); i++) {
			data[i] = Double.parseDouble(df.get(i, 1).toString());
			if (data[i] > max)
				max = data[i];
			if (data[i] < min)
				min = data[i];
		}
    }
    
    /**
     * Creates a sample {@link HistogramDataset}.
     * 
     * @return The dataset.
     */
    public IntervalXYDataset createDataset() {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.FREQUENCY);
        generateData();
        //double[] data = {23057.5,22948.30078,22948.30078,22948.30078,22620.40039,22511.09961,22511.09961,22401.90039,22401.90039};
        //dataset.addSeries("H1", data, data.length, 22100.0, 23100.0); 
        dataset.addSeries("H1", data, 10, min, max); 
        return dataset;     
    }
        
    /**
     * Creates a chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    public JFreeChart createChart(IntervalXYDataset dataset) {
        JFreeChart chart = ChartFactory.createHistogram(
            "Histogram Demo", 
            null, 
            null, 
            dataset, 
            PlotOrientation.VERTICAL, 
            true, 
            false, 
            false
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setForegroundAlpha(0.75f);
        NumberAxis axis = (NumberAxis) plot.getDomainAxis();
        axis.setAutoRangeIncludesZero(false);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
        
        return chart;
    }
    
    /**
     * The starting point for the demo.
     * 
     * @param args  ignored.
     * 
     * @throws IOException  if there is a problem saving the file.
     */
    public static void main(String[] args) throws IOException {
        
        HistogramTest demo = new HistogramTest("Histogram Test");
        demo.pack();
        demo.setLocationRelativeTo(null);;
        demo.setVisible(true);
        
    }

}