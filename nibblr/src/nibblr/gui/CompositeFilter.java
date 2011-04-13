package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class CompositeFilter {
	public CompositeFilter(final Composite composite) {
		composite.setLayout(new FillLayout());
		
		final Combo filter = new Combo(composite, SWT.READ_ONLY);
		filter.add(Values.FILTER_ALL);
		filter.add(Values.FILTER_READ);
		filter.add(Values.FILTER_UNREAD);
		filter.select(0);
		
		filter.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(filter.getItem(filter.getSelectionIndex()));
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
}
