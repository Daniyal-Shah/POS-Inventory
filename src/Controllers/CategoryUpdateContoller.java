/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DBConnection.Configs;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import MainPack.Category;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CategoryUpdateContoller implements Initializable{

    @FXML
    private JFXTextField upId;

    @FXML
    private JFXTextField upName;

    @FXML
    private JFXButton browse;
    
    @FXML
    private JFXTextField upDesc;

    @FXML
    private JFXTextField upImage;

    @FXML
    private JFXButton upSave;

    @FXML
    private JFXButton upCancel;

    ResultSet rs;
    private FileInputStream is=null;
    private ImageView imv=null;
    private Image im=null;
    private FileChooser chooseFile;
    private File file=null;  
    private Desktop desktop=Desktop.getDesktop();
    Category upCat;
    Configs dbCon=null;
    @FXML
    private JFXButton close;
    @FXML
    void fileHandler(ActionEvent event) {
        
        file=chooseFile.showOpenDialog((Stage)browse.getScene().getWindow());
      if(file!=null){
          im=new Image(file.toURI().toString(),50,100,true,true);
            try {
                is=new FileInputStream(file);
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
          upImage.setText(file.getAbsolutePath());
      }
    }
    Integer orId=0;
    String olName="";
    void getCategory(Category ct){
        upCat=ct;
        orId=ct.getId();
        olName=ct.getName();
        upId.setText(ct.getId().toString());
        upName.setText(ct.getName());
        upDesc.setText(ct.getDesc());
        upImage.setText(ct.getImv().toString());
    }
    
    @FXML
    void onAction(ActionEvent event) throws IOException {
        if(event.getSource()==upSave){
            if(addNewCategory()){
            clearFields();
            infoMessage("Successful");
            }
        }
        else if(event.getSource()==upCancel)
            upCancel.getScene().getWindow().hide();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseFile=new FileChooser();
        chooseFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg","*.png","*.gif"));
        dbCon=Configs.getInstance();
    }
 private void errorMessage(String message){
        Alert a1= new Alert(Alert.AlertType.ERROR);
            a1.setTitle("Error");
            a1.setContentText(message);
            a1.setHeaderText(null);
            a1.showAndWait();
    }
    private void infoMessage(String message){
        Alert a1= new Alert(Alert.AlertType.INFORMATION);
            a1.setContentText(message);
            a1.setHeaderText(null);
            a1.showAndWait();
    }

    private boolean addNewCategory() throws IOException {
        String id=upId.getText();
        String name=upName.getText().toLowerCase();
        String desc=upDesc.getText().toLowerCase();
        String image=upImage.getText();
        
        if(id.isEmpty() || name.isEmpty() || desc.isEmpty() || image.isEmpty())
            errorMessage("Please Complete All the Fields");
        else{
            if(upIdValidation() & upNameValidation() & upDescValidation() ){
             Integer.valueOf(upId.getText().trim());
        String qu="";
        PreparedStatement pst=null;
        if(!upImage.getText().equals(upCat.getImv().toString())){
            qu="UPDATE category SET CategoryID=? ,CategoryName=?, Description=?, Picture=? WHERE CategoryID="+orId;
            if(!checkDuplicate(id,1) && !checkDuplicate(name,2)){
            pst=dbCon.execQueryPrep(qu);
            try {
            pst.setInt(1, Integer.valueOf(upId.getText().trim()));
            pst.setString(2, name);
            pst.setString(3, desc);
            pst.setBinaryStream(4, (InputStream)is, (int)file.length());
            pst.executeUpdate();
            System.out.println("In if");
            return true;
            //pst.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                return false;
                }
            }
             else{
            errorMessage("Duplicate Values");
            }
        
        }
        else{
            qu="UPDATE category SET CategoryID="+id+","+"CategoryName='"+name+"',"+ "Description='"+desc+"' WHERE CategoryID="+orId;
            if(!checkDuplicate(id,1) && !checkDuplicate(name,2)){
            if(dbCon.execAction(qu)){
                System.out.println("exeAction");
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/Category.fxml"));
            Parent root=(Parent)loader.load();
            CategoryController cg=loader.getController();
            cg.refreshTable();
            return true;
            }
          }
            else{
                errorMessage("Duplicate Values");
            }
        }   
            }//end of inner if
        }//end of else
        
        
        return false;
    }

    private void clearFields() {
        upId.clear();
        upName.clear();
        upDesc.clear();
        upImage.clear(); 
    }   
    private boolean checkDuplicate(String id,Integer type) {
        String qu;
        if(type==1)
            qu="SELECT * FROM category WHERE CategoryID=(SELECT CategoryID from category where CategoryID="+Integer.valueOf(upId.getText().toLowerCase())+") and CategoryID!="+orId;
        else
            qu="SELECT * FROM category WHERE CategoryName=(SELECT CategoryName from category where CategoryName='"+upId.getText().toLowerCase()+"') and CategoryName!='"+olName+"';";
        
        try {
           ResultSet rs=dbCon.execQuery(qu);
            if(rs.next()){
                System.out.println(rs.getInt(1)+","+rs.getString(2));
               return true;
            }
            else
            {
               return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    private boolean upIdValidation(){
        Pattern p=Pattern.compile("[0-9]+");
        Matcher m=p.matcher(upId.getText());
        if(m.find() && m.group().equals(upId.getText()))
            return true;
        else{
            Alert a1= new Alert(Alert.AlertType.WARNING);
            a1.setTitle("Validate ID");
            a1.setContentText("Please Enter Valid ID");
            a1.setHeaderText(null);
            a1.showAndWait();
            return false;
        }
    }
    private boolean upNameValidation() {
        Pattern p=Pattern.compile("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}");
        Matcher m=p.matcher(upName.getText());
        if(m.find() && m.group().equals(upName.getText()))
            return true;
        else{
            Alert a1= new Alert(Alert.AlertType.WARNING);
            a1.setTitle("Validate Category Name");
            a1.setContentText("Please Enter Valid Category Name");
            a1.setHeaderText(null);
            a1.showAndWait();
            return false;
        }
    }
    private boolean upDescValidation() {
        Pattern p=Pattern.compile("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}");
        Matcher m=p.matcher(upDesc.getText());
        if(m.find() && m.group().equals(upDesc.getText()))
            return true;
        else{
            Alert a1= new Alert(Alert.AlertType.WARNING);
            a1.setTitle("Validate Description");
            a1.setContentText("Please Enter Valid Description");
            a1.setHeaderText(null);
            a1.showAndWait();
            return false;
        }
    }
    private boolean upImageValidation() {
        Pattern p=Pattern.compile("([a-zA-Z]:)?(\\\\\\\\?[a-zA-Z0-9_.-]+)*\\\\?\\\\?");
        Matcher m=p.matcher(upImage.getText());
        if(m.find() && m.group().equals(upImage.getText()))
            return true;
        else{
            Alert a1= new Alert(Alert.AlertType.WARNING);
            a1.setTitle("Validate Image Pathe");
            a1.setContentText("Please Enter Valid Image Path");
            a1.setHeaderText(null);
            a1.showAndWait();
            return false;
        }
    }

    @FXML
    private void closeWin(ActionEvent event) 
    {
        Stage stage=(Stage)close.getScene().getWindow();
        stage.close();        
    }
}
