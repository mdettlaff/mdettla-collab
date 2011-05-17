package nibblr.gui;

import java.util.LinkedList;
import java.util.List;

import nibblr.data.Data;
import nibblr.data.DataNotFoundException;
import nibblr.domain.Feed;
import nibblr.domain.FeedItem;
import nibblr.gui.CompositeFilter.Filter;
import nibblr.service.FeedHandler;
import nibblr.service.FeedService;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GUI implements Runnable {
	Display display;
	Shell shell;
	
	Data data;
	FeedService feedService;
	
	private MenuBar menu;
	private CompositeTool tool;
	private CompositeFilter filter;
	private CompositeChannels channels;
	private CompositeItems items;
	private CompositeView view;
	
	private boolean isAdd;
	private boolean isRecommend;
	
	public GUI(Data data, FeedService feedService) {
		this.data = data;
		this.feedService = feedService;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		display = new Display();
		shell = new Shell(display);
		shell.setText(Values.APP_NAME);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		shell.setLayout(layout);
		
		menu = new MenuBar(shell);
		tool = new CompositeTool(shell);
		Container container = new Container(shell);
		filter = container.getFilter();
		channels = container.getChannels();
		items = container.getItems();
		view = container.getView();
		
		isAdd = false;
		isRecommend = false;
		
		action();
		
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	// private
	
	private void add() {
		if(isAdd)
			return;
		isAdd = true;
		final List<Feed> feeds = new LinkedList<Feed>();
		final DialogAdd add = new DialogAdd(shell);
		feedService.downloadListOfAllFeeds(new FeedHandler() {
			@Override
			public void handleFeed(Feed feed) {
				final Feed f = feed;
				display.syncExec(new Runnable() {
					public void run() {
						if(!feeds.contains(f)) {
							feeds.add(f);
							List<Feed> select = add.getChannels();
							add.setChannels(feeds);
							add.selectChannels(select);
						}
					}
				});
			}
		});
		add.addActionOk(new Action() {
			@Override
			public void action() {
				for(Feed channel: add.getChannels()) {
					data.addFeed(channel);
					synchronize(channel);
				}
				Feed channel = channels.getChannel();
				channels.setChannels(data.getFeeds());
				channels.selectChannel(channel);
				add.dispose();
				isAdd = false;
			}
		});
		add.addActionCancel(new Action() {
			@Override
			public void action() {
				add.dispose();
				isAdd = false;
			}
		});
	}
	
	private void recommend() {
		if(isRecommend)
			return;
		isRecommend = true;
		final DialogRecommend recommend = new DialogRecommend(shell);
		recommend.setChannels(feedService.recommendFeeds());
		recommend.addActionOk(new Action() {
			@Override
			public void action() {
				recommend.getChannels();
				for(Feed channel: recommend.getChannels()) {
					data.addFeed(channel);
					synchronize(channel);
				}
				Feed channel = channels.getChannel();
				channels.setChannels(data.getFeeds());
				channels.selectChannel(channel);
				recommend.dispose();
				isRecommend = false;
			}
		});
		recommend.addActionCancel(new Action() {
			@Override
			public void action() {
				recommend.dispose();
				isRecommend = false;
			}
		});
	}
	
	private void synchronize() {
		feedService.updateFeeds(data.getFeeds(), new FeedHandler() {
			@Override
			public void handleFeed(Feed feed) {
				final Feed f = feed;
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						try {
							data.updateFeedItems(f);
							if(f == channels.getChannel()) {
								FeedItem select = items.getItem();
								items.setItems(data.getFeedItems(f, filter.getFilterStat()), data);
								items.selectItem(select);
							}
						} catch (DataNotFoundException e) {}
					}
				});
			}
		});
	}
	
	private void synchronize(Feed channel) {
		List<Feed> feeds = new LinkedList<Feed>();
		feeds.add(channel);
		feedService.updateFeeds(feeds, new FeedHandler() {
			@Override
			public void handleFeed(Feed feed) {
				final Feed f = feed;
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						try {
							data.updateFeedItems(f);
							if(f == channels.getChannel()) {
								FeedItem select = items.getItem();
								items.setItems(data.getFeedItems(f, filter.getFilterStat()), data);
								items.selectItem(select);
							}
						} catch (DataNotFoundException e) {}
					}
				});
			}
		});
	}
	
	private void exit() {
		System.out.println("action -> exit");
		shell.dispose();
	}
	
	private void search() {
		System.out.println("action -> search");
		new DialogSearch(shell);
	}
	
	private void preference() {
		System.out.println("action -> preference");
		new DialogPreferences(shell);
	}
	
	private void about() {
		System.out.println("action -> about");
		new DialogAbout(shell);
	}
	
	private void help() {
		System.out.println("action -> help");
		new DialogHelp(shell);
	}
	
	private void selectFilter() {
		try {
			Feed channel = channels.getChannel();
			items.setItems(data.getFeedItems(channel, filter.getFilterStat()), data);
		} catch (DataNotFoundException e) {}
	}
	
	private void selectChannel() {
		try {
			Feed channel = channels.getChannel();
			items.setItems(data.getFeedItems(channel, filter.getFilterStat()), data);
			view.setView(channel);
		} catch (DataNotFoundException e) {}
	}
	
	private void selectItem() {
		try {
			FeedItem item = items.getItem();
			data.read(item);
			Feed channel = data.getFeed(item);
			items.setItems(data.getFeedItems(channel, filter.getFilterStat()), data);
			items.selectItem(item);
			view.setView(item);
		} catch (DataNotFoundException e) {}
	}

	private void channelMark() {
		try {
			Feed channel = channels.getChannel();
			for(FeedItem item: data.getFeedItems(channel, Filter.ALL))
				data.read(item);
			items.setItems(data.getFeedItems(channel, filter.getFilterStat()), data);
		} catch (DataNotFoundException e) {}
	}
	
	private void channelSynchronize() {
		synchronize(channels.getChannel());
	}
	
	private void channelDelete() {
		final Feed channel = channels.getChannel();
		final DialogDelete delete = new DialogDelete(shell);
		delete.addActionOk(new Action() {
			@Override
			public void action() {
				data.removeFeed(channel);
				channels.setChannels(data.getFeeds());
				items.setItems();
				view.setView();
				delete.dispose();
			}
		});
		delete.addActionCancel(new Action() {
			@Override
			public void action() {
				delete.dispose();
			}
		});
	}
	
	private void action() {
		menu.addActionAdd(new Action() {
			@Override
			public void action() {
				add();
			}
		});
		menu.addActionRecommand(new Action() {
			@Override
			public void action() {
				recommend();
			}
		});
		menu.addActionSynchronize(new Action() {
			@Override
			public void action() {
				synchronize();
			}
		});
		menu.addActionExit(new Action() {
			@Override
			public void action() {
				exit();
			}
		});
		menu.addActionSearch(new Action() {
			@Override
			public void action() {
				search();
			}
		});
		menu.addActionPreferences(new Action() {
			@Override
			public void action() {
				preference();
			}
		});
		menu.addActionAbout(new Action() {
			@Override
			public void action() {
				about();
			}
		});
		menu.addActionHelp(new Action() {
			@Override
			public void action() {
				help();
			}
		});
		tool.addActionAdd(new Action() {
			@Override
			public void action() {
				add();
			}
		});
		tool.addActionRecommend(new Action() {
			@Override
			public void action() {
				recommend();
			}
		});
		tool.addActionSynchronize(new Action() {
			@Override
			public void action() {
				synchronize();
			}
		});
		tool.addActionSearch(new Action() {
			@Override
			public void action() {
				search();
			}
		});
		tool.addActionPreference(new Action() {
			@Override
			public void action() {
				preference();
			}
		});
		filter.addAction(new Action() {
			@Override
			public void action() {
				selectFilter();
			}
		});
		channels.addAction(new Action() {
			@Override
			public void action() {
				selectChannel();
			}
		});
		channels.addMarkAction(new Action() {
			@Override
			public void action() {
				channelMark();
			}
		});
		channels.addSynchronizeAction(new Action() {
			@Override
			public void action() {
				channelSynchronize();
			}
		});
		channels.addDeleteAction(new Action() {
			@Override
			public void action() {
				channelDelete();
			}
		});
		items.addAction(new Action() {
			@Override
			public void action() {
				selectItem();
			}
		});
	}
}