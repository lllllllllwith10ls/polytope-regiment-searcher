package regmap.defiss;

import regmap.basics.Regiment;
import regmap.basics.RegimentMember;

public class FacialEdge {
	private FacialVertex v1;
	private FacialVertex v2;
	private Regiment edge;
	public FacialEdge(FacialVertex v1, FacialVertex v2, Regiment edge) {
		this.v1 = v1;
		this.v2 = v2;
		this.edge = edge;
	}
	public FacialVertex getV1() {
		return v1;
	}
	public FacialVertex getV2() {
		return v2;
	}
	public Regiment getEdge() {
		return edge;
	}
	public String toString() {
		return v1.toString()+","+v2.toString()+"("+edge.toString()+")";
	}
	public boolean containedBy(RegimentMember polytope, Regiment facialReg) {
		
		if(polytope.containsRegiment(edge)) {
			RegimentMember element = polytope.containedRegimentMember(edge);
			if(element.containsRegiment(facialReg)) {
				RegimentMember subElement1 = polytope.containedRegimentMember(v1.getRegiment());
				RegimentMember subElement2 = polytope.containedRegimentMember(v2.getRegiment());
				if(element.contains(subElement1) && element.contains(subElement2)) {
					return true;
				}
			}
		}
		return false;
	}
}
