package regmap.search;

import java.util.ArrayList;
import java.util.Arrays;
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
			
			HashMap<String,Integer> usedRidges = new HashMap<>();
			for(int i = 0; i < currentPolytope.length; i++) {
				if(currentPolytope[i] == null) {
					continue;
				} else {
					for(int j = 0; j < currentPolytope[i].getRidges().length; j++) {
						if(!usedRidges.containsKey(currentPolytope[i].getRidges()[j])) {
							usedRidges.put(currentPolytope[i].getRidges()[j], 1);
						} else {
							usedRidges.put(currentPolytope[i].getRidges()[j], usedRidges.get(currentPolytope[i].getRidges()[j])+1);
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
					
					for(int i = 0; i < usedRidges.keySet().toArray().length; i++) {
						if(usedRidges.get(usedRidges.keySet().toArray()[i]) != 0 && 
								usedRidges.get(usedRidges.keySet().toArray()[i]) != 2) {
							conflict = true;
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
		return validPolytopes;
	}
}
