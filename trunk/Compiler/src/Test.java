import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class Test {

	protected Shell shell;
	private Text text_source;
	private Text text_result;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Test window = new Test();
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
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		shell.setSize(675, 560);
		shell.setText("\u7F16\u8BD1\u5668");
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem menuItem_file = new MenuItem(menu, SWT.CASCADE);
		menuItem_file.setText("\u6587\u4EF6");
		
		Menu menu_file = new Menu(menuItem_file);
		menuItem_file.setMenu(menu_file);
		
		MenuItem mntmCtrlo_openFile = new MenuItem(menu_file, SWT.NONE);
		mntmCtrlo_openFile.setText("\u6253\u5F00\u6587\u4EF6\tCtrl+O");
		
		MenuItem mntmCtrlx_exit = new MenuItem(menu_file, SWT.NONE);
		mntmCtrlx_exit.setText("\u9000\u51FA\tCtrl+X");
		
		MenuItem menuItem_help = new MenuItem(menu, SWT.CASCADE);
		menuItem_help.setText("\u5E2E\u52A9");
		
		Menu menu_help = new Menu(menuItem_help);
		menuItem_help.setMenu(menu_help);
		
		MenuItem menuItem_help_about = new MenuItem(menu_help, SWT.NONE);
		menuItem_help_about.setText("\u5173\u4E8E");
		
		text_source = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		text_source.setText("\u8FD9\u91CC\u663E\u793A\u6253\u5F00\u7684txt\u6587\u6863\u5185\u5BB9\uFF0C\u7528\u6237\u4E5F\u53EF\u4EE5\u81EA\u5DF1\u7F16\u8F91\u8868\u8FBE\u5F0F\u3002\r\n\r\n\u201C\u6587\u4EF6->\u6253\u5F00\u6587\u4EF6\u201D\u53EF\u4EE5\u6253\u5F00txt\u6587\u6863");
		text_source.setBounds(23, 22, 542, 187);
		
		Button button_lexical_analysis = new Button(shell, SWT.NONE);
		button_lexical_analysis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//JOptionPane.showMessageDialog(null, "词法分析！");
			}
		});
		button_lexical_analysis.setBounds(23, 244, 80, 27);
		button_lexical_analysis.setText("\u8BCD\u6CD5\u5206\u6790");
		
		Button button_syntax_analysis = new Button(shell, SWT.NONE);
		button_syntax_analysis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//JOptionPane.showMessageDialog(null, "语法分析！");
			}
		});
		button_syntax_analysis.setBounds(140, 244, 80, 27);
		button_syntax_analysis.setText("\u8BED\u6CD5\u5206\u6790");
		
		Button button_semantic_analysis = new Button(shell, SWT.NONE);
		button_semantic_analysis.setBounds(255, 244, 80, 27);
		button_semantic_analysis.setText("\u8BED\u4E49\u5206\u6790");
		
		Button button_code_optimization = new Button(shell, SWT.NONE);
		button_code_optimization.setBounds(372, 244, 80, 27);
		button_code_optimization.setText("\u4EE3\u7801\u4F18\u5316");
		
		Button button_code_generation = new Button(shell, SWT.NONE);
		button_code_generation.setBounds(485, 244, 80, 27);
		button_code_generation.setText("\u4EE3\u7801\u751F\u6210");
		
		Button button_clear_txt = new Button(shell, SWT.NONE);
		button_clear_txt.setBounds(579, 182, 51, 27);
		button_clear_txt.setText("\u6E05\u7A7A");
		
		text_result = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		text_result.setText("\u8FD9\u91CC\u663E\u793A\u5BF9\u4E0A\u9762\u6587\u672C\u6846\u4E2D\u5B57\u7B26\u6D41\u7684\u5404\u79CD\u5206\u6790\uFF0C\u7C7B\u578B\u4E3A\u53EA\u8BFB\u3002");
		text_result.setBounds(23, 305, 542, 175);
		
		Button button_clear_analysis = new Button(shell, SWT.NONE);
		button_clear_analysis.setBounds(579, 453, 51, 27);
		button_clear_analysis.setText("\u6E05\u7A7A");

	}
}
