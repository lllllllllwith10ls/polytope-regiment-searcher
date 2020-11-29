package regmap.basics;

import java.util.Arrays;

public class RegimentMember {
	private String[] ridges;
	private String name;
	private Regiment parent;
	public RegimentMember(String[] nRidges, String nName) {
		ridges = nRidges;
		name = nName;
		parent = null;
	}
	public void setParent(Regiment parentReg) {
		parent = parentReg;
	}
	public String[] getRidges() {
		return ridges;
	}
	public Regiment getParent() {
		return parent;
	}
	public String toString() {
		return /*Arrays.toString(ridges)+*/name;
	}
}
