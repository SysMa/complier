package com.semantic;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Tree;

import com.lexical.Token;
import com.parse.SimpleNode;
import com.parse.ParserTreeConstants;

public class SemanticAnalyzer implements ParserTreeConstants{
	private SimpleNode simpleNode;
	private SymbolTable symbolTable;
	
	private int FLAG_CLASS	= 0x0001;	// get symbol from the class field symbol table
	private int FLAG_PRE	= 0x0003;	// get symbol from the method param symbol table 
										// and the class field symbol table
	private int FLAG_METHOD = 0x0007;	// get symbol from the method param symbol table 
										// and the local symbol table before The specical symbol
										// and the class field symbol table
	private int FLAG_POST	= 0x000F;	// get symbol from the method param symbol table 
										// and the local symbol table
										// and the class field symbol table
	
	private int curMethodCount = 0;
	
	private Symbol lastSymbol = null;	// last symbol defined

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
							   + " has already been defined before.";
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
			case JJTID:
			case JJTTYPE:
				if (i != 0){
					break;
				}
				String type = "";
				if (children.getId() == JJTID){
					type = children.jjtGetValue().toString();
				}
				else{
					type = ((SimpleNode)children.jjtGetChild(i))
								.jjtGetValue().toString();

				}
				SimpleNode temp = (SimpleNode)node.jjtGetChild(i + 1);
				Token t = (Token)temp.jjtGetValue();
				String name = t.toString();
				
				if (name.equals(cs.getName())){
					String str = "Error occurs at line " + t.beginLine + ", column " 
					   + t.beginColumn + ", Varible " + name 
					   + " has already been defined as a Class before.";
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
								   + " has already been defined with the same parameters type"
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
							   + " has already been defined in the Class " 
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
						   + " has already been defined in the Class " 
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
						   + " has already been defined in the Method " + ms.getName();
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
						   + " has already been defined as a parameter in the Method "
						   + ms.getName();
						throw new Exception(str);
					}
					if (ms.getLocalsSymbolType(name) != null){
						String str = "Error occurs at line " + t.beginLine + ", column " 
						   + t.beginColumn + ", Varible " + name 
						   + " has already been defined in Method " + ms.getName();
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
	
	// the entry for type checking
	public void checkType() throws SemanticException{
		int childrenSize = simpleNode.jjtGetNumChildren();
		
		// get class's parent class
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
							   + " is not defined. ";
							throw new SemanticException(str);
						}
						else{
							cs.setParent(parent);
						}
					}
				}
				break;
			default:
				break;
			}
		}
		
		// check cyclic inheritance
		ArrayList<ClassSymbol> csList = symbolTable.getClassSymbolTable();
		for (int i = 0; i < csList.size(); i++){
			ClassSymbol cs = csList.get(i);
			ClassSymbol temp = cs.getParent();
			while (temp != null){
				if (temp.getName().equals(cs.getName())){
					String str = "Error occurs, cyclic inheritance involving "
						+ cs.getName() + ". ";
					throw new SemanticException(str);
				}
				temp = temp.getParent();
			}
		}
		
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)simpleNode.jjtGetChild(i);
			switch (children.getId()){
			case JJTMAINCLASS:
			case JJTCLASSDECL:
				curMethodCount = 0;
				SimpleNode subChild = (SimpleNode)children.jjtGetChild(1);
				Token t = (Token)subChild.jjtGetValue();
				String name = t.toString();
				ClassSymbol cs = symbolTable.getClassSymbol(name);
				checkType(children, cs);
				break;
			default:
				break;
			}
		}
	}
	
	// do the type checking in the class
	private void checkType(SimpleNode node, ClassSymbol cs) throws SemanticException{
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			switch (children.getId()){
			case JJTVARORMETHODDECL:
				checkType(children, cs);
				break;
			case JJTID:
			case JJTTYPE:
				if (i != 0){
					break;
				}
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
					else {
						String expType = cs.getFieldSymbolType(name);
						temp2 = (SimpleNode)node.jjtGetChild(i + 3);
						if (temp2.getId() == JJTEXP){
							t = (Token)temp2.jjtGetValue();
							String type = checkExp(temp2, cs, null, FLAG_CLASS);
							if (!expType.equals(type) && !("long".equals(expType) && "int".equals(type))){
								String str = "Incompatible types: " + expType + " required, but " 
									   + type + " found: line " + t.beginLine 
									   + ", column " + t.beginColumn + ". ";
								throw new SemanticException(str);
							}
						}
						else if (temp2.getId() == JJTEXPLIST){
							if (!"int[]".equals(expType) && !"long[]".equals(expType)){
								String str = "Error occurs at line " + t.beginLine 
								   + ", column " + t.beginColumn + ", Only int[] or long[] "
								   + "can be assigned with an array. ";
								throw new SemanticException(str);
							}
							String type = checkExpList(temp2, cs, null, FLAG_CLASS);
							if (!expType.equals(type) && !("long[]".equals(expType) && "int[]".equals(type))){
								String str = "Incompatible types: " + expType + " required, but " 
								   + type + " found: line " + t.beginLine 
								   + ", column " + t.beginColumn + ". ";
								throw new SemanticException(str);
							}
						}
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
	
	// do the type checking in the method
	private void checkType(SimpleNode node, ClassSymbol cs, MethodSymbol ms) throws SemanticException{
		lastSymbol = null;
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			switch (children.getId()){
			case JJTPREDECL:
				children = (SimpleNode)children.jjtGetChild(1);
				checkExp(children, cs, ms, FLAG_PRE);
				break;
			case JJTPOSTDECL:
				children = (SimpleNode)children.jjtGetChild(1);
				checkExp(children, cs, ms, FLAG_POST);
				break;
			case JJTSTATEMENT:
				checkStatement(children, cs, ms);
				break;
			case JJTRETURN:
				children = (SimpleNode)node.jjtGetChild(i + 1);
				String type = checkExp(children, cs, ms, FLAG_METHOD);
				if (!ms.getType().equals(type)){
					if (!("long".equals(ms.getType()) && "int".equals(type))){
						Token t = (Token)children.jjtGetValue();
						String str = "Incompatible types: " + ms.getType() + " required for "
							   + "the method's return type, but " 
							   + type + " found: line " + t.beginLine 
							   + ", column " + t.beginColumn + ". ";
						throw new SemanticException(str);
					}
				}
				break;
			default:
				break;
			}
		}
	}
	
	// check a statement's type
	private void checkStatement(SimpleNode node, ClassSymbol cs, MethodSymbol ms) throws SemanticException{
		SimpleNode children = (SimpleNode)node.jjtGetChild(0);
		switch (children.getId()){
		case JJTVARDECL:
			checkVarDecl(children, cs, ms);
			break;
		case JJTSTATEMENT:
			checkStatement(children, cs, ms);
			break;
		case JJTIF:{
			SimpleNode brother = (SimpleNode)node.jjtGetChild(1);
			Token t = (Token)brother.jjtGetValue();
			
			String type = checkExp(brother, cs, ms, FLAG_METHOD);
			if (!"boolean".equals(type)){
				String str = "Incompatible types: boolean required, but " 
					   + type + " found: line " + t.beginLine 
					   + ", column " + t.beginColumn + ". ";
				throw new SemanticException(str);
			}
			
			brother = (SimpleNode)node.jjtGetChild(2);
			checkStatement(brother, cs, ms);
			
			brother = (SimpleNode)node.jjtGetChild(4);
			checkStatement(brother, cs, ms);
		}
			break;
		case JJTWHILE:{
			SimpleNode brother = (SimpleNode)node.jjtGetChild(1);
			Token t = (Token)brother.jjtGetValue();
			
			String type = checkExp(brother, cs, ms, FLAG_METHOD);
			if (!"boolean".equals(type)){
				String str = "Incompatible types: boolean required, but " 
					   + type + " found: line " + t.beginLine 
					   + ", column " + t.beginColumn + ". ";
				throw new SemanticException(str);
			}
			
			brother = (SimpleNode)node.jjtGetChild(2);
			checkStatement(brother, cs, ms);
		}
			break;
		case JJTID:
			SimpleNode brother = (SimpleNode)node.jjtGetChild(1);
			if (brother.getId() == JJTID){
				checkVarDecl(node, cs, ms);
			}
			else{
				Token t = (Token)children.jjtGetValue();
				String name = t.toString();
				String expType = null;
				if (ms.ifcontain(name, lastSymbol)){
					expType = ms.getLocalsSymbolType(name);
				}
				if (expType == null){
					expType = ms.getParamsSymbolType(name);
					if (expType == null){
						expType = cs.getFieldSymbolType(name);
					}
				}
	
				if (expType == null){
					String str = "Error occurs at line " + t.beginLine 
					   + ", column " + t.beginColumn + ", Varible " + name
					   + " has not been defined yet. ";
					throw new SemanticException(str);
				}
				
				if (brother.getId() == JJTASSIGN) {
					brother = (SimpleNode)node.jjtGetChild(2);
					t = (Token)brother.jjtGetValue();
					String type = checkExp(brother, cs, ms, FLAG_METHOD);
					
					if (!expType.equals(type) && !("long".equals(expType) && "int".equals(type))){
						String str = "Incompatible types: " + expType + " required, but " 
						   + type + " found: line " + t.beginLine 
						   + ", column " + t.beginColumn + ". ";
						throw new SemanticException(str);
					}
				}
				else if (brother.getId() == JJTEXP){
					if (!"int[]".equals(expType) && !"long[]".equals(expType)){
						String str = "Incompatible types: int[] or long[] required, but " 
						   + expType + " found: line " + t.beginLine 
						   + ", column " + t.beginColumn + ". ";
						throw new SemanticException(str);
					}
					expType = expType.replace("[]", "");
					t = (Token)brother.jjtGetValue();
					String type = checkExp(brother, cs, ms, FLAG_METHOD);
					if (!"int".equals(type)){
						String str = "Incompatible types: int required, but " 
							   + type + " found: line " + t.beginLine 
							   + ", column " + t.beginColumn + ". ";
						throw new SemanticException(str);
					}
					
					brother = (SimpleNode)node.jjtGetChild(3);
					t = (Token)brother.jjtGetValue();
					type = checkExp(brother, cs, ms, FLAG_METHOD);
					if (!expType.equals(type) && !("long".equals(expType) && "int".equals(type))){
						String str = "Incompatible types: " + expType +  " required, but " 
							   + type + " found: line " + t.beginLine 
							   + ", column " + t.beginColumn + ". ";
						throw new SemanticException(str);
					}
				}
			}
			break;
		default:
			break;
		}
	}
	
	// check the varible declaration.
	private void checkVarDecl(SimpleNode node, ClassSymbol cs, MethodSymbol ms) throws SemanticException{
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			switch (children.getId()){
			case JJTID:
			case JJTTYPE:
				if (i != 0){
					break;
				}
				SimpleNode temp = (SimpleNode)node.jjtGetChild(i + 1);
				Token t = (Token)temp.jjtGetValue();
				String name = t.toString();
				
				lastSymbol = new Symbol();
				lastSymbol.setName(name);
				
				if (i + 2 < childrenSize){
					SimpleNode temp2 = (SimpleNode)node.jjtGetChild(i + 2);
					
					String expType = ms.getLocalsSymbolType(name);
					if (expType == null){
						expType = ms.getParamsSymbolType(name);
					}
					lastSymbol.setType(expType);
					
					temp2 = (SimpleNode)node.jjtGetChild(i + 3);
					if (temp2.getId() == JJTEXP){
						t = (Token)temp2.jjtGetValue();
						String type = checkExp(temp2, cs, ms, FLAG_METHOD);
						if (!expType.equals(type) && !("long".equals(expType) && "int".equals(type))){
							String str = "Incompatible types: " + expType + " required, but " 
								   + type + " found: line " + t.beginLine 
								   + ", column " + t.beginColumn + ". ";
							throw new SemanticException(str);
						}
					}
					else if (temp2.getId() == JJTEXPLIST){
						if (!"int[]".equals(expType) && !"long[]".equals(expType)){
							String str = "Error occurs at line " + t.beginLine 
							   + ", column " + t.beginColumn + ", Only int[] or long[] "
							   + "can be assigned with an array. ";
							throw new SemanticException(str);
						}
						String type = checkExpList(temp2, cs, ms, FLAG_METHOD);
						if (!expType.equals(type) && !("long[]".equals(expType) && "int[]".equals(type))){
							String str = "Incompatible types: " + expType + " required, but " 
							   + type + " found: line " + t.beginLine 
							   + ", column " + t.beginColumn + ". ";
							throw new SemanticException(str);
						}
					}
				}
				break;
			default:
				break;
			}
		}
	}
	
	// check the expression and return the expression's type
	private String checkExp(SimpleNode node, ClassSymbol cs, MethodSymbol ms, int flag) throws SemanticException{
		int childrenSize = node.jjtGetNumChildren();

		SimpleNode children = (SimpleNode)node.jjtGetChild(0);
		switch (children.getId()){
		case JJTNEW:{
			//new ID()
			if (childrenSize == 2){
				SimpleNode sub = (SimpleNode)node.jjtGetChild(1);
				Token t = (Token)sub.jjtGetValue();
				String typeName = t.toString();
				if (symbolTable.getClassSymbol(typeName) != null){
					return typeName;
				}
				else {
					String str = "Error occurs at line " + t.beginLine + ", column " 
					   + t.beginColumn + ", Class " + typeName 
					   + " isn't defined in the program. ";
					throw new SemanticException(str);
				}
			}
			//new int[4] or new long[4]
			else if(childrenSize == 3){
				SimpleNode sub = (SimpleNode)node.jjtGetChild(1);
				String typeName = sub.jjtGetValue().toString() + "[]";
				sub = (SimpleNode)node.jjtGetChild(2);
				Token t = (Token)sub.jjtGetValue();
				String tmpType = checkExp(sub, cs, ms, flag);
				if (!"int".equals(tmpType)){
					String str = "Incompatible types: int required, but " 
					   + tmpType + " found: line " + t.beginLine 
					   + ", column " + t.beginColumn + ". ";
					throw new SemanticException(str);
				}
				return typeName;
			}
		}
			break;
		case JJTLOWLEVELEXP:
			return checkLowLevelExp(children, cs, ms, flag);
		case JJTNOT:{
			SimpleNode sub = (SimpleNode)node.jjtGetChild(1);
			Token t = (Token)sub.jjtGetValue();
			String type = checkExp(sub, cs, ms, flag);
			if (!"boolean".equals(type)){
				String str = "Incompatible types: boolean required, but " 
				   + type + " found: line " + t.beginLine 
				   + ", column " + t.beginColumn + ". ";
				throw new SemanticException(str);
			}
			return "boolean";
		}
		default:
			break;
		}
		return null;
	}
	
	private String checkLowLevelExp(SimpleNode node, ClassSymbol cs, MethodSymbol ms, int flag) throws SemanticException{
		int childrenSize = node.jjtGetNumChildren();
		
		int curPos = 0;
		SimpleNode children = (SimpleNode)node.jjtGetChild(0);
		String type = checkHighLevelExp(children, cs, ms, flag);
		String operater = ""; 
		while (curPos + 2 < childrenSize){
			Token t;
			if (!"int".equals(type) && !"long".equals(type)
				&& !"boolean".equals(type)){
				t = (Token)children.jjtGetValue();
				String str = "Error occurs at line " + t.beginLine 
				   + ", column " + t.beginColumn + ", The user-defined type "
				   + type + " can't be an operand. ";
				throw new SemanticException(str);
			}
			SimpleNode children1 = (SimpleNode)node.jjtGetChild(curPos + 1);
			SimpleNode children2 = (SimpleNode)node.jjtGetChild(curPos + 2);
			switch (children1.getId()){
			case JJTAND:
			case JJTOR:{
				String type2 = checkHighLevelExp(children2, cs, ms, flag);
				if (!"boolean".equals(type) || !"boolean".equals(type2)){
					t = (Token)children1.jjtGetValue();
					String str = "Error occurs at line " + t.beginLine 
						   + ", column " + t.beginColumn + ", The operator '"
						   + t.image + "' is undefined for the types "
						   + type + ", " + type2;
					throw new SemanticException(str);
				}
				if ("".equals(operater)){
					operater = "logic";
				}
				else if (!"logic".equals(operater)){
					t = (Token)children1.jjtGetValue();
					String str = "Error occurs at line " + t.beginLine 
					   + ", column " + t.beginColumn + ", an expression can't contains "
					   + "the arithmetic operator '"
					   + t.image + "' and the logic operator at the same time. ";
					throw new SemanticException(str);
				}
				type = "boolean";
			}
				break;
			case JJTPLUS:
			case JJTMINUS:{
				String type2 = checkHighLevelExp(children2, cs, ms, flag);
				if ("boolean".equals(type) || "boolean".equals(type2)){
					t = (Token)children1.jjtGetValue();
					String str = "Error occurs at line " + t.beginLine 
						   + ", column " + t.beginColumn + ", The operator '"
						   + t.image + "' is undefined for the types "
						   + type + ", " + type2;
					throw new SemanticException(str);
				}
				if ("".equals(operater)){
					operater = "arithmetic";
				}
				else if (!"arithmetic".equals(operater)){
					t = (Token)children1.jjtGetValue();
					String str = "Error occurs at line " + t.beginLine 
					   + ", column " + t.beginColumn + ", an expression can't contains "
					   + "the logic operator '"
					   + t.image + "' and the arithmetic operator at the same time. ";
					throw new SemanticException(str);
				}
				if ("long".equals(type) || "long".equals(type2)){
					type = "long";
				}
				else{
					type = "int";
				}
			}
				break;
			default:
				break;
			}
			curPos += 2;
		}
		return type;
	}
	
	private String checkHighLevelExp(SimpleNode node, ClassSymbol cs, MethodSymbol ms, int flag) throws SemanticException{
		int childrenSize = node.jjtGetNumChildren();
		
		int curPos = 0;
		SimpleNode children = (SimpleNode)node.jjtGetChild(0);
		String type = checkUnaryExp(children, cs, ms, flag);
		String operater = ""; 
		while (curPos + 2 < childrenSize){
			Token t;
			if (!"int".equals(type) && !"long".equals(type)
				&& !"boolean".equals(type)){
				t = (Token)children.jjtGetValue();
				String str = "Error occurs at line " + t.beginLine 
				   + ", column " + t.beginColumn + ", The user-defined type "
				   + type + " can't be an operand. ";
				throw new SemanticException(str);
			}
			SimpleNode children1 = (SimpleNode)node.jjtGetChild(curPos + 1);
			SimpleNode children2 = (SimpleNode)node.jjtGetChild(curPos + 2);
			switch (children1.getId()){
			case JJTLESSTHAN:
			case JJTLARGERTHAN:{
				String type2 = checkUnaryExp(children2, cs, ms, flag);
				if ("boolean".equals(type) || "boolean".equals(type2)){
					t = (Token)children1.jjtGetValue();
					String str = "Error occurs at line " + t.beginLine 
						   + ", column " + t.beginColumn + ", The operator '"
						   + t.image + "' is undefined for the types "
						   + type + ", " + type2 + ".";
					throw new SemanticException(str);
				}
				if ("".equals(operater)){
					operater = "logic";
				}
				else if (!"logic".equals(operater)){
					t = (Token)children1.jjtGetValue();
					String str = "Error occurs at line " + t.beginLine 
					   + ", column " + t.beginColumn + ", an expression can't contains "
					   + "the arithmetic operator '"
					   + t.image + "' and the logic operator at the same time. ";
					throw new SemanticException(str);
				}
				type = "boolean";
			}
				break;
			case JJTMULTI:
			case JJTDIVISION:{
				String type2 = checkUnaryExp(children2, cs, ms, flag);
				if ("boolean".equals(type) || "boolean".equals(type2)){
					t = (Token)children1.jjtGetValue();
					String str = "Error occurs at line " + t.beginLine 
						   + ", column " + t.beginColumn + ", The operator '"
						   + t.image + "' is undefined for the types "
						   + type + ", " + type2 + ".";
					throw new SemanticException(str);
				}
				if ("".equals(operater)){
					operater = "arithmetic";
				}
				else if (!"arithmetic".equals(operater)){
					t = (Token)children1.jjtGetValue();
					String str = "Error occurs at line " + t.beginLine 
					   + ", column " + t.beginColumn + ", an expression can't contains "
					   + "the logic operator '"
					   + t.image + "' and the arithmetic operator at the same time. ";
					throw new SemanticException(str);
				}
				if ("long".equals(type) || "long".equals(type2)){
					type = "long";
				}
				else{
					type = "int";
				}
			}
				break;
			default:
				break;
			}
			curPos += 2;
		}
		return type;
	}
	
	private String checkUnaryExp(SimpleNode node, ClassSymbol cs, MethodSymbol ms, int flag) throws SemanticException{
		int childrenSize = node.jjtGetNumChildren();
		SimpleNode children = (SimpleNode)node.jjtGetChild(0);
		String type = "";
		Token t;
		switch (children.getId()){
		case JJTINT_LITERAL:
			type = "int";
			break;
		case JJTLONG_LITERAL:
			type = "long";
			break;
		case JJTTRUE:
		case JJTFALSE:
			type = "boolean";
			break;
		case JJTID:
			t = (Token)children.jjtGetValue();
			String name = t.toString();
			if (symbolTable.getClassSymbol(name) != null){
				String str = "Error occurs at line " + t.beginLine + ", column " 
				   + t.beginColumn + ", Class " + name 
				   + " can't be used directly, maybe you should define an object. ";
				throw new SemanticException(str);
			}
			boolean found = false;
			if ((flag & FLAG_PRE) == FLAG_PRE){
				type = ms.getParamsSymbolType(name);
				if (type != null){
					found = true;
				}
			}
			if ((flag & FLAG_METHOD) == FLAG_METHOD && !found){
				if (ms.ifcontain(name, lastSymbol)){
					type = ms.getLocalsSymbolType(name);
					found = true;
				}
			}
			if ((flag & FLAG_POST) == FLAG_POST && !found){
				type = ms.getLocalsSymbolType(name);
				if (type != null){
					found = true;
				}
			}
			if ((flag & FLAG_CLASS) == FLAG_CLASS && !found){
				type = cs.getFieldSymbolType(name);
				if (type == null && !found){
					String str = "Error occurs at line " + t.beginLine + ", column " 
					   + t.beginColumn + ", Varible " + name 
					   + " isn't defined in the program. ";
					if ((flag & FLAG_PRE) == FLAG_PRE){
						str = "Error occurs at line " + t.beginLine + ", column " 
						   + t.beginColumn + ", Varible " + name 
						   + " isn't defined before the pre-declaration. ";
					}
					throw new SemanticException(str);
				}
			}
			break;
		case JJTTHIS:
			type = cs.getName();
			break;
		case JJTEXP:
			type = checkExp(children, cs, ms, flag);
			break;
		default:
			break;
		}
		// id[1]
		if (childrenSize == 2){
			if (!"int[]".equals(type) && !"long[]".equals(type)){
				t = (Token)children.jjtGetValue();
				String str = "Incompatible types: int[] or long[] required, but " 
					   + type + " found: line " + t.beginLine 
					   + ", column " + t.beginColumn + ". ";
				throw new SemanticException(str);
			}

			SimpleNode brother = (SimpleNode)node.jjtGetChild(1);
			String temp = checkExp(brother, cs, ms, flag);
			if ("long".equals(temp)){
				t = (Token)brother.jjtGetValue();
				String str = "Incompatible types: Can't convert from long to int: " 
					+ "line " + t.beginLine + ", column " + t.beginColumn + ". ";
				throw new SemanticException(str);
			}
			if (!"int".equals(temp)){
				t = (Token)brother.jjtGetValue();
				String str = "Incompatible types: int required, but " 
					   + type + " found: line " + t.beginLine 
					   + ", column " + t.beginColumn + ". ";
				throw new SemanticException(str);
			}
			
			if ("int[]".equals(type)){
				type = "int";
			}
			else if ("long[]".equals(type)){
				type = "long";
			}
		}
		// .length
		else if (childrenSize == 3){
			if (!"int[]".equals(type) && !"long[]".equals(type)){
				t = (Token)children.jjtGetValue();
				String str = "Incompatible types: int[] or long[] required, but " 
					   + type + " found: line " + t.beginLine 
					   + ", column " + t.beginColumn + ". ";
				throw new SemanticException(str);
			}
			type = "int";
		}
		// .method()
		else if (childrenSize == 4) {
			ClassSymbol tempCs = symbolTable.getClassSymbol(type);
			if (tempCs == null){
				t = (Token)children.jjtGetValue();
				String str = "Incompatible types: a class varible required, but " 
					   + type + " found: line " + t.beginLine 
					   + ", column " + t.beginColumn + ". ";
				throw new SemanticException(str);
			}
			SimpleNode brother = (SimpleNode)node.jjtGetChild(2);
			t = (Token)brother.jjtGetValue();
			String methodName = t.toString();
			brother = (SimpleNode)node.jjtGetChild(3);
			ArrayList<String> params = checkParams(brother, cs, ms, flag);
			
			// inherit.
			boolean found = false;
			while (tempCs != null){
				MethodSymbol mss = tempCs.ifMethodExist(methodName, params);
				if (mss != null){
					found = true;
					type = mss.getType();
					break;
				}
				tempCs = tempCs.getParent();
			}
			
			if (!found){
				String paramStr = "";
				for (int i = 0; i < params.size() - 1; i++){
					paramStr += params.get(i) + ", ";
				}
				paramStr += params.get(params.size() - 1);
				
				String str = "Error occurs at line " + t.beginLine + ", column " 
				   + t.beginColumn + ", There is no method " + methodName + "(" + paramStr + ")"
				   + " defined in the Class " + cs.getName() + ". ";
				throw new SemanticException(str);
			}
			
		}
		return type;
	}
	
	private String checkExpList(SimpleNode node, ClassSymbol cs, MethodSymbol ms, int flag) throws SemanticException{
		String type = "";
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			Token t = (Token)children.jjtGetValue();
			
			String tempType = checkExp(children, cs, ms, flag);
				
			if ("long".equals(tempType)){
				type = "long";
			}
			else if ("int".equals(tempType)){
				if ("".equals(type)) {
					type = "int";
				}
			}
			else {
				String str = "Incompatible types: int or long required, but " 
					   + tempType + " found: line " + t.beginLine 
					   + ", column " + t.beginColumn + ". ";
				throw new SemanticException(str);
			}
		}
		
		return type + "[]";
	}
	
	private ArrayList<String> checkParams(SimpleNode node, ClassSymbol cs, MethodSymbol ms, int flag) throws SemanticException{
		ArrayList<String> params = new ArrayList<String>();
		
		int childrenSize = node.jjtGetNumChildren();
		for (int i = 0; i < childrenSize; i++){
			SimpleNode children = (SimpleNode)node.jjtGetChild(i);
			params.add(checkExp(children, cs, ms, flag));
		}
		
		return params;
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
