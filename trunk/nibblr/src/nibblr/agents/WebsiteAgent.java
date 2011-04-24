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
import nibblr.ontology.AddingSubscription;

public abstract class WebsiteAgent extends AbstractAgent {

	abstract AddingSubscription getAddingSubscription();

	@Override
	public void setup() {
		super.setup();

		registerServiceInDirectoryFacilitator();

		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				receiveMessages();
			}

			private void receiveMessages() {
				ACLMessage msg = receive();
				if (msg != null) {
					switch (msg.getPerformative()) {
					case ACLMessage.SUBSCRIBE:
						handleSubscribe(msg);
						break;
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

	private void handleSubscribe(ACLMessage msg) {
		try {
			System.out.println(getLocalName() + ": responding to SUBSCRIBE");
			ACLMessage response = new ACLMessage(ACLMessage.CONFIRM);
			response.addReceiver(msg.getSender());
			response.setLanguage(codec.getName());
			response.setOntology(ontology.getName());
			AddingSubscription addingSubscription = getAddingSubscription();

			Action addSubscription = new Action();
			addSubscription.setActor(msg.getSender());
			addSubscription.setAction(addingSubscription);
			getContentManager().fillContent(response, addSubscription);
			send(response);
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}
}
