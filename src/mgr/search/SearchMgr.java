package mgr.search;

import joinery.DataFrame;

public class SearchMgr {
	private DataFrame<Object> selectedAnimalSet;
	private AnimalFamilyOrganizer afOrg;
	
	private String[] familiesSelected; 
	//private birthSeasonRange;
	
	public SearchMgr() {
		afOrg = new AnimalFamilyOrganizer();		
	}
	
	public DataFrame<Object> selectAnimalByFounderIds(String[] founderCodes) {
		selectedAnimalSet = afOrg.getAnimalsByFamily(founderCodes);
		
		return selectedAnimalSet;
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
	
	public DataFrame<Object> getSelectedAnimalSet() {
		return selectedAnimalSet;
	}
}
