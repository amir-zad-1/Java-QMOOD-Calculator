package metrics;

import ast.*;
import java.util.*;

public class AIF
{
    private float m_sysVal;
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
        this.calcAif(classes);
    }

    /**
     *
     * @param classes
     */
    private void calcAif(Set<ClassObject> classes)
    {

        for(ClassObject c : classes)
        {
            if(c.isInterface())
            {
                this.m_classAif.put(c.getName(), 0.0f);
            }
            else
            {
                float aif = this.computeAIF(c);

                this.m_classAif.put(c.getName(), aif);
                this.m_sysVal += aif;
            }
        }

        m_sysVal = m_sysVal / classes.size();
    }


    /**
     *
     * @return
     */
    public float getSystemLevelValue()
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
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("==========");
        sb.append("AIF");
        sb.append("\n");

        for (String k : m_classAif.keySet())
        {
            sb.append(k);
            sb.append(" : ");
            sb.append(this.m_classAif.get(k));
            sb.append("\n");
        }

        sb.append("System Level AIF = ");
        sb.append(this.m_sysVal);
        sb.append("\n");
        sb.append("==========");


        return sb.toString();
    }
    
    /**
     * 
     * @return
     */
    public String toStringSystemLevel() {
		return " " + this.m_sysVal;
	}
    
    /**
     * 
     * @return
     */
    public String toString2() { return this.toStringSystemLevel(); }

}
