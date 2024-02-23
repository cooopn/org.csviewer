package mgr;

/**
 * Encapsulates data that describe a CS rhesus monkey.
 */
public class AnimalFamilyInfo {
	String subjId;
	String momId;
	String dadId;
	String sex;
	String founderId;
	int pedigree;
	int siblingNo;
	String dob;
	String bGroup;
	String unicode;

	/** Constructs an AnimalFamilyInfo object for a founder */
	public AnimalFamilyInfo(String founderId, String subjId) {
		this.subjId = subjId;
		this.founderId = founderId;
		sex = "f";
		pedigree = 1;
		unicode = founderId;
	}

	/** 
	 * Constructs an AnimalFamilyInfo object with values available 
	 * in the input file: 
	 *	data/qFounderMomChildrenSeq.csv
	 */
	public AnimalFamilyInfo(String subjId, String momId, String dadId, 
			String sex, String dob, String bGroup, String unicode) {
		this.subjId = subjId;
		this.momId = momId;
		this.dadId = dadId;
		this.sex = sex;
		this.dob = dob;
		this.bGroup = bGroup;
		this.unicode = unicode;
	}
	
	@Override
	public String toString() {
		String s = subjId;
		return  s + "," + momId + "," + dadId + "," + bGroup + "," + dob + 
				"," + sex + "," + founderId + "," + pedigree + 
				"," + siblingNo + "," + unicode;
	}

	/** Sets the value of founderId attribute to the given value. */	 
	public void setFounderId(String familyId) {
		this.founderId = familyId;
	}

	/** Sets the value of siblingNo attribute to the given value. */	 
	public void setSiblingNo(Integer integer) {
		this.siblingNo = integer;
		
	}

	/** Sets the value of pedigree attribute to the given value. */	 
	public void setPedigree(Integer integer) {
		this.pedigree = integer;
	}

	/** Returns the animalId of this animal */
	public String getAnimalId() {
		return subjId;
	}

	/** Returns the momId of this animal */
	public String getMomId() {
		return momId;
	}

	/** Returns the sex value of this animal */
	public String getGender() {
		return this.sex;
	}

	/** Returns the unicode of this animal */
	public String getUnicode() {
		return unicode;
	}
	
	/** Returns the dadId of this animal */
	public String getDadID() {
		return dadId;
	}

	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof AnimalFamilyInfo) {
			AnimalFamilyInfo info = (AnimalFamilyInfo)obj;
			result = info.subjId.equals(subjId);
		}
		
		return result;		
	}
}
