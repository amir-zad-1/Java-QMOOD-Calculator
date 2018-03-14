package metrics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.FieldInstructionObject;
import ast.MethodObject;
import ast.SystemObject;

public class LCOM {
	
	private Map<String, Integer> cohesionamountMap;

	public LCOM(SystemObject system) {
		
		Set<ClassObject> classes = system.getClassObjects();
		cohesionamountMap = new HashMap<String, Integer>();
		
		
		
		for(ClassObject classObject : classes) {
			int cohesionamount = computecohesionamount(classObject);
			if(cohesionamount != -1) {
				cohesionamountMap.put(classObject.getName(), cohesionamount);
			}
		}
		
	}
	
	private int computecohesionamount(ClassObject classObject) {
		
		
		int a = 0;
		int s = 0;
		List<MethodObject> methods = classObject.getMethodList();
		
		if(methods.size() < 2) {
			return -1;
		}
		
		for(int l=0; l<methods.size()-1; l++) {
			MethodObject nL = methods.get(l);
			List<FieldInstructionObject> attributesL = nL.getFieldInstructions();
			
			for(int k=l+1; k<methods.size(); k++) {
				MethodObject nK = methods.get(k);
				List<FieldInstructionObject> attributesK = nK.getFieldInstructions();
				Set<FieldInstructionObject> intersection = commonAttributes(attributesL, attributesK, classObject.getName());
				
				if(intersection.isEmpty()) {
					a++;
				} else {
					s++;
				}
				
			}
		}
		
		if (a > s) {
			return a - s;
		} else {
			return 0;
		}
	}
	
	private Set<FieldInstructionObject> commonAttributes(List<FieldInstructionObject> attributesL,
			List<FieldInstructionObject> attributesK, String className) {
		
		Set<FieldInstructionObject> commonAttributes = new HashSet<FieldInstructionObject>();
		
		for (FieldInstructionObject instructionL : attributesL) {
			
			if(instructionL.getOwnerClass().equals(className) && attributesK.contains(instructionL)) {
				commonAttributes.add(instructionL);
			}
		}
		return commonAttributes;
		
	}

	@Override
	public String toString() {
		StringBuilder strbuilder = new StringBuilder();
		for(String key : cohesionamountMap.keySet()) {
			strbuilder.append(key).append("\t").append(cohesionamountMap.get(key)).append("\n");
		}
		return strbuilder.toString();
	}
}
