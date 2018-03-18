package metrics;

import java.util.Set;

import ast.SystemObject;
import ast.inheritance.InheritanceDetection;

public class NOH {
	
	//------------------------------------------------------------------
	public int nohValue;

	
	public int getNOH_Value() {
		return nohValue;
	}

	public void setNOH_Value(int nOH_Value) {
		nohValue = nOH_Value;
	}
	
	public String toString() {
		return " " + getNOH_Value();
	}

	//------------------------------------------------------------------
	public NOH(SystemObject system) {
		InheritanceDetection inheritanceDetection = new InheritanceDetection(system);
		
		//the Set interface defines the set that does not allow duplicate elements
		Set<String> inheritanceCount = inheritanceDetection.getRoots();
		
		nohValue = inheritanceCount.size();
	}
	
	//------------------------------------------------------------------
	
}
