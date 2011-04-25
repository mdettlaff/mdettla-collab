package nibblr.agents;

import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nibblr.Application;
import nibblr.ApplicationFactory;
import nibblr.domain.Feed;
import nibblr.ontology.AddingSubscription;
import nibblr.ontology.UpdatingSubscription;
import nibblr.service.FeedHandler;
import nibblr.service.FeedService;

public class PersonalAgent extends AbstractAgent implements FeedService {

	private Map<Feed, AID> allFeeds;
	private FeedHandler feedUpdateHandler;
	private FeedHandler feedFoundHandler;

	public PersonalAgent() {
		allFeeds = new LinkedHashMap<Feed, AID>();
	}

	@Override
	public void downloadListOfAllFeeds(FeedHandler feedFoundHandler) {
		this.feedFoundHandler = feedFoundHandler;
		sendSubscribeToNibbleChannels();
	}

	@Override
	public void updateFeeds(
			Collection<Feed> feedsToUpdate, FeedHandler feedUpdateHandler) {
		this.feedUpdateHandler = feedUpdateHandler;
		for (Feed feedToUpdate : feedsToUpdate) {
			System.out.println(getLocalName() + ": requesting feed update");
			AID websiteAgent = allFeeds.get(feedToUpdate);
			if (websiteAgent == null) {
				throw new IllegalStateException("Unknown feed: " + feedToUpdate);
			}
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.addReceiver(websiteAgent);
			send(request);
		}
	}

	@Override
	public void setup() {
		super.setup();
		addBehaviours();
		Application application = ApplicationFactory.getInstance(this);
		application.startup();
	}

	private void addBehaviours() {
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				try {
					receiveMessages();
				} catch (UngroundedException e) {
					e.printStackTrace();
				} catch (CodecException e) {
					e.printStackTrace();
				} catch (OntologyException e) {
					e.printStackTrace();
				}
			}

			private void receiveMessages()
			throws UngroundedException, CodecException, OntologyException {
				ACLMessage msg = receive();
				if (msg != null) {
					switch (msg.getPerformative()) {
					case ACLMessage.CONFIRM:
						handleConfirmSubscription(msg);
						break;
					case ACLMessage.INFORM:
						handleInform(msg);
					}
				} else {
					block();
				}
			}
		});
	}

	private List<AID> getWebsiteAgents() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("nibbling");
		template.addServices(sd);
		List<AID> websiteAgents = null;
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			websiteAgents = new ArrayList<AID>(result.length);
			for (int i = 0; i < result.length; i++) {
				websiteAgents.add(result[i].getName());
			}
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		return websiteAgents;
	}

	private void sendSubscribeToNibbleChannels() {
		System.out.println(getLocalName() + ": subscribing to channels");
		ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
		for (AID websiteAgent : getWebsiteAgents()) {
			System.out.println(getLocalName() + ": subscribing to " +
					websiteAgent.getLocalName());
			msg.addReceiver(websiteAgent);
		}
		send(msg);
	}

	private void handleConfirmSubscription(ACLMessage msg)
	throws UngroundedException, CodecException, OntologyException {
		ContentElement ce;
		ce = getContentManager().extractContent(msg);
		if (ce instanceof Action) {
			Action addSubscription = (Action)ce;
			AddingSubscription addingSubscription =
				(AddingSubscription)addSubscription.getAction();
			System.out.println(getLocalName() + ": subscribed to " +
					addingSubscription.getName() +
					" (" + addingSubscription.getUrl() + ")");
			allFeeds.put(addingSubscription, msg.getSender());
			feedFoundHandler.handleFeed(addingSubscription);
		}
	}

	private void handleInform(ACLMessage msg)
	throws UngroundedException, CodecException, OntologyException {
		ContentElement ce;
		ce = getContentManager().extractContent(msg);
		if (ce instanceof Action) {
			Action updateSubscription = (Action)ce;
			UpdatingSubscription updatingSubscription =
				(UpdatingSubscription)updateSubscription.getAction();
			System.out.println(getLocalName() + ": received updated feed");
			feedUpdateHandler.handleFeed(updatingSubscription);
		}
	}
}
