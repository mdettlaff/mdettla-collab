package nibblr.gui;

import nibblr.preferences.Preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class DialogPreferences {
	
	private Shell shell;
	
	public DialogPreferences(Shell parentShell) {
		shell = new Shell(parentShell, SWT.DIALOG_TRIM);
		shell.setText(Values.TITLE_PREFERENCES);
		shell.setSize(500, 400);
		shell.setImage(new Image(shell.getDisplay(),
			Preferences.getIconsDir() + Preferences.getSeparator() + "preference.png"));
		
		shell.open();
	}
}
