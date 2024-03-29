package regmap.basics;

public class RegimentMember {
	private RegimentMember[] facets;
	private String name;
	private Regiment parent;
	public RegimentMember(RegimentMember[] nFacets, String nName) {
		facets = nFacets;
		name = nName;
		parent = null;
	}
	public void setParent(Regiment parentReg) {
		parent = parentReg;
	}
	public RegimentMember[] getFacets() {
		return facets;
	}
	public Regiment getParent() {
		return parent;
	}
	public String toString() {
		return name;
	}
	public String name() {
		return name;
	}
	public boolean contains(RegimentMember polytope) {
		for(int i = 0; i < facets.length; i++) {
			if(facets[i] == polytope || facets[i].contains(polytope)) {
				return true;
			}
		}
		return false;
	}
	public boolean containsRegiment(Regiment regiment) {
		if(getParent() == regiment) {
			return true;
		}
		for(int i = 0; i < regiment.getRegimentMembers().length; i++) {
			if(contains(regiment.getRegimentMembers()[i])) {
				return true;
			}
		}
		return false;
	}
	public RegimentMember containedRegimentMember(Regiment regiment) {
		if(getParent() == regiment) {
			return this;
		}
		for(int i = 0; i < regiment.getRegimentMembers().length; i++) {
			if(contains(regiment.getRegimentMembers()[i])) {
				return regiment.getRegimentMembers()[i];
			}
		}
		return null;
	}
}
