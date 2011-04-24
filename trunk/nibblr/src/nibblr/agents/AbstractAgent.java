package nibblr.agents;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import nibblr.ontology.NibblrOntology;

class AbstractAgent extends Agent {

	protected Codec codec = new SLCodec();
	protected Ontology ontology = NibblrOntology.getInstance();

	@Override
	public void setup() {
		System.out.println(getLocalName() + ": setup");

		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);
	}
}
