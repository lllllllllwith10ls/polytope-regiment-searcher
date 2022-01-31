package regmap.defiss;

import java.util.ArrayList;

import regmap.basics.Regiment;
import regmap.basics.RegimentMember;

public class Facial {
	String name;
	Regiment regiment;
	ArrayList<FacialVertex> vertices;
	ArrayList<FacialEdge> edges;
	public Facial(String name, ArrayList<FacialVertex> vertices, ArrayList<FacialEdge> edges, Regiment regiment) {
		this.name = name; 
		this.regiment = regiment;
		this.vertices = vertices;
		this.edges = edges;
	}
	public boolean fissary(RegimentMember[] polytope) {
		ArrayList<FacialEdge> markedEdges = new ArrayList<>();

		
		for(int i = 0; i < edges.size(); i++) {
			boolean isContained = false;
			for(int j = 0; j < polytope.length; j++) {
				if(edges.get(i).containedBy(polytope[j],regiment)) {
					isContained = true;
					markedEdges.add(edges.get(i));
					break;
				}
			}
			if(isContained) {
				break;
			}
		}
		if(markedEdges.size() <= 0) {
			return false;
		}
		boolean changed = true;
		while(changed) {
			changed = false;
			for(int i = 0; i < markedEdges.size(); i++) {
				for(int j = 0; j < edges.size(); j++) {
					if(!markedEdges.contains(edges.get(j))) {
						if(
							markedEdges.get(i).getV1() == edges.get(j).getV1() ||
							markedEdges.get(i).getV1() == edges.get(j).getV2() ||
							markedEdges.get(i).getV2() == edges.get(j).getV1() ||
							markedEdges.get(i).getV2() == edges.get(j).getV2()) {
							for(int k = 0; k < polytope.length; k++) {
								if(edges.get(j).containedBy(polytope[k],regiment)) {
									changed = true;
									markedEdges.add(edges.get(j));
									break;
								}
							}
						}
					}
				}
			}
		}
		boolean result = false;
		for(int i = 0; i < edges.size(); i++) {
			for(int j = 0; j < polytope.length; j++) {
				if(edges.get(i).containedBy(polytope[j],regiment) &&
						!markedEdges.contains(edges.get(i))) {
					result = true;
				}
			}
		}
		return result;
	}

	public ArrayList<FacialEdge> getEdges() {
		return edges;
	}
	public String toString() {
		return name;
	}
}
