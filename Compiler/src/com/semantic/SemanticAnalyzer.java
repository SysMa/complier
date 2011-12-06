package com.semantic;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Tree;

import com.lexical.Token;
import com.parse.SimpleNode;
import com.parse.ParserTreeConstants;

public class SemanticAnalyzer implements ParserTreeConstants{
	private SimpleNode simpleNode;
	private SymbolTable symbolTable;
	
	private enum EXP_ENTRY{PRE, POST, STATEMENT};
	
	private int curMethodCount = 0;

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
						ArrayList<MethodSymbol> msList = cs.getMethodSymbolList(ms.getName());
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
						MethodSymbol.addMethodCount();
						ms.setId();
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
	private void getSymbolTable(SimpleNode node, ClassSymbol cs, 
			MethodSymbol ms) throws Exception{
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			switch (children.getId()){
			case JJTTYPE:
			case JJTID:
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
						String str = "Error occurs at line " + t.beginLine + ", column " 
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
	
	public void checkType() throws SemanticException{
		int childrenSize = simpleNode.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)simpleNode.jjtGetChild(i);
			switch (children.getId()){
			case JJTMAINCLASS:
			case JJTCLASSDECL:
				SimpleNode subChild = (SimpleNode)children.jjtGetChild(1);
				Token t = (Token)subChild.jjtGetValue();
				String name = t.toString();
				ClassSymbol cs = symbolTable.getClassSymbol(name);
				if (children.jjtGetNumChildren() > 2){
					SimpleNode sn = (SimpleNode)children.jjtGetChild(2);
					if (sn.getId() == JJTEXTENDS){
						sn = (SimpleNode)children.jjtGetChild(3);
						t = (Token)sn.jjtGetValue();
						name = t.toString();
						ClassSymbol parent = symbolTable.getClassSymbol(name);
						if (parent == null){
							String str = "Error occurs at line " + t.beginLine 
							   + ", column " + t.beginColumn + ", Class " + name 
							   + " doesn't exist. ";
							throw new SemanticException(str);
						}
						else{
							cs.setParent(parent);
						}
					}
				}
				checkType(children, cs);
				break;
			default:
				break;
			}
		}
	}
	
	private void checkType(SimpleNode node, ClassSymbol cs) throws SemanticException{
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			switch (children.getId()){
			case JJTVARORMETHODDECL:
				checkType(children, cs);
				break;
			case JJTTYPE:
				SimpleNode temp = (SimpleNode)node.jjtGetChild(i + 1);
				Token t = (Token)temp.jjtGetValue();
				String name = t.toString();
				
				if (i + 2 < childrenSize){
					SimpleNode temp2 = (SimpleNode)node.jjtGetChild(i + 2);
					if (temp2.getId() == JJTFORMALLIST){
						curMethodCount++;
						MethodSymbol ms = cs.getMethodSymbol(name, curMethodCount);
						checkType(node, cs, ms);
						i = i + 2;
					}
				}
				break;
			case JJTMAIN:
				MethodSymbol ms = cs.getMethodSymbol("main", 0);
				checkType(node, cs, ms);
				break;
			default:
				break;
			}
		}
	}
	
	private void checkType(SimpleNode node, ClassSymbol cs, 
			MethodSymbol ms) throws SemanticException{
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			switch (children.getId()){
			case JJTPREDECL:
				checkExp(children, cs, ms, EXP_ENTRY.PRE);
				break;
			case JJTPOSTDECL:
				checkExp(children, cs, ms, EXP_ENTRY.POST);
				break;
			case JJTSTATEMENT:
				checkStatement(children, cs, ms);
				break;
			case JJTRETURN:
				break;
			default:
				break;
			}
		}
	}
	
	private void checkExp(SimpleNode node, ClassSymbol cs, 
			MethodSymbol ms, EXP_ENTRY flag) throws SemanticException{
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			switch (children.getId()){
			case JJTID:
				Token t = (Token)children.jjtGetValue();
				String name = t.toString();
				String type;
				if (flag == EXP_ENTRY.PRE){
					if ((type = ms.getParamsSymbolType(name)) != null){
						
					}
					else if ((type = cs.getFieldSymbolType(name)) != null){
						
					}
					else {
						String str = "Error occurs at line " + t.beginLine 
						   + ", column " + t.beginColumn + ", Varible " + name 
						   + " isn't defined before the pre-condition. ";
						throw new SemanticException(str);
					}
				}
				else if(flag == EXP_ENTRY.POST){
					if ((type = ms.getLocalsSymbolType(name)) != null){
						
					}
					else if ((type = ms.getParamsSymbolType(name)) != null){
						
					}
					else if ((type = cs.getFieldSymbolType(name)) != null){
						
					}
					else {
						String str = "Error occurs at line " + t.beginLine 
						   + ", column " + t.beginColumn + ", Varible " + name 
						   + " isn't defined. ";
						throw new SemanticException(str);
					}
				}
				else{
					
				}
				break;
			default:
				checkExp(children, cs, ms, flag);
				break;
			}
		}
	}
	
	private void checkStatement(SimpleNode node, ClassSymbol cs, MethodSymbol ms) throws SemanticException{
		
	}
	
	public String getSymbolTableString(){
		return symbolTable.toString();
	}
	
	public String getDupMethodString(){
		return symbolTable.getDupMethodString();
	}
	
	public void createTable(Tree tree) {
		symbolTable.createTable(tree);
	}
}
