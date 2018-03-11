package metrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;
import ast.inheritance.InheritanceDetection;
import ast.inheritance.InheritanceTree;

public class NOC {
	
	//---------------------------------------------------------------------------
	private Map<String, Integer> NOC_Map = new HashMap<String, Integer>();

	public double NOC_Value = 0;
	
	//----------------------------------------------------------------------------
	public NOC(SystemObject system) {
		
		//set of classes which does not duplicate
		Set<ClassObject> classes = system.getClassObjects();

		for (ClassObject classObject : classes) {
			
			int computeNOC = computeNOC(system, classObject);
			
			NOC_Value += computeNOC;
			
			NOC_Map.put(classObject.getName(), computeNOC);
		}
		
		NOC_Value = NOC_Value/classes.size();

	}

	
	//-------------------------------------------------------------------------------
	private int computeNOC(SystemObject system, ClassObject classObject) {
		
		InheritanceDetection inheritanceDetection = new InheritanceDetection(system);
		InheritanceTree inheritanceTree = inheritanceDetection.getTree(classObject.getName());
		
		int childCount = 0;
		if(inheritanceTree != null)
		{
			childCount = inheritanceTree.getRootNode().getChildCount();
		}
		return childCount;
	}
	
	
	//-------------------------------------------------------------------------------
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for (String key : NOC_Map.keySet()) {
			stringBuilder.append(key).append("\t").append(NOC_Map.get(key)).append("\n");
		}
		return stringBuilder.toString();
	}
	
	
	//-------------------------------------------------------------------------------
	public String toString2() {
		return "NOC Value is: "+NOC_Value;
	}
	
}
