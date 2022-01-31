package regmap.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import regmap.basics.Regiment;
import regmap.basics.RegimentMember;
import regmap.defiss.Facial;

public class RegimentSearch {
	private Regiment[] regiments;
	private ArrayList<Facial> facials;
	private ArrayList<RegimentMember[]> foundPolytopes;
	private ArrayList<RegimentMember[]> fissaryPolytopes;
	
	public RegimentSearch(Regiment[] reg, ArrayList<Facial> fac) {
		regiments = reg;
		facials = fac;
		fissaryPolytopes = new ArrayList<>();
	}
	public Regiment[] getRegiments() {
		return regiments;
	}
	public void search() {
		int iters = 0;
		ArrayList<RegimentMember[]> validPolytopes = new ArrayList<>();
		int[] polytopeNumber = new int[regiments.length];
		
		int polytopeLayer = 0;
		boolean finished = false;
		RegimentMember[] currentPolytope = new RegimentMember[regiments.length];
		while(!finished) {
			if(iters%50000 == 0) {
				System.out.println(Arrays.toString(currentPolytope));
				System.out.println(iters + " iters");
			}
			iters++;
			boolean breakOut = false;
			while(polytopeNumber[polytopeLayer] >= regiments[polytopeLayer].getRegimentMembers().length) {
				if(polytopeLayer == 0) {
					breakOut = true;
					break;
				} else {
					polytopeNumber[polytopeLayer] = 0;
					polytopeNumber[polytopeLayer-1]++;
					polytopeLayer--;
				}
			}
			if(breakOut) {
				break;
			}
			for(int i = 0; i < currentPolytope.length; i++) {
				currentPolytope[i] = 
				regiments[i].getRegimentMembers()[polytopeNumber[i]];
			}
			boolean conflict = false;
			HashMap<RegimentMember,Integer> usedRidges = new HashMap<>();
			
			for(int i = 0; i < currentPolytope.length; i++) {
				if(currentPolytope[i] == null) {
					continue;
				} else {
					for(int j = 0; j < currentPolytope[i].getFacets().length; j++) {
						if(!usedRidges.containsKey(currentPolytope[i].getFacets()[j])) {
							usedRidges.put(currentPolytope[i].getFacets()[j], 1);
						} else {
							usedRidges.put(currentPolytope[i].getFacets()[j], usedRidges.get(currentPolytope[i].getFacets()[j])+1);
						}
					}
				}
			}
			for(int i = 0; i < usedRidges.keySet().toArray().length; i++) {
				if(usedRidges.get(usedRidges.keySet().toArray()[i]) > 2) {
					conflict = true;
				}
			}
			if(conflict) {
				polytopeNumber[polytopeLayer]++;
				for(int i = polytopeLayer+1; i < polytopeNumber.length; i++) {
					polytopeNumber[i] = 0;
				}
				continue;
			} else {
				polytopeLayer = polytopeNumber.length;
				
				if(polytopeLayer >= polytopeNumber.length) {
					RegimentMember[] ridges = new RegimentMember[usedRidges.keySet().toArray().length];
					ridges = usedRidges.keySet().toArray(ridges);
					for(int i = 0; i < ridges.length; i++) {
						if(usedRidges.get(ridges[i]) != 0 && usedRidges.get(ridges[i]) != 2) {
							conflict = true;
						}
					}
					for(int i = 0; i < ridges.length; i++) {
						for(int j = 0; j < ridges.length; j++) {
							if(i != j) {
								if(conflictingElements(ridges[i],ridges[j])) {
									conflict = true;
								}
							}
						}
					}
					if(!conflict) {
						validPolytopes.add(currentPolytope.clone());
						if(validPolytopes.size()%1 == 0) {
							System.out.println(Arrays.toString(currentPolytope));
							System.out.println(validPolytopes.size());
						}
					}
					polytopeLayer--;
					polytopeNumber[polytopeLayer]++;
				}
			}
		}
		validPolytopes = removeIRCs(validPolytopes);
		defiss(validPolytopes);
	}
	public ArrayList<RegimentMember[]> removeIRCs(ArrayList<RegimentMember[]> polytopes) {
		ArrayList<RegimentMember[]> nullPolytopes = new ArrayList<>();
		for(int i = 0; i < polytopes.size(); i++) {
			boolean isNull = true;
			for(int j = 0; j < polytopes.get(i).length; j++) {
				if(!polytopes.get(i)[j].toString().equals("-")) {
					isNull = false;
				}
				
			}
			if(isNull) {
				nullPolytopes.add(polytopes.get(i));
				break;
			}
			
		}
		for(int i = 0; i < nullPolytopes.size(); i++) {
			polytopes.remove(nullPolytopes.get(i));
		}
		ArrayList<RegimentMember[]> ircs = new ArrayList<>();
		for(int i = 0; i < polytopes.size(); i++) {
			for(int j = 0; j < polytopes.size(); j++) {
				if(polytopes.get(j) == polytopes.get(i)) {
					continue;
				}
				boolean isIrc = true;
				for(int k = 0; k < polytopes.get(i).length; k++) {
					if(polytopes.get(i)[k] != polytopes.get(j)[k] 
							&& !polytopes.get(j)[k].toString().equals("-")) {
						isIrc = false;
					}
				}
				if(isIrc) {
					ircs.add(polytopes.get(i));
					break;
				}
			}
		}
		for(int i = 0; i < ircs.size(); i++) {
			polytopes.remove(ircs.get(i));
		}
		return polytopes;
	}
	public boolean conflictingElements(RegimentMember a, RegimentMember b) {
		if(a == b || a.toString() == "-" || b.toString() == "-") {
			return false;
		} else if(a.getParent() != null && b.getParent() != null && a.getParent() == b.getParent()) {
			return true;
		} else {
			for(int i = 0; i < a.getFacets().length; i++) {
				for(int j = 0; j < b.getFacets().length; j++) {
					if(conflictingElements(a.getFacets()[i],b.getFacets()[j])) {
						return true;
					}
				}
			}
			return false;
		}
	}
	
	public void defiss(ArrayList<RegimentMember[]> polytopes) {

		for(int j = polytopes.size()-1; j >= 0; j--) {
			boolean isFissary = false;
			for(int i = 0; i < facials.size(); i++) {
				if(facials.get(i).fissary(polytopes.get(j))) {
					System.out.println(Arrays.toString(polytopes.get(j))+"(F)");
					System.out.println(facials.get(i));
					isFissary = true;
				}
			}
			for(int i = 0; i < polytopes.get(j).length; i++) {
				if(polytopes.get(j)[i].name().contains("(F)")) {
					isFissary = true;
				}
			}
			if(isFissary) {
				fissaryPolytopes.add(polytopes.remove(j));
			}
		}
		foundPolytopes = polytopes;
	}

	public ArrayList<RegimentMember[]> getPolytopes() {
		return foundPolytopes;
	}
	public ArrayList<RegimentMember[]> getFissaries() {
		return fissaryPolytopes;
	}
}
