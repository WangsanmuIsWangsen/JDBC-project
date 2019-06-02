package application.module;

import javafx.beans.property.SimpleStringProperty;

public class SalaryInfo_1 {

	private final SimpleStringProperty month; 
    private final SimpleStringProperty monthSalary; 
   
    public SalaryInfo_1(String month_in, String monthSalary_in) 
    {  
        this.month = new SimpleStringProperty(month_in);  
        this.monthSalary = new SimpleStringProperty(monthSalary_in);  
    }
    public String getMonth() {  
        return month.get();  
    }  
    public void setMonth(String month_in) {  
    	month.set(month_in);  
    }  
          
    public String getMonthSalary() {  
        return monthSalary.get();  
    }  
    public void setMonthSalary(String monthSalary_in) {  
    	monthSalary.set(monthSalary_in);  
    }  
}
