package metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.*;
import ast.inheritance.InheritanceDetection;
import ast.inheritance.InheritanceTree;



public class CBO {
  private Map<String,  Integer> cboMap; //CBO Map (Class name and Coupling Value)
  private List<String> allClassesInSystem = new ArrayList<String>();
  public double CBO_Value;
  
  public CBO(SystemObject system) {
    // Hashmap to create unique key, value pairs
    cboMap = new HashMap<String,  Integer>();
    Set<ClassObject> classes = system.getClassObjects();
    allClassesInSystem = system.getClassNames();
    
    for(ClassObject classObj : classes) {
      // inherit coupling, export coupling and import coupling
      int importCoupling = computeImportCoupling(system, classObj);
      int exportCoupling = computeExportCoupling(system, classObj);
      int inheritanceCoupling = computeExportInheritanceCoupling(system, classObj); 
      inheritanceCoupling += computeImportInheritanceCoupling(system, classObj);
      // Calculate total of import, export and inherited values
      int cboValue = exportCoupling + inheritanceCoupling + importCoupling;
      CBO_Value += cboValue;
    
      cboMap.put(classObj.getName(), cboValue);
    }
    CBO_Value = CBO_Value/classes.size();
  }
  
  
  //Import Coupling
  private int computeImportCoupling(SystemObject system, ClassObject classObject) 
  {
    Set<ClassObject> allClassObjectInSystem = system.getClassObjects();
    int counter = 0;
    for(ClassObject eachClass : allClassObjectInSystem) 
    {
      List<FieldObject> classFields = eachClass.getFieldList();
      for(int i=0; i<classFields.size(); i++)
      {
        TypeObject to = classFields.get(i).getType();
        if(allClassesInSystem.contains(to.toString()))
        {
          if(to.toString().equals(classObject.getName()))
          {
            counter++; 
            break;
          }
        }
      }
    }
    return counter;
  }
  
  //Code for export couploing
  private int computeExportCoupling(SystemObject system, ClassObject classObj) {
    String className = classObj.getName();
    Set<String> distinctMethodCalls = new HashSet<String>();
    
    List<FieldObject> fields = classObj.getFieldList();
    List<MethodObject> methods = classObj.getMethodList();
    
    //Method Calls
    for(int i=0; i<methods.size(); i++)
    {
       List<MethodInvocationObject> methodInvocations = methods.get(i).getMethodInvocations();
       
       for(int j=0; j<methodInvocations.size(); j++)
       {
         String originalClassName = methodInvocations.get(j).getOriginClassName();
         if(className != originalClassName)
         {
           distinctMethodCalls.add(originalClassName);
         }
       }
      
    }
    
    for(int k=0; k<fields.size(); k++)
    {
      TypeObject to = fields.get(k).getType();

      if(to.toString() != className)
      {
        if(allClassesInSystem.contains(to.toString()))
        {
          distinctMethodCalls.add(to.toString());
        }
      }
      
    }
    
    return distinctMethodCalls.size();
  }
  
  

  private int computeExportInheritanceCoupling(SystemObject system, ClassObject classObj) {
    int count = 0;
      if(classObj.getSuperclass() != null)
      {
        count+=1;
      }
    return count;
  }
  
  // Inheritance import
  private int computeImportInheritanceCoupling(SystemObject system, ClassObject classObj) {
    InheritanceDetection inhDet = new InheritanceDetection(system);
    InheritanceTree inhTree = inhDet.getTree(classObj.getName());
    int childCount = 0;
    if(inhTree != null)
    {
      childCount = inhTree.getRootNode().getChildCount();
    }
    return childCount;
    
  }
  
  @Override
  public String toString() 
  {
    StringBuilder sb = new StringBuilder();
    for(String key : cboMap.keySet()) 
    {
      sb.append(key).append("\t").append(cboMap.get(key)).append("\n");
    }
    return sb.toString();
  }
  
  public String toString2() {
    return "CBO_Value: "+CBO_Value;
  }
  
}
