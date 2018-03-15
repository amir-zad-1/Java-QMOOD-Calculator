package ast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class SystemObject {

    private List<ClassObject> classList;
    //Map that has as key the classname and as value
    //the position of className in the classNameList
    private Map<String, Integer> classNameMap;
    private Map<MethodInvocationObject, FieldInstructionObject> getterMap;
    private Map<MethodInvocationObject, FieldInstructionObject> setterMap;
    private Map<MethodInvocationObject, FieldInstructionObject> collectionAdderMap;
    private Map<MethodInvocationObject, MethodInvocationObject> delegateMap;

    public SystemObject() {
        this.classList = new ArrayList<ClassObject>();
        this.classNameMap = new HashMap<String, Integer>();
        this.getterMap = new LinkedHashMap<MethodInvocationObject, FieldInstructionObject>();
        this.setterMap = new LinkedHashMap<MethodInvocationObject, FieldInstructionObject>();
        this.collectionAdderMap = new LinkedHashMap<MethodInvocationObject, FieldInstructionObject>();
        this.delegateMap = new LinkedHashMap<MethodInvocationObject, MethodInvocationObject>();
    }

    public void addClass(ClassObject c) {
        classNameMap.put(c.getName(),classList.size());
        classList.add(c);
    }
    
    public void addClasses(List<ClassObject> classObjects) {
    	for(ClassObject classObject : classObjects)
    		addClass(classObject);
    }
    
    public void replaceClass(ClassObject c) {
    	int position = getPositionInClassList(c.getName());
    	if(position != -1) {
    		classList.remove(position);
    		classList.add(position, c);
    	}
    	else {
    		addClass(c);
    	}
    }
    
    public void removeClasses(IFile file) {
    	List<ClassObject> classesToBeRemoved = new ArrayList<ClassObject>();
    	for(ClassObject classObject : classList) {
    		if(classObject.getIFile().equals(file))
    			classesToBeRemoved.add(classObject);
    	}
    	for(ClassObject classObject : classesToBeRemoved) {
    		removeClass(classObject);
    	}
    }
    
    public void removeClass(ClassObject c) {
    	int position = getPositionInClassList(c.getName());
    	if(position != -1) {
    		for(int i=position+1; i<classList.size(); i++) {
    			ClassObject classObject = classList.get(i);
    			classNameMap.put(classObject.getName(), classNameMap.get(classObject.getName())-1);
    		}
    		classNameMap.remove(c.getName());
    		classList.remove(c);
    	}
    }
    
    public void addGetter(MethodInvocationObject methodInvocation, FieldInstructionObject fieldInstruction) {
    	getterMap.put(methodInvocation, fieldInstruction);
    }
    
    public void addSetter(MethodInvocationObject methodInvocation, FieldInstructionObject fieldInstruction) {
    	setterMap.put(methodInvocation, fieldInstruction);
    }
    
    public void addCollectionAdder(MethodInvocationObject methodInvocation, FieldInstructionObject fieldInstruction) {
    	collectionAdderMap.put(methodInvocation, fieldInstruction);
    }
    
    public void addDelegate(MethodInvocationObject methodInvocation, MethodInvocationObject delegation) {
    	delegateMap.put(methodInvocation, delegation);
    }
    
    public FieldInstructionObject containsGetter(MethodInvocationObject methodInvocation) {
    	return getterMap.get(methodInvocation);
    }
    
    public FieldInstructionObject containsSetter(MethodInvocationObject methodInvocation) {
    	return setterMap.get(methodInvocation);
    }
    
    public FieldInstructionObject containsCollectionAdder(MethodInvocationObject methodInvocation) {
    	return collectionAdderMap.get(methodInvocation);
    }
    
    public MethodInvocationObject containsDelegate(MethodInvocationObject methodInvocation) {
    	return delegateMap.get(methodInvocation);
    }
    
    public MethodObject getMethod(MethodInvocationObject mio) {
    	ClassObject classObject = getClassObject(mio.getOriginClassName());
    	if(classObject != null)
    		return classObject.getMethod(mio);
    	return null;
    }

    public MethodObject getMethod(SuperMethodInvocationObject smio) {
    	ClassObject classObject = getClassObject(smio.getOriginClassName());
    	if(classObject != null)
    		return classObject.getMethod(smio);
    	return null;
    }

    public boolean containsMethodInvocation(MethodInvocationObject methodInvocation, ClassObject excludedClass) {
    	for(ClassObject classObject : classList) {
    		if(!excludedClass.equals(classObject) && classObject.containsMethodInvocation(methodInvocation))
    			return true;
    	}
    	return false;
    }

    public boolean containsFieldInstruction(FieldInstructionObject fieldInstruction, ClassObject excludedClass) {
    	for(ClassObject classObject : classList) {
    		if(!excludedClass.equals(classObject) && classObject.containsFieldInstruction(fieldInstruction))
    			return true;
    	}
    	return false;
    }

    public boolean containsSuperMethodInvocation(SuperMethodInvocationObject superMethodInvocation) {
    	for(ClassObject classObject : classList) {
    		if(classObject.containsSuperMethodInvocation(superMethodInvocation))
    			return true;
    	}
    	return false;
    }

    public ClassObject getClassObject(String className) {
        Integer pos = classNameMap.get(className);
        if(pos != null)
            return getClassObject(pos);
        else
            return null;
    }

    public ClassObject getClassObject(int pos) {
        return classList.get(pos);
    }

    public ListIterator<ClassObject> getClassListIterator() {
        return classList.listIterator();
    }

    public int getClassNumber() {
        return classList.size();
    }

    public int getPositionInClassList(String className) {
        Integer pos = classNameMap.get(className);
        if(pos != null)
            return pos;
        else
            return -1;
    }

    public Set<ClassObject> getClassObjects() {
    	Set<ClassObject> classObjectSet = new LinkedHashSet<ClassObject>();
    	classObjectSet.addAll(classList);
    	return classObjectSet;
    }

    public Set<ClassObject> getClassObjects(IPackageFragmentRoot packageFragmentRoot) {
    	Set<ClassObject> classObjectSet = new LinkedHashSet<ClassObject>();
    	try {
    		IJavaElement[] children = packageFragmentRoot.getChildren();
    		for(IJavaElement child : children) {
    			if(child.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
    				IPackageFragment packageFragment = (IPackageFragment)child;
    				classObjectSet.addAll(getClassObjects(packageFragment));
    			}
    		}
    	} catch(JavaModelException e) {
			e.printStackTrace();
		}
    	return classObjectSet;
    }

    public Set<ClassObject> getClassObjects(IPackageFragment packageFragment) {
    	Set<ClassObject> classObjectSet = new LinkedHashSet<ClassObject>();
    	try {
    		ICompilationUnit[] packageCompilationUnits = packageFragment.getCompilationUnits();
			for(ICompilationUnit iCompilationUnit : packageCompilationUnits) {
				classObjectSet.addAll(getClassObjects(iCompilationUnit));
			}
    	} catch(JavaModelException e) {
			e.printStackTrace();
		}
    	return classObjectSet;
    }

    public Set<ClassObject> getClassObjects(ICompilationUnit compilationUnit) {
    	Set<ClassObject> classObjectSet = new LinkedHashSet<ClassObject>();
		try {
			IType[] topLevelTypes = compilationUnit.getTypes();
			for(IType type : topLevelTypes) {
				classObjectSet.addAll(getClassObjects(type));
			}
		} catch(JavaModelException e) {
			e.printStackTrace();
		}
    	return classObjectSet;
    }

    public Set<ClassObject> getClassObjects(IType type) {
    	Set<ClassObject> classObjectSet = new LinkedHashSet<ClassObject>();
    	String typeQualifiedName = type.getFullyQualifiedName('.');
    	ClassObject classObject = getClassObject(typeQualifiedName);
    	if(classObject != null)
    		classObjectSet.add(classObject);
    	try {
			IType[] nestedTypes = type.getTypes();
			for(IType nestedType : nestedTypes) {
				classObjectSet.addAll(getClassObjects(nestedType));
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
    	return classObjectSet;
    }

    public AbstractMethodDeclaration getMethodObject(IMethod method) {
    	IType declaringType = method.getDeclaringType();
    	ClassObject classObject = getClassObject(declaringType.getFullyQualifiedName('.'));
    	if(classObject != null) {
    		ListIterator<MethodObject> mi = classObject.getMethodIterator();
    		while(mi.hasNext()) {
                MethodObject mo = mi.next();
                IMethod resolvedMethod = (IMethod)mo.getMethodDeclaration().resolveBinding().getJavaElement();
                if(method.isSimilar(resolvedMethod))
                	return mo;
    		}
    		ListIterator<ConstructorObject> ci = classObject.getConstructorIterator();
    		while(ci.hasNext()) {
    			ConstructorObject co = ci.next();
                IMethod resolvedMethod = (IMethod)co.getMethodDeclaration().resolveBinding().getJavaElement();
                if(method.isSimilar(resolvedMethod))
                	return co;
    		}
    	}
    	return null;
    }

    public List<String> getClassNames() {
        List<String> names = new ArrayList<String>();
        for(int i=0; i<classList.size(); i++) {
            names.add(getClassObject(i).getName());
        }
        return names;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(ClassObject classObject : classList) {
            sb.append(classObject.toString());
            sb.append("\n--------------------------------------------------------------------------------\n");
        }
        return sb.toString();
    }
	public List<MethodObject> getInheritedMethods(ClassObject classObject) {
		List<MethodObject> allMethodsInSub = classObject.getMethodList();
		List<MethodObject> methods = new ArrayList<MethodObject>();
		TypeObject superCType = classObject.getSuperclass();
		if (classObject.getSuperclass() != null) {
			ClassObject superCClass = getClassObject(superCType.getClassType());
			try {
				methods = superCClass.getMethodList();
				for (int i = 0; i < methods.size(); i++) {
					if (allMethodsInSub.contains(methods.get(i))) {
						methods.remove(i);
					}
					if ((methods.get(i).getAccess().toString() != "public")
							&& (methods.get(i).getAccess().toString() != "protected") && (methods.get(i).isStatic())) {
						methods.remove(i);
					}
				}
				while (superCClass != null && superCClass.getSuperclass() != null) {
					if (superCClass != null && superCClass.getSuperclass() != null) {
						superCType = superCClass.getSuperclass();
						superCClass = getClassObject(superCType.getClassType());
						try {
							methods.addAll(superCClass.getMethodList());
							for (int i = 0; i < methods.size(); i++) {
								if (allMethodsInSub.contains(methods.get(i))) {
									methods.remove(i);
								}
								if ((methods.get(i).getAccess().toString() != "public")
										&& (methods.get(i).getAccess().toString() != "protected")
										&& (methods.get(i).isStatic())) {
									methods.remove(i);
								}
							}
						} catch (Exception e) {
							return Collections.emptyList();
						}
					} else {
						break;
					}
				}
			} catch (Exception e) {
				return Collections.emptyList();
			}
		}
		return methods;
	}

	public float computeMFA(ClassObject classObject) {
		List<MethodObject> methodsDeclaredINCurrentClass = classObject.getMethodList();
		List<MethodObject> inheritedMethods = getInheritedMethods(classObject);
		if (inheritedMethods.size() == 0) {
			return 0.0f;
		} else if (methodsDeclaredINCurrentClass.size() + inheritedMethods.size() > 0) {
			return (float) inheritedMethods.size() / (methodsDeclaredINCurrentClass.size() + inheritedMethods.size());
		} else {
			return 0.0f;
		}
	}

	public float computeMIF(ClassObject classObject) {
		List<MethodObject> DeclaredMethod = classObject.getMethodList();
		List<MethodObject> inheritedMethods = getInheritedMethods(classObject); 
		
		if(DeclaredMethod.size()>0)
		{
			 return (float) inheritedMethods.size() / DeclaredMethod.size();
		}
		else
		{
			return 0.0f;
		}
	}
	public ClassObject superCClass(TypeObject superCType, ClassObject superCClass) {
		superCType = superCClass.getSuperclass();
		superCClass = getClassObject(superCType.getClassType());
		return superCClass;
	}
}
