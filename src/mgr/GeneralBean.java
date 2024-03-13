package mgr;

public class GeneralBean 
{
	private String[] generalInfo;
	
	public GeneralBean(AnimalFamilyInfo info)
	{
		generalInfo = new String[10];
		
		generalInfo[0] = info.subjId;
		generalInfo[1] = info.momId;
		generalInfo[2] = info.dadId;
		generalInfo[3] = info.sex;
		generalInfo[4] = info.founderId;
		generalInfo[5] = Integer.toString(info.pedigree);
		generalInfo[6] = Integer.toString(info.siblingNo);
		generalInfo[7] = info.dob;
		generalInfo[8] = info.bGroup;
		generalInfo[9] = info.unicode;
	}
	
	public String[] getGeneralInfo()
	{
		return generalInfo;
	}
}
