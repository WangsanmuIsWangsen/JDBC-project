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
    // JDBC �����������ݿ� URL
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
    	combo_select.getItems().addAll("�鿴������Ϣ","��������Ϣ");
    	combo_select.getSelectionModel().select(0);  //Ĭ��ѡ��鿴������Ϣ
    }

    @FXML
    private void on_btn_exit_clicked(ActionEvent event) throws SQLException
    {
    	Event.fireEvent(Main.getPrimaryStage(),
    			new WindowEvent(Main.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
    }
    
    @FXML
    private void on_btn_login_clicked(ActionEvent event)  //��½���¼�
    {
    	try {
    		int type = combo_select.getSelectionModel().getSelectedIndex(); //�õ�ѡ��ص�½����
	    	String stringUsrinfo = "", stringPwdinfo = "";
	    	stringUsrinfo = text_id.getText();
	    	stringPwdinfo = text_pass.getText();
	        if(stringUsrinfo.isEmpty() || stringPwdinfo.isEmpty()) {
	        	JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��", "����", JOptionPane.WARNING_MESSAGE);
	        } else {
	        	//�������ݿ�
	        	conn = DriverManager.getConnection(DB_URL,USER,PASS);
     	        stmt = conn.createStatement();
     	        ResultSet rs=null;
     	        String sql=null;
     	        sql="select Epassword from employee "   //��ѯԱ��������
       	         		+ "where Eid = '"+stringUsrinfo+"'";
     	        rs = stmt.executeQuery(sql);   //ִ�в�ѯ����
     	        
	        	if(rs.next()) {
	        	    if(rs.getString("Epassword").toString().trim().equals(stringPwdinfo.trim()))
    				{   //���������ȷ
	        	    	Main.employeeid = stringUsrinfo;
	        	    	if(type==0)  //�����ѯ������Ϣ����
	        	    	{
	        	    		Main.setPersonalUi();
	        	    	}
	        	    	else if(type==1)  //��������Ϣ
	        	    	{
	        	    		//�ж��Ƿ��Ǿ���
	        	    		sql="select worker.Wlevel from worker,employee "
	        	    				+ "where worker.Wid=employee.Wid and employee.Eid='"
	        	    				+stringUsrinfo+"'";
	        	    		rs=stmt.executeQuery(sql);
	        	    		if(rs.next()) {
	        	    			String Wlevel=rs.getString("worker.Wlevel");
	        	    			if(Wlevel.equals("�ܾ���")||Wlevel.equals("����"))
	        	    			{//����ڶ�������
	        	    				Main.setManagerUi();
	        	    			}
	        	    			else {
	        	    				text_pass.clear();
		        	    			JOptionPane.showMessageDialog(null, "�����ǹ�����Ա", "����", JOptionPane.WARNING_MESSAGE);
		        	    		}
	        	    		}
	     
	        	    	}
	        	    	else return;
	        		    
    				} else {
    					text_pass.clear();
    					JOptionPane.showMessageDialog(null, "�������", "����", JOptionPane.WARNING_MESSAGE);
    				}
	        	} else {
	        		text_id.clear();
	        		text_pass.clear();
	        	    JOptionPane.showMessageDialog(null, "�û�������", "����", JOptionPane.WARNING_MESSAGE);
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
