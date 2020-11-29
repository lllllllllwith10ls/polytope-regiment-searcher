package regmap.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import regmap.basics.Regiment;
import regmap.basics.RegimentMember;
import regmap.search.RegimentSearch;

public class RegimentTest {
	public static void main(String[] args) {
		fromFile();
	}
	public static void rapRegiment() {
		System.out.println("rap");
		String trig1 = "trig1";
		String trig2 = "trig2";
		String square = "square";
		RegimentMember oct = new RegimentMember(new String[] {trig1,trig2,trig2},"oct");
		RegimentMember thah1 = new RegimentMember(new String[] {trig1,square},"thah1");
		RegimentMember thah2 = new RegimentMember(new String[] {trig2,trig2,square},"thah2");
		RegimentMember empty1 = new RegimentMember(new String[] {},"empty");
		RegimentMember tet = new RegimentMember(new String[] {trig1},"tet");
		RegimentMember empty2 = new RegimentMember(new String[] {},"empty");
		RegimentMember trip = new RegimentMember(new String[] {trig1,square,square},"trip");
		RegimentMember empty3 = new RegimentMember(new String[] {},"empty");
		
		Regiment octReg = new Regiment(new RegimentMember[] {oct,thah1,thah2,empty1},"oct");
		Regiment tetReg = new Regiment(new RegimentMember[] {tet,empty2},"tet");
		Regiment tripReg = new Regiment(new RegimentMember[] {trip,empty3},"trip");
		
		
		RegimentSearch search = new RegimentSearch(new Regiment[] {octReg,tetReg,tripReg});
		search(search);
	}
	public static void texRegiment() {
		System.out.println("tex");
		String hig = "hig";
		String trig = "trig";
		String peg = "peg";

		RegimentMember ike = new RegimentMember(new String[] {trig},"ike");
		RegimentMember gad = new RegimentMember(new String[] {peg},"gad");
		RegimentMember empty = new RegimentMember(new String[] {},"empty");
		Regiment ikeReg = new Regiment(new RegimentMember[] {ike,gad,empty},"ike");

		RegimentMember tut = new RegimentMember(new String[] {hig,hig,trig},"tut");
		RegimentMember empty2 = new RegimentMember(new String[] {},"empty");
		Regiment tutReg = new Regiment(new RegimentMember[] {tut,empty2},"tut");

		RegimentMember ti = new RegimentMember(new String[] {hig,hig,peg},"ti");
		RegimentMember empty3 = new RegimentMember(new String[] {},"empty");
		Regiment tiReg = new Regiment(new RegimentMember[] {ti,empty3},"ti");
		
		
		RegimentSearch search = new RegimentSearch(new Regiment[] {ikeReg,tutReg,tiReg});
		search(search);
	}
	public static void roxRegiment() {
		System.out.println("rox");
		String trig1 = "trig1";
		String trig2 = "trig2";
		String square = "square";
		String peg = "peg";
		String dec = "dec";
		RegimentMember oct = new RegimentMember(new String[] {trig1,trig2,trig2},"oct");
		RegimentMember thah1 = new RegimentMember(new String[] {trig1,square},"thah1");
		RegimentMember thah2 = new RegimentMember(new String[] {trig2,trig2,square},"thah2");
		RegimentMember empty1 = new RegimentMember(new String[] {},"empty");
		Regiment octReg = new Regiment(new RegimentMember[] {oct,thah1,thah2,empty1},"oct");
		
		RegimentMember ike = new RegimentMember(new String[] {trig1},"ike");
		RegimentMember gad = new RegimentMember(new String[] {peg},"gad");
		RegimentMember empty2 = new RegimentMember(new String[] {},"empty");
		Regiment ikeReg = new Regiment(new RegimentMember[] {ike,gad,empty2},"ike");
		
		RegimentMember pip = new RegimentMember(new String[] {peg,square,square},"pip");
		RegimentMember empty3 = new RegimentMember(new String[] {},"empty");
		Regiment pipReg = new Regiment(new RegimentMember[] {pip,empty3},"trip");

		RegimentMember tid = new RegimentMember(new String[] {dec,dec,trig1},"tid");
		RegimentMember empty4 = new RegimentMember(new String[] {},"empty");
		Regiment tidReg = new Regiment(new RegimentMember[] {tid,empty4},"tid");

		RegimentMember id = new RegimentMember(new String[] {trig2,trig2,peg},"id");
		RegimentMember sidhid = new RegimentMember(new String[] {dec,peg},"sidhid");
		RegimentMember seihid = new RegimentMember(new String[] {trig2,trig2,dec},"seihid");
		RegimentMember empty5 = new RegimentMember(new String[] {},"empty");
		Regiment idReg = new Regiment(new RegimentMember[] {id,sidhid,seihid,empty5},"id");

		RegimentMember srid = new RegimentMember(new String[] {square,square,trig1,peg},"srid");
		RegimentMember saddid = new RegimentMember(new String[] {dec,dec,trig1,peg},"saddid");
		RegimentMember sird = new RegimentMember(new String[] {square,square,dec,dec},"sird");
		RegimentMember empty6 = new RegimentMember(new String[] {},"empty");
		Regiment sridReg = new Regiment(new RegimentMember[] {srid,saddid,sird,empty6},"srid");
		
		
		RegimentSearch search = new RegimentSearch(new Regiment[] {octReg,ikeReg,pipReg,tidReg,idReg,sridReg});
		search(search);
	}
	public static void fromFile() {
		File file = null;
		Scanner scanner = null;
		try {
			file = new File("regiment.txt");
			scanner = new Scanner(file);
		} catch(Exception e) {
			
		}
		ArrayList<String> strings = new ArrayList<>();
		String title = scanner.nextLine();
		String string = "";
		while(true) {
			string = scanner.next();
			if(string.contains("~~~")) {
				break;
			}
			strings.add(string);
		}
		ArrayList<RegimentMember> regimentMembers = new ArrayList<>();
		while(true) {
			ArrayList<String> ridges = new ArrayList<>();
			String name = scanner.next();
			if(name.contains("~~~")) {
				break;
			}
			while(scanner.hasNextInt()) {
				ridges.add(strings.get(scanner.nextInt()));
			}
			String[] temp = new String[ridges.size()];
			temp = ridges.toArray(temp);
			regimentMembers.add(new RegimentMember(temp,name));
		}
		ArrayList<Regiment> regiments = new ArrayList<>();
		while(true) {
			ArrayList<RegimentMember> regimentMembs = new ArrayList<>();
			if(!scanner.hasNext()) {
				break;
			}
			String name = scanner.next();
			while(scanner.hasNextInt()) {
				regimentMembs.add(regimentMembers.get(scanner.nextInt()));
			}
			regimentMembs.add(new RegimentMember(new String[] {}, "empty"));

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
		System.out.println(Arrays.toString(search.getRegiments())+"\n");
		for(int i = 0; i < result.size(); i++) {
			System.out.println(Arrays.toString(result.get(i)));
		}
	}
}
