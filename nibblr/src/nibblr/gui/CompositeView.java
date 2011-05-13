package nibblr.gui;

import java.text.DateFormat;
import java.util.Date;

import nibblr.domain.Feed;
import nibblr.domain.FeedItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

public class CompositeView {
	private Label title;
	private Link url;
	private Label date;
	private Browser view;
	
	public CompositeView(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.marginTop = 10;
		composite.setLayout(layout);
		
		title = new Label(composite, SWT.NONE);
		title.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false, 2, 1));
		FontData fontData = composite.getDisplay().getSystemFont().getFontData()[0];
		title.setFont(new Font(composite.getDisplay(),
			new FontData(fontData.getName(),
			(int)(fontData.getHeight() * 1.2),
			SWT.BOLD)));
		
		url = new Link(composite, SWT.NONE);
		url.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false, 1, 1));
		url.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Program.launch(e.text);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		date = new Label(composite, SWT.NONE);
		date.setLayoutData(new GridData(GridData.END, GridData.BEGINNING, false, false, 1, 1));
		
		view = new Browser(composite, SWT.BORDER);
		view.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1));
	}
	
	public void setView() {
		title.setText("");
		url.setText("");
		date.setText("");
		view.setText("");
		layout();
	}
	
	public void setView(Feed channel) {
		title.setText(channel.getName());
		url.setText("<a>" + channel.getUrl() + "</a>");
		date.setText("");
		view.setText(htmlTemplate(channel.getDescription()));
		layout();
	}
	
	public void setView(FeedItem item) {
		title.setText(item.getTitle());
		url.setText("<a>" + item.getUrl() + "</a>");
		date.setText(DateFormat.getDateInstance(DateFormat.FULL).format(item.getDate()));
		view.setText(htmlTemplate(item.getHTMLContent()));
		layout();
	}
	
	// private
	private String htmlTemplate(String content) {
		return "<html><head></head><body>" + content + "</body></html>";
	}
	
	private void layout() {
		date.setSize(date.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		url.setSize(url.computeSize(url.getParent().getSize().x - date.getSize().x, SWT.DEFAULT));
		url.getParent().layout();
	}
}
