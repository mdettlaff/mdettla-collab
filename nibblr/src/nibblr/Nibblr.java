package nibblr;

import nibblr.agents.PersonalAgent;
import nibblr.domain.FeedService;
import nibblr.gui.GUI;

public class Nibblr implements Application {

	// TODO do Łukasza: używaj tego obiektu do pobierania najświeższych
	// kanałów i wiadomości (niblów)
	private FeedService feedService;

	@Override
	public void setFeedService(FeedService feedService) {
		this.feedService = feedService;
	}

	@Override
	public void startup() {
		new GUI();
	}

	/**
	 * Uruchamia samo GUI, bez platformy agentowej - przydatne do testowania.
	 * W produkcji aplikacja powinna być startowana przez agenta
	 * {@link PersonalAgent}.
	 */
	public static void main(String[] args) {
		Application nibblr = new Nibblr();
		// zamiast null można przekazać mock object implementujący FeedService
		// do łatwiejszego testowania GUI
		nibblr.setFeedService(null);
		nibblr.startup();
	}
}
