package search;

import joinery.DataFrame;

public class SearchMgr {
	private DataFrame<Object> selectedAnimalIds;
	private AnimalFamilyOrganizer afOrg;
	
	private String[] familiesSelected; 

	public SearchMgr() {
		afOrg = new AnimalFamilyOrganizer();		
	}
	
	public DataFrame<Object> selectAnimalByFounderIds(String[] founderCodes) {
		selectedAnimalIds = afOrg.getAnimalsByFamily(founderCodes);
		
		return selectedAnimalIds;
	}
	
	public void setFamiliesSelected(String[] familiesSelected) {
		this.familiesSelected = familiesSelected;
	}
	
	public DataFrame<Object> selectAnimals() {
		if (familiesSelected == null)
			return this.afOrg.getAllAnimals();
		else
			return afOrg.getAnimalsByFamily(familiesSelected);
	}
	
	public DataFrame<Object> selectAllAnimals() {
		return this.afOrg.getAllAnimals();
	}
}
