package metrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;


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
				mValue = systemObj.computeMIF(classObject);
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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : classMap.keySet()) {
			sb.append(key).append("\t").append(classMap.get(key)).append("\n");
		}
		return sb.toString();
	}
	
	public String toString2() {
		return " " + mifValue;
	}
}
