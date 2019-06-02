package application.module;

import javafx.beans.property.SimpleStringProperty;

public class SalaryInfo_2 {

	private final SimpleStringProperty baseSalaryOneMonth; 
    private final SimpleStringProperty numOfAbsence; 
    private final SimpleStringProperty trueBaseSalary; 
    private final SimpleStringProperty overAllowence; 
    private final SimpleStringProperty allMonthSalary; 
   
    public SalaryInfo_2(String baseSalaryOneMonth_in, String numOfAbsence_in,String trueBaseSalary_in,
    		String overAllowence_in,String allMonthSalary_in) 
    {  
        this.baseSalaryOneMonth = new SimpleStringProperty(baseSalaryOneMonth_in);  
        this.numOfAbsence = new SimpleStringProperty(numOfAbsence_in);
        this.trueBaseSalary = new SimpleStringProperty(trueBaseSalary_in);
        this.overAllowence = new SimpleStringProperty(overAllowence_in);
        this.allMonthSalary = new SimpleStringProperty(allMonthSalary_in);
    }
    public String getBaseSalaryOneMonth() {  
        return baseSalaryOneMonth.get();  
    }  
    public void setBaseSalaryOneMonth(String baseSalaryOneMonth_in) {  
    	baseSalaryOneMonth.set(baseSalaryOneMonth_in);  
    }  
          
    public String getNumOfAbsence() {  
        return numOfAbsence.get();  
    }  
    public void setNumOfAbsence(String numOfAbsence_in) {  
    	numOfAbsence.set(numOfAbsence_in);  
    }  
    public String getTrueBaseSalary() {  
        return trueBaseSalary.get();  
    }  
    public void setTrueBaseSalary(String trueBaseSalary_in) {  
    	trueBaseSalary.set(trueBaseSalary_in);  
    }  
    public String getOverAllowence() {  
        return overAllowence.get();  
    }  
    public void setOverAllowence(String overAllowence_in) {  
    	overAllowence.set(overAllowence_in);  
    }  
    public String getAllMonthSalary() {  
        return allMonthSalary.get();  
    }  
    public void setAllMonthSalary(String allMonthSalary_in) {  
    	allMonthSalary.set(allMonthSalary_in);  
    }  
}
