package metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ast.ClassObject;
import ast.FieldObject;
import ast.SystemObject;
import ast.TypeObject;



public class DCC { 
	private List<String> ClassesInPackage = new ArrayList<String>();
	public double dccValue;
	
	public DCC(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();
		ClassesInPackage = system.getClassNames();
		
		for(ClassObject classObj : classes) {
			
			int IMCoupling = ExportCouplingCalculation(system, classObj);
			
			dccValue += IMCoupling;
		}
		dccValue = dccValue / classes.size();
	}

	
	
	private int ExportCouplingCalculation(SystemObject system, ClassObject classObj) 
	{
		Set<ClassObject> ObjectsInPackage = system.getClassObjects();
		int value = 0;
		for(ClassObject eachClass : ObjectsInPackage) 
		{
			List<FieldObject> classFields = eachClass.getFieldList();
			for(int i=0; i<classFields.size(); i++)
			{
				TypeObject to =	classFields.get(i).getType();
				if(ClassesInPackage.contains(to.toString()))
				{
					if(to.toString().equals(classObj.getName()))
					{
						value++; 
						break;
					}
				}
			}
		}
		return value;
	}
	
	
	
	@Override
	public String toString() 
	{
		return "System_Value: "+dccValue;
	}
	
}