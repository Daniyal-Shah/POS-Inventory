/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
//import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import DBConnection.*;
import static DBConnection.Configs.con;
import MainPack.User;
import com.jfoenix.controls.JFXComboBox;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;


public class LoginController implements Initializable{


    @FXML
    private JFXComboBox<String> empNo;
    
    @FXML
    private JFXPasswordField upass;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton forgotPass;

    @FXML
    private ImageView progress;

    @FXML
    private JFXButton signIn;
    @FXML
    private Label message;
    @FXML
    private JFXButton close;
    
   /* private FontAwesomeIconView rightOrWrong;

    private MaterialDesignIconView closeIcon;
   */
    
    ObservableList<User> userList=FXCollections.observableArrayList();
    ObservableList<String> empNoList=FXCollections.observableArrayList();
    Configs dbCon=null;
    DateFormat dateFormat;
    Date date;
    public LoginController()
    {
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date = new Date();  
    }
    @FXML
    void handleOnAction(ActionEvent event) throws IOException {
        
        final Parent root1=FXMLLoader.load(getClass().getResource("/FXML/MainWin.fxml"));
        if(event.getSource()==login)
        {
            if(loginFun()){
            progress.setVisible(true);
            PauseTransition pt=new PauseTransition();
            pt.setDuration(Duration.seconds(3));
            pt.setOnFinished((ActionEvent ev)->{
            login.getScene().getWindow().hide();
            Scene sc1=new Scene(root1);
            Stage mainWin=new Stage();
            mainWin.initStyle(StageStyle.UNDECORATED);
            mainWin.setScene(sc1);
            mainWin.show();
            mainWin.setResizable(false)  ;
                System.out.println("Login Successfully");
            });
            pt.play();
            }
        }
        else if(event.getSource()==signIn)
        {
            signIn.getScene().getWindow().hide();
            Stage signUpStage=new Stage();
            Parent root=FXMLLoader.load(getClass().getResource("/FXML/SignUp.fxml"));
            Scene sc=new Scene(root);
            signUpStage.setScene(sc);
            signUpStage.initStyle(StageStyle.UNDECORATED);
            signUpStage.show();
            signUpStage.setResizable(false)  ;            
        }
        else if(event.getSource()==forgotPass)
        {
            System.out.println("Inside ForgotPass");
            Stage signUpStage=new Stage();
            Parent root=FXMLLoader.load(getClass().getResource("/FXML/forgotPassword.fxml"));
            Scene sc=new Scene(root);
            signUpStage.setScene(sc);
            signUpStage.initStyle(StageStyle.UNDECORATED);
            signUpStage.show();
            signUpStage.setResizable(false)  ; 
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {

        try 
        {       
            Class.forName("com.mysql.jdbc.Driver");       
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            if(con!=null)
            {
                message.setText("Database is connected.");
            }
            else
            {
                message.setText("Database is disconnected.");                
            }

        }
        catch (Exception ex) 
        {
            System.out.println(ex.toString());
        } 

        try {
            con.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        dbCon=Configs.getInstance();
        
        loadEmpNo();
        empNo.setPromptText("Choose Employee ID");
        empNo.getItems().addAll(empNoList);
        
        login.setDisable(true);
        progress.setVisible(false);
        empNo.setStyle("-fx-text-inner-color: #a0a2ab;");
        upass.setStyle("-fx-text-inner-color: #a0a2ab;");
        //rightOrWrong.setVisible(false);
        
        upass.setOnAction((actionEvent)->
        {
        System.out.println(upass.getText());
        
    });
    
    //login.setDefaultButton(true);
    login.defaultButtonProperty().bind(login.focusedProperty());
    upass.textProperty().addListener((observable,oldVal,newVal)->{
            if(loginFun()){
        //    rightOrWrong.setVisible(true);
          //  rightOrWrong.setGlyphName("CHECK_CIRCLE");
            login.setDisable(false);
            login.requestFocus();
            }
            else{
      //      rightOrWrong.setVisible(true);
       //     rightOrWrong.setGlyphName("TIMES_CIRCLE");              
            }
    });
    empNo.setOnAction(actionEvent->{
        empNo.setStyle("-fx-text-fill: -fx-text-inner-color");
        System.out.println(actionEvent.getEventType().getName());
    });
    
    }

    private void loadEmpNo()  {
        empNoList.clear();
        String qu="SELECT uId FROM user";
        ResultSet rs=dbCon.execQuery(qu);
        try {
            while(rs.next()){
                empNoList.add(rs.getString("uId"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    private boolean loginFun() {
        userList.clear();
        String id=empNo.getValue();
        String pass=upass.getText();
        String qu="SELECT * FROM user WHERE uId="+id+" AND uPass="+"'"+pass+"'";
        ResultSet rs=dbCon.execQuery(qu);
        try {
            if(rs.next()){
                User user=new User(rs.getInt("uId"),rs.getString("uName"),rs.getString("uPass"));
                saveIntoFile(user);
                userList.add(user);
                String dateTime=dateFormat.format(date);
                qu="insert into trackuser values('"+dateTime+"',"+id+")";
                dbCon.execAction(qu);
                return true; 
            }
            else
                return false;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
        
        
    }

    private void saveIntoFile(User user) {
         String FILENAME="C:\\Users\\daniy\\OneDrive\\Desktop\\For Daniyal\\final libraries\\UserDetails.txt";
        String name=user.getUname();
        String pas=user.getPass();
        Integer id=user.getId();
        String content = id+"/"+name+"/"+pas;
       BufferedWriter f = null;
        try {
            f = new BufferedWriter(new FileWriter("C:\\Users\\daniy\\OneDrive\\Desktop\\For Daniyal\\final libraries\\UserDetails.txt"));
            f.write(content);
        }
        catch(IOException e) {
            System.out.println(e);
        }
        finally {
            if (f != null)
                try {
                    f.close();
            } catch (IOException ex) {
                    System.out.println(ex.toString());
            }  
        }
    }

    @FXML
    private void closeWin(ActionEvent event) 
    {
        Stage stage=(Stage)close.getScene().getWindow();
        stage.close();
    }
    
}
