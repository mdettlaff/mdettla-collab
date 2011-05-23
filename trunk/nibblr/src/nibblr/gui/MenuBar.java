package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MenuBar {
	
	private Shell shell;
	
	private Menu menu;
	
	private MenuItem add;
	private MenuItem recommend;
	private MenuItem synchronize;
	private MenuItem exit;
	private MenuItem search;
	private MenuItem preferences;
	private MenuItem about;
	private MenuItem help;
	
	public MenuBar(Shell shell) {
		this.shell = shell;
		
		menu = new Menu(this.shell, SWT.BAR);
		shell.setMenuBar(menu);
		// File menu
		MenuItem sectionFile = new MenuItem(menu, SWT.CASCADE);
		sectionFile.setText(Values.MENU_FILE);
		
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		sectionFile.setMenu(fileMenu);
		
		// File -> Add
		add = new MenuItem(fileMenu, SWT.PUSH);
		add.setText(Values.SUBMENU_ADD + "\tCtrl+A");
		add.setAccelerator(SWT.CTRL + 'A');
		
		// File -> Recommend
		recommend = new MenuItem(fileMenu, SWT.PUSH);
		recommend.setText(Values.SUBMENU_RECOMMEND + "\tCtrl+Shift+A");
		recommend.setAccelerator(SWT.CTRL + SWT.SHIFT + 'A');
		
		// File -> Synchronize
		synchronize = new MenuItem(fileMenu, SWT.PUSH);
		synchronize.setText(Values.SUBMENU_SYNCHRONIZE + "\tCrtl+R");
		synchronize.setAccelerator(SWT.CTRL + 'R');
		
		// File separator
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		// File -> Exit
		exit = new MenuItem(fileMenu, SWT.PUSH);
		exit.setText(Values.SUBMENU_EXIT + "\tCtrl+X");
		exit.setAccelerator(SWT.CTRL + 'X');
		
		// Edit menu
		MenuItem sectionEdit = new MenuItem(menu, SWT.CASCADE);
		sectionEdit.setText(Values.MENU_EDIT);
		
		Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
		sectionEdit.setMenu(editMenu);
		
		// Edit -> Search
		search = new MenuItem(editMenu, SWT.PUSH);
		search.setText(Values.SUBMENU_SEARCH + "\tCtrl+F");
		search.setAccelerator(SWT.CTRL + 'F');
		
		// File separator
		new MenuItem(editMenu, SWT.SEPARATOR);
		
		// Edit -> Preferences
		preferences = new MenuItem(editMenu, SWT.PUSH);
		preferences.setText(Values.SUBMENU_PREFERENCES);
		
		// Window menu
		MenuItem sectionWindow = new MenuItem(menu, SWT.CASCADE);
		sectionWindow.setText(Values.MENU_WINDOW);
		
		Menu windowMenu = new Menu(shell, SWT.DROP_DOWN);
		sectionWindow.setMenu(windowMenu);
		
		// Window -> Maximize
		MenuItem maximize = new MenuItem(windowMenu, SWT.PUSH);
		maximize.setText(Values.SUBMENU_MAXIMIZE);
		maximize.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MenuBar.this.shell.setMaximized(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// Window -> Minimize
		MenuItem minimize = new MenuItem(windowMenu, SWT.PUSH);
		minimize.setText(Values.SUBMENU_MINIMIZE);
		minimize.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MenuBar.this.shell.setMinimized(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// Window -> Restore
		MenuItem restore = new MenuItem(windowMenu, SWT.PUSH);
		restore.setText(Values.SUBMENU_RESTORE);
		restore.addSelectionListener(new SelectionListener() {	
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MenuBar.this.shell.setMaximized(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// Help menu
		MenuItem sectionHelp = new MenuItem(menu, SWT.CASCADE);
		sectionHelp.setText(Values.MENU_HELP);
		
		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		sectionHelp.setMenu(helpMenu);
		
		// Help -> About
		about = new MenuItem(helpMenu, SWT.PUSH);
		about.setText(Values.SUBMENU_ABOUT);
		
		// Help -> Help
		help = new MenuItem(helpMenu, SWT.PUSH);
		help.setText(Values.SUBMENU_HELP + "\tF1");
		help.setAccelerator(SWT.F1);
	}
	
	public void addActionAdd(final Action action) {
		add.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionRecommand(final Action action) {
		recommend.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionSynchronize(final Action action) {
		synchronize.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionExit(final Action action) {
		exit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionSearch(final Action action) {
		search.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionPreferences(final Action action) {
		preferences.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionAbout(final Action action) {
		about.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionHelp(final Action action) {
		help.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
}
