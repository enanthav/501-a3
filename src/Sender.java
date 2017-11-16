
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Sender extends Thread {
	private static ServerSocket serverSocket;
	private static Serializer serialize;
	
	public static void main(String[] args) {
		Document doc = null;
		Scanner input = new Scanner(System.in);
		int selection = 0;
		serialize = new Serializer();
		System.out.println("Select an option from below: ");
		System.out.println("[1] Object with Primitive Variables");
		System.out.println("[2] Object that contains References to Other Objects");
		System.out.println("[3] Object that Contains a Three Integer Array");
		System.out.println("[4] Object that Contains an Array of References");
		System.out.println("[5] Object using Collection Instance\n");
		System.out.print("> ");
		
		try {
			selection = input.nextInt();

		// create object based on user input
		if (selection == 1) {
			System.out.println("Input an integer: ");
			int inputInt = input.nextInt();
			System.out.println("Input a float: ");
			float inputFloat = input.nextFloat();
			System.out.println("True or false: you like cats");
			boolean inputBool = input.nextBoolean();
			PrimitiveClass primClass = new PrimitiveClass(inputInt, inputFloat, inputBool);
			System.out.println("Primitive object created");
			// serialize here
			doc = serialize.serialize(primClass);
		} else if (selection == 2) {
			System.out.println("Set integer value: ");
			int inputInt = input.nextInt();
			ObjectRef objReferences = new ObjectRef();
			objReferences.setIntVar(inputInt);
			System.out.println("Object that contains a reference to other objects created");
			
			// serialize
			doc = serialize.serialize(objReferences);
		} else if (selection == 3) {
			System.out.println("Input three integers: ");
			System.out.println("First integer: ");
			int inputInt1 = input.nextInt();
			System.out.println("Second integer: ");
			int inputInt2 = input.nextInt();
			System.out.println("Third integer: ");
			int inputInt3 = input.nextInt();
			PrimitiveArrayClass primArrayObj = new PrimitiveArrayClass(inputInt1, inputInt2, inputInt3);
			System.out.println("Primitive array object created");
			// serialize
			doc = serialize.serialize(primArrayObj);
		} else if (selection == 4) {
			Object obj1 = new Object();
			Object obj2 = new Object();
			System.out.println("Object that contains an array of object references created");
			// object that contains an array of object references
			ArrayObjects arrayObjectRefs = new ArrayObjects();
			arrayObjectRefs.addObjectToArray(obj1, 0);
			arrayObjectRefs.addObjectToArray(obj1, 2);
			
			//serialize
			doc = serialize.serialize(arrayObjectRefs);
		} else if (selection == 5) {
			// create objects to insert
			Object obj1 = new Object();
			Object obj2 = new Object();
			System.out.println("Object that contains an instance of Java's collection classes created");
			// object that uses instance of Java's collection classes
			ArrayObjects arrayObj = new ArrayObjects();
			arrayObj.addObjectToArray(obj1, 0);
			arrayObj.addObjectToArray(obj2, 1);
			
			// serialize
			doc = serialize.serialize(arrayObj);
		} else {
			System.out.println("Invalid number, please try again");
		}
		
		createXMLandSend(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void createXMLandSend(Document doc) throws FileNotFoundException, IOException {
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(doc, new FileOutputStream("sendXML.xml"));
		int port = 8080;
		String address = "127.0.0.1";
		Socket sock = new Socket(address, port);
		System.out.println("Connecting on port: "+ port);
		System.out.println("Client connected to " + sock.getRemoteSocketAddress());
		
		File xmlFile = new File("sendXML.xml");

		byte[] byteArray = new byte[(int) xmlFile.length()];
 		InputStream in = new FileInputStream(xmlFile);
		OutputStream out = sock.getOutputStream();
		
		in.read(byteArray, 0, byteArray.length);
		out.write(byteArray, 0, byteArray.length);
		out.flush();
		
		in.close();
        sock.close();
	}

}
