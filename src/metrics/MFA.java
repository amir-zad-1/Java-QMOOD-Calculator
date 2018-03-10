package metrics;

import java.util.Set;
import ast.SystemObject;
import ast.ClassObject;



public class MFA {
	
	public float mfaValue;
	
	public MFA(SystemObject systemObj)
	{
		Set<ClassObject> classes = systemObj.getClassObjects();
		for(ClassObject classObject : classes)
		{
			if(!classObject.isInterface())
			{
				mfaValue += systemObj.computeMFA(classObject);
			}
			else
			{
				mfaValue += 0;
			}
		}
		mfaValue = mfaValue/classes.size();
	}

	public String toString() {
		return "System_Value: "+mfaValue;
	}
}