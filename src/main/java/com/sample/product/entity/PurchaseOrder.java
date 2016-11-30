package com.sample.product.entity;

import java.sql.Date;

public class PurchaseOrder {
    private long id;
    private long productId;
    private int quantity;
    private Date orderTime;
    private Date stockArrivalTime;

    /* getters and setters */
    public long getId(){
            return id;
    }
    public void setId(long id){
    	this.id = id;
    }

	public long getProductId(){
		return productId;
	}
    public void setProductId(long productId){
    	this.productId = productId;
    }

	public int getQuantity(){
		return quantity;
	}
    public void setQuantity(int quantity){
    	this.quantity = quantity;
    }

	public Date getOrderTime(){
		return orderTime;
	}
    public void setOrderTime(Date orderTime){
    	this.orderTime = orderTime;
    }

    public Date getStockArrivalTime(){
		return stockArrivalTime;
	}
    public void setStockArrivalTime(Date stockArrivalTime){
    	this.stockArrivalTime = stockArrivalTime;
    }

}
