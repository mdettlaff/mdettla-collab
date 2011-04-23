package nibblr.ontology;

import jade.content.onto.BeanOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;

public class NibblrOntology extends BeanOntology {

	private static final String ONTOLOGY_NAME = "NibblrOntology";

	private static Ontology instance = new NibblrOntology();

	public static Ontology getInstance() {
		return instance; 
	}

	private NibblrOntology() {
		super(ONTOLOGY_NAME);
		try {
			add(Concepts.AddingSubscription.class);
		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}
}
