package nibblr.gui;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GUI implements Runnable {
	Display display;
	Shell shell;
	
	private MenuBar menu;
	
	private CompositeTool tool;
	private CompositeChannels channels;
	private CompositeItems items;
	private CompositeView view;
	
	public GUI() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		display = new Display();
		shell = new Shell(display);
		shell.setText(Values.APP_NAME);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		shell.setLayout(layout);
		
		menu = new MenuBar(shell);
		tool = new CompositeTool(shell);
		Container container = new Container(shell);
		channels = container.getChannels();
		items = container.getItems();
		view = container.getView();
		
		// Listener 
		menu.getAdd().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				add();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		menu.getSynchronize().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				synchronize();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		menu.getExit().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				exit();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		menu.getSearch().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				search();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		menu.getPreferences().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				preference();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		menu.getAbout().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				about();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		menu.getHelp().addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				help();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		tool.getAdd().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				add();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		tool.getSynchronize().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				synchronize();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		tool.getSearch().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				search();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		tool.getPreference().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				preference();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	// private
	
	private void add() {
		
	}
	
	private void synchronize() {
		
	}
	
	private void exit() {
		shell.dispose();
	}
	
	private void search() {
		new DialogSearch(shell);
	}
	
	private void preference() {
		new DialogPreferences(shell);
	}
	
	private void about() {
		new DialogAbout(shell);
	}
	
	private void help() {
		new DialogHelp(shell);
	}
}
