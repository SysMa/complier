package com.compilerGUI;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.wb.swt.SWTResourceManager;

import com.lexical.LexicalAnalyzer;
import com.lexical.Token;
import com.parse.Parser;
import com.parse.SimpleNode;
import com.semantic.SemanticAnalyzer;
import com.semantic.SemanticException;


public class CompilerGUI {

	protected Shell shell;
	protected Shell sematicshell;
	private SashForm sashForm_1;
	private Tree symbolTree;
	private StyledText text_sourceCode;
	private Text text_analysis;
	private Text text_statistic;
	private int line = -1;
	
	private boolean textChanged = false;
	private boolean lexicalOk = false;
	private boolean parseOk = false;
	
	private ArrayList<Token> tokenSource = new ArrayList<Token>();
	private SimpleNode root;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CompilerGUI window = new CompilerGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		
		sematicshell = new Shell(display);
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage("icon/eclipse.png"));
		shell.setSize(690, 491);
		shell.setText("\u7F16\u8BD1\u5668");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem menuItem_file = new MenuItem(menu, SWT.CASCADE);
		menuItem_file.setText("\u6587\u4EF6");
		
		Menu menuItem_fileFrame = new Menu(menuItem_file);
		menuItem_file.setMenu(menuItem_fileFrame);
		
		MenuItem menuItem_fileFrame_openFile = new MenuItem(menuItem_fileFrame, SWT.NONE);
		menuItem_fileFrame_openFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			    final String textString;
			    FileDialog dialog = new FileDialog(shell, SWT.OPEN);
			    dialog.setFilterExtensions(new String[] {"*.txt"});
			    String name = dialog.open();
			    if ((name == null) || (name.length() == 0))
			    	return;
			    try {
			      File file = new File(name);
			      FileInputStream stream= new FileInputStream(file.getPath());
			      try {
			        Reader in = new BufferedReader(new InputStreamReader(stream));
			        char[] readBuffer= new char[2048];
			        StringBuffer buffer= new StringBuffer((int) file.length());
			        int n;
			        while ((n = in.read(readBuffer)) > 0) {
			          buffer.append(readBuffer, 0, n);
			        }
			        textString = buffer.toString();
			        stream.close();
			      } catch (IOException e1) {
			        MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
			        box.setMessage("Error reading file:\n" + name);
			        box.open();
			        return;
			      }
			    } catch (FileNotFoundException e1) {
			      MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
			      box.setMessage("File not found:\n" + name);
			      box.open();
			      return;
			    }
			    text_sourceCode.setText(textString);
			}
		});
		menuItem_fileFrame_openFile.setText("\u6253\u5F00\u6587\u4EF6");
		
		new MenuItem(menuItem_fileFrame, SWT.SEPARATOR);
		
		MenuItem menuItem_fileFrame_exit = new MenuItem(menuItem_fileFrame, SWT.NONE);
		menuItem_fileFrame_exit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		menuItem_fileFrame_exit.setText("\u9000\u51FA");
		
		MenuItem menuItem_option = new MenuItem(menu, SWT.CASCADE);
		menuItem_option.setText("\u9009\u9879");
		
		Menu menuItem_optionFrame = new Menu(menuItem_option);
		menuItem_option.setMenu(menuItem_optionFrame);
		
		MenuItem menuItem_optionFrame_lexicalAnalysis = new MenuItem(menuItem_optionFrame, SWT.NONE);
		menuItem_optionFrame_lexicalAnalysis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_analysis.setVisible(true);
				symbolTree.setVisible(false);
				sashForm_1.layout();
				
				if (line != -1){
					text_sourceCode.setLineBackground( line-1,1, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				}
				
				String input = text_sourceCode.getText();
				HashMap<String, Integer> tokenCountMap = new HashMap<String, Integer>();
				StringBuffer tokenStr = new StringBuffer("这是词法分析结果:\n");
				LexicalAnalyzer la = new LexicalAnalyzer(
						new ByteArrayInputStream(input.getBytes()));
				try{
					tokenStr.append(la.getTokenString(tokenCountMap));
					TreeMap<String, Integer> mapSorted = 
						new TreeMap<String, Integer>(tokenCountMap);
					text_analysis.setText(tokenStr.toString());
					String statistic = "这是词法分析的统计结果:\n数目\t\t关键字或变量\n";
					for (Iterator<String> i = mapSorted.keySet().iterator(); 
			          	i.hasNext();){
						String key = i.next();
						statistic += mapSorted.get(key) + "\t\t" + key + "\n";
					}
					text_statistic.setText(statistic);
					tokenSource = la.getTokenSource();
					lexicalOk = true;
					textChanged = false;
				}
				catch (Exception e2){
					text_analysis.setText(e2.getMessage());
					text_statistic.setText("");
				}
			}
		});
		menuItem_optionFrame_lexicalAnalysis.setText("\u8BCD\u6CD5\u5206\u6790");
		
		MenuItem menuItem_optionFrame_syntaxAnalysis = new MenuItem(menuItem_optionFrame, SWT.NONE);
		menuItem_optionFrame_syntaxAnalysis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_analysis.setVisible(true);
				symbolTree.setVisible(false);
				sashForm_1.layout();
				
				if (textChanged){
					text_analysis.setText("源文件已发生更改，请重新进行词法分析！");
					text_statistic.setText("");
					textChanged = false;
				}
				else if (!lexicalOk){
					text_analysis.setText("请先进行词法分析！");
					text_statistic.setText("");
				}
				else{
					Parser parser = new Parser(tokenSource);
					StringBuffer parseTree = 
						new StringBuffer("这是生成的语法分析树:\n");
					Map<String, Integer> weightMap = new HashMap<String, Integer>();
					try{
						root = parser.program(weightMap);
						root.dump("", parseTree);
						Integer weight = weightMap.get("$weight$");
						Integer count = weightMap.get("$count$");
						text_analysis.setText(parseTree.toString());
						text_statistic.setText("该语法分析树的总权重:\t\t\t" + weight
								+ "\n该语法分析树中简单语句的数目:\t" + count);
						parseOk = true;
						textChanged = false;
					}
					catch (Exception e2)
				    {
					    text_analysis.setText(e2.getMessage());
					    text_statistic.setText("");
				    }
				}
			}
		});
		menuItem_optionFrame_syntaxAnalysis.setText("\u8BED\u6CD5\u5206\u6790");
		
		MenuItem menuItem_optionFrame_semanticAnalysis = new MenuItem(menuItem_optionFrame, SWT.NONE);
		menuItem_optionFrame_semanticAnalysis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_analysis.setVisible(true);
				symbolTree.setVisible(false);
				sashForm_1.layout();
				
				if (textChanged){
					text_analysis.setText("源文件已发生更改，请重新进行词法分析！");
					text_statistic.setText("");
					textChanged = false;
				}
				else if (!parseOk){
					text_analysis.setText("请先进行语法分析！");
					text_statistic.setText("");
				}
				else{
					SemanticAnalyzer sa = new SemanticAnalyzer(root);
					try{
						sa.getSymbolTable();
						String dupMethod = "Duplicate methods: \n"
										 + sa.getDupMethodString();

						text_analysis.setVisible(false);
						symbolTree.setVisible(true);
						text_statistic.setText(dupMethod);
						
						sa.createTable(symbolTree);
						symbolTree.redraw();
						sashForm_1.layout();
						
						sa.checkType();
					}
					catch (SemanticException se){
						text_analysis.setVisible(true);
						sashForm_1.layout();
						
						String temp = se.getMessage();
						int begin = temp.indexOf("line");
						if (begin != -1){
							String substr = temp.substring(begin+5, temp.indexOf(",", begin));
							line = Integer.parseInt(substr);
							text_sourceCode.setLineBackground( line-1,1, Display.getCurrent().getSystemColor(SWT.COLOR_RED));
						}
						text_analysis.setText(se.getMessage());
					}
					catch (Exception e2)
				    {
						text_analysis.setVisible(true);
						sashForm_1.layout();
				        text_analysis.setText(e2.getMessage());
				        text_statistic.setText("");
				    }
				}
			}
		});
		menuItem_optionFrame_semanticAnalysis.setText("\u8BED\u4E49\u5206\u6790");
		
		MenuItem menuItem_optionFrame_codeGeneration = new MenuItem(menuItem_optionFrame, SWT.NONE);
		menuItem_optionFrame_codeGeneration.setText("\u4EE3\u7801\u751F\u6210");
		
		MenuItem menuItem_help = new MenuItem(menu, SWT.CASCADE);
		menuItem_help.setText("\u5E2E\u52A9");
		
		Menu menuItem_helpFrame = new Menu(menuItem_help);
		menuItem_help.setMenu(menuItem_helpFrame);
		
		MenuItem menu_helpFrame_about = new MenuItem(menuItem_helpFrame, SWT.NONE);
		menu_helpFrame_about.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//JOptionPane.showMessageDialog(null,"这是帮助信息!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
				try {
					//打开当前文件夹下面的 README.txt文件
					Runtime.getRuntime().exec("cmd /c start notepad .\\README.txt");
				}
				catch(Exception e2) {
					e2.printStackTrace();
				} 
			}
		});
		menu_helpFrame_about.setText("\u5173\u4E8E");
		
		SashForm sashForm = new SashForm(shell, SWT.NONE);
		textChanged = false;
		
		text_sourceCode = new StyledText(sashForm, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		text_sourceCode.setText("Code here.");
		text_sourceCode.setRightMargin(1);
		text_sourceCode.setIndent(1);
		text_sourceCode.setLeftMargin(1);
		text_sourceCode.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		
		sashForm_1 = new SashForm(sashForm, SWT.VERTICAL);
		
		symbolTree = new Tree(sashForm_1, SWT.BORDER);
		symbolTree.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		symbolTree.setVisible(false);
		symbolTree.setHeaderVisible(true);
		symbolTree.setLinesVisible(false);
		TreeColumn tc = new TreeColumn(symbolTree, SWT.LEFT);
		tc.setText("Scope");
		tc = new TreeColumn(symbolTree, SWT.LEFT);
		tc.setText("Type");
		tc = new TreeColumn(symbolTree, SWT.LEFT);
		tc.setText("Varible Name");
		
		text_analysis = new Text(sashForm_1, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		text_analysis.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		text_analysis.setText("\u8BCD\u6CD5\u5206\u6790\u9636\u6BB5\uFF1AGenerate and display a token stream. Show appropriate warning messages for illegal identifiers or tokens. \r\nNote: Any words such as \"5x\" and \"2y\", must be reported as an illegal identifier.\r\n\r\n\u8BED\u6CD5\u5206\u6790\u9636\u6BB5\uFF1AGenerate and display a parse tree (or an abstract syntax tree) for this program. Show appropriate warning messages for syntax errors.\r\nNote: A program passing lexical analysis may fail during the syntactical analysis.  Then you need to display the results in the lexical analysis.");
		
		text_statistic = new Text(sashForm_1, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		text_statistic.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		text_statistic.setText("\u8BCD\u6CD5\u5206\u6790\u9636\u6BB5\uFF1AShow the occurrence times of each keyword or identifier.\r\n\r\n\u8BED\u6CD5\u5206\u6790\u9636\u6BB5\uFF1ACalculate the weight of a parse tree.");
		sashForm_1.setWeights(new int[] {1, 1, 1});
		sashForm.setWeights(new int[] {1, 1});

	}
}
