package application.module;

import javafx.beans.property.SimpleStringProperty;

public class AttendanceInfo {

	private final SimpleStringProperty historyAttendance; 
   
    public AttendanceInfo(String historyAttendance_in) 
    {  
        this.historyAttendance = new SimpleStringProperty(historyAttendance_in);
    }
    public String getHistoryAttendance() {  
        return historyAttendance.get();  
    }  
    public void setHistoryAttendance(String historyAttendance_in) {  
    	historyAttendance.set(historyAttendance_in);  
    }  
}
