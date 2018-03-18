package metrics;

import ast.SystemObject;

public class DSC { 
	public int dscValue;
	
	public DSC(SystemObject system) {
		dscValue = system.getClassNumber();
	}
	
	@Override
	public String toString() 
	{
		return " " + dscValue;
	}
	
}
