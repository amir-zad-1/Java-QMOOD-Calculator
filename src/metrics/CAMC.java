package metrics;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;


public class CAMC {
	public float CAMC_Value;

	public CAMC(SystemObject system){
		Set<ClassObject> classes = system.getClassObjects();
		
			
		for(ClassObject classObject : classes){
			CAMC_Value += computeCAMC(system,classObject);
		}
		CAMC_Value = CAMC_Value/classes.size();
		
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
		return "CAMC Value: "+CAMC_Value;
	}
	
}