package mgr.search;

import java.io.IOException;

import joinery.DataFrame;

public class AnimalFamilyInfoAccessor {
	private String fileName = "data/qAnimalFamilyInfoBSeason.csv";
	
	private DataFrame<Object> afiDataframe; // = new DataFrame();
	
	public AnimalFamilyInfoAccessor() {
		try {
			this.afiDataframe = DataFrame.readCsv(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DataFrame<Object> getAfiDataframe() {
		return this.afiDataframe;
	}
}
