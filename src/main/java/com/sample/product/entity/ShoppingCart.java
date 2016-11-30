package com.sample.product.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.sample.product.entity.Product;

//configuration for session, please refer to: http://tuhrig.de/making-a-spring-bean-session-scoped/
@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart implements java.io.Serializable {

	/**
	 * serialVersionUID is generated automatically
	 */
	private static final long serialVersionUID = 3476619468809859762L;
	//private List<Long> productList = new ArrayList<Long>();
	private List<Product> productList = new ArrayList<Product>();

	public List<Product> getCart(){
		return productList;
	}
	public void add(Product aProduct){
		productList.add(aProduct);
	}
	public int count(){
		return productList.size();
	}
	public void cleanup(){
		productList = new ArrayList<Product>();
	}

	public List<Product> getOrder(){
		return productList;
	}
	public void delete(long id){
			getCart().remove((int)id);
			// loop through the productList to reset id

	}
	
	
}
