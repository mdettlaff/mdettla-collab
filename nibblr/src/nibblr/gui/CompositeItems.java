package nibblr.gui;

import java.text.DateFormat;
import java.util.List;

import nibblr.domain.FeedItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class CompositeItems {
	private Font fontRead;
	private Font fontUnread;
	
	private Table items;
	
	public CompositeItems(Composite composite) {
		composite.setLayout(new FillLayout());
		
		FontData fontData = composite.getDisplay().getSystemFont().getFontData()[0];
		fontRead = new Font(composite.getDisplay(), fontData.getName(), fontData.getHeight(), SWT.NORMAL);
		fontUnread = new Font(composite.getDisplay(), fontData.getName(), fontData.getHeight(), SWT.BOLD);
		
		items = new Table(composite, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		items.setHeaderVisible(true);
		items.setLinesVisible(true);
		new TableColumn(items, SWT.LEFT).setText(Values.ITEMS_COLUMN_TITLE);
		new TableColumn(items, SWT.RIGHT).setText(Values.ITEMS_COLUMN_DATE);
		setColumnWidth();
	}
	
	public void addAction(final Action action) {
		items.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void setItems() {
		items.removeAll();
		setColumnWidth();
	}
	
	public void setItems(List<FeedItem> items) {
		this.items.removeAll();
		for(FeedItem item: items) {
			this.items.setData("" + this.items.getItemCount(), item);
			TableItem tableItem = new TableItem(this.items, SWT.NONE, this.items.getItemCount());
			tableItem.setText(0, item.getTitle());
			tableItem.setText(1, DateFormat.getDateInstance(DateFormat.MEDIUM).format(item.getDate()));
			tableItem.setFont((item.isRead() ? fontRead : fontUnread));
		}
		setColumnWidth();
	}
	
	public FeedItem getItem() {
		return (FeedItem)items.getData("" + items.getSelectionIndex());
	}
	
	public void selectItem(FeedItem item) {
		for(int i = 0; i < items.getItemCount(); i++)
			if(items.getData("" + i) == item) {
				items.select(i);
				return;
			}
	}
	
	// private
	private void setColumnWidth() {
		for(TableColumn column: items.getColumns())
			column.pack();
	}
}
