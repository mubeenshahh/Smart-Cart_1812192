package com.Smartcartt.app.Model;

public class Virtual_shops extends Users {

    private String shopname, shopaddress, shopimage, conf_phone;



    public Virtual_shops(){

    }



    public String getConf_phone() {
        return conf_phone;
    }

    public void setConf_phone(String sphone) {
        this.conf_phone = sphone;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopaddress() {
        return shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        this.shopaddress = shopaddress;
    }

    public String getShopimage() {
        return shopimage;
    }

    public void setShopimage(String shopimage) {
        this.shopimage = shopimage;
    }

    public Virtual_shops(String sname, String saddress, String simage, String conf_phone, String vs){

        this.shopname= sname;
        this.shopaddress= saddress;
        this.shopimage=  simage;
        this.conf_phone=conf_phone;



    }








}
