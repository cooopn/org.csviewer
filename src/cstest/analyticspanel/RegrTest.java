package cstest.analyticspanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Paint;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RegrTest {

	private static final int N = 16;
	private static final Random R = new Random();

	private static XYDataset createDataset() {
		XYSeries series = new XYSeries("Data");
		for (int i = 0; i < N; i++) {
			series.add(i, R.nextGaussian() + i);
		}
		XYSeriesCollection xyData = new XYSeriesCollection(series);
		/*//count regression data------------------ as requirement
        double[] coefficients = Regression.getOLSRegression(xyData, 0);
        double b = coefficients[0]; // intercept
        double m = coefficients[1]; // slope

        double x = series.getDataItem(0).getXValue();
        System.out.println("x:"+x+"m:"+m+"b:"+b+"ans:"+ m * x + b);
        trend.add(x, m * x + b);
        x = series.getDataItem(series.getItemCount() - 1).getXValue();
        System.out.println("x:"+x+"m:"+m+"b:"+b+"ans:"+ m * x + b);
        trend.add(x, m * x + b);

		 */
		XYSeries trend = new XYSeries("Trend");
		//add here regressiondata
		//here dummydata added
		//trend.add(0, 0);//1 st regression line startpoint
		trend.add(1, 2);//1 st regression line startpoint
		trend.add(3,4);//1 st regression line endpoint
		
		trend.add(5,6);//2  regression line startpoint
		trend.add(7,9);//2 regression line endpoint
		
		trend.add(7.5,8.5);//3  regression line startpoint
		trend.add(10,7);//3 regression line endpoint
		
		trend.add(9,8);//4  regression line startpoint
		trend.add(12,12);//4 regression line endpoint
		
		xyData.addSeries(trend);

		return xyData;
	}

	private static JFreeChart createChart(final XYDataset dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart("Test", "X", "Y",
				dataset, PlotOrientation.VERTICAL, true, false, false);
		return chart;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame f = new JFrame();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				XYDataset dataset = createDataset();
				JFreeChart chart = createChart(dataset);
				XYPlot plot=chart.getXYPlot();
				XYItemRenderer renderer = new CustomLineRenderertest(true,false);
				plot.setRenderer(renderer);
				ChartPanel chartPanel = new ChartPanel(chart) {
					
					private static final long serialVersionUID = 1L;

					@Override
					public Dimension getPreferredSize() {
						return new Dimension(640, 480);
					}
				};
				f.add(chartPanel);
				f.pack();
				f.setLocationRelativeTo(null);
				f.setVisible(true);
			}
		});
	}
}

class CustomLineRenderertest extends XYLineAndShapeRenderer {
	/**
	 * for add study line chart red green
	 */
	private static final long serialVersionUID = 1L;

	public CustomLineRenderertest(boolean lines, boolean shapes){
		super(lines, shapes);
	}

	@Override
	public Paint getItemPaint(int row, int column) {
		if(row>2){
			if(column%2==0){
				return new Color(0, 0,0, 0);
			}else{
				return Color.YELLOW;
			}

		}
		else if (row == 0)
			return Color.RED;
		else  if (row == 1)
			return Color.BLUE;
		else 
			return Color.GREEN;
	}
}