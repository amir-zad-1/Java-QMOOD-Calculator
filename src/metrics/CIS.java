package metrics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import ast.ClassObject;
import ast.SystemObject;
import ast.MethodObject;

public class CIS 
{
	//mahsa ghoreishi

	public int systemValue=0;
	public CIS(SystemObject system) 
	{
		//the number of public methods in all classes
		
		List<MethodObject> arrMethods = new ArrayList();

		//get classes of the system
		Set<ClassObject> setClasses = system.getClassObjects();
		
		
		Iterator<ClassObject> classesItrator = setClasses.iterator();
		while(classesItrator.hasNext())
		{
			ClassObject classObject = classesItrator.next();
			 
				
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
				}
			
			}
			
		}
		systemValue = systemValue/setClasses.size();
	}
	

	public String toString2() {
		return "System_Value: "+systemValue;
	}

}
