package model;

public abstract class Employee {
	
	private String name;
	protected Payment pmnt;
	
	public Employee(String s){
		
	}
	
	public String getName(){
		return "";
	}
	
	public String getPayType(){
		return "";
	} 
	
	public abstract void calcSalary();
	public abstract String getEmpType();
	
	public void setPayment(Payment p)
	{
		
	}
	
}
