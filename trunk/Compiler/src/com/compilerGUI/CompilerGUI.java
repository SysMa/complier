package com.compilerGUI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;


public class CompilerGUI {

	protected Shell shell;
	private Text text_sourceCode;
	private Text text_analysis;
	private Text text_statistic;

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
		shell.setImage(SWTResourceManager.getImage("icon\\eclipse.png"));
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
		menuItem_fileFrame_openFile.setText("\u6253\u5F00\u6587\u4EF6");
		
		MenuItem menuItem_fileFrame_exit = new MenuItem(menuItem_fileFrame, SWT.NONE);
		menuItem_fileFrame_exit.setText("\u9000\u51FA");
		
		MenuItem menuItem_option = new MenuItem(menu, SWT.CASCADE);
		menuItem_option.setText("\u9009\u9879");
		
		Menu menuItem_optionFrame = new Menu(menuItem_option);
		menuItem_option.setMenu(menuItem_optionFrame);
		
		MenuItem menuItem_optionFrame_lexicalAnalysis = new MenuItem(menuItem_optionFrame, SWT.NONE);
		menuItem_optionFrame_lexicalAnalysis.setText("\u8BCD\u6CD5\u5206\u6790");
		
		MenuItem menuItem_optionFrame_syntaxAnalysis = new MenuItem(menuItem_optionFrame, SWT.NONE);
		menuItem_optionFrame_syntaxAnalysis.setText("\u8BED\u6CD5\u5206\u6790");
		
		MenuItem menuItem_optionFrame_semanticAnalysis = new MenuItem(menuItem_optionFrame, SWT.NONE);
		menuItem_optionFrame_semanticAnalysis.setText("\u8BED\u4E49\u5206\u6790");
		
		MenuItem menuItem_optionFrame_codeGeneration = new MenuItem(menuItem_optionFrame, SWT.NONE);
		menuItem_optionFrame_codeGeneration.setText("\u4EE3\u7801\u751F\u6210");
		
		MenuItem menuItem_help = new MenuItem(menu, SWT.CASCADE);
		menuItem_help.setText("\u5E2E\u52A9");
		
		Menu menuItem_helpFrame = new Menu(menuItem_help);
		menuItem_help.setMenu(menuItem_helpFrame);
		
		MenuItem menu_helpFrame_about = new MenuItem(menuItem_helpFrame, SWT.NONE);
		menu_helpFrame_about.setText("\u5173\u4E8E");
		
		SashForm sashForm = new SashForm(shell, SWT.NONE);
		
		text_sourceCode = new Text(sashForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		text_sourceCode.setText("Take an XYZ program as the input.");
		
		SashForm sashForm_1 = new SashForm(sashForm, SWT.VERTICAL);
		
		text_analysis = new Text(sashForm_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		text_analysis.setText("\u8BCD\u6CD5\u5206\u6790\u9636\u6BB5\uFF1AGenerate and display a token stream. Show appropriate warning messages for illegal identifiers or tokens. \r\nNote: Any words such as \"5x\" and \"2y\", must be reported as an illegal identifier.\r\n\r\n\u8BED\u6CD5\u5206\u6790\u9636\u6BB5\uFF1AGenerate and display a parse tree (or an abstract syntax tree) for this program. Show appropriate warning messages for syntax errors.\r\nNote: A program passing lexical analysis may fail during the syntactical analysis.  Then you need to display the results in the lexical analysis.");
		
		text_statistic = new Text(sashForm_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		text_statistic.setText("\u8BCD\u6CD5\u5206\u6790\u9636\u6BB5\uFF1AShow the occurrence times of each keyword or identifier.\r\n\r\n\u8BED\u6CD5\u5206\u6790\u9636\u6BB5\uFF1ACalculate the weight of a parse tree.");
		sashForm_1.setWeights(new int[] {1, 1});
		sashForm.setWeights(new int[] {1, 1});

	}
}
