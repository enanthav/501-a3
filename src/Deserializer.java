import java.io.File;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;

import org.jdom2.input.SAXBuilder;
import org.jdom2.*;


public class Deserializer {
	
	public Object deserialize(org.jdom2.Document document) throws Exception {
		Element root = document.getRootElement();
		List objectList = root.getChildren();
		HashMap map = new HashMap();
		createInstances(map, objectList);
		assignFieldValues(map, objectList);
		return map.get("0");
	}


	private void assignFieldValues(HashMap map, List list) throws Exception {
		for (Object object : list) {
			Element obj = (Element) object;
			Object classInstance = map.get(obj.getAttributeValue("id"));
			List fieldElements = obj.getChildren();
			
			// check if array
			if (classInstance.getClass().isArray()) {
				Class componentType = classInstance.getClass().getComponentType();
				for (int i = 0; i < fieldElements.size(); i++) {
					Element fieldElement = (Element) fieldElements.get(i);

					if (fieldElement.getName().equals("null")) {
						Array.set(classInstance, i, null);
					} else if (fieldElement.getName().equals("reference")) {
						Array.set(classInstance, i, map.get(fieldElement.getText()));
					} else if (fieldElement.getName().equals("value")) { // primitive types
						if (classInstance.getClass().getComponentType().getName().equals("boolean")) {
							if (fieldElement.getText().equals("true")) {
								Array.set(classInstance, i, Boolean.TRUE);
							} else {
								Array.set(classInstance, i, Boolean.FALSE);
							}
						} else if (classInstance.getClass().getComponentType().getName().equals("int")) {
							Array.set(classInstance, i,  Integer.valueOf(fieldElement.getText()));
						} else if (classInstance.getClass().getComponentType().getName().equals("float")) {
							Array.set(classInstance, i, Float.valueOf(fieldElement.getText()));
						} else {
							Array.set(classInstance, i, fieldElement.getText());
						}
					}

				
				}
			
		} else { // not an array
			for (Object f : fieldElements) {
				Element fieldElement = (Element) f;
				Class fieldDeclaringClass = Class.forName(fieldElement.getAttributeValue("declaringclass"));
				Field field = fieldDeclaringClass.getDeclaredField(fieldElement.getAttributeValue("name"));
				field.setAccessible(true);
				
				Element value = (Element) fieldElement.getChildren().get(0);
				Class<?> fieldType = field.getType();
				
				if (value.getName().equals("null")) {
					field.set(classInstance, null);
				} else if (value.getName().equals("reference")) {
					field.set(classInstance, map.get(value.getText()));
				} else if (value.getName().equals("value")) {
					// check primitives
					if (fieldType.getName().equals("boolean")) {
						if (value.getText().equals("true")) {
							field.set(classInstance, Boolean.TRUE);
						} else {
							field.set(classInstance, Boolean.FALSE);
						}
					} else if (fieldType.getName().equals("int")) {
						field.set(classInstance, Integer.valueOf(value.getText()));
					} else if (fieldType.getName().equals("float")) {
						field.set(classInstance, Float.valueOf(value.getText()));
					} 
					
				}
			}
		}
	}
}


	private void createInstances(HashMap map, List list) throws Exception {
		// create instances
		for (Object object : list) {
			Element obj = (Element) object;
			Class c = Class.forName(obj.getAttributeValue("class"));
			Object classInstance = obj;
			
			
			// check if array
			if (c.isArray()) {
				classInstance = Array.newInstance(c.getComponentType(), Integer.parseInt(obj.getAttributeValue("length")));
			} else {
				classInstance = c.newInstance();
			}
			map.put(obj.getAttributeValue("id"), classInstance);
		} 
	}
}


