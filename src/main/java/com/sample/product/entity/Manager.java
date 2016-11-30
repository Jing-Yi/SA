package com.sample.product.entity;

public class Manager {
    private long id;
    private String name;
    private String phone;
    private String address;
    private String password;

    /* getters and setters */
    public long getId(){
            return id;
    }
    public void setId(long id){
    	this.id = id;
    }

    public String getName(){
            return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
    	this.phone = phone;
    }

    public String getAddress(){
    		return address;
    }
    public void setAddress(String address){
		this.address = address;
    }
    
    public String getPassword(){
    	return password;
    }
    
    public void setPassword(String password){
    	this.password = password;
    }
    

}//Manager

