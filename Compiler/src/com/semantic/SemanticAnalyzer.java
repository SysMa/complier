package com.semantic;

import java.util.ArrayList;

import com.lexical.Token;
import com.parse.SimpleNode;
import com.parse.ParserTreeConstants;

public class SemanticAnalyzer implements ParserTreeConstants{
	private SimpleNode simpleNode;
	private SymbolTable symbolTable;

	public SemanticAnalyzer() {
		// TODO Auto-generated constructor stub
	}
	public SemanticAnalyzer(SimpleNode simpleNode){
		this.simpleNode = simpleNode;
		symbolTable = new SymbolTable();
	}
	
	// get the symbol table
	public void getSymbolTable() throws Exception{
		int childrenSize = simpleNode.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)simpleNode.jjtGetChild(i);
			switch (children.getId()){
			case JJTMAINCLASS:
			case JJTCLASSDECL:
				SimpleNode subChild = (SimpleNode)children.jjtGetChild(1);
				Token t = (Token)subChild.jjtGetValue();
				String name = t.toString();
				if (symbolTable.getClassSymbol(name) != null){
					String str = "Error occurs at line " + t.beginLine + ", column " 
							   + t.beginColumn + ", Class " + name 
							   + " has already defined before.";
					throw new Exception(str);
				}
				ClassSymbol cs = new ClassSymbol(name);
				getSymbolTable(children, cs);
				symbolTable.putClassSymbol(cs);
				break;
			default:
				break;
			}
		}
	}
	
	private void getSymbolTable(SimpleNode node, ClassSymbol cs) throws Exception{
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			switch (children.getId()){
			case JJTVARORMETHODDECL:
				getSymbolTable(children, cs);
				break;
			case JJTTYPE:
				String type = ((SimpleNode)children.jjtGetChild(0))
								.jjtGetValue().toString();
				SimpleNode temp = (SimpleNode)node.jjtGetChild(i + 1);
				Token t = (Token)temp.jjtGetValue();
				String name = t.toString();
				
				if (name.equals(cs.getName())){
					String str = "Error occurs at line " + t.beginLine + ", column " 
					   + t.beginColumn + ", Varible " + name 
					   + " has already defined as a Class before.";
					throw new Exception(str);
				}
				
				if (i + 2 < childrenSize){
					SimpleNode temp2 = (SimpleNode)node.jjtGetChild(i + 2);
					if (temp2.getId() == JJTFORMALLIST){
						MethodSymbol ms = new MethodSymbol();
						getSymbolTable(node, cs, ms);
						ArrayList<MethodSymbol> msList = cs.getMethodSymbol(ms.getName());
						for (int j = 0; j < msList.size(); j++){
							MethodSymbol tMs = msList.get(j);
							if (tMs.paramsEquals(ms)){
								String str = "Error occurs at line " + t.beginLine + ", column " 
								   + t.beginColumn + ", Method " + ms.getName() 
								   + " has already defined with the same parameters type"
								   + " in the Class " + cs.getName() + ".";
								throw new Exception(str);
							}
						}
						cs.putMethodSymbol(ms);
						i = i + 2;
					}
					else {
						if (cs.getFieldSymbolType(name) != null){
							String str = "Error occurs at line " + t.beginLine + ", column " 
							   + t.beginColumn + ", Varible " + name 
							   + " has already defined in the Class " 
							   + cs.getName() + ".";
							throw new Exception(str);
						}
						cs.putFieldSymbol(name, type);
						i++;
					}
				}
				else {
					if (cs.getFieldSymbolType(name) != null){
						String str = "Error occurs at line " + t.beginLine + ", column " 
						   + t.beginColumn + ", Varible " + name 
						   + " has already defined in the Class " 
						   + cs.getName() + ".";
						throw new Exception(str);
					}
					cs.putFieldSymbol(name, type);
					i++;
				}
				break;
			case JJTMAIN:
				MethodSymbol ms = new MethodSymbol("main", "void");
				getSymbolTable(node, cs, ms);
				cs.putMethodSymbol(ms);
				break;
			default:
				break;
			}
		}
	}
	private void getSymbolTable(SimpleNode node, ClassSymbol cs, MethodSymbol ms) throws Exception{
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			switch (children.getId()){
			case JJTTYPE:
			case JJTID:
				if (childrenSize < 2){
					break;
				}
				String type = "";
				String name = "";
				Token t = new Token();
				if (children.getId() == JJTID){
					type = children.jjtGetValue().toString();
					SimpleNode temp = (SimpleNode)node.jjtGetChild(i + 1);
					if (temp.getId() == JJTID){
						t = (Token)temp.jjtGetValue();
						name = t.toString();
					}
					else {
						break;
					}
				}
				else {
					type = ((SimpleNode)children.jjtGetChild(0))
								.jjtGetValue().toString();
					SimpleNode temp = (SimpleNode)node.jjtGetChild(i + 1);
					t = (Token)temp.jjtGetValue();
					name = t.toString();
				}
				if (i + 2 < childrenSize){
					SimpleNode temp2 = (SimpleNode)node.jjtGetChild(i + 2);
					if (temp2.getId() == JJTFORMALLIST){
						ms.setName(name);
						ms.setType(type);
						getSymbolTable(temp2, cs, ms);
						i = i + 2;
					}
				}
				if (((SimpleNode)children.jjtGetParent()).getId() == JJTFORMALLIST){
					if (ms.getParamsSymbolType(name) != null){
						String str = "Error occurs at line " + t.beginLine + ", column " 
						   + t.beginColumn + ", Parameter " + name 
						   + " has already defined in the Method " + ms.getName();
						throw new Exception(str);
					}
					ms.putParamsSymbol(new Symbol(name, type));
					i++;
				}
				else if (((SimpleNode)children.jjtGetParent()).getId() == JJTVARDECL || 
						children.getId() == JJTID){
					if (ms.getParamsSymbolType(name) != null){
						String str = "Error occurs at line " + t.beginLine + ", column " 
						   + t.beginColumn + ", Varible " + name 
						   + " has already defined as a parameter in the Method "
						   + ms.getName();
						throw new Exception(str);
					}
					if (ms.getLocalsSymbolType(name) != null){
						String str = "At line " + t.beginLine + ", column " 
						   + t.beginColumn + ", Varible " + name 
						   + " has already defined in Method " + ms.getName();
						throw new Exception(str);
					}
					ms.putLocalsSymbol(new Symbol(name, type));
					i++;
				}
				break;
			case JJTSTATEMENT:
			case JJTVARDECL:
				getSymbolTable(children, cs, ms);
				break;
			default:
				break;
			}
		}
	}
	
	public void checkType(){
		
	}
	
	public String getSymbolTableString(){
		return symbolTable.toString();
	}
}
