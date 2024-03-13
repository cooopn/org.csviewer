package mgr.familytree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import mgr.AnimalFamilyInfo;


public class PatrilinealTreeGenerator {
	// keeps all sires in a chronicle order
	LinkedList<String> chronicalSireSet = new LinkedList<String>();
	// maps a dad to his children stored in a list
	HashMap<String, LinkedList<AnimalFamilyInfo>> dadChildrenLookup = new HashMap();

	// for file IO
	String DEFAULT_FILE = "data/qAnimalsSortedBySireId.txt";
	String file = DEFAULT_FILE;
	PrintWriter  writer;
	
	MatrilinealTreeGenerator matrilTreeGen;

	public static void main(String[] args) {
		PatrilinealTreeGenerator patrilTreeGen = new PatrilinealTreeGenerator();
		patrilTreeGen.loadData();
		patrilTreeGen.printData();	
		
		System.out.println(patrilTreeGen.chronicalSireSet.size());
		System.out.println(patrilTreeGen.dadChildrenLookup.keySet().size());
	}

	void printData() {
		int count = 0;
		LinkedList<AnimalFamilyInfo> chdList;
		for (String dad : chronicalSireSet) {
			chdList = dadChildrenLookup.get(dad);
			if (chdList != null) {
				count++;
				//System.out.println("Tree started from " + dad);
				writer.println("Tree started from " + dad);
			}

			//dadChildrenLookup.put(dad, null);
			//System.out.println(dad + "-->" + chdList);
			printSubTree(chdList, "");
		}
       try {
    	   writer.close();
    	   System.out.println("csv file closed.");
       } catch (Exception ex) {/*ignore*/}
       
       System.out.println("Trees printed " + count);
	}

	private void printSubTree(LinkedList<AnimalFamilyInfo> chdList, String indentation) {
		if (chdList != null) {
			indentation += "\t";
			//System.out.println(indentation + chdList.size());
			for (AnimalFamilyInfo ai : chdList) {
				/*
				if (!(ai.sex.equals("\"M\""))) {
					//System.out.println(ai + " is Female");
					continue;
				}
				*/
				//System.out.println("removing" + chronicalSireSet.remove(ai.subjId));
				writer.println(indentation + ai);
				LinkedList<AnimalFamilyInfo> ccList = dadChildrenLookup.get(ai.getAnimalId());
				dadChildrenLookup.put(ai.getAnimalId(), null);
				//System.out.println(indentation + ccList);
				printSubTree(ccList, indentation);
			}
		} 
	}

	PatrilinealTreeGenerator() {
		matrilTreeGen = new MatrilinealTreeGenerator();
		try {
			matrilTreeGen.doIt();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
	        writer = new PrintWriter ("data/PatrilTreeInfo.txt");
	        //writer = new PrintWriter ("data/FamilyTreeBySire_inCS.txt");
			System.out.println("opening csv file...");
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }
	}

	void loadData() {
		Scanner input = null;
		try {
			input = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	
		String line;
		String[] parts;
		String dadId, chdId;
		AnimalFamilyInfo childInfo;
		String dob;
		while (input.hasNextLine()) {
			line = input.nextLine();
			parts = line.split(",");
			dadId = parts[1];
			chdId = parts[2];
			if (!chronicalSireSet.contains(dadId)) {
				chronicalSireSet.addLast(dadId);
			}
			childInfo = matrilTreeGen.getAnimalIdFamilyInfoLookup(chdId);
			putChildInfoToMap(dadId, childInfo);
		}
	}
	
	private void putChildInfoToMap(String dadId, AnimalFamilyInfo childInfo) {
		if (!dadChildrenLookup.containsKey(dadId)) {
			LinkedList<AnimalFamilyInfo> chdList = new LinkedList<AnimalFamilyInfo>();
			chdList.add(childInfo);
			//System.out.println(dadId + " : " +  chdList);
			dadChildrenLookup.put(dadId, chdList);
		}
		else {
			dadChildrenLookup.get(dadId).addLast(childInfo);
			//System.out.println(dadId + " + " +  childInfo);
		}
	}

	public AnimalFamilyInfo getAnimalIdFamilyInfoLookup(String animalId) {
		// TODO Auto-generated method stub
		return matrilTreeGen.getAnimalIdFamilyInfoLookup(animalId);
	}

}
