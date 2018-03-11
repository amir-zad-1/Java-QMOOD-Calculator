package metrics;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ast.ClassObject;
import ast.MethodInvocationObject;
import ast.MethodObject;
import ast.SystemObject;

public class RFC 
{
  
  private Map<String, Integer> classMap;
  public Set<String> M;
  public Set<String> R;
  private int RFC;
  public double RFC_Value;
  
  public RFC(SystemObject system)
  {
    classMap = new HashMap<String, Integer>();
    Set<ClassObject> classes = system.getClassObjects();
    int rfcValue;
    for(ClassObject classObject : classes)
    {
      rfcValue = computeRFC(system,classObject);
      RFC_Value += rfcValue;
      classMap.put(classObject.getName(), rfcValue);
    }
    RFC_Value = RFC_Value/classes.size();
  }
  
  private int computeRFC(SystemObject system, ClassObject classObject)
  {
    M = new HashSet<String>();
    R = new HashSet<String>();
    RFC = 0;
    
    List<String> classesNames = system.getClassNames();
    
    List<MethodObject> methods = classObject.getMethodList();
    
    for(int i=0; i<methods.size(); i++)
    {
       M.add(methods.get(i).getClassName()+"."+methods.get(i).getSignature());
       
       List<MethodInvocationObject> methodInvocations = methods.get(i).getMethodInvocations();
       
       for(int j=0; j<methodInvocations.size(); j++)
       {
         String originalClassName = methodInvocations.get(j).getOriginClassName();
         if(classesNames.contains(originalClassName))
         {
           R.add(originalClassName+"."+methodInvocations.get(j).getSignature());
         }
       }
      
    }
    RFC = unionOfSets(M,R);
    return RFC;
  }
  
  private int unionOfSets(Set<String> set1, Set<String> set2)
  {
    set1.addAll(set2);
    return set1.size();
    
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
    return "RFC Value: "+RFC_Value;
  }

}