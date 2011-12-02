package com.semantic;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

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

	public String getDupMethodString() {
		String str = "";
		
		for (int i = 0; i < classSymbolTable.size(); i++){
			ClassSymbol cs = classSymbolTable.get(i);
			String temp = cs.getDupMethodString();
			if (!"".equals(temp)){
				str += "\tIn class " + cs.getName() + ": \n" + temp;
			}
		}
		
		return str;
	}
	
	public void createTable(Tree tree){;
		tree.removeAll();

		// Create the data
		for (int i = 0; i < classSymbolTable.size(); i++) {
			TreeItem classnode = new TreeItem(tree, SWT.NONE);
			ClassSymbol cs = classSymbolTable.get(i);
			cs.fillNode(classnode);
			classnode.setExpanded(true);
		}

		// Pack the columns
		TreeColumn[] columns = tree.getColumns();
		for (int i = 0, n = columns.length; i < n; i++) {
			columns[i].pack();
		}
	}
}