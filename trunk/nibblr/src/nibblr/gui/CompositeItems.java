package nibblr.gui;

import java.text.DateFormat;

import nibblr.domain.FeedItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class CompositeItems {
	private Table items;
	
	private Menu menu;
	
	private MenuItem delete;
	
	public CompositeItems(Composite composite) {
		composite.setLayout(new FillLayout());
		
		items = new Table(composite, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		items.setHeaderVisible(true);
		items.setLinesVisible(true);
		new TableColumn(items, SWT.LEFT).setText(Values.ITEMS_COLUMN_TITLE);
		new TableColumn(items, SWT.RIGHT).setText(Values.ITEMS_COLUMN_DATE);
		setColumnWidth();
		
		// Items menu
		menu = new Menu(items);
		items.setMenu(menu);
		menu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(MenuEvent e) {
				for(MenuItem item: menu.getItems())
					item.setEnabled(items.getSelectionIndex() != -1);
			}
			
			@Override
			public void menuHidden(MenuEvent e) {}
		});
		
		// Items menu -> Delete
		delete = new MenuItem(menu, SWT.PUSH);
		delete.setText(Values.ITEMS_MENU_DELETE);
	}
	
	public Table getItems() {
		return items;
	}
	
	public MenuItem getDelete() {
		return delete;
	}
	
	public void setItems() {
		items.removeAll();
		setColumnWidth();
	}
	
	public void setItems(FeedItem[] items) {
		this.items.removeAll();
		for(FeedItem item: items)
			new TableItem(this.items, SWT.NONE).setText(new String[] {
				item.getTitle(),
				DateFormat.getDateInstance(DateFormat.MEDIUM).format(item.getDate())});
		setColumnWidth();
	}
	
	// private
	private void setColumnWidth() {
		for(TableColumn column: items.getColumns())
			column.pack();
	}
}
