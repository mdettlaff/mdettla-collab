package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class MainToolBar {
	public MainToolBar(final Shell shell) {
		final ToolBar tool = new ToolBar(shell, SWT.WRAP);
		tool.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));
		
		// TODO: create images
		Image image = new Image(shell.getDisplay(), 32, 32);
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
