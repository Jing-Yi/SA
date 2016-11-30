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
import com.sample.product.entity.PurchaseOrder;
import com.sample.product.dao.PurchaseOrderDAO;

public class PurchaseOrderDAODB implements PurchaseOrderDAO{
	private DataSource dataSource;
	private Connection conn = null ;
	private ResultSet rs = null ;
	private PreparedStatement smt = null ;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void create(Product aProduct, int qty) {

		// remove first parameter when Id is auto-increment
	    String sql = "INSERT into purchaseOrder (ProductID, Quantity, OrderTime) VALUES(?, ?, Now())";	
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setLong(1, aProduct.getId());
			smt.setInt(2, qty);
			smt.executeUpdate();			
			smt.close();
			System.out.println("id ="+aProduct.getId());
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		
	}


	
	public List<PurchaseOrder> getList() {
		String sql="SELECT * FROM PurchaseOrder";
		List<PurchaseOrder> poList = new ArrayList<PurchaseOrder>();

		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			while(rs.next()){
				poList.add(getPO(rs));
			}
			rs.close();
			smt.close();
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return poList;
	}

	public int stockProduct(PurchaseOrder po) throws SQLException {
		long poid = po.getId();    
		int result = 0;
	    PreparedStatement st1 = null;
	    PreparedStatement st2 = null;
		try {
			conn = dataSource.getConnection();
			//Issue a query and get a result
			smt = conn.prepareStatement("SELECT * from PurchaseOrder WHERE POID = ?");
			smt.setLong(1,poid);
			rs = smt.executeQuery();
			PurchaseOrder p;
			int qty=0;
			long pId=0;
		    if(rs.next()) {
		    	p = getPO(rs);
		    	pId = p.getProductId();
		    	qty = p.getQuantity();
		    } // if
		    smt.close();
		    
		    conn.setAutoCommit(false);
		    //the following two SQL have to be done in the same transaction
		    st1 = conn.prepareStatement("UPDATE product SET Inventory = Inventory + ? WHERE ProductId = ?");
		    st1.setInt(1,qty);
		    st1.setLong(2,pId);
		    result = st1.executeUpdate();
		    st1.close();
		    st2 = conn.prepareStatement("UPDATE PurchaseOrder SET InventoryArrivalTime = Now() WHERE POID = ?");
		    st2.setLong(1,poid);
		    result = st2.executeUpdate();
		    st2.close();
		    conn.commit();
		    conn.close();
		    } // try
		  	catch (SQLException e ) {
		  		e.printStackTrace();
		  		if (conn != null) { 
		  			try { 
		  				System.err.print("Transaction is being rolled back"); 
		  				conn.rollback(); } 
		  			catch(SQLException excep){
		  				e.printStackTrace(); 
		  			}
		  		}
		  		
		  	 } finally { 
		  	 try {
				if (st1 != null) {
				   st1.close(); }
		  	  	if (st2 != null) { 
		  	  		st2.close(); }
		  	} catch (SQLException e) {
		  		e.printStackTrace();
			}
		      conn.close();
		  	 } 

		return result;
	} //stockProduct
	
	public PurchaseOrder getPO(ResultSet rs) throws SQLException {
		try {
			PurchaseOrder aPO = new PurchaseOrder();
			aPO.setId(rs.getLong("POID"));
			aPO.setProductId(rs.getLong("productID"));
			aPO.setQuantity(rs.getInt("quantity"));
			aPO.setOrderTime(rs.getDate("orderTime"));
			aPO.setStockArrivalTime(rs.getDate("ArrivalTime"));
		    return aPO;
		    } //try
		    catch (SQLException e) {
		      throw e;
		    }
	   } //getPO

}
