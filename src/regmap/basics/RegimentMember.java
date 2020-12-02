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
}
