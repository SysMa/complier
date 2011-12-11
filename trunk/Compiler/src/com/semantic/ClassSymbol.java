package com.semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class ClassSymbol extends Symbol {
	
	private ClassSymbol parent;
	private HashMap<String, String> fieldSymbolTable;
	private ArrayList<MethodSymbol> methodSymbolTable;
	
	public ClassSymbol(){
		fieldSymbolTable = new HashMap<String, String>();
		methodSymbolTable = new ArrayList<MethodSymbol>();
		parent = null;
		MethodSymbol.refreshMethodCount();
	}
	public ClassSymbol(String name){
		super(name, "class");
		fieldSymbolTable = new HashMap<String, String>();
		methodSymbolTable = new ArrayList<MethodSymbol>();
		parent = null;
		MethodSymbol.refreshMethodCount();
	}
	
	public ClassSymbol getParent() {
		return parent;
	}
	public void setParent(ClassSymbol parent) {
		this.parent = parent;
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
	
	public ArrayList<MethodSymbol> getMethodSymbolList(String name){
		ArrayList<MethodSymbol> msList = new ArrayList<MethodSymbol>();
		for (int i = 0; i < methodSymbolTable.size(); i++){
			MethodSymbol ms = methodSymbolTable.get(i);
			if (ms.getName().endsWith(name)){
				msList.add(ms);
			}
		}
		return msList;
	}
	
	public MethodSymbol getMethodSymbol(String name, int id){
		for (int i = 0; i < methodSymbolTable.size(); i++){
			MethodSymbol ms = methodSymbolTable.get(i);
			if (ms.getName().equals(name) && ms.getId() == id){
				return ms;
			}
		}
		return null;
	}
	
	public MethodSymbol ifMethodExist(String name, ArrayList<String> params){
		ArrayList<MethodSymbol> msList = getMethodSymbolList(name);
		ArrayList<MethodSymbol> mss = new ArrayList<MethodSymbol>();
		int minIndex = 0;
		int min = 1000;
		
		for (int i = 0; i < msList.size(); i++){
			int flag = 0;
			MethodSymbol ms = msList.get(i);
			ArrayList<Symbol> paramList = ms.getParamsSymbolTable();
			if (params.size() == paramList.size()){
				int j;
				for (j = 0; j < params.size(); j++){
					String type = params.get(j);
					String expType = paramList.get(j).getType();
					if (!type.equals(expType)){
						if (("int".equals(type) && "long".equals(expType))
							|| ("int[]".equals(type) && "long[]".equals(expType))){
							flag++;
							continue;
						}
						break;
					}
				}
				if (j == params.size()){
					if (flag > 1){
						if (flag < min){
							min = flag;
							minIndex = mss.size();
						}
						mss.add(ms);
					}
					else {
						return ms;
					}
				}
			}
		}
		if (mss.size() == 0){
			return null;
		}
		return mss.get(minIndex);
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
	
	private HashMap<String, ArrayList<MethodSymbol>> getDupMethod(){
		HashMap<String, ArrayList<MethodSymbol>> dupms = new 
					HashMap<String, ArrayList<MethodSymbol>>();
		
		for (int i = 0; i < methodSymbolTable.size(); i++){
			MethodSymbol ms = methodSymbolTable.get(i);
			ArrayList<MethodSymbol> msList = getMethodSymbolList(ms.getName());
			if (msList.size() > 1 && !dupms.containsKey(ms.getName())){
				dupms.put(ms.getName(), msList);
			}
		}
		return dupms;
	}
	
	public String getDupMethodString(){
		String str = "";
		
		HashMap<String, ArrayList<MethodSymbol>> dupms = getDupMethod();
		Set<String> keys = dupms.keySet();
		
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();){
			String key = iter.next();
			ArrayList<MethodSymbol> msList = dupms.get(key);
			for (int i = 0; i < msList.size(); i++){
				MethodSymbol ms = msList.get(i);
				str += "\t\t" + ms.getType() + " " + ms.getName() + "(";
				ArrayList<Symbol> params = ms.getParamsSymbolTable();
				for (int j = 0; j < params.size(); j++){
					Symbol s = params.get(j);
					str += s.getType() + " " + s.getName();
					if (j != (params.size() - 1)){
						str += ", ";
					}
				}
				str += ")\n";
			}
			str += "\n";
		}
		
		return str;
	}
	
	public void fillNode(TreeItem classnode){
		classnode.setText(0, "Class");
		classnode.setText(1, getType());
		classnode.setText(2, getName());
		
		fillField(classnode);
		fillMethod(classnode);
	}
	

	public void fillField(TreeItem classnode){
		Set<String> keys = fieldSymbolTable.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();){
			String key = i.next();
			TreeItem filednode = new TreeItem(classnode, SWT.NONE);
			filednode.setText(0, "Field");
			filednode.setText(1, fieldSymbolTable.get(key));
			filednode.setText(2, key);
		}
	}
	
	public void fillMethod(TreeItem classnode){
		for (int i = 0; i < methodSymbolTable.size(); i++){
			MethodSymbol ms = methodSymbolTable.get(i);
			TreeItem methodnode = new TreeItem(classnode, SWT.NONE);
			ms.fillmethodNode(methodnode);
		}
	}
}
