package com.semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ClassSymbol extends Symbol {
	
	private HashMap<String, String> fieldSymbolTable;
	private ArrayList<MethodSymbol> methodSymbolTable;
	
	public ClassSymbol(){
		fieldSymbolTable = new HashMap<String, String>();
		methodSymbolTable = new ArrayList<MethodSymbol>();
	}
	public ClassSymbol(String name){
		super(name, "class");
		fieldSymbolTable = new HashMap<String, String>();
		methodSymbolTable = new ArrayList<MethodSymbol>();
	}
	
	// get field symbol's type via name if the symbol doen't exist, return null
	public String getFieldSymbolType(String name){
		if (fieldSymbolTable.containsKey(name)){
			return fieldSymbolTable.get(name);
		}
		return null;
	}
	// add a new field symbol
	public void putFieldSymbol(String name, String type){
		fieldSymbolTable.put(name, type);
	}

	public ArrayList<MethodSymbol> getMethodSymbolTable() {
		return methodSymbolTable;
	}

	public void setMethodSymbolTable(ArrayList<MethodSymbol> methodSymbolTable) {
		this.methodSymbolTable = methodSymbolTable;
	}
	
	public ArrayList<MethodSymbol> getMethodSymbol(String name){
		ArrayList<MethodSymbol> msList = new ArrayList<MethodSymbol>();
		for (int i = 0; i < methodSymbolTable.size(); i++){
			MethodSymbol ms = methodSymbolTable.get(i);
			if (ms.getName().endsWith(name)){
				msList.add(ms);
			}
		}
		return msList;
	}
	
	// add a new method symbol
	public void putMethodSymbol(MethodSymbol ms){
		methodSymbolTable.add(ms);
	}
	
	private String fieldToString(){
		String str = "";
		Set<String> keys = fieldSymbolTable.keySet();
		if (keys.size() != 0){
			str += "Field:\n";
		}
		for (Iterator<String> i = keys.iterator(); i.hasNext();){
			String key = i.next();
			str += "\t" + fieldSymbolTable.get(key) + "\t\t\t" + key + "\n";
		}
		return str;
	}
	
	private String methodToString(){
		String str = "";
		for (int i = 0; i < methodSymbolTable.size(); i++){
			MethodSymbol ms = methodSymbolTable.get(i);
			str += "Method:\n" + ms.toString();
		}
		return str;
	}
	
	@Override
	public String toString(){
		String str = "\t" + getType() + "\t\t\t" + getName() + "\n"
				   + fieldToString() + methodToString();
		return str;
	}
}
