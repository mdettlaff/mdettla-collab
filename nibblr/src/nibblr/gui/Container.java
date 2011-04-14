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

public class Container {
	private final int MARGIN = 3;
	private final int SPACING = 3;
	
	private double sashVerticalRatio = 0.25;
	private double sashHorizontalRatio = 0.45;
	
	private Composite container;
	private Composite containerChannels;
	private Composite containerItems;
	private Composite containerView;
	
	private Sash sashVertical;
	private Sash sashHorizontal;
	
	private CompositeChannels channels;
	private CompositeItems items;
	private CompositeView view;
	
	public Container(final Shell shell) {
		container = new Composite(shell, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		
		containerChannels = new Composite(container, SWT.NONE);
		containerItems = new Composite(container, SWT.NONE);
		containerView = new Composite(container, SWT.NONE);
		
		sashVertical = new Sash(container, SWT.VERTICAL | SWT.SMOOTH);
		sashHorizontal = new Sash(container, SWT.HORIZONTAL | SWT.SMOOTH);
		
		container.addListener(SWT.Resize,  new Listener() {
			@Override
			public void handleEvent(Event event) {
				layout();
			}
		});
		
		sashVertical.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sashVerticalRatio = (double)e.x / container.getBounds().width;
				layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		sashHorizontal.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sashHorizontalRatio = (double)e.y / container.getBounds().height;
				layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		channels = new CompositeChannels(containerChannels);
		items = new CompositeItems(containerItems);
		view = new CompositeView(containerView);
	}
	
	public CompositeChannels getChannels() {
		return channels;
	}
	
	public CompositeItems getItems() {
		return items;
	}
	
	public CompositeView getView() {
		return view;
	}
	
	// private
	private void layout() {
		int x = (int)(sashVerticalRatio * container.getBounds().width);
		int y = (int)(sashHorizontalRatio * container.getBounds().height);
		containerChannels.setBounds(
			MARGIN,
			0,
			x - MARGIN,
			container.getBounds().height - MARGIN);
		sashVertical.setBounds(
			x,
			0,
			SPACING,
			container.getBounds().height);
		containerItems.setBounds(
			x + SPACING,
			0,
			container.getBounds().width - x - SPACING - MARGIN,
			y);
		sashHorizontal.setBounds(
			x + SPACING,
			y,
			container.getBounds().width - x - SPACING,
			SPACING);
		containerView.setBounds(
			x + SPACING,
			y + SPACING,
			container.getBounds().width - x - SPACING - MARGIN,
			container.getBounds().height - y - SPACING - MARGIN);
	}
}
