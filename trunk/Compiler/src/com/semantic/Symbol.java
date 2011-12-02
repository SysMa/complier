package com.semantic;

public class Symbol {
	private String name;
	private String type;
	
	public Symbol() {
		// TODO Auto-generated constructor stub
	}
	
	public Symbol(String name, String type){
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
