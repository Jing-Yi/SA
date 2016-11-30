package com.sample.product.dao;

import java.sql.SQLException;
import java.util.List;

import com.sample.product.entity.Product;
import com.sample.product.entity.PurchaseOrder;


public interface SalesOrderDAO {	
	
	public int sellProduct(List<Long> pList) throws SQLException;


}
