package nibblr.gui;

import nibblr.preferences.Preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class CompositeTool {
	private Display display;
	
	private ToolBar tool;
	private ToolItem add;
	private ToolItem recommend;
	private ToolItem synchronize;
	private ToolItem search;
	private ToolItem preference;
	
	public CompositeTool(Shell shell) {
		display = shell.getDisplay();
		
		tool = new ToolBar(shell, SWT.WRAP);
		tool.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));
		
		// Add
		add = new ToolItem(tool, SWT.PUSH);
		add.setImage(getImage("add.png"));
		add.setToolTipText(Values.TOOLTIP_ADD);
		
		// Recommend
		recommend = new ToolItem(tool, SWT.PUSH);
		recommend.setImage(getImage("recommend.png"));
		recommend.setToolTipText(Values.TOOLTIP_RECOMMEND);
		
		// Synchronize
		synchronize = new ToolItem(tool, SWT.PUSH);
		synchronize.setImage(getImage("synchronize.png"));
		synchronize.setToolTipText(Values.TOOLTIP_SYNCHRONIZE);
		
		// Separator
		new ToolItem(tool, SWT.SEPARATOR);
		
		// Search
		search = new ToolItem(tool, SWT.PUSH);
		search.setImage(getImage("search.png"));
		search.setToolTipText("Search");
		
		// Separator
		new ToolItem(tool, SWT.SEPARATOR);
		
		// Preference
		preference = new ToolItem(tool, SWT.PUSH);
		preference.setImage(getImage("preference.png"));
		preference.setToolTipText(Values.TOOLTIP_PREFERENCES);
	}
	
	public void addActionAdd(final Action action) {
		add.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionRecommend(final Action action) {
		recommend.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionSynchronize(final Action action) {
		synchronize.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionSearch(final Action action) {
		search.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addActionPreference(final Action action) {
		preference.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	// private
	
	private Image getImage(String name) {
		return new Image(display,
			Preferences.getIconsDir() + Preferences.getSeparator() + name);
	}
}
