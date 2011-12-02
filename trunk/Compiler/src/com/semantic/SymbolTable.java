package com.semantic;

import java.util.ArrayList;

public class SymbolTable {
	private ArrayList<ClassSymbol> classSymbolTable;
	
	public SymbolTable(){
		classSymbolTable = new ArrayList<ClassSymbol>();
	}

	public ClassSymbol getClassSymbol(String name) {
		for (int i = 0; i < classSymbolTable.size(); i++){
			ClassSymbol cs = classSymbolTable.get(i);
			if (cs.getName().equals(name)){
				return cs;
			}
		}
		return null;
	}

	public void putClassSymbol(ClassSymbol cs) {
		classSymbolTable.add(cs);
	}
	
	@Override
	public String toString(){
		String str = "";
		for (int i = 0; i < classSymbolTable.size(); i++){
			ClassSymbol cs = classSymbolTable.get(i);
			str += "Class:\n" + cs.toString();
		}
		return str;
	}
}