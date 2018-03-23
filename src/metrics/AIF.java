package metrics;

import ast.*;
import java.util.*;


/**
 * Attribute Hiding Factor
 * A = Number of attributes inherited in a class
 * B = A(c) + Number of attributes declared in a class
 * Nom, Denom = 0.0
 * for class in class_diagram
 *     Nom += A(class)
 * 
 *  for class in class_diagram
 *      Denom += B(class)
 * 
 * AIF = A / (A+B)
 *      
 * @author amir
 *
 */
public class AIF
{
    private double m_sysVal;
    private Map<String, Float> m_classAif;
    private SystemObject m_sys;

    /**
     * Constructor
     * @param sys
     */
    public AIF(SystemObject sys)
    {
        this.m_sys = sys;
        this.m_classAif = new HashMap<>();
        Set<ClassObject> classes = sys.getClassObjects();

        // Step 1
        this.calcAIF(classes);
    }

    /**
     *
     * @param classes
     */
    private void calcAIF(Set<ClassObject> classes)
    {
    	float result = 0.0f;
    	float nom = 0 , denom = 0;
    	
    	 for(ClassObject c : classes)
         {
             if(c.isInterface() == false)
             {
            	 List<FieldObject> myAttrs = c.getFieldList();
                 List<FieldObject> inheritedAttrs = getInheritedAttrs(c, myAttrs);

            	 nom += inheritedAttrs.size() * 1.0f;
            	 denom += myAttrs.size() * 1.0f;
            	 
             }
         }
    	denom += nom;
    	this.m_sysVal = nom / denom;
    }


    /**
     *
     * @return
     */
    public double getSystemLevelValue()
    {
        return this.m_sysVal;
    }

    /**
     *
     * @param c
     * @return
     */
    private float computeAIF(ClassObject c)
    {
        float result;
        List<FieldObject> myAttrs = c.getFieldList();
        List<FieldObject> inheritedAttrs = getInheritedAttrs(c, myAttrs);

        if(myAttrs.size() > 0)
        {
            result = ((float) inheritedAttrs.size() / myAttrs.size());
        }
        else
        {
            result = 0.0f;
        }

        return result;
    }

    /**
     *
     * @param c
     * @param myAttrs
     * @return
     */
    private List<FieldObject> getInheritedAttrs(ClassObject c, List<FieldObject> myAttrs)
    {
        List<FieldObject> fields = new ArrayList<FieldObject>();
        TypeObject myParent = c.getSuperclass();

        if(c.getSuperclass() != null)
        {
            //Parent Class Object
            ClassObject parentCObject = this.m_sys.getClassObject(myParent.getClassType());

            try
            {
                fields = parentCObject.getFieldList();
                this.removeDups(myAttrs, fields);
                while(parentCObject.getSuperclass() != null)
                {
                    if(parentCObject.getSuperclass() != null)
                    {
                        myParent = parentCObject.getSuperclass();
                        parentCObject = this.m_sys.getClassObject(myParent.getClassType());

                        try
                        {
                            fields.addAll(parentCObject.getFieldList());
                            removeDups(myAttrs, fields);
                        }
                        catch(Exception e)
                        {
                            return Collections.emptyList();
                        }
                    }
                    else
                    {
                        break;
                    }
                }
            }
            catch(Exception e)
            {
                return Collections.emptyList();
            }
        }
        return fields;
    }

    /**
     *
     * @param myAttrs
     * @param fields
     */
    private void removeDups(List<FieldObject> myAttrs, List<FieldObject> fields)
    {
        for(int i=0; i < fields.size(); i++)
        {
            boolean b1 = (!fields.get(i).getAccess().toString().equals("protected"));
            boolean b2 = (!fields.get(i).getAccess().toString().equals("public"));
            boolean b3 = (fields.get(i).isStatic());

            if ((b1 && b2 && b3) || myAttrs.contains(fields.get(i)))
            {
                fields.remove(i);
            }
        }
    }

     
    /**
     * 
     * @return
     */
    public String toStringSystemLevel() {
		return " " + this.m_sysVal;
	}
    
   
}
