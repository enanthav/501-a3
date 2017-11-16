import static org.junit.jupiter.api.Assertions.*;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.Test;

import org.junit.After;
import org.junit.Before;

public class TestClass {
	ArrayObjects arrObj;
	CollectionObjects collObj;
	ObjectClass objCls;
	ObjectRef objRef;
	PrimitiveArrayClass primArray;
	PrimitiveClass primClass;
	
	@Test
	public void testCreateArrayObjects() {
		assertNull(arrObj);
		arrObj = new ArrayObjects();
		assertNotNull(arrObj);
	}
	
	@Test
	public void testCreateCollObj() {
		assertNull(collObj);
		collObj = new CollectionObjects();
		assertNotNull(collObj);
	}
	
	@Test
	public void testCreateObjectClass() {
		assertNull(objCls);
		objCls = new ObjectClass();
		assertNotNull(objCls);
	}
	
	@Test
	public void testCreateObjectRef() {
		assertNull(objRef);
		objRef = new ObjectRef();
		assertNotNull(objRef);
	}
	
	@Test
	public void testCreatePrimArray() {
		assertNull(primArray);
		primArray = new PrimitiveArrayClass();
		assertNotNull(primArray);
	}
	@Test
	public void testCreatePrimClass() {
		assertNull(primClass);
		primClass = new PrimitiveClass();
		assertNotNull(primClass);
	}
}
