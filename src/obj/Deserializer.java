package obj;

public class Deserializer {
    public Object deserialize(org.jdom2.Document document){
    	Element classElement = document.getRootElement();	
		List<Element> classObjectList = classElement.getChildren();
		HashMap mapOfObjects = new HashMap();
		for(Element objectElement : classObjectList){
			Class objClass = Class.forName(objectElement.getAttributeValue("class"));
			Object classInstance = null;
			// Now that we know the class of the element, we can recreate it, then load in it's field values
		
			// Check if array of classes, or just one class
			if (objClass.isArray()){
				int length = Integer.parseInt(objectElement.getAttributeValue("length"));
				classInstance = Array.newInstance(objClass.getComponentType(), length);
			}else{
				// Just a single class, instantiate it
				
				classInstance = objClass.getDeclaredConstructor(null).newInstance(null); 
			}
			Attribute objectAttribute = objectElement.getAttribute("id");
			mapOfObjects.put(objectAttribute.getValue(), classInstance);
			System.out.println("Put instance in map: "+ objectAttribute.getValue());
			
			/// I think all this needs to be completed before we can move on to the field values. All instances need to be made.
			// Take the stuff before, and put it outside of the first for loop.
			
			
			
			System.out.println("mapOfObjects: " + mapOfObjects.get("1"));
			
		} 
	// Now set the field values for the classes
		
		for(Element objectElement : classObjectList){
			Object classInstance = mapOfObjects.get(objectElement.getAttributeValue("id"));
			List<Element> fieldElementList = objectElement.getChildren(); 
			
			if (classInstance.getClass().isArray()){
				// Is an array
				
			}else{
				// Not an array, so get fields to set their values for class
				for (Element fieldElement : fieldElementList){
					Class declaringClass = Class.forName(fieldElement.getAttributeValue("declaringclass"));
					Field field = declaringClass.getDeclaredField(fieldElement.getAttributeValue("name"));
					System.out.println("Field: " + field.getName());
					
					Element fieldValue = (Element) fieldElement.getChildren().get(0);
					
					// Set value for the field
					System.out.println("Field name = " + field.getName());
					if (fieldValue.getName().equals("value")){
						// Primitive field type
						System.out.println("-Is a value");
						if(field.getType().getName().equals("boolean")){
							System.out.println("--Type boolean");
							if(fieldValue.getText().equals("true")){
								field.set(classInstance, Boolean.TRUE);
							}else{
								field.set(classInstance, Boolean.FALSE);
							}
						}else if(field.getType().getName().equals("int")){
							System.out.println("--Type int");
							field.set(classInstance, Integer.valueOf(fieldValue.getText()));
						}else if(field.getType().getName().equals("char")){
							System.out.println("--Type char");
							field.set(classInstance, new Character(fieldValue.getText().charAt(0)));
						}
					}else if(fieldValue.getName().equals("reference")){
						System.out.println("-Is a reference");
						field.set(classInstance, mapOfObjects.get(fieldValue.getText()));
						
					}else if(fieldValue.getName().equals("null")){
						System.out.println("-Is null");
						field.set(classInstance, null);
					}
				}
			}		
		}
		 
		System.out.println("mapOfObjects index 0 = " + mapOfObjects.get("1"));
		return mapOfObjects.get("1");
		
		}
    }
