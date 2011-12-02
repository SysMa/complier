package com.semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//for table tree
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

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
	
	@SuppressWarnings("deprecation")
	public void fillNode(TableTreeItem classnode){
		classnode.setText(0, "Class");
		classnode.setText(1, getType());
		classnode.setText(2, getName());
		
		fillFiled(classnode);
		fillMethod(classnode);
	}
	
	@SuppressWarnings("deprecation")
	public void fillFiled(TableTreeItem classnode){
		Set<String> keys = fieldSymbolTable.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();){
			String key = i.next();
			TableTreeItem filednode = new TableTreeItem(classnode, SWT.NONE);
			filednode.setText(0, "Filed");
			filednode.setText(1, fieldSymbolTable.get(key));
			filednode.setText(2, key);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void fillMethod(TableTreeItem classnode){
		for (int i = 0; i < methodSymbolTable.size(); i++){
			MethodSymbol ms = methodSymbolTable.get(i);
			TableTreeItem methodnode = new TableTreeItem(classnode, SWT.NONE);
			ms.fillmethodNode(methodnode);
		}
	}
}
