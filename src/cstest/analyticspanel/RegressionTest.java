package cstest.analyticspanel;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Random;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @see https://stackoverflow.com/a/37716411/230513
 * @see http://stackoverflow.com/a/37716411/230513
 */
public class RegressionTest {

    private static final int N = 16;
    private static final Random R = new Random();

    private static XYDataset createDataset() {
        XYSeries series = new XYSeries("Data");
        for (int i = 0; i < N; i++) {
            series.add(i, R.nextGaussian() + i);
        }
        XYSeriesCollection xyData = new XYSeriesCollection(series);
        double[] coefficients = Regression.getOLSRegression(xyData, 0);
        double b = coefficients[0]; // intercept
        double m = coefficients[1]; // slope       
        XYSeries linear = new XYSeries("Linear");
        double x0 = series.getDataItem(0).getXValue();
        linear.add(x0, m * x0 + b);
        x0 = series.getDataItem(series.getItemCount() - 1).getXValue();
        linear.add(x0, m * x0 + b);
        double[] coefs = Regression.getPolynomialRegression(xyData, 0, 3);
        
        XYSeries trend = new XYSeries("Polynomial");
        XYSeries deviation = new XYSeries("Deviation");
        double x, y;
        for (int i=0; i<N; i++) {
        	x = series.getDataItem(i).getXValue();
        	y = coefs[0] + coefs[1]*x + coefs[2]*x*x + coefs[3]*x*x*x;
        	trend.add(x, y);
        	deviation.add(x, series.getDataItem(i).getYValue());
        	deviation.add(x, y);
        }
        System.out.println(series.getItemCount() + "==>" + deviation.getItemCount());
        xyData.addSeries(linear);
        xyData.addSeries(trend);
        xyData.addSeries(deviation);
        return xyData;
    }

    private static JFreeChart createChart(final XYDataset dataset) {
        JFreeChart chart = ChartFactory.createScatterPlot("Test", "X", "Y",
            dataset, PlotOrientation.VERTICAL, true, false, false);
        XYPlot plot = chart.getXYPlot();
        //XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        XYLineAndShapeRenderer r = new CustomLineRenderertest(true,false);
		plot.setRenderer(r);
        r.setSeriesLinesVisible(0, Boolean.FALSE);
        r.setSeriesShapesVisible(0, Boolean.TRUE);
        r.setSeriesLinesVisible(1, Boolean.TRUE);
        r.setSeriesShapesVisible(1, Boolean.FALSE);
        r.setSeriesLinesVisible(2, Boolean.TRUE);
        r.setSeriesShapesVisible(2, Boolean.FALSE);
        r.setSeriesLinesVisible(3, Boolean.TRUE);
        r.setSeriesShapesVisible(3, Boolean.FALSE);
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
                ChartPanel chartPanel = new ChartPanel(chart) {
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