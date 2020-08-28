package com.example.recyclerviewdatabase;

public class Contacts {
    private int id;
    private String name;
    private String phoneNumber;
    private String cantidadProd;
    private int total;

    Contacts(String name, String phno, String cantidadProd) {
        this.name = name;
        this.phoneNumber = phno;
        this.cantidadProd = cantidadProd;
        //this.total = total;
    }
    Contacts(int id, String name, String phno, String cantidadProd, int total) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phno;
        this.cantidadProd = cantidadProd;
        this.total = total;
    }
    int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    String getPhno() {
        return phoneNumber;
    }
    public void setPhno(String phno) {
        this.phoneNumber = phno;
    }
    String getCantidadProd() {
        return cantidadProd;
    }
    public void setCantidadProd(String cantidadProd) {
        this.cantidadProd = cantidadProd;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

