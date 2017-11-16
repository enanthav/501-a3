import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
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
	public org.jdom2.Document serialize(Object obj) throws IllegalArgumentException, IllegalAccessException {
		IdentityHashMap map = new IdentityHashMap();
		Element root = new Element("serialized");
		Document doc = new Document(root);
		return serializeHelper(obj, doc, map);
	}
	

	public Document serializeHelper(Object obj, Document doc, IdentityHashMap map) throws IllegalArgumentException, IllegalAccessException {
		Class<?> c = obj.getClass();
		String id = Integer.toString(map.size());
		map.put(obj, id);
		// add new node
		Element node = new Element("object");
		node.setAttribute("class", c.getSimpleName());
		node.setAttribute("id", Integer.toString(map.size()-1));
		doc.getRootElement().addContent(node);
		
		
		// check if array
		if (c.isArray()) {
			node.setAttribute("length", Integer.toString(Array.getLength(obj)));
			// check if primitive
			if (c.getComponentType().isPrimitive()) {

	    		for (int i = 0; i < Array.getLength(obj); i++) {
					System.out.println(Array.get(obj, i));
	    			Element arrayElement = new Element("value");
	    			arrayElement.setText(Array.get(obj, i).toString());
	    			node.addContent(arrayElement);
	    		}
			} else { // obj references
	    		for (int i = 0; i < Array.getLength(obj); i++) {
	    			if (Array.get(obj, i) != null) {
		    			Element arrayObjRef = new Element("reference");
		    			if (map.containsKey(Array.get(obj, i))) { // check if obj already in map
		    				arrayObjRef.setText(map.get(Array.get(obj, i)).toString());
		    			} else { // assign new id
		    				arrayObjRef.setText(String.valueOf(map.size()));
	    					serializeHelper(Array.get(obj, i), doc, map);
		    			}
		    			node.addContent(arrayObjRef);
		    		}
	    		}
			}
			
			
			
			
			
		} else { // not an array
			Field[] fields = c.getDeclaredFields();
			// will need to check if primitive or obj ref
			for (Field f : fields) {
				f.setAccessible(true);
	
				Element fieldElement = new Element("field");
				fieldElement.setAttribute("name", f.getName());
				fieldElement.setAttribute("declaringclass", f.getDeclaringClass().getSimpleName());


				if (f.get(obj) != null) {
					if (f.getType().isPrimitive()) { // check if primitive
						Element val = new Element("value");
						val.setText(f.get(obj).toString());
						fieldElement.addContent(val);
					} else { // obj reference
						Element ref = new Element("reference");
						if (map.containsKey(f.get(obj))) {
							// already exists in Identity Map
							ref.setText(map.get(obj).toString());
						} else {
							ref.setText(String.valueOf(map.size()));
							serializeHelper(f.get(obj), doc, map);
						}
						fieldElement.addContent(ref);
					}
				} else {
					Element empty = new Element("null");
					fieldElement.addContent(empty);
				}
				node.addContent(fieldElement);
			}
		}
		return doc;
	}

	public void testSerializer(Document doc2) throws FileNotFoundException {
		XMLOutputter outputter = new XMLOutputter();
		
		String output = outputter.outputString(doc2);
		
		System.out.println(output);
		
		PrintWriter out = new PrintWriter("newest.xml");
		out.println(output);
		out.close();
	}
}