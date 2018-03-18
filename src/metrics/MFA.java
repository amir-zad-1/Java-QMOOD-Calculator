package metrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;


public class MFA {
	
	public float mfaValue = 0;
	float MFACount = 0;
	
	private Map<String, Float> classMap = new HashMap<String, Float>();//SMYH
	
	public MFA(SystemObject systemObj)
	{
		Set<ClassObject> classes = systemObj.getClassObjects();
		for(ClassObject classObject : classes)
		{
			
			if(!classObject.isInterface())
			{
				MFACount = systemObj.computeMFA(classObject);
				mfaValue += MFACount;
				
			}
			else
			{
				mfaValue += 0;
			}
			classMap.put(classObject.getName(),MFACount );//SMYH
		}
		mfaValue = mfaValue/classes.size();
	}
	public String toString() {
		return "System_Value: "+mfaValue;
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

