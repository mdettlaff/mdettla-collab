package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MenuBar {
	public MenuBar(final Shell shell) {
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		// File menu
		MenuItem file = new MenuItem(menu, SWT.CASCADE);
		file.setText(Values.MENU_FILE);
		
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		file.setMenu(fileMenu);
		
		// File -> Add
		MenuItem fileAdd = new MenuItem(fileMenu, SWT.PUSH);
		fileAdd.setText(Values.SUBMENU_ADD + "\tCtrl+A");
		fileAdd.setAccelerator(SWT.CTRL + 'A');
	
		// File separator
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		// File -> Synchronize
		MenuItem fileSynchronize = new MenuItem(fileMenu, SWT.PUSH);
		fileSynchronize.setText(Values.SUBMENU_SYNCHRONIZE + "\tCrtl+R");
		fileSynchronize.setAccelerator(SWT.CTRL + 'R');
		
		// File separator
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		// File -> Import
		MenuItem fileImport = new MenuItem(fileMenu, SWT.PUSH);
		fileImport.setText(Values.SUBMENU_IMPORT + "\tCtrl+I");
		fileImport.setAccelerator(SWT.CTRL + 'I');
		
		// File -> Export
		MenuItem fileExport = new MenuItem(fileMenu, SWT.PUSH);
		fileExport.setText(Values.SUBMENU_EXPORT + "\tCtrl+E");
		fileExport.setAccelerator(SWT.CTRL + 'E');
		
		// File separator
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		// File -> Exit
		MenuItem fileExit = new MenuItem(fileMenu, SWT.PUSH);
		fileExit.setText(Values.SUBMENU_EXIT + "\tCtrl+X");
		fileExit.setAccelerator(SWT.CTRL + 'X');
		fileExit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// Edit menu
		MenuItem edit = new MenuItem(menu, SWT.CASCADE);
		edit.setText(Values.MENU_EDIT);
		
		Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
		edit.setMenu(editMenu);
		
		// Edit -> Search
		MenuItem editSearch = new MenuItem(editMenu, SWT.PUSH);
		editSearch.setText(Values.SUBMENU_SEARCH + "\tCtrl+F");
		editSearch.setAccelerator(SWT.CTRL + 'F');
		editSearch.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new DialogSearch(shell);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// File separator
		new MenuItem(editMenu, SWT.SEPARATOR);
		
		// Edit -> Preferences
		MenuItem editPreferences = new MenuItem(editMenu, SWT.PUSH);
		editPreferences.setText(Values.SUBMENU_PREFERENCES);
		editPreferences.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new DialogPreferences(shell);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// Window menu
		MenuItem window = new MenuItem(menu, SWT.CASCADE);
		window.setText(Values.MENU_WINDOW);
		
		Menu windowMenu = new Menu(shell, SWT.DROP_DOWN);
		window.setMenu(windowMenu);
		
		// Window -> Maximize
		MenuItem windowMaximize = new MenuItem(windowMenu, SWT.PUSH);
		windowMaximize.setText(Values.SUBMENU_MAXIMIZE);
		windowMaximize.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.setMaximized(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// Window -> Minimize
		MenuItem windowMinimize = new MenuItem(windowMenu, SWT.PUSH);
		windowMinimize.setText(Values.SUBMENU_MINIMIZE);
		windowMinimize.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.setMinimized(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// Window -> Restore
		MenuItem windowRestore = new MenuItem(windowMenu, SWT.PUSH);
		windowRestore.setText(Values.SUBMENU_RESTORE);
		windowRestore.addSelectionListener(new SelectionListener() {	
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.setMaximized(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// Help menu
		MenuItem help = new MenuItem(menu, SWT.CASCADE);
		help.setText(Values.MENU_HELP);
		
		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		help.setMenu(helpMenu);
		
		// Help -> About
		MenuItem helpAbout = new MenuItem(helpMenu, SWT.PUSH);
		helpAbout.setText(Values.SUBMENU_ABOUT);
		helpAbout.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new DialogAbout(shell);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// Help -> Help
		MenuItem helpHelp = new MenuItem(helpMenu, SWT.PUSH);
		helpHelp.setText(Values.SUBMENU_HELP + "\tF1");
		helpHelp.setAccelerator(SWT.F1);
		helpHelp.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new DialogHelp(shell);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
	}
}
