package regmap.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

import regmap.basics.Regiment;
import regmap.basics.RegimentMember;
import regmap.defiss.Facial;
import regmap.defiss.FacialEdge;
import regmap.defiss.FacialVertex;
import regmap.search.RegimentSearch;

public class RegimentTest {
	public static void main(String[] args) {
		fromFile();
	}
	public static void fromFile() {
		File file = null;
		Scanner scanner = null;
		try {
			file = new File("regiment.txt");
			scanner = new Scanner(file);
		} catch(Exception e) {
			
		}
		String title = scanner.nextLine();
		HashMap<String,RegimentMember> regimentMembers = new HashMap<>();
		while(true) {
			ArrayList<RegimentMember> ridges = new ArrayList<>();
			String ridgeStr = scanner.nextLine();
			if(ridgeStr.contains("~~~")) {
				break;
			}
			Scanner tempScan = new Scanner(ridgeStr);
			if(!tempScan.hasNext()) {
				continue;
			}
			String name = tempScan.next();
			while(tempScan.hasNext()) {
				String ridge = tempScan.next();
				if(regimentMembers.get(ridge) == null) {
					System.out.println(ridge);
				}
				
				ridges.add(regimentMembers.get(ridge));
			}
			tempScan.close();
			RegimentMember[] temp = new RegimentMember[ridges.size()];
			temp = ridges.toArray(temp);
			regimentMembers.put(name,new RegimentMember(temp,name));
		}
		
		
		HashMap<String,Regiment> subRegiments = new HashMap<>();
		while(true) {
			ArrayList<RegimentMember> regimentMembs = new ArrayList<>();
			ArrayList<String> regimentStrings = new ArrayList<>();

			String memberStr = scanner.nextLine();
			if(memberStr.contains("~~~")) {
				break;
			}
			Scanner tempScan = new Scanner(memberStr);
			if(!tempScan.hasNext()) {
				continue;
			}
			String name = tempScan.next();
			while(tempScan.hasNext()) {
				regimentStrings.add(tempScan.next());
			}
			tempScan.close();
			for(int i = 0; i < regimentStrings.size(); i++) {
				regimentMembs.add(regimentMembers.get(regimentStrings.get(i)));
			}
			regimentMembs.add(new RegimentMember(new RegimentMember[] {}, "-"));

			RegimentMember[] temp = new RegimentMember[regimentMembs.size()];
			temp = regimentMembs.toArray(temp);

			subRegiments.put(name,new Regiment(temp,name));
		}
		ArrayList<Regiment> regArray = new ArrayList<>();
		HashMap<String,Regiment> regiments = new HashMap<>();
		while(true) {
			ArrayList<RegimentMember> regimentMembs = new ArrayList<>();
			ArrayList<String> regimentStrings = new ArrayList<>();
			

			String memberStr = scanner.nextLine();
			if(memberStr.contains("~~~")) {
				break;
			}
			Scanner tempScan = new Scanner(memberStr);
			if(!tempScan.hasNext()) {
				continue;
			}
			String name = tempScan.next();
			while(tempScan.hasNext()) {
				regimentStrings.add(tempScan.next());
			}
			tempScan.close();
			for(int i = 0; i < regimentStrings.size(); i++) {
				regimentMembs.add(regimentMembers.get(regimentStrings.get(i)));
				if(regimentMembers.get(regimentStrings.get(i)) == null) {
					System.out.println(name);
				}
			}
			if(!name.substring(name.length()-1).equals("-")) {
				regimentMembs.add(0,new RegimentMember(new RegimentMember[] {}, "-"));
			}

			RegimentMember[] temp = new RegimentMember[regimentMembs.size()];
			temp = regimentMembs.toArray(temp);
			Regiment reg = new Regiment(temp,name);
			regiments.put(name,reg);
			regArray.add(reg);
			subRegiments.put(name,reg);
		}
		
		ArrayList<Facial> facials = new ArrayList<>();
		while(true) {
			HashMap<String,FacialVertex> facialVertices = new HashMap<>();
			ArrayList<FacialVertex> facialVertexArray = new ArrayList<>();
			ArrayList<FacialEdge> facialEdges = new ArrayList<>();
			if(!scanner.hasNextLine()) {
				break;
			}

			String facialName = scanner.nextLine();
			String vertexStr = scanner.nextLine();
			Scanner tempScan = new Scanner(vertexStr);
			if(!tempScan.hasNext()) {
				continue;
			}
			while(tempScan.hasNext()) {
				String name = tempScan.next();
				String polyName = tempScan.next();
				if(subRegiments.get(polyName) == null) {
					System.out.println(name);
				}
				FacialVertex vertex = new FacialVertex(name,subRegiments.get(polyName));
				facialVertices.put(name,vertex);
				facialVertexArray.add(vertex);
			}
			tempScan.close();
			
			while(true) {
				String edgeStr = scanner.nextLine();
				if(edgeStr.contains("~")) {
					break;
				}
				Scanner tempScan2 = new Scanner(edgeStr);
				if(!tempScan2.hasNext()) {
					continue;
				}
				String vName1 = tempScan2.next();
				String vName2 = tempScan2.next();
				String eName = tempScan2.next();
				if(facialVertices.get(vName1) == null) {
					System.out.println(vName1);
				}
				if(facialVertices.get(vName2) == null) {
					System.out.println(vName2);
				}
				if(subRegiments.get(eName) == null) {
					System.out.println(eName);
				}
				FacialVertex v1 = facialVertices.get(vName1);
				FacialVertex v2 = facialVertices.get(vName2);
				Regiment edge = subRegiments.get(eName);
				tempScan2.close();
				facialEdges.add(new FacialEdge(v1,v2,edge));
			}
			if(subRegiments.get(facialName) == null) {
				System.out.println(facialName);
			}
			facials.add(new Facial(facialName, facialVertexArray, facialEdges, subRegiments.get(facialName)));
		}
		Regiment[] temp = regArray.toArray(new Regiment[0]);
		
		RegimentSearch search = new RegimentSearch(temp,facials);
		search(search, title);
		scanner.close();
	}
	public static void search(RegimentSearch search, String title) {
		search.search();
		System.out.println("Outputting to output.txt");
		
		try {
			System.setOut(new PrintStream(new FileOutputStream("output.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(title);
		ArrayList<RegimentMember[]> result = search.getPolytopes();
		ArrayList<RegimentMember[]> fissaries = search.getFissaries();
		
		for(int i = 0; i < search.getRegiments().length; i++) {
			System.out.print(search.getRegiments()[i]+"\t");
		}
		System.out.println("\n");
		for(int i = 0; i < result.size(); i++) {
			for(int j = 0; j < result.get(i).length; j++) {
				System.out.print(result.get(i)[j]+"\t");
			}
			System.out.println();
		}
		for(int i = 0; i < fissaries.size(); i++) {
			for(int j = 0; j < fissaries.get(i).length; j++) {
				System.out.print(fissaries.get(i)[j]+"\t");
			}
			System.out.println(" (F)");
		}
		System.out.println(result.size()+ " polytopes and "+ fissaries.size() + " compounds/fissaries.");
	}
}
