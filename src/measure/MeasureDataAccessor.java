package measure;

import java.io.IOException;

import joinery.DataFrame;

public class MeasureDataAccessor {
	private String fileName = "data/CleanedMeasureData.csv";
	
	private DataFrame<Object> measureDataframe; // = new DataFrame();
	
	public MeasureDataAccessor() {
		try {
			this.measureDataframe = DataFrame.readCsv(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DataFrame<Object> getMeasureDataframe() {
		return this.measureDataframe;
	}
}
