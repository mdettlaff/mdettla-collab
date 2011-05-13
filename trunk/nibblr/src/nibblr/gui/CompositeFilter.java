package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class CompositeFilter {
	public static enum Filter {
		ALL,
		READ,
		UNRREAD
	}
	
	private Combo filter;
	
    public CompositeFilter(Composite composite) {
        composite.setLayout(new FillLayout());
        
        filter = new Combo(composite, SWT.READ_ONLY);
        filter.add(Values.FILTER_ALL);
        filter.add(Values.FILTER_READ);
        filter.add(Values.FILTER_UNREAD);
        filter.select(0);
    }
    
    public void addAction(final Action action) {
    	filter.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    }
    
    public Filter getFilterStat() {
    	switch(filter.getSelectionIndex()) {
    		case 1:
    			return Filter.READ;
    		case 2:
    			return Filter.UNRREAD;
    		default:
    			return Filter.ALL;
    	}
    }
}
