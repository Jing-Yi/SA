package com.sample.product.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;







//import com.mysql.jdbc.Statement;
import com.sample.product.entity.Product;
import com.sample.product.entity.ShoppingCart;
import com.sample.product.dao.SalesOrderDAO;

public class SalesOrderDAODB implements SalesOrderDAO{
	private DataSource dataSource;
	private Connection conn = null ;
	private ResultSet rs = null ;
	private PreparedStatement smt = null ;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int sellProduct(List<Long> pList) throws SQLException {
		    
		if (pList.size() == 0){
			return 0; // if nothing in the shopping cart
		}
		long orderID = 0;
		int result = 0;
		int count = 0; // count how many sales order items were processed successfully
		
	    PreparedStatement stCreateOrder = null;
		PreparedStatement stUpdateProduct = null;
		PreparedStatement stInsertOrderItem = null;


		try {
		      
			//Connect to a database
			conn = dataSource.getConnection();
			conn.setAutoCommit(false); //make it a transaction
			/*
			String sql = "INSERT INTO salesOrder (OrderTime) VALUES(Now())";
			st1 = conn.prepareStatement(sql);
			st1.executeUpdate();
			st1.close();
		    */
			
			// get order id for MS Access and SQL Server
			/*
			st2 = conn.prepareStatement("SELECT @@IDENTITY");
			ResultSet rs = st2.executeQuery();
		      if (rs != null && rs.next()) {
		    	  orderID = rs.getInt(1);
		      }
		      st2.close();
		      //System.out.println(orderID+"is created");
			*/
		      
			// get order id for MySQL
			
			String sqlCreateOrder = "INSERT INTO salesOrder (OrderTime) VALUES(Now())";
		    stCreateOrder  = conn.prepareStatement(sqlCreateOrder, PreparedStatement.RETURN_GENERATED_KEYS);
		    result = stCreateOrder.executeUpdate();
		    ResultSet rs = stCreateOrder.getGeneratedKeys();
		    if (rs != null && rs.next()) {
		    	orderID = rs.getLong(1);
		    }
		    System.out.println("order id:"+orderID);
		    stCreateOrder.close();		    
		      
		    for (long productID:pList){
		    	//the following two SQL have to be done in the same transaction
		    	//Issue a query and get a result
		    	stUpdateProduct = conn.prepareStatement("UPDATE product SET Inventory = Inventory - 1 WHERE ProductId = ?");
		    	stUpdateProduct.setLong(1,productID);
		    	result = stUpdateProduct.executeUpdate();
		    	stUpdateProduct.close();
		    	//System.out.println(productID+"is updated");
		    	String sqlInsertOrderItem = "INSERT INTO salesOrderItem (SOID, ProductID, Quantity) VALUES(?, ?, 1)";
		    	stInsertOrderItem = conn.prepareStatement(sqlInsertOrderItem);
		    	stInsertOrderItem.setLong(1,orderID);
		    	stInsertOrderItem.setLong(2,productID);
		    	result = stInsertOrderItem.executeUpdate();
		    	stInsertOrderItem.close();
		    	System.out.println(productID+"is processed");
		    	count ++;
		      }// for all products on the cart
		      
		      conn.commit();
		      conn.close();
		      
		} // try
    	catch (Exception e) {
    		count = 0;
    		e.printStackTrace();
	  		if (conn!= null) { 
	  			try { 
	  				System.err.print("Transaction is being rolled back"); 
	  				conn.rollback(); } 
	  			catch(SQLException excep){
	  				e.printStackTrace(); 
	  			}
	  		}
	    } finally { 
		  	  if (stCreateOrder != null) {
		  	   stCreateOrder.close(); }
		  	  if (stUpdateProduct != null) { 
		  	   stUpdateProduct.close(); }
		  	  if (stInsertOrderItem != null) { 
			  	   stInsertOrderItem.close(); }
		  	  conn.close();
		} 
	    return count;
	} //sellProduct


}
