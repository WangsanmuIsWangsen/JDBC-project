package application.module;

import javafx.beans.property.SimpleStringProperty;

public class DepartmentInfo_1 {

	private final SimpleStringProperty id; 
    private final SimpleStringProperty name; 
    private final SimpleStringProperty type;
    private final SimpleStringProperty level;
    private final SimpleStringProperty absence;
    private final SimpleStringProperty allowence;
    private final SimpleStringProperty salary;

   
    public DepartmentInfo_1(String id_in, String name_in, String type_in,String level_in,
    		String absence_in,String allowence_in,String salary_in) 
    {  
        this.id = new SimpleStringProperty(id_in);  
        this.name = new SimpleStringProperty(name_in);  
        this.type = new SimpleStringProperty(type_in);
        this.level = new SimpleStringProperty(level_in);
        this.absence = new SimpleStringProperty(absence_in);
        this.allowence = new SimpleStringProperty(allowence_in);
        this.salary = new SimpleStringProperty(salary_in);
    }
    public String getId() {  
        return id.get();  
    }  
    public void setId(String id_in) {  
    	id.set(id_in);  
    }  
          
    public String getName() {  
        return name.get();  
    }  
    public void setName(String name_in) {  
    	name.set(name_in);
    }  
      
    public String getType() {  
        return type.get();  
    }  
    public void setType(String type_in) {  
    	type.set(type_in);  
    }
    
    public String getAbsence() {  
        return absence.get();  
    }  
    public void setAbsence(String absence_in) {  
    	absence.set(absence_in);  
    }
    
    public String getAllowence() {  
        return allowence.get();  
    }  
    public void setAllowence(String allowence_in) {  
    	allowence.set(allowence_in);  
    } 
    
    public String getSalary() {  
        return salary.get();
    }  
    public void setSalary(String salary_in) {  
    	salary.set(salary_in);  
    }
    public String getLevel() {  
        return level.get();  
    }  
    public void setLevel(String level_in) {  
    	level.set(level_in);  
    }
}
