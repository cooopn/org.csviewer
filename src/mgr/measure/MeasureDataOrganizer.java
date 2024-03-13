package mgr.measure;

import java.util.ArrayList;
import java.util.List;

import joinery.DataFrame;
import joinery.DataFrame.Predicate;
import mgr.search.AnimalFamilyInfoAccessor;

public class MeasureDataOrganizer {
	private MeasureDataAccessor accessor;
	private List<String> measureKeys;
	
	public MeasureDataOrganizer(MeasureDataAccessor accessor) {
		this.accessor = accessor;
	}
	
	public MeasureDataOrganizer() {
		this(new MeasureDataAccessor());
	}

	public DataFrame<Object> getMeasureWithEAAD(String measureKey) {
		//int index = getIndexForKey(measureKey);
		
		return accessor.getMeasureDataframe().retain("CS_Tattoo", "EAAD", measureKey);
	}
	
	public DataFrame<Object> getMeasureWithEAAD(String[] measureKeys) {
		DataFrame<Object> df = accessor.getMeasureDataframe().retain("CS_Tattoo", "EAAD");
		for (String k : measureKeys)
			df.add(k, accessor.getMeasureDataframe().col(k));
		
		return df;
	}
	
	public DataFrame<Object> getMeasureForKeys(String ... keys) {
		DataFrame<Object> df = accessor.getMeasureDataframe().retain("CS_Tattoo");
		String[] keyList = keys;
		for (String k : keyList)
			df.add(k, accessor.getMeasureDataframe().col(k));
		
		return df;
	}
	
	public int getIndexForKey(String measureKey) {
		if (measureKeys == null)
			measureKeys = new ArrayList(accessor.getMeasureDataframe().columns());
		
		return measureKeys.indexOf(measureKey);
	}

	public static void main(String[] args) {
	}

	public String[] getSelectedKeys() {
		// TODO Auto-generated method stub
		return this.measureKeys.toArray(new String[measureKeys.size()]);
	}

}
