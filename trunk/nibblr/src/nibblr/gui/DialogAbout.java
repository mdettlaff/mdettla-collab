package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class DialogAbout {
	public DialogAbout(final Shell shell) {
		final Shell aboutShell = new Shell(shell, SWT.DIALOG_TRIM);
		aboutShell.setText(Values.TITLE_ABOUT);
		aboutShell.setSize(500, 400);
		
		aboutShell.open();
	}
}
