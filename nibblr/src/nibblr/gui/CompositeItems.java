package nibblr.gui;

import java.text.DateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class CompositeItems {
	public CompositeItems(final Composite composite) {
		composite.setLayout(new FillLayout());
		
		final Table items = new Table(composite, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		items.setHeaderVisible(true);
		items.setLinesVisible(true);
		new TableColumn(items, SWT.LEFT).setText(Values.ITEMS_COLUMN_TITLE);
		new TableColumn(items, SWT.RIGHT).setText(Values.ITEMS_COLUMN_DATE);
		
		for(int i = 0; i < 20; i++)
			new TableItem(items, 0).setText(new String[] {"Item " + i, DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date())});
		
		for(TableColumn column: items.getColumns())
			column.pack();
		
		items.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(items.getItem(items.getSelectionIndex()).getText());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		// Items menu
		Menu menu = new Menu(items);
		items.setMenu(menu);
		
		// Items menu -> Delete
		MenuItem menuDelete = new MenuItem(menu, SWT.PUSH);
		menuDelete.setText(Values.ITEMS_MENU_DELETE);
		menuDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(items.getItem(items.getSelectionIndex()).getText() + " DELETE");
				items.remove(items.getSelectionIndex());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
}
