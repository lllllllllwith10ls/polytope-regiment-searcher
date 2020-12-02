package regmap.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

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
		ArrayList<RegimentMember> regimentMembers = new ArrayList<>();
		while(true) {
			ArrayList<RegimentMember> ridges = new ArrayList<>();
			String name = scanner.next();
			if(name.contains("~~~")) {
				break;
			}
			while(scanner.hasNextInt()) {
				ridges.add(regimentMembers.get(scanner.nextInt()));
			}
			RegimentMember[] temp = new RegimentMember[ridges.size()];
			temp = ridges.toArray(temp);
			regimentMembers.add(new RegimentMember(temp,name));
		}
		
		
		ArrayList<Regiment> subRegiments = new ArrayList<>();
		while(true) {
			ArrayList<RegimentMember> regimentMembs = new ArrayList<>();
			ArrayList<Integer> regimentNumbs = new ArrayList<>();
			String name = scanner.next();
			if(name.contains("~~~")) {
				break;
			}
			while(scanner.hasNextInt()) {
				regimentNumbs.add(scanner.nextInt());
			}
			for(int i = regimentNumbs.get(0); i <= regimentNumbs.get(regimentNumbs.size()-1); i++) {
				regimentMembs.add(regimentMembers.get(i));
			}
			regimentMembs.add(new RegimentMember(new RegimentMember[] {}, "-"));

			RegimentMember[] temp = new RegimentMember[regimentMembs.size()];
			temp = regimentMembs.toArray(temp);
			
			subRegiments.add(new Regiment(temp,name));
		}
		
		ArrayList<Regiment> regiments = new ArrayList<>();
		while(true) {
			ArrayList<RegimentMember> regimentMembs = new ArrayList<>();
			ArrayList<Integer> regimentNumbs = new ArrayList<>();
			if(!scanner.hasNext()) {
				break;
			}
			String name = scanner.next();
			while(scanner.hasNextInt()) {
				regimentNumbs.add(scanner.nextInt());
			}
			for(int i = regimentNumbs.get(0); i <= regimentNumbs.get(regimentNumbs.size()-1); i++) {
				regimentMembs.add(regimentMembers.get(i));
			}
			regimentMembs.add(new RegimentMember(new RegimentMember[] {}, "-"));

			RegimentMember[] temp = new RegimentMember[regimentMembs.size()];
			temp = regimentMembs.toArray(temp);
			
			regiments.add(new Regiment(temp,name));
		}
		System.out.println(title);

		Regiment[] temp = new Regiment[regiments.size()];
		temp = regiments.toArray(temp);
		
		RegimentSearch search = new RegimentSearch(temp);
		search(search);
		scanner.close();
	}
	public static void search(RegimentSearch search) {
		ArrayList<RegimentMember[]> result = search.search();
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
