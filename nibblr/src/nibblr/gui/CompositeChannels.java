package nibblr.gui;

import nibblr.domain.Feed;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class CompositeChannels {
	private List channels;
	
	private Menu menu;
	
	private MenuItem synchronize;
	private MenuItem edit;
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
		
		// Channels menu -> Synchronize
		synchronize = new MenuItem(menu, SWT.PUSH);
		synchronize.setText(Values.CHANNELS_MENU_SYNCHRONIZE);
		
		// Channels menu -> Edit
		edit = new MenuItem(menu, SWT.PUSH);
		edit.setText(Values.CHANNELS_MENU_EDIT);
		
		// Channels menu -> Separator
		new MenuItem(menu, SWT.SEPARATOR);
		
		// Channels menu -> Delete
		delete = new MenuItem(menu, SWT.PUSH);
		delete.setText(Values.CHANNELS_MENU_DELETE);
	}
	
	public List getChannels() {
		return channels;
	}
	
	public MenuItem getSynchronize() {
		return synchronize;
	}
	public MenuItem getEdit() {
		return edit;
	}
	
	public MenuItem getDelete() {
		return delete;
	}
	
	public void setChannels() {
		channels.removeAll();
	}
	
	public void setChannels(Feed[] channels) {
		this.channels.removeAll();
		for(Feed channel: channels)
			this.channels.add(channel.getName());
	}
}
