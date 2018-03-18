package metrics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;

public class CF {
	double TC;
	double cnt;

	double CF; // Coupling Factor 
//Mahsa ghoreishi
	List<String> doneClasses = new ArrayList(); // Classes that are already visited
	
	public CF(SystemObject system) {
		
		
		
		
		Set<ClassObject> setClasses = system.getClassObjects();		
		Iterator<ClassObject> classesItrator = setClasses.iterator();
		
		
		Set<ClassObject> setClassesInner = system.getClassObjects();		
		Iterator<ClassObject> innerItrator = setClassesInner.iterator();
		
		
		//iterato on a class
		while(classesItrator.hasNext()) {
			ClassObject classObject = classesItrator.next();
		
			if(!classObject.isInterface()){
				TC=TC+1;
				doneClasses.add(classObject.getName());
				//for every class again iterate on all classes of the system.
				innerItrator = system.getClassListIterator();
				while(innerItrator.hasNext()) {
					ClassObject innerObject = innerItrator.next();
					if(!innerObject.isInterface()){
						
						if(!doneClasses.contains(innerObject.getName())){
							if(innerObject.equals(classObject) || innerObject.isFriend(classObject.getName())||
									classObject.equals(innerObject) || classObject.isFriend(innerObject.getName()))
							{
								cnt++;
							}
								
						}
					}
				}
			}
		}
		
		CF=cnt/((TC*TC)-TC); 
		

	}


	public String toString() {
		return " " + CF;
	}


}
