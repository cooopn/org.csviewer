package measure;

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
	
	public void setSelectedKeys(String[] selectedKeys) {
		this.selectedKeys = selectedKeys;
	}
}
