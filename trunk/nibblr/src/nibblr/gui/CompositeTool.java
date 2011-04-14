package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
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
	
	public ToolItem getAdd() {
		return add;
	}
	
	public ToolItem getSynchronize() {
		return synchronize;
	}
	
	public ToolItem getSearch() {
		return search;
	}
	
	public ToolItem getPreference() {
		return preference;
	}
	
	// private
	private Image getImage(String name) {
		Image image = new Image(display, 32, 32);
		Color color = display.getSystemColor(SWT.COLOR_BLUE);
		GC gc = new GC(image);
		gc.setBackground(color);
		gc.fillRectangle(image.getBounds());
		gc.dispose();
		return image;
	}
}
