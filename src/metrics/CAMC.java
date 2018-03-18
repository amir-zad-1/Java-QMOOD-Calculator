package metrics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;


public class CAMC {
	public float camcValue;
	private Map<String, Float> classMap = new HashMap<String, Float>();//SMYH

	public CAMC(SystemObject system){
		Set<ClassObject> classes = system.getClassObjects();
		
			
		for(ClassObject classObject : classes){
			float CAMC = computeCAMC(system,classObject);;
			camcValue += CAMC; 
			classMap.put(classObject.getName(), CAMC);//SMYH
		}
		camcValue = camcValue/classes.size();
		
	}

	private float computeCAMC(SystemObject system, ClassObject classObject){
		float camc;
		int paramTypes = 0;
		Set<String> distinctParameters = new HashSet<String>();
		Set<String> totalDistinctParameters = new HashSet<String>();
		List<MethodObject> methods = classObject.getMethodList();
		
		int nbreMethods = methods.size(); //Number of methods in class 
		int nbreParamType= 0; //Number of parameter types in class 
		
	
		
			for(int i=0; i<nbreMethods; i++){
				List<String> parameters = methods.get(i).getParameterList();
				 
				 for(int j=0; j<parameters.size(); j++)
				 {
					 distinctParameters.add(parameters.get(j));
					 totalDistinctParameters.add(parameters.get(j));
				 }
				 paramTypes += distinctParameters.size()+1;
				 nbreParamType = totalDistinctParameters.size();
				 distinctParameters.removeAll(parameters);
			}
			
			if(nbreMethods == 0 ) {
				return 0;
      }	else {
				camc =(float) paramTypes /(nbreMethods * (nbreParamType+1));
				return camc;
      }
	}
	
	public String toString() {
		return " " + camcValue;
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
