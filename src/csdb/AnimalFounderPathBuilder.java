package csdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

//import csdb.DyadNode;

public class AnimalFounderPathBuilder {
	Map<String, LinkedList<String>> animalFounderPaths = 
			new TreeMap<String, LinkedList<String>>();
	Map<String, Dyad> matrilTreeLinks = new TreeMap<>();
	Map<String, Dyad> animalParentLookup = new TreeMap<>();
	Map<String, String> animalFounderLookup = new TreeMap<>();
	Map<String, Double> inbreedCoefs = new TreeMap<>();

	public static void main(String[] args) {
		AnimalFounderPathBuilder builder = new AnimalFounderPathBuilder();
		builder.build();
	}

	public void build() {
		Scanner input = null;
		try {
			input = new Scanner(new File("data/qAnimalParentsFounderInfo.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] parts;
		while (input.hasNextLine()) {
			parts = input.nextLine().split(",");
			animalParentLookup.put(parts[0], new Dyad(parts[3], parts[4]));
			//System.out.println(parts[0] + "=" + parts[3] + "x" + parts[4]);
		}
		System.out.println("#2lookup=" + animalParentLookup.size());
		
		try {
			input = new Scanner(new File("data/qFounderMomChildrenSeq.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//String t = "\"3L5\"";
		while (input.hasNextLine()) {
			parts = input.nextLine().split(",");
			matrilTreeLinks.put(parts[0], new Dyad(parts[5], parts[4]));
			//if (limit++ > 10000)
			//	System.out.println(parts[0] + "=" + parts[5] + "x" + parts[4]);
			if (parts[6] != null && parts[6].length() > 0)
				animalFounderLookup.put(parts[0], parts[6]);
			else
				animalFounderLookup.put(parts[0], animalFounderLookup.get(parts[5]));
		}
		System.out.println("total#inCS=" + matrilTreeLinks.size());
		System.out.println("1F9's dyad=" + matrilTreeLinks.get("\"1F9\""));
		
		Set<String> tattoos = animalParentLookup.keySet();
		//LinkedList<String> path = null;
		for (String t : tattoos) {
			createPathForAnimal(t);
			
			if (!tattoos.contains(animalParentLookup.get(t).dam))
				createPathForAnimal(animalParentLookup.get(t).dam);
				
			if (!tattoos.contains(animalParentLookup.get(t).sire))
				createPathForAnimal(animalParentLookup.get(t).sire);
		}
		
		//sample animal
		//AnimalId	Sex	DamGenetic	SireGenetic	DamMatriLine	SireMatriLine
		//1P8	M	0C8	1C2	Q1050	Q1050
		//testWithDyadsFromSameFounder();		
	}

	private void createPathForAnimal(String t) {
		LinkedList<String> path = new LinkedList<String>();
		path.add(t);
		//System.out.println("Dyad==>"+matrilTreeLinks.get(t));
		fillPath(path);
		//System.out.println(t+"==>"+path);
		animalFounderPaths.put(t, path);
		
	}

	private void testWithDyadsFromSameFounder() {
		Scanner input = null;
		try {
			// .txt: 16 animals whose parents are in the set with 2000 
			//input = new Scanner(new File("data/qDyadFromSameFounder.txt"));
			// .csv: 163 animals in the set with 2000 whose dyad share founder
			input = new Scanner(new File("data/qDyadFromSameFounder.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] parts;
		LinkedList<String> damPath, sirePath;
		String animal, dam, sire;
		String commonMom;
		int count = 0;
		double damLevels, sireLevels, coef;
		while (input.hasNextLine()) {
			parts = input.nextLine().split(",");
			animal = parts[0];
			dam = parts[2];
			sire = parts[3];
			damPath = animalFounderPaths.get(dam);
			sirePath = animalFounderPaths.get(sire);
			commonMom = findCommonElement(damPath, sirePath);
			System.out.println(animal + "=" + dam + "x" + sire);
			System.out.println("  Dam path==>"+damPath+damPath.size());
			System.out.println(" Sire path==>"+sirePath+sirePath.size());
			damLevels = damPath.size() - damPath.indexOf(commonMom);
			sireLevels = sirePath.size() - sirePath.indexOf(commonMom);
			System.out.println("\t" + commonMom + "==>" +
					damLevels + ":" + sireLevels);
			inbreedCoefs.put(animal, calInbrdCoef(damLevels, sireLevels));
			count++;
		}
		
		System.out.println("Animal checked: "+count);
		
		outputInbrdCoef();
		
		animal = "\"0B0\"";
		dam = matrilTreeLinks.get(animal).dam;
		sire = matrilTreeLinks.get(animal).sire;
		Set<String> ancestorTreeForMom = new HashSet<String>();
		buildAncestorTree(dam, ancestorTreeForMom);
		Set<String> ancestorTreeForDad = new HashSet<String>();
		buildAncestorTree(sire, ancestorTreeForDad);
		System.out.println(ancestorTreeForMom);
		System.out.println(ancestorTreeForDad);
		System.out.println(ancestorTreeForMom.retainAll(ancestorTreeForDad));
		System.out.println(ancestorTreeForMom);
	}

	private void buildAncestorTree(String animal, Set<String> ancestorTree) {
		Dyad d = matrilTreeLinks.get(animal);
		System.out.println(animal +"==>" + d);
		if (d == null)
			return;
		
		String dam = d.dam;
		if (dam == null || dam.trim().length() == 0)
			return;
		else {
			ancestorTree.add(dam);
			buildAncestorTree(dam, ancestorTree);
		}
			
		String sire = d.sire;
		if (sire == null || sire.trim().length() == 0)
			return;
		else {
			ancestorTree.add(sire);
			buildAncestorTree(sire, ancestorTree);
		}
	}

	private Double calInbrdCoef(double damLevels, double sireLevels) {
		double coef = Math.pow(.5, damLevels + sireLevels - 1);
		return coef;
	}

	private void outputInbrdCoef() {
		for (String animal : inbreedCoefs.keySet()) {
			System.out.println(animal + ", " + inbreedCoefs.get(animal));
		}
		
	}

	private String findCommonElement(LinkedList<String> damPath, 
			LinkedList<String> sirePath) {
		String commonValue = null;
		int minLength = Math.min(damPath.size(), sirePath.size());
		for (int i=0; i<minLength; i++) {
			if (damPath.get(i).equals(sirePath.get(i)))
				commonValue = damPath.get(i);
			else
				break;
		}
		return commonValue;
	}

	private void fillPath(List<String> path) {
		// TODO Auto-generated method stub
		String tattoo = path.get(0);
		//System.out.println(tattoo);
		String dam;
		Dyad d;
		while (true) {
			d = matrilTreeLinks.get(tattoo);
			if (d == null)
				break;
			else {
				dam = d.dam;
				path.add(0, dam);
				tattoo = dam;
			}
			//System.out.println(dam);
		}
		
	}

	public class Dyad {
		String dam;
		String sire;
		
		Dyad(String dam, String sire) {
			this.dam = dam;
			this.sire = sire;
		}
		
		public String getDam() {
			return dam;
		}
		
		public String getSire() {
			return sire;
		}
		
		public String toString() {
			return dam + "x" + sire;
		}
	}

	public Dyad getDyadForChild(String tattoo) {
		// TODO Auto-generated method stub
		return this.matrilTreeLinks.get(tattoo);
	}

	public String getFounderForAnimal(String tattoo) {
		System.out.println("In getFounder |" + tattoo + "|");
		if (tattoo == null || tattoo.trim().length() == 0)
			return "";
		
		return this.animalFounderLookup.get(tattoo);
	}
}
