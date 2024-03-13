package dftest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import joinery.DataFrame;
import joinery.DataFrame.JoinType;
import mgr.measure.BoneMeasureMgr;
import mgr.measure.MeasureDataAccessor;
import mgr.measure.MeasureDataOrganizer;
import mgr.search.AnimalFamilyInfoAccessor;
import mgr.search.AnimalFamilyOrganizer;

public class SelectionDriver {
	public static void main(String[] args) {
		testJoin();
		
		//testMultiFamilySelection();
	}
	
	private static void testMultiFamilySelection() {
		AnimalFamilyOrganizer mgr = new AnimalFamilyOrganizer();
		
		String[] mcodes = {"Q1012", "Q1022"};
		DataFrame<Object> df1 = mgr.getAnimalsByFamily(mcodes);
		System.out.println(df1.columns() + " @" + df1.length());
		
		String[] meas = {"Gl-Op", "Go-Go", "Po-Go"};
		BoneMeasureMgr bMgr = new BoneMeasureMgr();
		bMgr.setSelectedKeys(meas);
		DataFrame<Object> df2 = bMgr.selectMeasures();
		System.out.println(df2.columns() + " @" + df2.length());
		
		DataFrame<Object> df = df2.joinOn(df1, JoinType.INNER, 0);
		System.out.println(df.columns() + " @" + df.length());
	}

	private static void testJoin() {
		AnimalFamilyInfoAccessor afi = new AnimalFamilyInfoAccessor();
		
		System.out.println(afi.getAfiDataframe().columns());
		System.out.println(afi.getAfiDataframe().length());
		System.out.println(afi.getAfiDataframe().col("Matril").size());
		System.out.println(afi.getAfiDataframe().row(10));
		
		AnimalFamilyOrganizer mgr = new AnimalFamilyOrganizer(afi);
		
		String founderId = "Q1068";
		DataFrame<Object> selected = mgr.getAnimalsByFamily(founderId);
		System.out.println(selected.length());
		System.out.println(selected.col("AnimalID").size());
		System.out.println(selected.col("AnimalID"));
		
		founderId = "Q1022";
		DataFrame<Object> selected22 = mgr.getAnimalsByFamily(founderId);
		System.out.println(selected22.length());
		for (int i=0; i<selected22.length(); i++)
			selected.append(selected22.row(i));
		System.out.println(selected.length());
		
		String sex = "m";
		DataFrame<Object> selectedMale = mgr.getAnimalsByGender(selected, sex);
		System.out.println(selectedMale.length());		
		
		selected = mgr.getAnimalsByBSeason(2011);
		System.out.println("Animal born in season 2011: " + selected.length());
		
		selected = mgr.getAnimalsByBSeasonRange(1960, 1963);
		System.out.println("Animal born between 1960 and 1963: " + selected.length());
		
		System.out.println("Cols: " + 
		selected.retain("AnimalID", "Matril", "BSeason").size());
		
		//serializeTest(selected);

		MeasureDataOrganizer org = new MeasureDataOrganizer(new MeasureDataAccessor());
		
		System.out.println(org.getMeasureWithEAAD("Go-Go").columns());
		System.out.println(org.getMeasureWithEAAD("Go-Go").dropna().length());
		
		System.out.println(org.getMeasureForKeys("Go-Go", "Ba-Br", "Eu-Eu", "Gl-Op").columns());
		System.out.println(org.getMeasureForKeys("Go-Go", "Ba-Br", "Eu-Eu", "Gl-Op").dropna().length());

		String[] keys = {"Go-Go", "Ba-Br", "Eu-Eu", "Gl-Op"};
		System.out.println(org.getMeasureForKeys(keys).columns());
		System.out.println("fd.retain(String[]) next...");
		System.out.println(org.getMeasureForKeys(keys).retain(keys).columns());

		System.out.println(selected.columns() + " @" + selected.length());

		DataFrame<Object> other = org.getMeasureWithEAAD("Go-Go").dropna();
		System.out.println(other.columns() + " @" + other.length());
		System.out.println(selected.joinOn(other, JoinType.INNER, 0).length());
	}

	/*
	private static void serializeTest(DataFrame<Object> selected) {
		DfWrapper<Object> wrapper = new DfWrapperBuilder<Object>().buildWrapper(selected);
        try {
	        FileOutputStream fileOut = new FileOutputStream("data/afiDf.ser");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(wrapper);
	        out.close();
	        fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("Serialized data is saved in data/afiDf.ser");
	}
	*/
}
