package regmap.search;

import java.util.ArrayList;
import java.util.HashMap;

import regmap.basics.Regiment;
import regmap.basics.RegimentMember;

public class RegimentSearch {
	private Regiment[] regiments;
	
	public RegimentSearch(Regiment[] reg) {
		regiments = reg;
	}
	public Regiment[] getRegiments() {
		return regiments;
	}
	public ArrayList<RegimentMember[]> search() {
		ArrayList<RegimentMember[]> validPolytopes = new ArrayList<>();
		int[] polytopeNumber = new int[regiments.length];
		int polytopeLayer = 0;
		boolean finished = false;
		RegimentMember[] currentPolytope = new RegimentMember[regiments.length];
		
		while(!finished) {
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
			RegimentMember[] regimentMembers = 
					regiments[polytopeLayer].getRegimentMembers();
			currentPolytope[polytopeLayer] = 
					regimentMembers[polytopeNumber[polytopeLayer]];
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
				polytopeLayer++;
				if(polytopeLayer >= polytopeNumber.length) {
					polytopeLayer--;
					polytopeNumber[polytopeLayer]++;
				}
				continue;
			} else {
				
				polytopeLayer++;
				
				if(polytopeLayer >= polytopeNumber.length) {
					RegimentMember[] ridges = new RegimentMember[usedRidges.keySet().toArray().length];
					ridges = usedRidges.keySet().toArray(ridges);
					for(int i = 0; i < ridges.length; i++) {
						if(usedRidges.get(ridges[i]) != 0 && 
								usedRidges.get(ridges[i]) != 2) {
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
					}
					polytopeLayer--;
					polytopeNumber[polytopeLayer]++;
				}
			}
		}
		validPolytopes = removeIRCs(validPolytopes);
		return validPolytopes;
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
}
