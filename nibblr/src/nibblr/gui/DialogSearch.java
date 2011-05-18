package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DialogSearch {
	private Shell shell;
	
	private Text keywords;
	
	private Button find;
	private Button cancel;
	
	public DialogSearch(Shell parentShell) {
		shell = new Shell(parentShell, SWT.DIALOG_TRIM);
		shell.setText(Values.TITLE_SEARCH);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		
		shell.setLayout(layout);
		
		Label label = new Label(shell, SWT.NONE);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label.setText(Values.DIALOG_SEARCH_KEYWORDS);
		
		keywords = new Text(shell, SWT.SINGLE | SWT.BORDER);
		keywords.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		keywords.setFocus();
		
		cancel = new Button(shell, SWT.PUSH);
		cancel.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, true, false, 2, 1));
		cancel.setText(Values.DIALOG_SEARCH_CANCEL);
		
		find = new Button(shell, SWT.PUSH);
		find.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		find.setText(Values.DIALOG_SEARCH_FIND);
		
		shell.setSize(shell.computeSize(250, SWT.DEFAULT));
		
		shell.open();
	}
	
	public String getKeywords() {
		return keywords.getText();
	}
	
	public void dispose() {
		if(!shell.isDisposed())
			shell.dispose();
	}
	
	public void addActionFind(final Action action) {
		find.addSelectionListener(new SelectionListener() {
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
	
	public void addActionDispose(final Action action) {
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				action.action();
			}
		});
	}
}
