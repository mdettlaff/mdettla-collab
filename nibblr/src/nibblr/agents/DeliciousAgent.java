package nibblr.agents;

import nibblr.ontology.AddingSubscription;

public class DeliciousAgent extends WebsiteAgent {

	@Override
	AddingSubscription getAddingSubscription() {
		AddingSubscription addingSubscription = new AddingSubscription();
		addingSubscription.setName("Delicious");
		addingSubscription.setUrl("http://delicious.com/");
		return addingSubscription;
	}
}
