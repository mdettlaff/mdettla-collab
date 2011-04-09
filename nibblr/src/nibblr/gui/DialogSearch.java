package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class DialogSearch {
	public DialogSearch(final Shell shell) {
		Shell searchShell = new Shell(shell, SWT.DIALOG_TRIM);
		searchShell.setText(Values.TITLE_SEARCH);
		searchShell.setSize(500, 400);
		
		searchShell.open();
	}
}
