/**
 * sample program for web programming written by Ben Wu
 * reference: http://www.mkyong.com/spring/maven-spring-jdbc-example/
 */
package com.sample.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sample.product.entity.Manager;
import com.sample.product.entity.Product;

public class ManagerDAODB implements ManagerDAO {
	private DataSource dataSource;
	private Connection conn = null ;
	private ResultSet rs = null ;
	private PreparedStatement smt = null ;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Manager> getList(){
		String sql = "SELECT * FROM manager";
		return getList(sql);
	}

	// make it a generic method for different conditions
	public List<Manager> getList(String sql) {
					
		List<Manager> managerList = new ArrayList<Manager>();
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			while(rs.next()){
				Manager manager = new Manager();
				manager.setId(rs.getInt("managerID"));			
				manager.setName(rs.getString("name"));
				manager.setPhone(rs.getString("phone"));
				manager.setAddress(rs.getString("address"));
				manager.setPassword(rs.getString("password"));
				managerList.add(manager);
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
		return managerList;
	}
		  
	public void insert(Manager manager) {

		// remove first parameter when Id is auto-increment
	    String sql = "INSERT INTO manager (Name, Phone, Address, Password) VALUES(?, ?, ?, ?)";	
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, manager.getName());
			smt.setString(2, manager.getPhone());
			smt.setString(3, manager.getAddress());
			smt.setString(4, manager.getPassword());
			smt.executeUpdate();			
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
		
	}

	public Manager get(long id) {
		Manager manager = new Manager();
		String sql = "SELECT * FROM manager WHERE managerID = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setLong(1, id);
			rs = smt.executeQuery();
			if(rs.next()){
				manager.setId(rs.getInt("managerID"));
				manager.setName(rs.getString("name"));
				manager.setPhone(rs.getString("phone"));
				manager.setAddress(rs.getString("address"));
				manager.setPassword(rs.getString("password"));
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
		return manager;
	}
	public Manager get(String name) {
		Manager manager = new Manager();
		String sql = "SELECT * FROM manager WHERE name = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, name);
			rs = smt.executeQuery();
			if(rs.next()){
				manager.setId(rs.getInt("managerID"));
				manager.setName(rs.getString("name"));
				manager.setPhone(rs.getString("phone"));
				manager.setAddress(rs.getString("address"));
				manager.setPassword(rs.getString("password"));
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
		return manager;
	}
	
	public void update(Manager manager) {
		
		String sql = "UPDATE product SET Name=?, Phone=?, Address=?, Password=? "
				+ "WHERE managerID = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, manager.getName());
			smt.setString(2, manager.getPhone());
			smt.setString(3, manager.getAddress());
			smt.setString(4, manager.getPassword());
			smt.setLong(5, manager.getId());
			smt.executeUpdate();			
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
		
	}
	
	public void delete(long id) {
		
		String sql = "DELETE FROM manager WHERE managerID = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setLong(1, id);
			smt.executeUpdate();			
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
	}

	public int count(){
		return 0; //no longer needed
	}

}//

