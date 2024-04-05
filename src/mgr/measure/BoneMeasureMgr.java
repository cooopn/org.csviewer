package mgr.measure;

import joinery.DataFrame;

public class BoneMeasureMgr {
	private MeasureDataOrganizer mdOrg;
	
	private String[] selectedKeys;
	
	public BoneMeasureMgr() {
		mdOrg = new MeasureDataOrganizer();
	}
	
	public DataFrame<Object> selectMeasures() {
		return mdOrg.getMeasureWithEAAD(selectedKeys);
	}
	
	public String[] getSelectedKeys() {
		return selectedKeys;
	}
	
	public void setSelectedKeys(String[] selectedKeys) {
		this.selectedKeys = selectedKeys;
	}
	
	public DataFrame<Object> getMeasureByTattoo(String tattoo) {
		return mdOrg.getMeasureByTattoo(tattoo);
	}
	
	public boolean hasMeasureForTattoo(String tattoo) {
		return mdOrg.getMeasureByTattoo(tattoo).length() == 1;
	}
}
