package nibblr.agents;

import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nibblr.Application;
import nibblr.ApplicationFactory;
import nibblr.domain.Feed;
import nibblr.domain.FeedService;
import nibblr.ontology.AddingSubscription;

public class PersonalAgent extends AbstractAgent implements FeedService {

	public static final int SUBSCRIPTION_LIST_REFRESH_INTERVAL = 1000;

	private Set<Feed> allFeeds;

	public PersonalAgent() {
		allFeeds = new LinkedHashSet<Feed>();
	}

	@Override
	public Set<Feed> getAllFeeds() {
		return allFeeds;
	}

	@Override
	public void setup() {
		super.setup();
		addBehaviours();
		Application application = ApplicationFactory.getInstance();
		application.setFeedService(this);
		application.startup();
	}

	private void addBehaviours() {
		addBehaviour(new TickerBehaviour(this, SUBSCRIPTION_LIST_REFRESH_INTERVAL) {
			@Override
			public void onTick() {
				sendSubscribeToNibbleChannels();
			}
		});

		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					switch (msg.getPerformative()) {
					case ACLMessage.CONFIRM:
						handleConfirmSubscription(msg);
						break;
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

	private void handleConfirmSubscription(ACLMessage msg) {
		ContentElement ce;
		try {
			ce = getContentManager().extractContent(msg);
			if (ce instanceof Action) {
				Action addSubscription = (Action)ce;
				AddingSubscription addingSubscription =
					(AddingSubscription)addSubscription.getAction();
				System.out.println(getLocalName() + ": subscribed to " +
						addingSubscription.getName() +
						" (" + addingSubscription.getUrl() + ")");
				allFeeds.add(addingSubscription);
			}
		} catch (UngroundedException e) {
			e.printStackTrace();
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}
}
