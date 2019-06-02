package application;
	
import java.io.IOException;
import java.sql.*;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Main extends Application {
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	 	 
	 static Stage primarystage = null;
	 private SplitPane root_personal = null;
	 private SplitPane root_login = null;
	 private SplitPane root_manager = null;
	 private static Scene scene_personal = null;
	 private static Scene scene_login = null;
	 private static Scene scene_manager = null;
	 
	 static String employeeid;  //����½ID��¼����
	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException, SQLException 
	{
		try {
			primaryStage.setTitle("���ʹ���ϵͳ");
			primarystage = primaryStage;
			root_personal = FXMLLoader.load(getClass().getResource("view/info.fxml"));
        	root_login = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        	root_manager = FXMLLoader.load(getClass().getResource("view/manage.fxml"));
            scene_personal = new Scene(root_personal);
            scene_login = new Scene(root_login);
            scene_manager = new Scene(root_manager);
            setLoginUi();
            primarystage.show();
	         // ע�� JDBC ����
	        Class.forName(JDBC_DRIVER);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e){
	         // ���� Class.forName ����
	         e.printStackTrace();
	     }
    }
    public void setPrimaryStage(Stage stage)
    {
    	primarystage = stage;
    }
    public static void setLoginUi()
    {
        primarystage.setScene(scene_login);
    }
    public static void setPersonalUi()
    {
    	primarystage.setScene(scene_personal);
    }
    public static void setManagerUi()
    {
    	primarystage.setScene(scene_manager);
    }
    public static void main(String[] args) {
        launch(args);
    }
	public static Stage getPrimaryStage() {
		return primarystage;
	}
}
