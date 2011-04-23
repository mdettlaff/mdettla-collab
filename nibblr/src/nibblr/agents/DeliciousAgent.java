package nibblr.agents;

import nibblr.ontology.Concepts;
import nibblr.ontology.Concepts.AddingSubscription;

public class DeliciousAgent extends WebsiteAgent {

	@Override
	AddingSubscription getAddingSubscription() {
		Concepts.AddingSubscription addingSubscription =
			new Concepts.AddingSubscription();
		addingSubscription.setName("Delicious");
		addingSubscription.setUrl("http://delicious.com/");
		return addingSubscription;
	}
}
