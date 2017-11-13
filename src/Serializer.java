import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
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
	private Integer id;
	private IdentityHashMap<Integer, Object> map;
	private Element root;
	private Document doc;

	public Serializer() {
		id = 0;
		map = new IdentityHashMap<Integer, Object>();
		root = new Element("serialized");
	}

	public org.jdom2.Document serialize(Object obj) {
		// serialize obj
		// serialize field
		// serialize rimitive
		// serialize obj ref
		// serialize array obj
		
		try {
			
			// serialize object
			Document doc = new Document(root);
			// serialize fields
			root.addContent(serializeHelper(obj));
			XMLOutputter xmlOutput = new XMLOutputter();
			String output = xmlOutput.outputString(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	public Element serializeHelper(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		// add new node
		Element node = new Element("object");
		node.setAttribute("class", obj.getClass().getSimpleName());
		node.setAttribute("id", String.valueOf(id));
		
		map.put(id, obj);
		id++; 
		// check if array
		if (obj.getClass().isArray()) {
			node.setAttribute("length", String.valueOf(Array.getLength(obj)));
			// check if primitive
			if (obj.getClass().getComponentType().isPrimitive()) {
	    		for (int i = 0; i < Array.getLength(obj); i++) {
	    			Element arrayElement = new Element("value");
	    			arrayElement.setText(Array.get(obj,  i).toString());
	    			node.addContent(arrayElement);
	    		}
			} else { // obj references
	    		for (int i = 0; i < Array.getLength(obj); i++) {
	    			Element arrayObjRef = new Element("reference");
	    			if (map.containsKey(Array.get(obj, i))) { // check if obj already in map
	    				arrayObjRef.setText(String.valueOf(map.get(Array.get(obj, i)).toString()));
	    			} else { // assign new id
	    				arrayObjRef.setText(String.valueOf(map.size()));
	    			}
	    		}
			}

		}
		
	
		
		// will need to check if primitive or obj ref
		for (Field f : fields) {
			f.setAccessible(true);

			Element fieldElement = new Element("field");
			fieldElement.setAttribute("name", f.getName());
			fieldElement.setAttribute("declaringclass", f.getDeclaringClass().getSimpleName());

			// check if primitive
			if (f.getType().isPrimitive()) {
				try {
					Element val = new Element("value");
					val.setText(f.get(obj).toString());
					fieldElement.addContent(val);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else { // obj reference
				try {
					Element ref = new Element("reference");
					if (map.containsKey(f.get(obj))) {
						// already exists in Identity Map
						ref.setText(String.valueOf(map.get(f.get(obj)).toString()));
					} else {
						ref.setText(String.valueOf(map.size()));
					}
					fieldElement.addContent(ref);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return node;
		
	}
}