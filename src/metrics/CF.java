package metrics;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;

public class CF {
  double total_classes,numerator_value;
  double coupling_factor_value;
  String totalCFStr; 
  Set<String> CompletedClasses = new HashSet<String>();
  
  public CF(SystemObject system) {
    ListIterator<ClassObject> iterator1 = system.getClassListIterator();
    ListIterator<ClassObject> iterator2 = system.getClassListIterator();
    
    while(iterator1.hasNext()) {
      
      ClassObject cObj1 = iterator1.next();
      if(cObj1.isInterface())
        continue;
      total_classes++;
      CompletedClasses.add(cObj1.getName());
      iterator2 = system.getClassListIterator();
      while(iterator2.hasNext()) {
        ClassObject cObj2 = iterator2.next();
        if(cObj2.isInterface())
          continue;
        if(!CompletedClasses.contains(cObj2.getName())){
          if(cObj2.equals(cObj1) || cObj2.isFriend(cObj1.getName())||
              cObj1.equals(cObj2) || cObj1.isFriend(cObj2.getName()))
          {
            numerator_value++;
          }
            
        }
      }
    }
    
    coupling_factor_value=numerator_value/((total_classes*total_classes)-total_classes); 
    totalCFStr=numerator_value+"/"+((total_classes*total_classes)-total_classes);
  }

  public double getCF(){
    return coupling_factor_value;
  }
  
  public String toString() {
    return "Sytem_Value: "+coupling_factor_value;
  }
}
