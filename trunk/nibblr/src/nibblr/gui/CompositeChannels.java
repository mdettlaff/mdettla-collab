package nibblr.gui;

import nibblr.domain.Feed;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class CompositeChannels {
	private List channels;
	
	private Menu menu;
	
	private MenuItem mark;
	private MenuItem synchronize;
	private MenuItem delete;
	
	public CompositeChannels(Composite composite) {
		composite.setLayout(new FillLayout());
		
		channels = new List(composite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		
		// Channels menu
		menu = new Menu(channels);
		channels.setMenu(menu);
		menu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(MenuEvent e) {
				for(MenuItem item: menu.getItems())
					item.setEnabled(channels.getSelectionIndex() != -1);
			}
			
			@Override
			public void menuHidden(MenuEvent e) {}
		});
		
		// Channels menu -> Mark
		mark = new MenuItem(menu, SWT.PUSH);
		mark.setText(Values.CHANNELS_MENU_MARK);
		
		// Channels menu -> Synchronize
		synchronize = new MenuItem(menu, SWT.PUSH);
		synchronize.setText(Values.CHANNELS_MENU_SYNCHRONIZE);
		
		// Channels menu -> Separator
		new MenuItem(menu, SWT.SEPARATOR);
		
		// Channels menu -> Delete
		delete = new MenuItem(menu, SWT.PUSH);
		delete.setText(Values.CHANNELS_MENU_DELETE);
	}
	
	public void addAction(final Action action) {
		channels.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addMarkAction(final Action action) {
		mark.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addSynchronizeAction(final Action action) {
		synchronize.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addDeleteAction(final Action action) {
		delete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void setChannels() {
		channels.removeAll();
	}
	
	public void setChannels(java.util.List<Feed> channels) {
		this.channels.removeAll();
		for(Feed channel: channels) {
			this.channels.setData("" + this.channels.getItemCount(), channel);
			this.channels.add(channel.getName());
		}
	}
	
	public Feed getChannel() {
		return (Feed)channels.getData("" + channels.getSelectionIndex());
	}
	
	public void selectChannel(Feed channel) {
		for(int i = 0; i < channels.getItemCount(); i++)
			if(channels.getData("" + i) == channel) {
				channels.select(i);
				return;
			}
		channels.select(-1);
	}
}
