package nibblr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;

public class MainContainer {
	private final int MARGIN = 2;
	private final int SPACING = 3;
	
	private double sashVerticalRatio = 0.25;
	private double sashHorizontalRatio = 0.45;
	
	private Composite container;
	private Composite filter;
	private Composite channels;
	private Composite items;
	private Composite view;
	
	private Sash sashVertical;
	private Sash sashHorizontal;
	
	public MainContainer(final Shell shell) {
		this.container = new Composite(shell, SWT.NONE);
		this.container.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		
		this.filter = new Composite(this.container, SWT.NONE);
		this.channels = new Composite(this.container, SWT.NONE);
		this.items = new Composite(this.container, SWT.NONE);
		this.view = new Composite(this.container, SWT.NONE);
		
		new CompositeFilter(this.filter);
		new CompositeChannels(this.channels);
		new CompositeItems(this.items);
		new CompositeView(this.view);
		
		this.sashVertical = new Sash(this.container, SWT.VERTICAL | SWT.SMOOTH);
		this.sashHorizontal = new Sash(this.container, SWT.HORIZONTAL | SWT.SMOOTH);
		
		this.container.addListener(SWT.Resize,  new Listener() {
			@Override
			public void handleEvent(Event event) {
				layout();
			}
		});
		
		this.sashVertical.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sashVerticalRatio = (double)e.x / container.getBounds().width;
				layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		this.sashHorizontal.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sashHorizontalRatio = (double)e.y / container.getBounds().height;
				layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	private void layout() {
		int x = (int)(this.sashVerticalRatio * this.container.getBounds().width);
		int y = (int)(this.sashHorizontalRatio * this.container.getBounds().height);
		this.filter.setBounds(MARGIN, 0, x - MARGIN, this.filter.computeSize(x, SWT.DEFAULT).y);
		this.channels.setBounds(MARGIN, this.filter.getBounds().height + SPACING, x - MARGIN, this.container.getBounds().height - this.filter.getBounds().height - SPACING - MARGIN);
		this.sashVertical.setBounds(x, 0, SPACING, this.container.getBounds().height);
		this.items.setBounds(x + SPACING, 0, this.container.getBounds().width - x - SPACING - MARGIN, y);
		this.sashHorizontal.setBounds(x + SPACING, y, this.container.getBounds().width - x - SPACING, SPACING);
		this.view.setBounds(x + SPACING, y + SPACING, this.container.getBounds().width - x - SPACING - MARGIN, this.container.getBounds().height - y - SPACING - MARGIN);
	}
}
