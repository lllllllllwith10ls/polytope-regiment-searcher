package regmap.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;

import regmap.basics.Regiment;
import regmap.basics.RegimentMember;
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
				ridges.add(regimentMembers.get(tempScan.next()));
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
		
		HashMap<String,Regiment> regiments = new HashMap<>();
		while(true) {
			ArrayList<RegimentMember> regimentMembs = new ArrayList<>();
			ArrayList<String> regimentStrings = new ArrayList<>();
			if(!scanner.hasNextLine()) {
				break;
			}

			String memberStr = scanner.nextLine();
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
			regiments.put(name,new Regiment(temp,name));
		}
		System.out.println(title);

		Regiment[] temp = regiments.values().toArray(new Regiment[0]);
		
		RegimentSearch search = new RegimentSearch(temp);
		search(search);
		scanner.close();
	}
	public static void search(RegimentSearch search) {
		search.search();
		ArrayList<RegimentMember[]> result = search.getPolytopes();
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
		System.out.println(result.size()+ " polytopes");
	}
}
