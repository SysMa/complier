import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TabItem;


public class CompilerGUI {

	protected Shell shell;
	private Text txtTakeAnXyz;
	private Text txtgenerateAndDisplay;
	private Text txtshowTheOccurrence;

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
		shell.setSize(690, 491);
		shell.setText("\u7F16\u8BD1\u5668");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem menu_file = new MenuItem(menu, SWT.CASCADE);
		menu_file.setText("\u6587\u4EF6");
		
		Menu menu_fileFrame = new Menu(menu_file);
		menu_file.setMenu(menu_fileFrame);
		
		MenuItem menu_fileFrame_open = new MenuItem(menu_fileFrame, SWT.NONE);
		menu_fileFrame_open.setText("\u6253\u5F00\u6587\u4EF6");
		
		MenuItem menu_fileFrame_options = new MenuItem(menu_fileFrame, SWT.CASCADE);
		menu_fileFrame_options.setText("\u9009\u9879");
		
		Menu menu_fileFrame_optionsFrame = new Menu(menu_fileFrame_options);
		menu_fileFrame_options.setMenu(menu_fileFrame_optionsFrame);
		
		MenuItem lexicalAnalysis = new MenuItem(menu_fileFrame_optionsFrame, SWT.NONE);
		lexicalAnalysis.setText("\u8BCD\u6CD5\u5206\u6790");
		
		MenuItem syntaxAnalysis = new MenuItem(menu_fileFrame_optionsFrame, SWT.NONE);
		syntaxAnalysis.setText("\u8BED\u6CD5\u5206\u6790");
		
		MenuItem semanticAnalysis = new MenuItem(menu_fileFrame_optionsFrame, SWT.NONE);
		semanticAnalysis.setText("\u8BED\u4E49\u5206\u6790");
		
		MenuItem codeGeneration = new MenuItem(menu_fileFrame_optionsFrame, SWT.NONE);
		codeGeneration.setText("\u4EE3\u7801\u751F\u6210");
		
		MenuItem menu_fileFrame_exit = new MenuItem(menu_fileFrame, SWT.NONE);
		menu_fileFrame_exit.setText("\u9000\u51FA");
		
		MenuItem menu_help = new MenuItem(menu, SWT.CASCADE);
		menu_help.setText("\u5E2E\u52A9");
		
		Menu menu_helpFrame = new Menu(menu_help);
		menu_help.setMenu(menu_helpFrame);
		
		MenuItem menu_helpFrame_about = new MenuItem(menu_helpFrame, SWT.NONE);
		menu_helpFrame_about.setText("\u5173\u4E8E");
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		
		TabItem tabItem_analysis = new TabItem(tabFolder, SWT.NONE);
		tabItem_analysis.setText("\u5206\u6790\u7ED3\u679C");
		
		SashForm sashForm = new SashForm(tabFolder, SWT.SMOOTH);
		tabItem_analysis.setControl(sashForm);
		
		txtTakeAnXyz = new Text(sashForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		txtTakeAnXyz.setText("Take an XYZ program as the input.");
		
		txtgenerateAndDisplay = new Text(sashForm, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		txtgenerateAndDisplay.setText("(\u8FD9\u4E2A\u6587\u672C\u6846\u4E2D\u7684\u5DE6\u53F3\u6EDA\u52A8\u6761\u6211\u7ED9\u53BB\u6389\u4E86\uFF0C\u6539\u7528\u81EA\u52A8\u6362\u884C)\r\n\r\n\u8BCD\u6CD5\u5206\u6790\u9636\u6BB5\uFF1AGenerate and display a token stream. Show appropriate warning messages for illegal identifiers or tokens. \r\nNote: Any words such as \"5x\" and \"2y\", must be reported as an illegal identifier.\r\n\r\n\u8BED\u6CD5\u5206\u6790\u9636\u6BB5\uFF1AGenerate and display a parse tree (or an abstract syntax tree) for this program. Show appropriate warning messages for syntax errors.\r\nNote: A program passing lexical analysis may fail during the syntactical analysis.  Then you need to display the results in the lexical analysis.");
		sashForm.setWeights(new int[] {332, 331});
		
		TabItem tabItem_statistics = new TabItem(tabFolder, SWT.NONE);
		tabItem_statistics.setText("\u7EDF\u8BA1\u7ED3\u679C");
		
		SashForm sashForm_1 = new SashForm(tabFolder, SWT.NONE);
		tabItem_statistics.setControl(sashForm_1);
		
		txtshowTheOccurrence = new Text(sashForm_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		txtshowTheOccurrence.setText("\u8BCD\u6CD5\u5206\u6790\u9636\u6BB5\uFF1AShow the occurrence times of each keyword or identifier\r\n\r\n\u8BED\u6CD5\u5206\u6790\u9636\u6BB5\uFF1ACalculate the weight of a parse tree\uFF08\u9274\u4E8E\u7248\u9762\u8FD9\u4E48\u5927\uFF0C\u53EA\u8F93\u51FA\u4E00\u4E2A\u6570\u5B57\u7684\u8BDD\u4E0D\u592A\u96C5\u89C2\uFF0C\u8003\u8651\u8981\u4E0D\u8981\u628A\u8BA1\u7B97\u6574\u68F5\u6811\u6743\u91CD\u7684\u8FC7\u7A0B\u8F93\u51FA\u51FA\u6765\uFF09\r\n\r\n\u8BED\u4E49\u5206\u6790\u9636\u6BB5\u548C\u4EE3\u7801\u751F\u6210\u9636\u6BB5\uFF1A\u5747\u4E0D\u8F93\u51FA\r\n\r\n");
		sashForm_1.setWeights(new int[] {1});

	}
}
