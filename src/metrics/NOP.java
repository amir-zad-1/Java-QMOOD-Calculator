package metrics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;

public class NOP { 
  public int nopValue;
  private Map<String, Integer> classMap = new HashMap<String, Integer>();//SMYH
  
  public NOP(SystemObject system) {
    Set<ClassObject> classes = system.getClassObjects();
    
    for(ClassObject classObj : classes) {
    	
    	int NOPCount = computeNOP(system, classObj);
    	nopValue += NOPCount;
    	
    	classMap.put(classObj.getName(), NOPCount);//SMYH
    }
  }

  private int computeNOP(SystemObject system, ClassObject classObj) 
  {
    int counter = 0;
    List<MethodObject> methods = classObj.getMethodList();
    for(int i=0; i<methods.size(); i++)
    {
      if(methods.get(i).isAbstract())
      {
        counter++;
      }
    }
    return counter;
  }
  
  @Override
  public String toString() 
  {
    return " " + nopValue;
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
