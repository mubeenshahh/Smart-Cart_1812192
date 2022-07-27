package com.Smartcartt.app.Model;

public class Users {
    private String name, email, phone, password, image, address, virtualshopstatus, isAdmin, AdminApproval, VirtualShopApproval, OrderPlaced;

    public String getAdminApproval() {
        return AdminApproval;
    }

    public void setAdminApproval(String adminApproval) {
        AdminApproval = adminApproval;
    }


    public Users()
    {

    }

    public String getVirtualshopstatus() {
        return virtualshopstatus;
    }

    public String getOrderPlaced() {
        return OrderPlaced;
    }

    public void setOrderPlaced(String orderPlaced) {
        OrderPlaced = orderPlaced;
    }

    public void setVirtualshopstatus(String virtualshopstatus) {
        this.virtualshopstatus = virtualshopstatus;
    }

    public Users(String name, String email, String phone, String password, String image,String vs, String address, String isAdmin, String AdminApproval, String virtualshopstatus, String Orderplaced) {
        this.name = name;
        this.phone = phone;
        this.OrderPlaced=Orderplaced;
        this.password = password;
        this.image = image;
        this.address = address;
        this.email =email;
        this.isAdmin=isAdmin;
        this.virtualshopstatus=virtualshopstatus;
        this.AdminApproval=AdminApproval;
        this.VirtualShopApproval=vs;
    }

    public String getName() {
        return name;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVirtualShopApproval() {
        return VirtualShopApproval;
    }

    public void setVirtualShopApproval(String virtualShopApproval) {
        VirtualShopApproval = virtualShopApproval;
    }

    public String  getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
