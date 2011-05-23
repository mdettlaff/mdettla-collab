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

public class DialogRecommend {
	private Shell shell;
	
	private List recommend;
	
	private Button ok;
	private Button cancel;
	
	public DialogRecommend(Shell parentShell) {
		shell = new Shell(parentShell, SWT.DIALOG_TRIM);
		shell.setText(Values.TITLE_RECOMMEND);
		shell.setSize(500, 400);
		shell.setImage(new Image(shell.getDisplay(),
			Preferences.getIconsDir() + Preferences.getSeparator() + "recommend.png"));
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		
		shell.setLayout(layout);
		
		Label label = new Label(shell, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		label.setText(Values.DIALOG_RECOMMEND_LABEL);
		
		recommend = new List(shell, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		recommend.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		cancel = new Button(shell, SWT.PUSH);
		cancel.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, true, false));
		cancel.setText(Values.DIALOG_RECOMMEND_CANCEL);
		cancel.setFocus();
		
		ok = new Button(shell, SWT.PUSH);
		ok.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		ok.setText(Values.DIALOG_RECOMMEND_OK);
		
		shell.open();
	}
	
	public java.util.List<Feed> getChannels() {
		java.util.List<Feed> channels = new LinkedList<Feed>();
		for(int i: recommend.getSelectionIndices())
			channels.add((Feed)recommend.getData("" + i));
		return channels;
	}
	
	public void setChannels(java.util.List<Feed> channels) {
		for(Feed channel: channels) {
			recommend.setData("" + recommend.getItemCount(), channel);
			recommend.add(channel.getName());
		}
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
