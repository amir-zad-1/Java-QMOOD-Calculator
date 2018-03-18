package metrics;

import ast.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ANA
{
	private double anaValue;
    private SystemObject m_sys;
    private Map<String, Integer> classMap = new HashMap<String, Integer>();//SMYH

    /**
     *
     * @param sys
     */
	public ANA(SystemObject sys)
    {
		this.anaValue = 0.0f;
		this.m_sys = sys;

		//calculate ANA
        this.calcAna();
	}


	/**
	 * 
	 */
	private void calcAna()
    {
		
        Set<ClassObject> classes = this.m_sys.getClassObjects();
        DIT dit = new DIT(this.m_sys);
        float ditValue = 0.0f;

        for (ClassObject c : classes)
        {
        	int DITCount = dit.DITCalculation(this.m_sys, c);
            ditValue += DITCount;
            classMap.put(c.getName(), DITCount);//SMYH
        }

        this.anaValue = ditValue / classes.size();

    }
	
	/**
	 * 
	 * @return
	 */
	public double getANA()
    {
        return this.anaValue;
    }

    /**
     *
     * @return
     */
	public String toString()
	{
        return " "+anaValue;
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
