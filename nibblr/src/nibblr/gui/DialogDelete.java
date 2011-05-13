package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DialogDelete {
	private Shell shell;

	private Label icon;
	private Label text;
	
	private Button ok;
	private Button cancel;
	
	public DialogDelete(Shell shell) {
		this.shell = new Shell(shell, SWT.DIALOG_TRIM);
		this.shell.setText(Values.TITLE_DELETE);
		this.shell.setImage(this.shell.getDisplay().getSystemImage(SWT.ICON_QUESTION));
		this.shell.setLayout(new GridLayout(3, false));
		
		icon = new Label(this.shell, SWT.NONE);
		icon.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));
		icon.setImage(this.shell.getDisplay().getSystemImage(SWT.ICON_QUESTION));
		
		text = new Label(this.shell, SWT.NONE);
		text.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false, 2, 1));
		text.setText(Values.DIALOG_DELETE_TEXT);
		
		cancel = new Button(this.shell, SWT.PUSH);
		cancel.setLayoutData(new GridData(GridData.END, GridData.CENTER, true, false, 2, 1));
		cancel.setText(Values.DIALOG_DELETE_CANCEL);
		cancel.setFocus();
		cancel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DialogDelete.this.shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		ok = new Button(this.shell, SWT.PUSH);
		ok.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
		ok.setText(Values.DIALOG_DELETE_OK);
		ok.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DialogDelete.this.shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		this.shell.setSize(this.shell.computeSize(250, SWT.DEFAULT));
		this.shell.open();
	}
	
	public void addActionOk(final Action action) {
		ok.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionCancel(final Action action) {
		cancel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
}
