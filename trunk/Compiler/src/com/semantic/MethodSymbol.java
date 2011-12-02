package com.semantic;

import java.util.ArrayList;

public class MethodSymbol extends Symbol {
	private ArrayList<Symbol> paramsSymbolTable;
	private ArrayList<Symbol> localsSymbolTable;
	
	public MethodSymbol(){
		paramsSymbolTable = new ArrayList<Symbol>();
		localsSymbolTable = new ArrayList<Symbol>();
	}
	public MethodSymbol(String name, String type){
		super(name, type);
		paramsSymbolTable = new ArrayList<Symbol>();
		localsSymbolTable = new ArrayList<Symbol>();
	}
	
	public ArrayList<Symbol> getParamsSymbolTable() {
		return paramsSymbolTable;
	}
	public void setParamsSymbolTable(ArrayList<Symbol> paramsSymbolTable) {
		this.paramsSymbolTable = paramsSymbolTable;
	}
	
	// get params symbol's type via name if the symbol doesn't exist, return null
	public String getParamsSymbolType(String name){
		for (int i = 0; i < paramsSymbolTable.size(); i++){
			Symbol s = paramsSymbolTable.get(i);
			if (s.getName().equals(name)){
				return s.getType();
			}
		}
		return null;
	}
	// add a new params symbol
	public void putParamsSymbol(Symbol s){
		paramsSymbolTable.add(s);
	}
	
	// get locals symbol's type via name if the symbol doesn't exist, return null
	public String getLocalsSymbolType(String name){
		for (int i = 0; i < localsSymbolTable.size(); i++){
			Symbol s = localsSymbolTable.get(i);
			if (s.getName().equals(name)){
				return s.getType();
			}
		}
		return null;
	}
	// add a new locals symbol
	public void putLocalsSymbol(Symbol s){
		localsSymbolTable.add(s);
	}
	
	// before the symbol if the locals symbol exists
	public boolean ifcontain(String name, Symbol before){
		for (int i = 0; i < localsSymbolTable.size(); i++){
			Symbol s = localsSymbolTable.get(i);
			if (s.getName().equals(name)){
				return true;
			}
			if (s.getName().equals(before.getName())){
				break;
			}
		}
		return false;
	}
	
	// judge if the params' type are equal
	public boolean paramsEquals(MethodSymbol ms){
		if (paramsSymbolTable.size() != ms.getParamsSymbolTable().size()){
			return false;
		}
		for (int i = 0; i < paramsSymbolTable.size(); i++){
			if (!paramsSymbolTable.get(i).getType().equals(
					ms.getParamsSymbolTable().get(i).getType())){
				return false;
			}
		}
		return true;
	}
	
	private String paramsToString(){
		String str = "";
		if (paramsSymbolTable.size() != 0){
			str += "Params:\n";
		}
		for (int i = 0; i < paramsSymbolTable.size(); i++){
			Symbol s = paramsSymbolTable.get(i);
			str += "\t" + s.getType() + "\t\t\t" + s.getName() + "\n";
		}
		return str;
	}
	
	private String localsToString(){
		String str = "";
		if (localsSymbolTable.size() != 0){
			str += "Locals:\n";
		}
		for (int i = 0; i < localsSymbolTable.size(); i++){
			Symbol s = localsSymbolTable.get(i);
			str += "\t" + s.getType() + "\t\t\t" + s.getName() + "\n";
		}
		return str;
	}
	
	@Override
	public String toString(){
		String str = "";
		str += "\t" + getType() + "\t\t\t" + getName() + "\n"
			 + paramsToString() + localsToString();
		return str;
	}
	
}
