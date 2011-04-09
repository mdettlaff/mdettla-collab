package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class DialogPreferences {
	public DialogPreferences(final Shell shell) {
		final Shell preferencesShell = new Shell(shell, SWT.DIALOG_TRIM);
		preferencesShell.setText(Values.TITLE_PREFERENCES);
		preferencesShell.setSize(500, 400);
		
		preferencesShell.open();
	}
}
