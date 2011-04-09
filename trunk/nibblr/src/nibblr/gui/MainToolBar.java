package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class MainToolBar {
	public MainToolBar(final Shell shell) {
		final ToolBar tool = new ToolBar(shell, SWT.WRAP);
		tool.setLocation(shell.getClientArea().x, shell.getClientArea().y);
		
		shell.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				tool.setSize(tool.computeSize(shell.getClientArea().width, SWT.DEFAULT));
			}
		});
		
		// TODO: create images
		Image image = new Image(shell.getDisplay(), 16, 16);
		Color color = shell.getDisplay().getSystemColor(SWT.COLOR_BLUE);
		GC gc = new GC(image);
		gc.setBackground(color);
		gc.fillRectangle(image.getBounds());
		gc.dispose();
		// TODO
		
		// Add
		ToolItem add = new ToolItem(tool, SWT.PUSH);
		add.setImage(image);
		add.setToolTipText(Values.TOOLTIP_ADD);
		
		// Synchronize
		ToolItem synchronize = new ToolItem(tool, SWT.PUSH);
		synchronize.setImage(image);
		synchronize.setToolTipText(Values.TOOLTIP_SYNCHRONIZE);
		
		// Separator
		new ToolItem(tool, SWT.SEPARATOR);
		
		// Filter
		ToolItem filter = new ToolItem(tool, SWT.DROP_DOWN);
		filter.setToolTipText(Values.TOOLTIP_FILTER);
		filter.setText("Filter: All");
		
		// Separator
		new ToolItem(tool, SWT.SEPARATOR);
		
		// Search
		ToolItem search = new ToolItem(tool, SWT.PUSH);
		search.setImage(image);
		search.setToolTipText("Search");
		search.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new DialogSearch(shell);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// Separator
		new ToolItem(tool, SWT.SEPARATOR);
		
		// Preference
		ToolItem preference = new ToolItem(tool, SWT.PUSH);
		preference.setImage(image);
		preference.setToolTipText(Values.TOOLTIP_PREFERENCES);
		preference.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new DialogPreferences(shell);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		tool.pack();
	}
}
