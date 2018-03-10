package metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;
import ast.TypeObject;


public class MIF {
	private Map<String, Float> classMap;
	public double mifValue;
	
	public MIF(SystemObject systemObj)
	{
		Set<ClassObject> classes = systemObj.getClassObjects();
		classMap = new HashMap<String, Float>();
		float mValue = 0.0f;
		for(ClassObject classObject : classes)
		{
			if(!classObject.isInterface())
			{
				mValue = computeMIF(systemObj, classObject);
				mifValue += mValue;
				classMap.put(classObject.getName(), mValue);
			}
			else
			{
				classMap.put(classObject.getName(), 0.0f);
			}
		}
		mifValue = mifValue/classes.size();
	}

	private float computeMIF(SystemObject systemObj, ClassObject classObject) {
		List<MethodObject> DeclaredMethod = classObject.getMethodList();
		List<MethodObject> inheritedMethods = getInheritedMethods(systemObj, classObject); 
		
		if(DeclaredMethod.size()>0)
		{
			 return (float) inheritedMethods.size() / DeclaredMethod.size();
		}
		else
		{
			return 0.0f;
		}
	}

	private List<MethodObject> getInheritedMethods(SystemObject systemObj, ClassObject classObject) 
	{
		List<MethodObject> allMethodsInSub = classObject.getMethodList();
		List<MethodObject> methods = new ArrayList<MethodObject>();
		TypeObject superCType = classObject.getSuperclass();
		if(classObject.getSuperclass() != null)
		{
			ClassObject superCClass = systemObj.getClassObject(superCType.getClassType());
			try 
			{ 
				methods = superCClass.getMethodList();
				for(int i=0; i<methods.size(); i++)
				{
					if(allMethodsInSub.contains(methods.get(i)))
					{
						methods.remove(i);
					}
					if((methods.get(i).getAccess().toString() != "public") && (methods.get(i).getAccess().toString() != "protected") && (methods.get(i).isStatic()))
					{
						methods.remove(i);
					}
				}
				while(superCClass != null && superCClass.getSuperclass() != null)
				{
					if(superCClass != null && superCClass.getSuperclass() != null)
					{
						superCClass = systemObj.superCClass(superCType, superCClass);
						try{  
							methods.addAll(superCClass.getMethodList());
							for(int i=0; i<methods.size(); i++)
							{
								if(allMethodsInSub.contains(methods.get(i)))
								{
									methods.remove(i);
								}
								if((methods.get(i).getAccess().toString() != "public") && (methods.get(i).getAccess().toString() != "protected") && (methods.get(i).isStatic()))
								{
									methods.remove(i);
								}
							}
						}
						catch(Exception e)
						{
							return Collections.emptyList();
						}
					}
					else
					{
						break;
					}
				}
			}
			catch(Exception e)
			{
				return Collections.emptyList();
			}
		}
		return methods;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : classMap.keySet()) {
			sb.append(key).append("\t").append(classMap.get(key)).append("\n");
		}
		return sb.toString();
	}
	
	public String toString2() {
		return "System_Value: "+mifValue;
	}
}