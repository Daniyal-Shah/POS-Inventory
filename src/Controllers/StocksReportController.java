/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class StocksReportController implements Initializable{

    @FXML
    private JFXDatePicker selectedDate;

    @FXML
    private ToggleGroup stockType;
    
    @FXML
    private JFXRadioButton stocksOut;

    @FXML
    private JFXRadioButton stocksIn;

    @FXML
    private JFXButton loadReport;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXDatePicker selectedDate1;

     @FXML
    private JFXButton close;
     
    void closeStage(MouseEvent event) {
        Stage stage=(Stage)close.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void onAction(ActionEvent event) {
        if(event.getSource()==loadReport){
            if(!(((JFXTextField)selectedDate.getEditor()).getText().isEmpty()) && 
                    !(((JFXTextField)selectedDate1.getEditor()).getText().isEmpty())){
                loadReport();
            }
        }else if(event.getSource()==cancel){
            Stage win=(Stage)cancel.getScene().getWindow();
            win.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stocksIn.setSelected(true);
        stocksIn.setToggleGroup(stockType);
        stocksOut.setToggleGroup(stockType);
    }

    private void loadReport() {
        LocalDate fromDate=selectedDate.getValue();
        LocalDate toDate=selectedDate1.getValue();
        if(stocksIn.isSelected()){
            String qu="";
            HashMap<String,Object> hm=new HashMap<>();
            hm.put("fromDate",fromDate.toString());
            hm.put("toDate",toDate.toString());
            PrintReport pr=new PrintReport(qu,hm);
            pr.showReport("stocksIn");
        }else{
             String qu="";
            HashMap<String,Object> hm=new HashMap<>();
            hm.put("fromDate",fromDate.toString());
            hm.put("toDate",toDate.toString());
            PrintReport pr=new PrintReport(qu,hm);
            pr.showReport("stocksOut");
        }
    }

    @FXML
    private void closeWin(ActionEvent event) 
    {
              Stage stage=(Stage)close.getScene().getWindow();
        stage.close();
    }
    
}
