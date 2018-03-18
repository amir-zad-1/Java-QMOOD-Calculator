package metrics;

import ast.*;

import java.util.Set;

public class ANA
{
	private double anaValue;
    private SystemObject m_sys;

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
            ditValue += dit.DITCalculation(this.m_sys, c);
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

}