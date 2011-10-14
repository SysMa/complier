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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;


public class Test {

	protected Shell shell;
	private Text txttxttxtjdfoasijdfoasjfopaisjfoasjefoaisjfoasijfoasijfaos;
	private Text txtResult;

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
		shell.setSize(751, 560);
		shell.setText("\u7F16\u8BD1\u5668");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_file = new Menu(mntmFile);
		mntmFile.setMenu(menu_file);
		
		MenuItem mntmCtrlo_openFile = new MenuItem(menu_file, SWT.NONE);
		mntmCtrlo_openFile.setText("Open");
		
		MenuItem mntmEmptyResult = new MenuItem(menu_file, SWT.NONE);
		mntmEmptyResult.setText("Empty Result");
		
		MenuItem mntmCtrlx_exit = new MenuItem(menu_file, SWT.NONE);
		mntmCtrlx_exit.setText("Exit");
		
		MenuItem mntmAction = new MenuItem(menu, SWT.CASCADE);
		mntmAction.setText("Action");
		
		Menu menu_1 = new Menu(mntmAction);
		mntmAction.setMenu(menu_1);
		
		MenuItem mntmLex = new MenuItem(menu_1, SWT.NONE);
		mntmLex.setText("Lex");
		
		MenuItem mntmParse = new MenuItem(menu_1, SWT.NONE);
		mntmParse.setText("Parse");
		
		MenuItem mntmSyntax = new MenuItem(menu_1, SWT.NONE);
		mntmSyntax.setText("Syntax");
		
		MenuItem mntmCodeOpt = new MenuItem(menu_1, SWT.NONE);
		mntmCodeOpt.setText("Code Opt");
		
		MenuItem mntmCodeGeneration = new MenuItem(menu_1, SWT.NONE);
		mntmCodeGeneration.setText("Code Generation");
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");
		
		Menu menu_help = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_help);
		
		MenuItem mntmAbout = new MenuItem(menu_help, SWT.NONE);
		mntmAbout.setText("About");
		
		SashForm sashForm = new SashForm(shell, SWT.BORDER | SWT.SMOOTH);
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		txttxttxtjdfoasijdfoasjfopaisjfoasjefoaisjfoasijfoasijfaos = new Text(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txttxttxtjdfoasijdfoasjfopaisjfoasjefoaisjfoasijfoasijfaos.setText("Scoure Code");
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		txtResult = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtResult.setText("Result");
		sashForm.setWeights(new int[] {1, 1});

	}
}
