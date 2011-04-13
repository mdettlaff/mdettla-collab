package nibblr.gui;

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
	public CompositeChannels(final Composite composite) {
		composite.setLayout(new FillLayout());
		
		final List channels = new List(composite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		
		for(int i = 0; i < 10; i++)
			channels.add("Channel " + i);
		
		channels.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(channels.getSelection()[0]);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		// Channels menu
		final Menu menu = new Menu(channels);
		channels.setMenu(menu);
		
		// Channels menu -> Mark
		MenuItem menuMark = new MenuItem(menu, SWT.PUSH);
		menuMark.setText(Values.CHANNELS_MENU_MARK);
		menuMark.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(channels.getSelection()[0] + " MARK");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		// Channels menu -> Synchronize
		MenuItem menuSynchronize = new MenuItem(menu, SWT.PUSH);
		menuSynchronize.setText(Values.CHANNELS_MENU_SYNCHRONIZE);
		menuSynchronize.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(channels.getSelection()[0] + " SYNCHRONIZE");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		// Channels menu -> Edit
		MenuItem menuEdit = new MenuItem(menu, SWT.PUSH);
		menuEdit.setText(Values.CHANNELS_MENU_EDIT);
		menuEdit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(channels.getSelection()[0] + " EDIT");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		// Channels menu -> Separator
		new MenuItem(menu, SWT.SEPARATOR);
		
		// Channels menu -> Delete
		MenuItem menuDelete = new MenuItem(menu, SWT.PUSH);
		menuDelete.setText(Values.CHANNELS_MENU_DELETE);
		menuDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(channels.getSelection()[0] + " DELETE");
				channels.remove(channels.getSelection()[0]);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		// Channels MenuListener
		menu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(MenuEvent e) {
				for(MenuItem item: menu.getItems())
					item.setEnabled(channels.getSelectionIndex() != -1);
			}
			
			@Override
			public void menuHidden(MenuEvent e) {}
		});
	}
}
