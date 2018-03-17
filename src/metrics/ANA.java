package metrics;

import ast.*;

import java.util.Set;

public class ANA
{
	private double m_ana;
    private SystemObject m_sys;

    /**
     *
     * @param sys
     */
	public ANA(SystemObject sys)
    {
		this.m_ana = 0.0f;
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

        this.m_ana = ditValue / classes.size();

    }
	
	/**
	 * 
	 * @return
	 */
	public double getANA()
    {
        return this.m_ana;
    }

    /**
     *
     * @return
     */
	public String toString()
	{
	    StringBuilder sb = new StringBuilder();

        sb.append("==========");
        sb.append("ANA");
        sb.append("\n");

        sb.append("System Level ANA = ");
        sb.append(this.m_ana);
        sb.append("\n");
        sb.append("==========");

        return sb.toString();
	}

}