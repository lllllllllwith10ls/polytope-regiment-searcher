package regmap.defiss;

import regmap.basics.Regiment;

public class FacialVertex {
	private String name;
	private Regiment polytope;
	public FacialVertex(String name, Regiment polytope) {
		this.name = name;
		this.polytope = polytope;
	}
	public String getName() {
		return name;
	}
	public Regiment getRegiment() {
		return polytope;
	}
	public String toString() {
		return name;
	}
}
