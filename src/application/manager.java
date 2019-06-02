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
import java.text.ParseException;
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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.stage.WindowEvent;

public class manager implements Initializable {

	// JDBC 驱动名及数据库 URL
    static final String DB_URL = "jdbc:mysql://localhost:3306/salary_data";
    static final String USER = "root";
    static final String PASS = "19971106";
    static Connection conn = null;
    static Statement stmt = null,stmt_1=null;
    private String departmentId=null,flag=null,workId=null;
    
    ObservableList<String> select_workName = FXCollections.observableArrayList();   //
    ObservableList<String> select_department = FXCollections.observableArrayList();   //
    ObservableList<DepartmentInfo_1> departmentInfo1_list = FXCollections.observableArrayList();   //
    ObservableList<DepartmentInfo_2> departmentInfo2_list = FXCollections.observableArrayList();
	
    @FXML
    private Button btn_logout,btn_exit,btn_update,btn_addinfo;
    @FXML
    private TableView<DepartmentInfo_1> tableview_month;
    @FXML
    private TableView<DepartmentInfo_2> tableview_year;
    @FXML
    private TableColumn<?, ?>month_id,month_name,month_type,month_level,month_absence,month_allowence,month_perMonSalary;
    @FXML
    private TableColumn<?, ?>year_id,year_name,year_type,year_level,year_award,year_perYearSalary;
    @FXML
    private DatePicker datePicker_overTime,datePicker_queryTime;
	@FXML
	private ComboBox<String> combo_selectDepartment,combo_workName,combo_selectDepartmentUp;
	@FXML
	private TextField textField_overType,textField_allowenceOfOver,baseSalary,numOfAttendance_OneMonth,textField_allowenceOfEid,textField_allowenceOfDay;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
    {
		datePicker_overTime.setValue(LocalDate.now());
		datePicker_queryTime.setValue(LocalDate.now());
		//DepartmentInfo_1
		month_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		month_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		month_type.setCellValueFactory(new PropertyValueFactory<>("type"));
		month_level.setCellValueFactory(new PropertyValueFactory<>("level"));
		month_absence.setCellValueFactory(new PropertyValueFactory<>("absence"));
		month_allowence.setCellValueFactory(new PropertyValueFactory<>("allowence"));
		month_perMonSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
		//DepartmentInfo_2
		year_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		year_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		year_type.setCellValueFactory(new PropertyValueFactory<>("type"));
		year_level.setCellValueFactory(new PropertyValueFactory<>("level"));
		year_award.setCellValueFactory(new PropertyValueFactory<>("allowence"));
		year_perYearSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
   		 	stmt=conn.createStatement();
   		 	String sql="select * "
		 			  +"from department";
		 	ResultSet rs=stmt.executeQuery(sql);
		 	String departmentName=null;
		 	while(rs.next())
		 	{
		 		departmentId=rs.getString("Did");
		 		departmentName=rs.getString("Dname");
		 		select_department.add(departmentName);
		 	}
		    combo_selectDepartmentUp.setItems(select_department);
		    combo_selectDepartment.setItems(select_department);
		    combo_selectDepartmentUp.getSelectionModel().select(0);
		    if(rs!=null)
		    	rs.close();
		    stmt.close();
		    conn.close();
		}
		catch(SQLException se){
         	// 处理 JDBC 错误
         	se.printStackTrace();
		}
    }
	
	@FXML
	private void on_mouse_enteredOfM(Event event)
	{
		try {
			departmentInfo1_list.clear();
			departmentInfo2_list.clear();
			
			String employeeId=Main.employeeid; //取登陆时记录的ID
			
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
   		 	stmt=conn.createStatement();
   		 	stmt_1=conn.createStatement();
   		 	ResultSet rs;
   		 	
   		 	String sql=null;
   		    String SelectedDep=null;//已经选择的部门
   		    int selectedIndex=combo_selectDepartment.getSelectionModel().getSelectedIndex();
   		    selectedIndex+=1;
   		    departmentId=Integer.toString(selectedIndex);
		    SelectedDep=combo_selectDepartment.getSelectionModel().getSelectedItem();
		    if(flag!=SelectedDep)
		    {
		    	combo_workName.getItems().clear();
		    	//System.out.print(selectedIndex);
		    	sql="select Wid,Wname "
		    		+"from worker "
		    		+"where Did='"+departmentId+"'";
		    	rs=stmt.executeQuery(sql);
		    	String workerN=null;
		    	while(rs.next())
		    	{
		    		System.out.print(1);
		    		workId=rs.getString("Wid");
		    		workerN=rs.getString("Wname");
		    		select_workName.add(workerN);
		    	}
		    	combo_workName.setItems(select_workName);
		    	flag=SelectedDep;
		    }
		    
		    //departmentInfo
		    int selectIndex2=combo_selectDepartmentUp.getSelectionModel().getSelectedIndex();
		    selectIndex2+=1;
		    String departmentSelect=null;
		    departmentSelect=Integer.toString(selectIndex2);   //Did
		    sql="select employee.Eid,employee.Ename,worker.Wname,worker.Wlevel "
		    		+"from employee,worker "
		    		+"where "
		    		+"employee.Wid=worker.Wid "
		    		+"and worker.Did='"+departmentSelect+"'"
		    		+"group by employee.Eid";
		    rs=stmt.executeQuery(sql);
		    String mId=null,mEmployeeName=null,mWorkName=null,mLevel=null;
		    while(rs.next())
		    {
		    	mId=rs.getString("employee.Eid");
		    	mEmployeeName=rs.getString("employee.Ename");
		    	mWorkName=rs.getString("worker.Wname");
		    	mLevel=rs.getString("worker.Wlevel");
		    	//计算月工资
		    	String salaryTime=null;  //只取年月
	   	     	String salaryTimeMonth=null;
		        LocalDate datetmp5 = datePicker_queryTime.getValue();
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
			    String sql_1=null;
			    ResultSet rs_1;
			    sql_1="select worker.Wsalary,worker.Wsattendance "
				    	+"from employee,worker "
				    	+"where "
				    	+"employee.Wid=worker.Wid "
				    	+"and employee.Eid='"+mId+"'"
				    	+"group by employee.Eid";
			        rs_1=stmt_1.executeQuery(sql_1);
				    int shouldAttendance=0;
				    int monthFuncSalary=0,absenceNum=0,overAllowence=0;//缺勤次数=count（应该到的次数）-打卡次数
				    double trueSalary=0,finallyMonthSalary=0;
				    while(rs_1.next())
				    {
				    	shouldAttendance=rs_1.getInt("worker.Wsattendance");
				    	monthFuncSalary=rs_1.getInt("worker.Wsalary");
				    	//overAllowence=rs_1.getInt("sum(subsidy.Ssubsidy)");
				    }
				    sql_1="select sum(subsidy.Ssubsidy) "
				    		+"from subsidy "
				    		+"where "
				    		+"Eid='"+mId+"' "
				    		+"and subsidy.Sstarttime>='"+monthStart+"' "
					    	+"and subsidy.Sstarttime<='"+monthEnd+"'";
				    rs_1=stmt_1.executeQuery(sql_1);
				    while(rs_1.next())
				    {
				    	overAllowence=rs_1.getInt("sum(subsidy.Ssubsidy)");
				    }
				    sql_1="select count(Atime) "
				    	+"from attendance "
				    	+"where "
				    	+"Eid='"+mId+"' "
				    	+"and Atime>='"+monthStart+"' "
				    	+"and Atime<='"+monthEnd+"'";
				    rs_1=stmt_1.executeQuery(sql_1);
				    int trueAttendance=0;
				    while(rs_1.next())
				    {
				    	trueAttendance=rs_1.getInt("count(Atime)");
				    }
				    absenceNum=shouldAttendance-trueAttendance;   //计算出缺勤次数
				    if(trueAttendance==0) trueSalary=0;
				    else
				    trueSalary=(monthFuncSalary/shouldAttendance)*trueAttendance;
				    finallyMonthSalary=trueSalary+overAllowence;
				    departmentInfo1_list.add(new DepartmentInfo_1(mId,mEmployeeName,
				    		mWorkName,mLevel,Integer.toString(absenceNum),
				    		Integer.toString(overAllowence),
				    		String.format("%-8.2f", finallyMonthSalary)));
				    
				    //YearSalary
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
					    sql_1="select sum(subsidy.Ssubsidy) "
					    	+"from subsidy "
					    	+"where "
					    	+"Eid='"+mId+"' "
					    	+"and subsidy.Sstarttime>='"+monthStart+"' "
					    	+"and subsidy.Sstarttime<='"+monthEnd+"'";
					    rs_1=stmt_1.executeQuery(sql_1);
					    //int monthFuncSalary=0,absenceNum=0,overAllowence=0;//缺勤次数=count（应该到的次数）-打卡次数
					    //double trueSalary=0,finallyMonthSalary=0;
					    while(rs_1.next())
					    {
					    	overAllowence=rs_1.getInt("sum(subsidy.Ssubsidy)");
					    }
					    sql_1="select worker.Wsalary,woker.Wsattendance "
					    		+"from worker "
						    	+"where "
						    	+"employee.Wid=worker.Wid "
						    	+"and employee.Eid='"+mId+"'";
					    while(rs_1.next())
					    {
					    	shouldAttendance=rs_1.getInt("worker.Wsattendance");
					    	monthFuncSalary=rs_1.getInt("worker.Wsalary");
					    }
					    sql_1="select count(Atime) "
					    	+"from attendance "
					    	+"where "
					    	+"Eid='"+mId+"' "
					    	+"and Atime>='"+monthStart+"' "
					    	+"and Atime<='"+monthEnd+"'";
					    rs_1=stmt_1.executeQuery(sql_1);
					    //int trueAttendance=0;
					    while(rs_1.next())
					    {
					    	trueAttendance=rs_1.getInt("count(Atime)");
					    }
					    absenceNum=shouldAttendance-trueAttendance;   //计算出缺勤次数
					    if(trueAttendance==0) trueSalary=0;
					    else
					    trueSalary=(monthFuncSalary/shouldAttendance)*trueAttendance;
					    finallyMonthSalary=trueSalary+overAllowence;
					    bonusOfYear+=finallyMonthSalary;  //所有月工资
				    }
				    salaryAll=bonusOfYear;
				    bonusOfYear/=12;
				    salaryAll+=bonusOfYear;
				    departmentInfo2_list.add(new DepartmentInfo_2(mId,mEmployeeName,
				    		mWorkName,mLevel,String.format("%-8.2f", bonusOfYear),
				    		String.format("%-8.2f", salaryAll)));
		    }
		    
		    stmt_1.close();
		    stmt.close();
		    conn.close();
		}
		catch(SQLException se){
         	// 处理 JDBC 错误
         	se.printStackTrace();
		}
		finally {
			tableview_month.setItems(departmentInfo1_list);
			tableview_year.setItems(departmentInfo2_list);
		}
	}
	
	@FXML
	private void on_btn_update_clicked(ActionEvent event)
    {
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
   		 	stmt=conn.createStatement();
   		 	
   		 	String baseSalaryReset=null,shouleAttendanceReset=null;
   		 	baseSalaryReset=baseSalary.getText();
   		 	shouleAttendanceReset=numOfAttendance_OneMonth.getText();
   		 	String sql="update worker set Wsalary = "+ "'"+baseSalaryReset+"', "
   		 			+"Wsattendance='"+shouleAttendanceReset+"' "
   		 			+"where "
   		 			+"Wid='"+workId+"'";
   		 	stmt.executeUpdate(sql);
   		 	stmt.close();
   		 	conn.close();
		}
		catch(SQLException se){
         	// 处理 JDBC 错误
         	se.printStackTrace();
		}
    }
	@FXML
	private void on_btn_addinfo_clicked(ActionEvent event)
    {
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
   		 	stmt=conn.createStatement();
   		 	stmt_1=conn.createStatement();
   		 	
   		 	String id=null,time=null,type=null,day=null,allowence=null;
   		 	id=textField_allowenceOfEid.getText();//得到输入的员工ID
   		 	LocalDate date=datePicker_overTime.getValue();
   		 	time=date.toString()+" 09:00:00";
   		 	type=textField_overType.getText();
   		 	day=textField_allowenceOfDay.getText();
   		 	allowence=textField_allowenceOfOver.getText();
   		 	if(textField_allowenceOfEid.getText().length()<1||
   		 		textField_overType.getText().length()<1||
   		 		textField_allowenceOfDay.getText().length()<1||
   		 		textField_allowenceOfOver.getText().length()<1)
   		 	{
		 			JOptionPane.showMessageDialog(null, "请补全信息", "警告", JOptionPane.WARNING_MESSAGE);
   		 	}
   		 	else {
   		 		String sql="select Eid "
   		 				+"from employee "
   		 				+"where Eid='"+id+"'";
   		 		ResultSet rs=stmt.executeQuery(sql);
   		 		if(rs.next())   //如果存在
   		 		{
   		 			sql="insert into subsidy values('"+id+"','"+type+"','"+time+"','"+day+"','"+allowence+"')";
   		 			stmt_1.execute(sql);
   		 		}
   		 		else {
   		 			JOptionPane.showMessageDialog(null, "不存在此ID", "警告", JOptionPane.WARNING_MESSAGE);
   		 		}
   		 	}
   		 	stmt_1.close();
   		 	stmt.close();
   		 	conn.close();
		}
		catch(SQLException se){
         	// 处理 JDBC 错误
         	se.printStackTrace();
		} 
    }
	@FXML
    private void on_btn_exit_clicked(ActionEvent event)
    {
    	Event.fireEvent(Main.getPrimaryStage(),
    		new WindowEvent(Main.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
    }
    
    @FXML
    private void on_btn_logout_clicked(ActionEvent event)
    {
    	Main.setLoginUi();
    }
	
}
