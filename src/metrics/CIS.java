package metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ast.ClassObject;
import ast.SystemObject;
import ast.MethodObject;

public class CIS 
{
	//mahsa ghoreishi
	private Map<String, Integer> classMap = new HashMap<String, Integer>();
	public double systemValue=0;
	
	public CIS(SystemObject system) 
	{
		int classesValue=0;
		//the number of public methods in all classes
		
		List<MethodObject> arrMethods = new ArrayList();

		//get classes of the system
		Set<ClassObject> setClasses = system.getClassObjects();
		
		
		Iterator<ClassObject> classesItrator = setClasses.iterator();
		while(classesItrator.hasNext())
		{
			ClassObject classObject = classesItrator.next();
			classesValue = 0; 
				
			//get methods of the class
			arrMethods = classObject.getMethodList(); 
			
			//iterate over methods of every class
			for(MethodObject method : arrMethods)
			{
				//count the public methods in a class
				String modifier = method.getAccess().toString();
				if(modifier.equals("public"))
				{
					systemValue +=1;
					classesValue += 1; 
				}
			
			}
			classMap.put(classObject.getName(), classesValue);
			
		}
		systemValue = systemValue/setClasses.size();
	}
	

	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		for(String key : classMap.keySet()) 
		{
			sb.append(key).append("\t").append(classMap.get(key)).append("\n");
		}
		return sb.toString();
	}
	
	public String toString2() {
		return " " + systemValue;
	}

}
