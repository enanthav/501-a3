import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	private IdentityHashMap<Integer, Object> map;
	private Element root;
	private Document doc;

	public Serializer() {
		map = new IdentityHashMap<Integer, Object>();
		root = new Element("serialized");
	}

	public org.jdom2.Document serialize(Object obj) {
		try {
			Document doc = new Document(root);

			// take in object
			// create XML document that can be saved

		} catch (Exception e) {
			System.out.println(e);
		}
		return doc;
	}


}