package com.sample.product.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

//configuration for session, please refer to: http://tuhrig.de/making-a-spring-bean-session-scoped/
//@Component
//@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Account implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8772944132506476198L;
	private String name;
	private String password;

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}

	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}

	
}

