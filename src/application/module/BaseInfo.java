package application.module;

import javafx.beans.property.SimpleStringProperty;

public class BaseInfo {

	private final SimpleStringProperty id; 
    private final SimpleStringProperty sex; 
    private final SimpleStringProperty age;
    private final SimpleStringProperty nativeSpace;
    private final SimpleStringProperty telephone;
    private final SimpleStringProperty workType;
    private final SimpleStringProperty level;
    private final SimpleStringProperty inTime;
    private final SimpleStringProperty workAge;
    private final SimpleStringProperty department;
   
    public BaseInfo(String id_in, String sex_in, String age_in,String nativeSpace_in,
    		String telephone_in,String workType_in,String level_in,String inTime_in,
    		String workAge_in,String department_in) 
    {  
        this.id = new SimpleStringProperty(id_in);  
        this.sex = new SimpleStringProperty(sex_in);  
        this.age = new SimpleStringProperty(age_in);
        this.nativeSpace = new SimpleStringProperty(nativeSpace_in);
        this.telephone = new SimpleStringProperty(telephone_in);
        this.workType = new SimpleStringProperty(workType_in);
        this.level = new SimpleStringProperty(level_in);
        this.inTime = new SimpleStringProperty(inTime_in);  
        this.workAge = new SimpleStringProperty(workAge_in);  
        this.department = new SimpleStringProperty(department_in);  
    }
    public String getId() {  
        return id.get();  
    }  
    public void setId(String id_in) {  
    	id.set(id_in);  
    }  
          
    public String getSex() {  
        return sex.get();  
    }  
    public void setSex(String sex_in) {  
    	sex.set(sex_in);  
    }  
      
    public String getAge() {  
        return age.get();  
    }  
    public void setAge(String age_in) {  
    	age.set(age_in);  
    }
    
    public String getNativeSpace() {  
        return nativeSpace.get();  
    }  
    public void setNativeSpace(String nativeSpace_in) {  
    	nativeSpace.set(nativeSpace_in);  
    }
    
    public String getTelephone() {  
        return telephone.get();  
    }  
    public void setTelephone(String telephone_in) {  
    	telephone.set(telephone_in);  
    } 
    
    public String getWorkType() {  
        return workType.get();  
    }  
    public void setWorkType(String workType_in) {  
    	workType.set(workType_in);  
    } 
    public String getInTime() {  
        return inTime.get();  
    }  
    public void setInTime(String inTime_in) {  
    	inTime.set(inTime_in);  
    }
    public String getWorkAge() {  
        return workAge.get();  
    }  
    public void setWorkAge(String workAge_in) {  
    	workAge.set(workAge_in);  
    }
    public String getDepartment() {  
        return department.get();  
    }  
    public void setDepartment(String department_in) {  
    	department.set(department_in);  
    }
    public String getLevel() {  
        return level.get();  
    }  
    public void setLevel(String level_in) {  
    	level.set(level_in);  
    }
}
