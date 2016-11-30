package com.sample.product.entity;

public class Product {
    private long id;
    private String category;
    private String desc;
    private int inventory;
    private int reorderPoint;
    private String details;

    /* getters and setters */
    public long getId(){
            return id;
    }
    public void setId(long id){
    	this.id = id;
    }

    public String getCategory(){
            return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    
    public String getDesc(){
        return desc;
    }
    public void setDesc(String desc){
    	this.desc = desc;
    }

    public int getInventory(){
    		return inventory;
    }
    public void setInventory(int inventory){
		this.inventory = inventory;
    }
    
    public int getReorderPoint(){
    		return reorderPoint;
    }
    public void setReorderPoint(int reorderPoint){
		this.reorderPoint = reorderPoint;
    }

    public String getDetails(){
    	return details;
    }
    
    public void setDetails(String details){
    	this.details = details;
    }
}//Product

