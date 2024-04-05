package cstest.analyticspanel;

import java.util.List;
import java.util.ArrayList;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import joinery.DataFrame;

public class CSColumnChartDataGenerator {
	private String[] catMeasures ;
	private int[][] catData;
	private int[][] catFreq;
	private DataFrame<Object> df;

	public CSColumnChartDataGenerator(String[] catMeasures, 
			DataFrame<Object> df) {
		this.catMeasures = catMeasures;
		this.df = df;
		catFreq = new int[6][catMeasures.length];
		catData = new int[df.length()][catMeasures.length];
	}
	
	public CSColumnChartDataGenerator() {
	}
	
	public void prepareDataFor(String[] catMeasures, 
			DataFrame<Object> df) {
		this.catMeasures = catMeasures;
		this.df = df;
		catFreq = new int[6][catMeasures.length];
		catData = new int[df.length()][catMeasures.length];

		prepareData();
	}

	private void printDf(DataFrame<Object> other) {
		System.out.println(other.columns());
		for (int i=0; i<other.length(); i++)
			System.out.println(other.row(i));
	}

	public void prepareData() {
		printDf(df);
		
		int v;
		for (int r=0; r<df.length(); r++) {
			//System.out.print(df.get(r, 0).toString());
			for (int c=0; c<catMeasures.length; c++) {
				// +2 to offset CS_Tattoo, EAAD
				v = Integer.parseInt(df.get(r, c+2).toString());
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
	
	public CategoryDataset createDataset( ) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (int r=0; r<catFreq.length; r++) 
			for (int c=0; c<catMeasures.length; c++)
				dataset.setValue(catFreq[r][c], ""+r, catMeasures[c]);

		return dataset;
	}

	public void prepareDataFor(DataFrame<Object> df) {
		this.df = df;
		List<String> cats = new ArrayList<>();
		for (Object cat : df.columns())
			if (!cat.toString().equals("EAAD"))
				if (!cat.toString().equals("CS_Tattoo"))
					cats.add(cat.toString());
		
		System.out.println("Cats w/o EAAD and CS_Tattoo => " + cats);
		catMeasures = cats.toArray(new String[cats.size()]);
		catFreq = new int[6][catMeasures.length];
		catData = new int[df.length()][catMeasures.length];

		prepareData();
	}
}
