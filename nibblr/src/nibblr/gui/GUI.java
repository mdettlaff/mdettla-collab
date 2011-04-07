package nibblr.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GUI {
	public GUI() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Nibblr");
		shell.open ();
		while(!shell.isDisposed ()) {
			if(!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}
