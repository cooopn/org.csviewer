package mgr.search;

import java.util.List;

import joinery.DataFrame;
import joinery.DataFrame.Predicate;

public class AnimalFamilyOrganizer {
	private AnimalFamilyInfoAccessor accessor;
	private AnimalFamilyInfoAccessor defaultAccessor = 
			new AnimalFamilyInfoAccessor();
	
	// prepared for creating accessor using a different file/source
	public AnimalFamilyOrganizer(AnimalFamilyInfoAccessor accessor) {
		this.accessor = accessor;
	}
	
	public AnimalFamilyOrganizer() {
		this.accessor = defaultAccessor;
	}
	
	public DataFrame<Object> getAllAnimals() {
		return accessor.getAfiDataframe();
	}

	public DataFrame<Object> getAnimalsByFamily(DataFrame<Object> df, 
			String founderId) {
		Predicate<Object> p = new Predicate<Object>() {
	          @Override
	          public Boolean apply(List<Object> values) {
	        	  //String matril = values.get("Matril").toString();
	        	  String matril = values.get(6).toString();
	              return matril.equalsIgnoreCase(founderId);
	          }
		};
		
		return df.select(p);
	}
	
	public DataFrame<Object> getAnimalsByFamily(String founderId) {		
		return getAnimalsByFamily(accessor.getAfiDataframe(), founderId);	
	}
	
	public DataFrame<Object> getAnimalsByGender(DataFrame<Object> df, 
			String gender) {
		Predicate<Object> p = new Predicate<Object>() {
	          @Override
	          public Boolean apply(List<Object> values) {
	        	  //String matril = values.get("Matril").toString();
	        	  //System.out.print(values.get(0) + " is " + values.get(5));
	        	  String sex = values.get(5).toString();
	        	  //System.out.print(" ==> " + values.get(5));
	              return sex.equalsIgnoreCase(gender);
	          }
		};
		
		return df.select(p);	
	}
	
	public DataFrame<Object> getAnimalsByGender(String gender) {
		return getAnimalsByGender(accessor.getAfiDataframe(), gender);	
	}
	
	public DataFrame<Object> getAnimalsByBSeason(DataFrame<Object> df, 
			int bSeason) {
		Predicate<Object> p = new Predicate<Object>() {
	          @Override
	          public Boolean apply(List<Object> values) {
	        	  //String matril = values.get("Matril").toString();
	        	  //System.out.print(values.get(0) + " is " + values.get(5));
	        	  int year = Integer.parseInt(values.get(4).toString());
	        	  //System.out.print(" ==> " + values.get(5));
	              return year == bSeason;
	          }
		};
		
		return df.select(p);
	}
		
	public DataFrame<Object> getAnimalsByBSeason(int bSeason) {
		return getAnimalsByBSeason(accessor.getAfiDataframe(), bSeason);	
	}
	
	public DataFrame<Object> getAnimalsByBSeasonRange(DataFrame<Object> df,
			int beginSeason, int endSeason) {
		Predicate<Object> p = new Predicate<Object>() {
	          @Override
	          public Boolean apply(List<Object> values) {
	        	  //String matril = values.get("Matril").toString();
	        	  //System.out.print(values.get(0) + " is " + values.get(5));
	        	  int year = Integer.parseInt(values.get(4).toString());
	        	  //System.out.print(" ==> " + values.get(5));
	              return year >= beginSeason && year <= endSeason;
	          }
		};
		
		return df.select(p);
	}
		
	public DataFrame<Object> getAnimalsByBSeasonRange(int beginSeason, 
			int endSeason) {
		return getAnimalsByBSeasonRange(accessor.getAfiDataframe(), 
				beginSeason, endSeason);	
	}

	public DataFrame<Object> getAnimalsByFamily(String[] founderCodes) {
		/*
		DataFrame<Object> selected = null;
		
		System.out.println("# of codes = " + founderCodes.length);
		if (founderCodes != null && founderCodes.length > 0) {
			selected = getAnimalsByFamily(founderCodes[0]);
			System.out.println("__initial size = " + selected.length());
		}
		
		DataFrame<Object> temp;
		for (int i=1; i < founderCodes.length; i++) {
			temp = getAnimalsByFamily(founderCodes[i]);
			for (int row = 0; row < temp.length(); row++) {
				System.out.println("____row #" + row + ": " + temp.row(row));
				selected.append(temp.row(row));
			}
			System.out.println("__set #" + i + " size = " + 
					getAnimalsByFamily(founderCodes[i]).length());
		}
		
		return selected;
		*/
		
		Predicate<Object> p = new Predicate<Object>() {
	          @Override
	          public Boolean apply(List<Object> values) {
	        	  //String matril = values.get("Matril").toString();
	        	  String matril = values.get(6).toString();
	              return stringInArray(matril, founderCodes);
	          }

			private boolean stringInArray(String matril, String[] founderCodes) {
				boolean inArray = false;
				
				for (String s : founderCodes)
					if (s.equals(matril))
						return true;
					
				return inArray;
			}
		};
		
		return accessor.getAfiDataframe().select(p);

	}
}
