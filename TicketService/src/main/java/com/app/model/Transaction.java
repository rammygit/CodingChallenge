package com.app.model;

import java.io.Serializable;

/**
 * holds details about the current transaction
 * @author ramkumarsundarajan
 *
 */
public class Transaction implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String code;
	
	private String description;
	
	private boolean error;
	
	public Transaction(String code,String description){
		this.code = code;
		this.description = description;
	}
	
	
	public Transaction(){
		
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
