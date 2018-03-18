package metrics;

import ast.SystemObject;

public class DSC { 
	public int dscamount;
	
	public DSC(SystemObject system) {
		dscamount = system.getClassNumber();
	}
	
	@Override
	public String toString() 
	{
		return " " + dscamount;
	}
	
}
