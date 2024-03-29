package nibblr.agents;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import nibblr.http.HttpRequestFactory;
import nibblr.http.HttpRequestFactoryFactory;
import nibblr.ontology.AddingSubscription;
import nibblr.ontology.UpdatingSubscription;
import nibblr.sources.FeedSource;

abstract class WebsiteAgent extends AbstractAgent {

	protected HttpRequestFactory requestFactory;

	protected abstract FeedSource getFeedSource();

	@Override
	public void setup() {
		super.setup();

		requestFactory = HttpRequestFactoryFactory.getInstance();

		registerServiceInDirectoryFacilitator();

		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				try {
					receiveMessages();
				} catch (CodecException e) {
					e.printStackTrace();
				} catch (OntologyException e) {
					e.printStackTrace();
				}
			}

			private void receiveMessages() throws CodecException, OntologyException {
				ACLMessage msg = receive();
				if (msg != null) {
					switch (msg.getPerformative()) {
					case ACLMessage.SUBSCRIBE:
						handleSubscribe(msg);
						break;
					case ACLMessage.REQUEST:
						handleRequest(msg);
					}
				} else {
					block();
				}
			}
		});
	}

	private void registerServiceInDirectoryFacilitator() {
		try {
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType("nibbling");
			sd.setName("Providing nibbles (news items)");
			dfd.addServices(sd);
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	private void handleSubscribe(ACLMessage msg)
	throws CodecException, OntologyException {
		System.out.println(getLocalName() + ": responding to SUBSCRIBE");
		ACLMessage response = new ACLMessage(ACLMessage.CONFIRM);
		response.addReceiver(msg.getSender());
		response.setLanguage(codec.getName());
		response.setOntology(ontology.getName());
		AddingSubscription addingSubscription =
			new AddingSubscription(getFeedSource().downloadFeedInfo());

		Action addSubscription = new Action();
		addSubscription.setActor(msg.getSender());
		addSubscription.setAction(addingSubscription);
		getContentManager().fillContent(response, addSubscription);
		send(response);
	}

	private void handleRequest(ACLMessage msg)
	throws CodecException, OntologyException {
		System.out.println(getLocalName() + ": responding to REQUEST");
		ACLMessage response = new ACLMessage(ACLMessage.INFORM);
		response.addReceiver(msg.getSender());
		response.setLanguage(codec.getName());
		response.setOntology(ontology.getName());
		UpdatingSubscription updatingSubscription =
			new UpdatingSubscription(getFeedSource().downloadFeedWithItems());

		Action updateSubscription = new Action();
		updateSubscription.setActor(msg.getSender());
		updateSubscription.setAction(updatingSubscription);
		getContentManager().fillContent(response, updateSubscription);
		send(response);
	}
}
