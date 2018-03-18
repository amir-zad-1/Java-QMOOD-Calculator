package metrics;

import java.util.Set;

import ast.SystemObject;
import ast.inheritance.InheritanceDetection;

public class NOH {
	
	//------------------------------------------------------------------
	public int NOH_Value;

	
	public int getNOH_Value() {
		return NOH_Value;
	}

	public void setNOH_Value(int nOH_Value) {
		NOH_Value = nOH_Value;
	}
	
	public String toString() {
		return " " + getNOH_Value();
	}

	//------------------------------------------------------------------
	public NOH(SystemObject system) {
		InheritanceDetection inheritanceDetection = new InheritanceDetection(system);
		
		//the Set interface defines the set that does not allow duplicate elements
		Set<String> inheritanceCount = inheritanceDetection.getRoots();
		
		NOH_Value = inheritanceCount.size();
	}
	
	//------------------------------------------------------------------
	
}
