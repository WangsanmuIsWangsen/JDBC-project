package application; 

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import application.module.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class info implements Initializable {

	// JDBC 驱动名及数据库 URL
    static final String DB_URL = "jdbc:mysql://localhost:3306/salary_data";
    static final String USER = "root";
    static final String PASS = "19971106";
    static Connection conn = null;
    static Statement stmt = null,stmt_1 = null,stmt_2=null;
    
    ObservableList<BaseInfo> baseInfo_list = FXCollections.observableArrayList();   //
    ObservableList<AttendanceInfo> attendanceInfo_list = FXCollections.observableArrayList();
    ObservableList<AllowenceInfo> allowenceInfo_list = FXCollections.observableArrayList();
    ObservableList<SalaryInfo_1> salaryInfo1_list = FXCollections.observableArrayList();
    ObservableList<SalaryInfo_2> salaryInfo2_list = FXCollections.observableArrayList();
    
    
    @FXML
    private Label label_name;
    @FXML
    private Button btn_sign,btn_exit;
    @FXML
    TableView<BaseInfo> tableview_base;
    @FXML
    TableView<AttendanceInfo> tableview_attendance;
    @FXML
    TableView<AllowenceInfo> tableview_allowence;
    @FXML
    TableView<SalaryInfo_1> tableview_salaryOfYear;
    @FXML
    TableView<SalaryInfo_2> tableview_salaryOfMonth;
    @FXML
    private TableColumn<?, ?>column_id,column_sex,column_age,column_native,column_telephone,column_typeOfWork,
    column_level,column_inTime,column_workAge,column_department,column_headerOfDep;
    @FXML
    private TableColumn<?, ?>column_attendanceOfHis;
    @FXML
    private TableColumn<?, ?>column_overType,column_overTime,column_overDay,column_subsidy;
    @FXML
    private TableColumn<?, ?>column_month,column_monthSalary;
    @FXML
    private TableColumn<?, ?>column_baseSalaryOneMonth,column_numOfAbsence,column_trueBaseSalary,
    column_overAllowence,column_allMonthSalary;
    @FXML
    DatePicker attendance_timebegin,attendance_timeend;
    @FXML
    DatePicker allowance_timebegin,allowance_timeend;
    @FXML
    DatePicker salary_time;
    
	@Override
    public void initialize(URL location, ResourceBundle resources)
    {
		attendance_timebegin.setValue(LocalDate.now());
		attendance_timeend.setValue(LocalDate.now());
		salary_time.setValue(LocalDate.now());
		//baseinfo
		column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		column_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
		column_age.setCellValueFactory(new PropertyValueFactory<>("age"));
		column_native.setCellValueFactory(new PropertyValueFactory<>("nativeSpace"));
		column_telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
		column_typeOfWork.setCellValueFactory(new PropertyValueFactory<>("workType"));
		column_level.setCellValueFactory(new PropertyValueFactory<>("level"));
		column_inTime.setCellValueFactory(new PropertyValueFactory<>("inTime"));
		column_workAge.setCellValueFactory(new PropertyValueFactory<>("workAge"));
		column_department.setCellValueFactory(new PropertyValueFactory<>("department"));
    	//attendance
		column_attendanceOfHis.setCellValueFactory(new PropertyValueFactory<>("historyAttendance"));
        //allowence
		column_overType.setCellValueFactory(new PropertyValueFactory<>("overType"));
		column_overTime.setCellValueFactory(new PropertyValueFactory<>("overTime"));
		column_overDay.setCellValueFactory(new PropertyValueFactory<>("overDat"));
		column_subsidy.setCellValueFactory(new PropertyValueFactory<>("overVal"));
		//salary1
		column_month.setCellValueFactory(new PropertyValueFactory<>("month"));
		column_monthSalary.setCellValueFactory(new PropertyValueFactory<>("monthSalary"));
        //salary2
		column_baseSalaryOneMonth.setCellValueFactory(new PropertyValueFactory<>("baseSalaryOneMonth"));
		column_numOfAbsence.setCellValueFactory(new PropertyValueFactory<>("numOfAbsence"));
		column_trueBaseSalary.setCellValueFactory(new PropertyValueFactory<>("trueBaseSalary"));
		column_overAllowence.setCellValueFactory(new PropertyValueFactory<>("overAllowence"));
		column_allMonthSalary.setCellValueFactory(new PropertyValueFactory<>("allMonthSalary"));

    }
	@FXML
    private void on_mouse_entered(Event event)  //鼠标输入绑定函数
    {  //这里还应该将基本信息读取出来
    	try
		{
    		baseInfo_list.clear();
    	    attendanceInfo_list.clear();
    	    allowenceInfo_list.clear();
    	    salaryInfo1_list.clear();
    	    salaryInfo2_list.clear();
    	    
    		 conn = DriverManager.getConnection(DB_URL,USER,PASS);
    		 stmt=conn.createStatement();
	         stmt_1=conn.createStatement();
	         stmt_2=conn.createStatement();
	         String employeeId=Main.employeeid; //取登陆时记录的ID
	         String sql_1 ="select Ename from employee where Eid= '"
 	        		 +employeeId+"'";
 	         ResultSet rs_1=stmt_1.executeQuery(sql_1);
 	         String employeeName=null;
 	         while(rs_1.next())
 	         {
 	        	employeeName=rs_1.getString("Ename");
 	         }
 	         label_name.setText(employeeName+",欢迎您");
 	         
 	         //baseInfoUi
 	         String sql="select employee.Eid,employee.Esex,employee.Eage,"
    	         		+ "employee.Eaddress,employee.Ephone,worker.Wname,worker.Wlevel,"
       	         		+ "employee.Eworkstartdata,employee.Eworktime,department.Dname,department.Did "
       	         		+ "from employee,worker,department "
       	         		+ "where "
       	         		+ "employee.Wid=worker.Wid " 
       	         		+ "and worker.Did=department.Did " 
       	         		+ "and employee.Eid= '"+employeeId+"'"
       	         		+ "group by employee.Eid";
 	         ResultSet rs = stmt.executeQuery(sql);
 	         String Eid=null,Esex=null,Eage=null;
 	         String Eaddress=null,Etelephone=null,Wname=null;
 	         String Wlevel=null,Eworkstartdata=null,Eworktime=null,Dname=null;
 	         String Did=null;
 	         while(rs.next())
 	         {
 	        	//System.out.print(1);
 	        	Eid=rs.getString("Eid");
 	        	Esex=rs.getString("Esex");
 	        	Eage=rs.getString("Eage");
 	        	Eaddress=rs.getString("Eaddress");
 	        	Etelephone=rs.getString("Ephone");
 	        	Wname=rs.getString("Wname");
 	        	Wlevel=rs.getString("Wlevel");
 	        	Eworkstartdata=rs.getString("Eworkstartdata");
 	        	Eworktime=rs.getString("Eworktime");
 	        	Dname=rs.getString("Dname");
 	        	Did=rs.getString("Did");
 	         }
	        baseInfo_list.add(new BaseInfo(Eid,Esex,Eage,Eaddress,Etelephone,Wname,Wlevel,
	        		Eworkstartdata,Eworktime,Dname));
 	         
 	         //attendanceUi
 	        String attendance_timeBegin,attendance_timeEnd;
 	        LocalDate datetmp1 = attendance_timebegin.getValue();
   		    LocalDate datetmp2 = attendance_timeend.getValue();
   		    if(datetmp1==null||datetmp2==null)
   		    {
   		    	attendance_timeBegin=LocalDate.now().toString();
   		    	attendance_timeEnd=LocalDate.now().toString();
   		    }
   		    else
   		    {
   		    	attendance_timeBegin=datetmp1.toString();
   	   		    attendance_timeEnd=datetmp2.toString();
   		    }
   		    attendance_timeBegin += " 00:00:00";
   	     	attendance_timeEnd += " 23:59:59";
   	     	sql="select attendance.Atime "
   	     		+"from employee,attendance "
   	     		+"where "
   	     		+"employee.Eid=attendance.Eid "
   	     		+"and attendance.Atime>='"+attendance_timeBegin+"'"
   	     		+"and attendance.Atime<='"+attendance_timeEnd+"'";
   	     	rs=stmt.executeQuery(sql);
   	     	String attendanceAtime=null;
   	     	while(rs.next())
   	     	{
   	     	    attendanceAtime=rs.getString("Atime");
   	     	    attendanceInfo_list.add(new AttendanceInfo(attendanceAtime));
   	     	}
   	     	
   	     	//allowenceUi
   	     	String allowence_timeBegin,allowence_timeEnd;
	        LocalDate datetmp3 = allowance_timebegin.getValue();
		    LocalDate datetmp4 = allowance_timeend.getValue();
		    if(datetmp3==null||datetmp4==null)
		    {
		    	allowence_timeBegin=LocalDate.now().toString();
		    	allowence_timeEnd=LocalDate.now().toString();
		    }
		    else
		    {
		    	allowence_timeBegin=datetmp3.toString();
		    	allowence_timeEnd=datetmp4.toString();
		    }
		    allowence_timeBegin += " 00:00:00";
		    allowence_timeEnd += " 23:59:59";
		    sql="select subsidy.Sovertimekind,subsidy.Sstarttime,subsidy.Sdaycount,subsidy.Ssubsidy "
		    	+"from employee,subsidy "
		    	+"where "
		    	+"employee.Eid=subsidy.Eid and employee.Eid='"+employeeId+"' "
		    	+"and subsidy.Sstarttime>='"+allowence_timeBegin+"' "
		    	+"and subsidy.Sstarttime<='"+allowence_timeEnd+"'";
		    	//+"group by allowence.Sovertimekind,allowence.Sstarttime,allowence.Sdaycount,allowence.Ssubsidy";
   	     	rs=stmt.executeQuery(sql);
   	     	String allowenceKind=null,allowenceStart=null;
   	     	int allowenceDay=0,allowenceVal=0;
   	     	while(rs.next())
   	     	{
   	     		allowenceKind=rs.getString("Sovertimekind");
   	     		allowenceStart=rs.getString("Sstarttime");
   	     		allowenceDay=rs.getInt("Sdaycount");
   	     		allowenceVal=rs.getInt("Ssubsidy");
   	     		allowenceInfo_list.add(new AllowenceInfo(allowenceKind,allowenceStart,
   	     			Integer.toString(allowenceDay),Integer.toString(allowenceVal)));
   	     	}
   	     	
		    //SalaryInfoUi_2
   	     	String salaryTime=null;  //只取年月
   	     	String salaryTimeMonth=null;
	        LocalDate datetmp5 = salary_time.getValue();
		    if(datetmp5==null)
		    {
		    	salaryTime=LocalDate.now().toString();
		    }
		    else
		    {
		    	salaryTime=datetmp5.toString();
		    }
		    salaryTimeMonth=salaryTime.substring(0,7);   //取年-月
		    //MonthSalary
		    int tmp=0;
		    String monthStart=null,monthEnd=null;   //查找开始时间和结束时间
		    monthStart=salaryTimeMonth+"-01 00:00:00";
		    if(salaryTimeMonth.substring(5, 7).equals("12"))//如果是12月
		    {
		    	tmp=Integer.valueOf(salaryTimeMonth.substring(0,4));
		    	tmp+=1;
		    	monthEnd=""+tmp+"-01-01 00:00:00";//次年的月初
		    }
		    else {
		    	tmp=Integer.valueOf(salaryTimeMonth.substring(5,7));//取月份
		    	if(tmp>=9)
		    	{
		    		tmp+=1;
			    	monthEnd=monthStart.substring(0,4)+"-"+tmp+"-01 00:00:00";
		    	}
		    	else {
		    		tmp+=1;
		    		monthEnd=monthStart.substring(0,4)+"-0"+tmp+"-01 00:00:00";
		    	}
		    }  //时间输出正确
		    //System.out.print(monthStart);
		    //System.out.print(monthEnd);
		    sql="select worker.Wsalary,worker.Wsattendance "
		    	+"from employee,worker "
		    	+"where "
		    	+"employee.Wid=worker.Wid "
		    	+"and employee.Eid='"+employeeId+"'"
		    	+"group by employee.Eid";
		    rs=stmt.executeQuery(sql);
		    int shouldAttendance=0;
		    int monthFuncSalary=0,absenceNum=0,overAllowence=0;//缺勤次数=count（应该到的次数）-打卡次数
		    double trueSalary=0,finallyMonthSalary=0;
		    while(rs.next())
		    {
		    	shouldAttendance=rs.getInt("worker.Wsattendance");
		    	monthFuncSalary=rs.getInt("worker.Wsalary");
		    	//salaryInfo2_list.add(new SalaryInfo_2(Integer.toString(monthFuncSalary),Integer.toString(absenceNum),
		    			//Integer.toString(trueSalary),Integer.toString(overAllowence),Integer.toString(finallyMonthSalary)));
		    }
		    sql="select sum(subsidy.Ssubsidy) "
		    		+"from subsidy "
		    		+"where "
		    		+"Eid='"+employeeId+"' "
		    		+"and subsidy.Sstarttime>='"+monthStart+"' "
			    	+"and subsidy.Sstarttime<='"+monthEnd+"'";
		    rs=stmt.executeQuery(sql);
		    while(rs.next())
		    {
		    	overAllowence=rs.getInt("sum(subsidy.Ssubsidy)");
		    }
		    sql="select count(Atime) "
		    	+"from attendance "
		    	+"where "
		    	+"Eid='"+employeeId+"' "
		    	+"and Atime>='"+monthStart+"' "
		    	+"and Atime<='"+monthEnd+"'";
		    rs=stmt.executeQuery(sql);
		    int trueAttendance=0;
		    while(rs.next())
		    {
		    	trueAttendance=rs.getInt("count(Atime)");
		    }
		    absenceNum=shouldAttendance-trueAttendance;   //计算出缺勤次数
		    if(trueAttendance==0) trueSalary=0;
		    else
		    trueSalary=(monthFuncSalary/shouldAttendance)*trueAttendance;
		    finallyMonthSalary=trueSalary+overAllowence;
		    salaryInfo2_list.add(new SalaryInfo_2(Integer.toString(monthFuncSalary),Integer.toString(absenceNum),
		    		String.format("%-8.2f", trueSalary),Integer.toString(overAllowence),String.format("%-8.2f", finallyMonthSalary)));
		    
		    //SalaryInfoUi_1
		    
		    int tmp1=0;
		    double bonusOfYear=0;
		    double salaryAll=0;
		    String salaryTimeYear=null;
		    salaryTimeYear=salaryTime.substring(0,4);  //查询年份
		    for(int i=1;i<=12;i++)  //计算1-12月份的月实际工资
		    {
		    	//monthStart=salaryTimeMonth+"-01 00:00:00";
			    if(i==12)//如果是12月
			    {
			    	monthStart=salaryTimeYear+"-12-01 00:00:00";
			    	monthEnd=salaryTimeYear+"-12-31 23:59:59";
			    }
			    else if(i>=10)
			    {
			    	monthStart=salaryTimeYear+"-"+i+"-01 00:00:00";
			    	tmp1=i+1;
				    monthEnd=salaryTimeYear+"-"+tmp1+"-01 00:00:00";
			    }
			    else if(i==9)
			    {
			    	monthStart=salaryTimeYear+"-0"+i+"-01 00:00:00";
				    monthEnd=salaryTimeYear+"-10-01 00:00:00";
			    }
			    else {
			    	monthStart=salaryTimeYear+"-0"+i+"-01 00:00:00";
			    	tmp1=i+1;
			    	monthEnd=salaryTimeYear+"-0"+tmp1+"-01 00:00:00";
			    }
			      //时间输出正确
			    //System.out.print(monthStart);
			    //System.out.print(monthEnd);
			    //System.out.print("\n");
			    overAllowence=0;
			    sql="select sum(subsidy.Ssubsidy) "
			    	+"from subsidy "
			    	+"where "
			    	+"Eid='"+employeeId+"' "
			    	+"and subsidy.Sstarttime>='"+monthStart+"' "
			    	+"and subsidy.Sstarttime<='"+monthEnd+"'";
			    rs=stmt.executeQuery(sql);
			    //int monthFuncSalary=0,absenceNum=0,overAllowence=0;//缺勤次数=count（应该到的次数）-打卡次数
			    //double trueSalary=0,finallyMonthSalary=0;
			    while(rs.next())
			    {
			    	overAllowence=rs.getInt("sum(subsidy.Ssubsidy)");
			    }
			    sql="select worker.Wsalary,worker.Wsattendance "
			    		+"from worker "
				    	+"where "
				    	+"employee.Wid=worker.Wid "
				    	+"and employee.Eid='"+employeeId+"'";
			    while(rs.next())
			    {
			    	shouldAttendance=rs.getInt("worker.Wsattendance");
			    	monthFuncSalary=rs.getInt("worker.Wsalary");
			    }
			    sql="select count(Atime) "
			    	+"from attendance "
			    	+"where "
			    	+"Eid='"+employeeId+"' "
			    	+"and Atime>='"+monthStart+"' "
			    	+"and Atime<='"+monthEnd+"'";
			    rs=stmt.executeQuery(sql);
			    //int trueAttendance=0;
			    while(rs.next())
			    {
			    	trueAttendance=rs.getInt("count(Atime)");
			    }
			    absenceNum=shouldAttendance-trueAttendance;   //计算出缺勤次数
			    if(trueAttendance==0) trueSalary=0;
			    else
			    trueSalary=(monthFuncSalary/shouldAttendance)*trueAttendance;
			    finallyMonthSalary=trueSalary+overAllowence;
			    salaryInfo1_list.add(new SalaryInfo_1(Integer.toString(i),String.format("%-8.2f", finallyMonthSalary)));
			    bonusOfYear+=finallyMonthSalary;
		    }
		    salaryAll=bonusOfYear;
		    bonusOfYear/=12;
		    salaryAll+=bonusOfYear;
		    salaryInfo1_list.add(new SalaryInfo_1("年终奖金",String.format("%-8.2f", bonusOfYear)));
		    salaryInfo1_list.add(new SalaryInfo_1("年工资",String.format("%-8.2f", salaryAll)));
		    
 	        if(rs!=null)
  	        	 rs.close();
 	         stmt.close();
   	         stmt_1.close();
   	         stmt_2.close();
   	         conn.close();
		}
    	catch(SQLException se){
	         	// 处理 JDBC 错误
	         	se.printStackTrace();
	    }
    	finally {
    		tableview_base.setItems(baseInfo_list);
    		tableview_attendance.setItems(attendanceInfo_list);
    		tableview_allowence.setItems(allowenceInfo_list);
    		tableview_salaryOfYear.setItems(salaryInfo1_list);
    		tableview_salaryOfMonth.setItems(salaryInfo2_list);
    	}
    }

	    @FXML
	    private void on_btn_exit_clicked(ActionEvent event)
	    {
	    	Event.fireEvent(Main.getPrimaryStage(),
	    		new WindowEvent(Main.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
	    }
	    @FXML
	    private void on_btn_sign_clicked(ActionEvent event)  //点击打卡
	    {
	    	try {
	    		Date date=new Date();
	    		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   		 	stmt=conn.createStatement();
	   		 	
	   		    String employeeId=Main.employeeid; //取登陆时记录的ID
	   		    String sql=null;
	   		 	//做限制  如果某天打过卡那么将提示不能打卡
	   		    String signDay=LocalDate.now().toString();
	   		    System.out.print(signDay);
	   		    String dayStart=signDay+" 00:00:00";
	   		    String dayEnd=signDay+" 23:59:59";
	   		    sql="select Eid "
	   		    	+"from attendance "
	   		    	+"where "
	   		    	+"Atime>='"+dayStart+"' "
	   		    	+"and Atime<='"+dayEnd+"'";
	   		    ResultSet rs = stmt.executeQuery(sql);
	   		    if(rs.next())
	   		    {//如果存在
	   		    	JOptionPane.showMessageDialog(null, "您当天已经签过到", "警告", JOptionPane.WARNING_MESSAGE);
	   		    }
	   		    else {
	   		    	sql="insert into attendance values('"+employeeId+"','"+dateFormat.format(date)+"')";
		   		 	stmt.executeUpdate(sql);
	   		    }
	   		 	
	   		 	stmt.close();
	   		 	conn.close();
	    	}
	    	catch(SQLException se){
	         	// 处理 JDBC 错误
	         	se.printStackTrace();
	    }
	    }
}
