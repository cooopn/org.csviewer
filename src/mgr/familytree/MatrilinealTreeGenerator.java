package mgr.familytree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import mgr.AnimalFamilyInfo;

public class MatrilinealTreeGenerator {

	Map<String, String> momMatrLookup = new TreeMap<String, String>();
	List<AnimalFamilyInfo> rows = new LinkedList<AnimalFamilyInfo>();
	Map<String, Integer> momChildrenCount = new TreeMap<String, Integer>();
	Map<String, Integer> animalPedigree = new TreeMap<String, Integer>();	

	// adding an animalId --> AnimalFamilyInfo object map
	Map<String, AnimalFamilyInfo> animalIdFamilyInfoLookup =
			new TreeMap<String, AnimalFamilyInfo>();

	public static void main(String[] args) throws FileNotFoundException {
		MatrilinealTreeGenerator main = new MatrilinealTreeGenerator();
		main.doIt();
	}
		
	public AnimalFamilyInfo getAnimalIdFamilyInfoLookup(String animalId) {
		return animalIdFamilyInfoLookup.get(animalId);
	}
	
	void doIt() throws FileNotFoundException {
		Scanner input = new Scanner(new File("data/qFounderMomChildrenSeq.csv"));

		// skip the first line with column labels
		//System.out.println(input.nextLine());
		//input.nextLine();
		
		String line = "";
		AnimalFamilyInfo info;
		String familyId;
		String[] parts;
		
		boolean hasFCode;
		String curMom;
		int momPedigree;
		Integer intMomPed;
		int cc = 1000;
		
		line = input.nextLine();	// ignore the label line
		//System.out.println(line);
		
		while (input.hasNextLine()) {
			line = input.nextLine();
			parts = line.split(",");
			
			hasFCode = parts[6].length() > 0;
			curMom = parts[5];
			
			if (momChildrenCount.get(curMom) == null)
				momChildrenCount.put(curMom, 1);
			else
				momChildrenCount.put(curMom, momChildrenCount.get(curMom)+1);
			
			//if (!hasFCode) break;
			info = new AnimalFamilyInfo(parts[0], parts[5], parts[4], parts[2], 
					parts[1].substring(0, parts[1].indexOf(" ")), parts[3], parts[7]);
			
			//if (cc-- > 0) 
			//	System.out.println(cc+":"+info+"!"+hasFCode);
			
			if (hasFCode) {
				familyId = parts[6];
				momMatrLookup.put(parts[0], parts[6]);
				animalPedigree.put(parts[0], 2);	
			}
			else {
				familyId = momMatrLookup.get(curMom);
				//System.out.println(curMom+":"+parts[6]+"!");
				intMomPed = animalPedigree.get(curMom);
				//if (cc-- > 0) 
					//System.out.println(cc+":"+curMom+"!"+familyId+"&"+intMomPed);
				if (intMomPed == null)
					continue;
				momPedigree = intMomPed;
				animalPedigree.put(parts[0], momPedigree+1);	
				//if (familyId == null && dd -- > 0)
					//System.out.println(dd + ": " + parts[3]);
				momMatrLookup.put(parts[0], familyId);
				
				// moved here from outside the else branch
			}
				info.setFounderId(familyId);
				info.setSiblingNo(momChildrenCount.get(curMom));
				info.setPedigree(animalPedigree.get(parts[0]));
			
			rows.add(info);
		}
		
		System.out.println(momMatrLookup.keySet().size());
		//System.out.println(momMatrLookup.get("A37"));

		for (AnimalFamilyInfo row : rows) {
			animalIdFamilyInfoLookup.put(row.getAnimalId(), row);
		}

		/*
		//output -- skipped here : moved to a tree builder class 
		 * for rebuilding when needed
		PrintWriter  writer = null;

        try {
            writer = new PrintWriter ("data//AnimalFamilyInfo.csv");
    		System.out.println("opening csv file...");
            
    		for (AnimalFamilyInfo row : rows) {
    			animalIdFamilyInfoLookup.put(row.getAnimalId(), row);
    			//writer.println(row.toString());
    			//if (c-- == 0)
    			//	break;
    		}
        } catch (IOException ex) {
            // Report
        } finally {
           try {
        	   writer.close();
        	   System.out.println("csv file closed.");
           } catch (Exception ex) {
		   }
        }
        
        int c = 10;
        for (String momId : momChildrenCount.keySet()) {
        	if (c-- == 0)
        		break;
        	//System.out.println(momId + "==>" + momChildrenCount.get(momId));
        }
        */
	}
}