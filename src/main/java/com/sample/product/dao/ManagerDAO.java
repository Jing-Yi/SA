package com.sample.product.dao;

import java.util.List;

import com.sample.product.entity.Manager;

public interface ManagerDAO {
	
	public List<Manager> getList();
	public void insert(Manager manager);

	public Manager get(long id);
	
	public Manager get(String name);
	
	public void update(Manager manager);
	
	public void delete(long id);

	public int count();

}//ProductDAO

