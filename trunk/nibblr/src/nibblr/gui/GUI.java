package nibblr.gui;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GUI implements Runnable {
	public GUI() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText(Values.APP_NAME);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		
		shell.setLayout(layout);
				
		new MenuBar(shell);
		new MainToolBar(shell);
		new MainContainer(shell);
		
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
