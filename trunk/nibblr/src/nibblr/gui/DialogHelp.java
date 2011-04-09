package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class DialogHelp {
	public DialogHelp(final Shell shell) {
		final Shell helpShell = new Shell(shell, SWT.DIALOG_TRIM);
		helpShell.setText(Values.TITLE_HELP);
		helpShell.setSize(500, 400);
		
		helpShell.open();
	}
}
