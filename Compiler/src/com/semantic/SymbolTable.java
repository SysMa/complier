package com.semantic;

import java.util.ArrayList;

// for table tree
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
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
	
	// below for the table tree
	public void run(Shell shell) {
	    shell.setText("TableTree of Symbol Table");
	    createContents(shell);
	    shell.pack();
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!shell.getDisplay().readAndDispatch()) {
	    	  shell.getDisplay().sleep();
	      }
	    }
	    shell.getDisplay().dispose();
	  }
	
	/**
	 * Creates the main window's contents
	 * 
	 * @param shell
	 *            the main window
	 */
	@SuppressWarnings("deprecation")
	private void createContents(final Shell shell) {
		shell.setLayout(new FillLayout());

		// Create the TableTree and set some attributes on the underlying table
		TableTree tableTree = new TableTree(shell, SWT.NONE);
		Table table = tableTree.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);

		// Create the columns, passing the underlying table
		for (int i = 0; i < 3; i++) {
			new TableColumn(table, SWT.LEFT).setText("Column " + (i + 1));
		}

		// Create the data
		for (int i = 0; i < classSymbolTable.size(); i++) {
			TableTreeItem classnode = new TableTreeItem(tableTree, SWT.NONE);
			ClassSymbol cs = classSymbolTable.get(i);
			cs.fillNode(classnode);
			classnode.setExpanded(true);
		}

		// Pack the columns
		TableColumn[] columns = table.getColumns();
		for (int i = 0, n = columns.length; i < n; i++) {
			columns[i].pack();
		}
	}
}