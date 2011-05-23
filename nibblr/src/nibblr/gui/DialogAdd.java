package nibblr.gui;

import java.util.LinkedList;

import nibblr.domain.Feed;
import nibblr.preferences.Preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class DialogAdd {
	private Shell shell;
	
	private List add;
	
	private Button ok;
	private Button cancel;
	
	public DialogAdd(Shell parentShell) {
		shell = new Shell(parentShell, SWT.DIALOG_TRIM);
		shell.setText(Values.TITLE_ADD);
		shell.setSize(500, 400);
		shell.setImage(new Image(shell.getDisplay(),
			Preferences.getIconsDir() + Preferences.getSeparator() + "add.png"));
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		
		shell.setLayout(layout);
		
		Label label = new Label(shell, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		label.setText(Values.DIALOG_ADD_LABEL);
		
		add = new List(shell, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		add.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		cancel = new Button(shell, SWT.PUSH);
		cancel.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, true, false));
		cancel.setText(Values.DIALOG_ADD_CANCEL);
		cancel.setFocus();
		
		ok = new Button(shell, SWT.PUSH);
		ok.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		ok.setText(Values.DIALOG_ADD_OK);
		
		shell.open();
	}
	
	public java.util.List<Feed> getChannels() {
		java.util.List<Feed> channels = new LinkedList<Feed>();
		for(int i: add.getSelectionIndices())
			channels.add((Feed)add.getData("" + i));
		return channels;
	}
	
	public void setChannels(java.util.List<Feed> channels) {
		add.removeAll();
		for(Feed channel: channels) {
			add.setData("" + add.getItemCount(), channel);
			add.add(channel.getName());
		}
	}
	
	public void selectChannels(java.util.List<Feed> channels) {
		int[] si = new int[channels.size()];
		for(int i = 0; i < channels.size(); i++)
			si[i] = -1;
		for(int i = 0; i < add.getItemCount(); i++)
			for(int j = 0; j < channels.size(); j++)
				if((Feed)add.getData("" + i) == channels.get(j)) {
					si[j] = i;
					continue;
				}
		add.select(si);
	}
	
	public void dispose() {
		if(!shell.isDisposed())
			shell.dispose();
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
	
	public void addActionDispose(final Action action) {
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				action.action();
			}
		});
	}
}
