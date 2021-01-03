/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author daniy
 */
public class MainWinController implements Initializable {

    @FXML
    private Label username;
    @FXML
    private JFXButton out;
    @FXML
    private Label date;
    @FXML
    private JFXButton pos;
    @FXML
    private JFXButton product;
    @FXML
    private JFXButton category;
    @FXML
    private JFXButton inventory;
    @FXML
    private JFXButton sr;
    @FXML
    private JFXButton dsr;
    @FXML
    private JFXButton close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
    Date datee = new Date();  
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
    String strDate = formatter.format(datee);  
    date.setText(strDate);
        
        // TODO
        String sCurrentLine="";
        Integer val=null;
        String FILENAME="C:\\Users\\daniy\\OneDrive\\Desktop\\For Daniyal\\final libraries\\UserDetails.txt";
        BufferedReader br = null;
	FileReader fr = null;
        try {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            String[] ar=(sCurrentLine = br.readLine()).split("/");
            val=Integer.valueOf(ar[0]);
            String userName = ar[1];
            username.setText(userName);
            System.out.println(ar[1]);
            System.out.println("My nO"+val);

        } 
        catch (FileNotFoundException ex)
        {
           System.out.println(ex.toString());
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
        

    }   
    @FXML
    private void logOut(ActionEvent event) throws IOException 
    {

                   
    }

    @FXML
    void handleButton(ActionEvent event) throws IOException 
{
        Stage win=new Stage();
        Scene sc=null;
        
	if(event.getSource()==category)
	{
            Parent root=FXMLLoader.load(getClass().getResource("/FXML/Category.fxml"));
            sc=new Scene(root);
            win.setScene(sc);
            win.show();           
        }

        else if(event.getSource()==product)
	{
            Parent root=FXMLLoader.load(getClass().getResource("/FXML/Product.fxml"));
            sc=new Scene(root);
            win.setScene(sc);
            win.show();
        }
        else if(event.getSource()==dsr)	
	{
            Parent root=FXMLLoader.load(getClass().getResource("/FXML/DailySalesReport.fxml"));
            sc=new Scene(root);
            win.setScene(sc);
            win.show();
        }
        else if(event.getSource()==sr)
	{
            Parent root=FXMLLoader.load(getClass().getResource("/FXML/StocksReport.fxml"));
            sc=new Scene(root);
            win.setScene(sc);
            win.show();
        }
        else if(event.getSource()==pos)
	{
            Parent root=FXMLLoader.load(getClass().getResource("/FXML/POS.fxml"));
            sc=new Scene(root);
            win.setScene(sc);
            win.show();
        }
	//Inventory feature by Ali
	else if(event.getSource()==inventory)
	{
          String qu="";
          HashMap<String,Object> hm=new HashMap<>();
          PrintReport pr=new PrintReport(qu,hm);
          pr.showReport();
        }
        win.setResizable(false);
    }

    @FXML
    private void closeWin(ActionEvent event) 
    {
        Stage stage=(Stage)close.getScene().getWindow();
        stage.close();
    }
    
}
