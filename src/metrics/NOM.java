package metrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;

public class NOM 
{
  private Map<String, Integer> classMap = new HashMap<String, Integer>();
  private double NOM_Value;
  public NOM(SystemObject system)
  {
    Set<ClassObject> classes = system.getClassObjects();
    
    for(ClassObject classObject : classes)
    {
      classMap.put(classObject.getName(), classObject.getNumberOfMethods());
      NOM_Value += classObject.getNumberOfMethods();
    }
    NOM_Value = NOM_Value/classes.size(); 
    
  }
  
  @Override
  public String toString() 
  {
    StringBuilder sb = new StringBuilder();
    for(String key : classMap.keySet()) 
    {
      sb.append(key).append("\t").append(classMap.get(key)).append("\n");
    }
    return sb.toString();
  }
  
  public String toString2() {
    return "NOM Value: "+NOM_Value;
  }

}
