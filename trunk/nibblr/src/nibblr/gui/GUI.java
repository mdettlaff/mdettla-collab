package nibblr.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GUI {
	public GUI() {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText(Values.APP_NAME);
		
		new MenuBar(shell);
		new MainToolBar(shell);
		
		shell.open ();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
