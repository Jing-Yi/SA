package com.sample.product.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sample.product.entity.Product;
import com.sample.product.entity.PurchaseOrder;


public interface PurchaseOrderDAO {	
	
	public void create(Product aProduct, int qty);
	public List<PurchaseOrder> getList();
	public int stockProduct(PurchaseOrder po) throws SQLException;
	public PurchaseOrder getPO(ResultSet rs) throws SQLException;
	
}
