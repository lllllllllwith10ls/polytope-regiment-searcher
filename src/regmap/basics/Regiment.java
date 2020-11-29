package regmap.basics;

public class Regiment {
	private RegimentMember[] regimentMembers;
	private String name;
	public Regiment(RegimentMember[] regMembs, String nName) {
		regimentMembers = regMembs;
		name = nName;
		for(int i = 0; i < regimentMembers.length; i++) {
			regimentMembers[i].setParent(this);
		}
	}
	public String getName() {
		return name;
	}
	public RegimentMember[] getRegimentMembers() {
		return regimentMembers;
	}
	public String toString() {
		return name;
	}
}
