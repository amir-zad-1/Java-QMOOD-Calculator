package metrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import ast.ClassObject;
import ast.SystemObject;
import ast.TypeObject;

public class DIT {
	private Map<String, Integer> DITMap = new HashMap<String, Integer>();
	public double systemValue;

	public DIT(SystemObject system) {
		Set<ClassObject> ProjectClasses = system.getClassObjects();

		for (ClassObject classObject : ProjectClasses) {
			int DITCalculation = DITCalculation(system, classObject);
			systemValue += DITCalculation;

			DITMap.put(classObject.getName(), DITCalculation);
		}
		systemValue = systemValue/ProjectClasses.size();
	}

	int DITCalculation(SystemObject system, ClassObject classObject) {
		TypeObject superCType = classObject.getSuperclass();
		int NumOfDIT = 0;
		if(classObject.getSuperclass() != null)
		{
			NumOfDIT++;
			
			ClassObject superCClass = system.getClassObject(superCType.getClassType());
			while(superCClass != null && superCClass.getSuperclass() != null)
			{
				if(superCClass != null && superCClass.getSuperclass() != null)
				{
					NumOfDIT++;
					
				}
				else
				{
					break;
				}
				superCType = superCClass.getSuperclass();
				superCClass = system.getClassObject(superCType.getClassType());
			}
		}
		return NumOfDIT;
		
	}
	
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		for (String key : DITMap.keySet()) {
			sb.append(key).append("\t").append(DITMap.get(key)).append("\n");
		}
		return sb.toString();
	}
	
	public String toString2() {
		return " " + systemValue;
	}

}
