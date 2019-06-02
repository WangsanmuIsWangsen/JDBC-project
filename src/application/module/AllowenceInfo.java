package application.module;

import javafx.beans.property.SimpleStringProperty;

public class AllowenceInfo {

	private final SimpleStringProperty overType; 
    private final SimpleStringProperty overTime; 
    private final SimpleStringProperty overDat;
    private final SimpleStringProperty overVal;
   
    public AllowenceInfo(String overType_in, String overTime_in, String overDat_in,String overVal_in) 
    {  
        this.overType = new SimpleStringProperty(overType_in);  
        this.overTime = new SimpleStringProperty(overTime_in);  
        this.overDat = new SimpleStringProperty(overDat_in);
        this.overVal = new SimpleStringProperty(overVal_in);
    }
    public String getOverType() {  
        return overType.get();  
    }  
    public void setOverType(String overType_in) {  
    	overType.set(overType_in);  
    }  
          
    public String getOverTime() {  
        return overTime.get();  
    }  
    public void setOverTime(String overTime_in) {  
    	overTime.set(overTime_in);  
    }  
      
    public String getOverDat() {  
        return overDat.get();  
    }  
    public void setOverDat(String overDat_in) {  
    	overDat.set(overDat_in);  
    }
    public String getOverVal() {  
        return overVal.get();  
    }  
    public void setOverVal(String overVal_in) {  
    	overVal.set(overVal_in);
    }
}
