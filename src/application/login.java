package application; 

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class login implements Initializable
{
    // JDBC 驱动名及数据库 URL
    static final String DB_URL = "jdbc:mysql://localhost:3306/salary_data";
    static final String USER = "root";
    static final String PASS = "19971106";
    static Connection conn = null;
    static Statement stmt = null;
    
    @FXML
    private SplitPane split_login;
    @FXML
    private Button btn_exit,btn_login; 
    @FXML
    public ComboBox<String> combo_select;
    @FXML
    private TextField text_pass,text_id;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
    	combo_select.getItems().addAll("查看个人信息","管理部门信息");
    	combo_select.getSelectionModel().select(0);  //默认选择查看个人信息
    }

    @FXML
    private void on_btn_exit_clicked(ActionEvent event) throws SQLException
    {
    	Event.fireEvent(Main.getPrimaryStage(),
    			new WindowEvent(Main.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
    }
    
    @FXML
    private void on_btn_login_clicked(ActionEvent event)  //登陆绑定事件
    {
    	try {
    		int type = combo_select.getSelectionModel().getSelectedIndex(); //得到选择地登陆类型
	    	String stringUsrinfo = "", stringPwdinfo = "";
	    	stringUsrinfo = text_id.getText();
	    	stringPwdinfo = text_pass.getText();
	        if(stringUsrinfo.isEmpty() || stringPwdinfo.isEmpty()) {
	        	JOptionPane.showMessageDialog(null, "用户名或密码不能为空", "警告", JOptionPane.WARNING_MESSAGE);
	        } else {
	        	//连接数据库
	        	conn = DriverManager.getConnection(DB_URL,USER,PASS);
     	        stmt = conn.createStatement();
     	        ResultSet rs=null;
     	        String sql=null;
     	        sql="select Epassword from employee "   //查询员工的密码
       	         		+ "where Eid = '"+stringUsrinfo+"'";
     	        rs = stmt.executeQuery(sql);   //执行查询操作
     	        
	        	if(rs.next()) {
	        	    if(rs.getString("Epassword").toString().trim().equals(stringPwdinfo.trim()))
    				{   //如果密码正确
	        	    	Main.employeeid = stringUsrinfo;
	        	    	if(type==0)  //进入查询个人信息界面
	        	    	{
	        	    		Main.setPersonalUi();
	        	    	}
	        	    	else if(type==1)  //管理部门信息
	        	    	{
	        	    		//判断是否是经理
	        	    		sql="select worker.Wlevel from worker,employee "
	        	    				+ "where worker.Wid=employee.Wid and employee.Eid='"
	        	    				+stringUsrinfo+"'";
	        	    		rs=stmt.executeQuery(sql);
	        	    		if(rs.next()) {
	        	    			String Wlevel=rs.getString("worker.Wlevel");
	        	    			if(Wlevel.equals("总经理")||Wlevel.equals("经理"))
	        	    			{//进入第二个界面
	        	    				Main.setManagerUi();
	        	    			}
	        	    			else {
	        	    				text_pass.clear();
		        	    			JOptionPane.showMessageDialog(null, "您不是管理人员", "警告", JOptionPane.WARNING_MESSAGE);
		        	    		}
	        	    		}
	     
	        	    	}
	        	    	else return;
	        		    
    				} else {
    					text_pass.clear();
    					JOptionPane.showMessageDialog(null, "密码错误", "警告", JOptionPane.WARNING_MESSAGE);
    				}
	        	} else {
	        		text_id.clear();
	        		text_pass.clear();
	        	    JOptionPane.showMessageDialog(null, "用户不存在", "警告", JOptionPane.WARNING_MESSAGE);
	        	}
	        	rs.close();
      	        stmt.close();
      	        conn.close();
	        }
	    }
	    catch (Exception ex){
	    	ex.printStackTrace();
	    }
    }
}
