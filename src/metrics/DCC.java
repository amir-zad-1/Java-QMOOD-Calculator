package metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.FieldObject;
import ast.SystemObject;
import ast.TypeObject;



public class DCC { 
	private List<String> ClassesInPackage = new ArrayList<String>();
	private Map<String, Integer> classMap = new HashMap<String, Integer>();//SMYH
	public double dccValue;
	
	public DCC(SystemObject system) {
		Set<ClassObject> classes = system.getClassObjects();
		ClassesInPackage = system.getClassNames();
		
		for(ClassObject classObj : classes) {
			
			int IMCoupling = ExportCouplingCalculation(system, classObj);
			
			dccValue += IMCoupling;
			classMap.put(classObj.getName(), IMCoupling);//SMYH
		}
		dccValue = dccValue / classes.size();
	}

	
	
	private int ExportCouplingCalculation(SystemObject system, ClassObject classObj) 
	{
		Set<ClassObject> ObjectsInPackage = system.getClassObjects();
		int value = 0;
		for(ClassObject eachClass : ObjectsInPackage) 
		{
			System.out.println("class :" + eachClass);
			List<FieldObject> classFields = eachClass.getFieldList();
			for(int i=0; i<classFields.size(); i++)
			
			{
				TypeObject to =	classFields.get(i).getType();
				if(ClassesInPackage.contains(to.toString()))
				{
					if(to.toString().equals(classObj.getName()))
					
					{
						value++;
						
						System.out.println("value1 :" + value);
						
						
						break;
					}
					System.out.println("value2 :" + value);
				}
			}
			classMap.put(classObj.getName(), value);//SMYH
		}
		
		return value;
	}
	
	
	
	
	@Override
	public String toString() 
	{
		return "System_Value: "+dccValue;
	}
	public String toString2() //SMYH
	{
		StringBuilder sb = new StringBuilder();
		for (String key : classMap.keySet()) {
			sb.append(key).append("\t").append(classMap.get(key)).append("\n");
		}
		return sb.toString();
	}
	
}
