/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPack;

import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Product {
    private  SimpleStringProperty barCode;
    private  SimpleStringProperty name;
    private  SimpleStringProperty desc;
    private  SimpleStringProperty selling;
    private  SimpleStringProperty unitPrice;
    private  SimpleStringProperty stkIn;
    private  SimpleStringProperty stockOut;
    public  SimpleObjectProperty<Image> imv;
    private  SimpleObjectProperty<Category> ct;
    private  SimpleStringProperty supp;
    private  SimpleStringProperty imPath;
    public Product(String barCode, String name, String desc, String unitPrice,String stkIn, String stockOut,Image imv,Category ct, String supp) {
        this.barCode = new SimpleStringProperty(barCode);
        this.name =new SimpleStringProperty( name);
        this.desc =new SimpleStringProperty( desc);
        this.unitPrice =new SimpleStringProperty( unitPrice);
        this.stockOut =new SimpleStringProperty( stockOut);
        this.stkIn =new SimpleStringProperty( stkIn);
        this.imv =new SimpleObjectProperty(imv);
         this.ct =new SimpleObjectProperty(ct);
         this.supp =new SimpleStringProperty( supp);
     }
    //bar code getter setter
    public String getBarCode(){
        return this.barCode.get();
    }
    public void setBarCode(String barCode){
        this.barCode.set(barCode);
    }
    public StringProperty barCodeProperty(){
        return this.barCode;
    }
    //name getter and setter
    public String getName(){
        return this.name.get();
    }
    public void setName(String name){
        this.name.set(name);
    }
    public StringProperty nameProperty(){
        return this.name;
    }
    
    //description setter and getter
    
    public String getDesc(){
        return this.desc.get();
    }
    public void setDesc(String desc){
        this.desc.set(desc);
    }
    public StringProperty descProperty(){
        return this.desc;
    }
    
    //Unit price getter and setter
    
    public String getUnitPrice(){
        return this.unitPrice.get();
    }
    public void setUnitPrice(String unitPrice){
        this.unitPrice.set(unitPrice);
    }
    public StringProperty unitPriceProperty(){
        return this.unitPrice;
    }
    
    //stockOut getter and Setter
    
    public String getStockOut(){
        return this.stockOut.get();
    }
    public void setStockOut(String stockOut){
        this.stockOut.set(stockOut);
    }
    public StringProperty stockOutProperty(){
        return this.stockOut;
    }
    
  
    //stockIn getter and Setter
    
    public String getStkIn(){
        return this.stkIn.get();
    }
    public void setStkIn(String stkIn){
        this.stkIn.set(stkIn);
    }
    public StringProperty stkInProperty(){
        return this.stkIn;
    }
    
    //image getter and Setter
    public Image getImv(){
        return this.imv.get();
    }
    public void setImv(Image imv){
        this.imv.set(imv);
    }
    public ObjectProperty<Image> imvProperty(){
        return this.imv;
    }
    
    //category getter and setter
    public Category getCt(){
        return this.ct.get();
    }
    public void setCt(Category ct){
        this.ct.set(ct);
    }
    public ObjectProperty<Category> ctProperty(){
        return this.ct;
    }
    
    
    //supplier getter and setter;
    public String getSupp(){
        return this.supp.get();
    }
    public void setSupp(String supp){
        this.supp.set(supp);
    }
    public SimpleStringProperty suppProperty(){
        return this.supp;
    }
    
}
