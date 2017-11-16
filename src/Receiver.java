import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

public class Receiver {
	private static Deserializer deserializer;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int portNumber = 8080;
		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			serverSocket.setSoTimeout(100000);
			System.out.println("Connecting to server on port: " + portNumber);
			Socket client = serverSocket.accept();
			System.out.println("Client is connected");
			
			File xmlFile = new File("receivedXML.xml");
			InputStream in = client.getInputStream();
			FileOutputStream out = new FileOutputStream(xmlFile);
			byte[] byteArray = new byte[8888];
			
			int input = in.read(byteArray, 0, byteArray.length);
			out.write(byteArray, 0, input);
			out.flush();
			in.close();
			out.close();
			client.close();
			serverSocket.close();
			
			
			// build document
			SAXBuilder documentBuilder = new SAXBuilder();
			Document doc = documentBuilder.build(xmlFile);
			// deserialize
			deserializer = new Deserializer();
			Object obj = deserializer.deserialize(doc);
			// visualize
			ObjectInspector visualizer = new ObjectInspector();
			visualizer.inspect(obj, false);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}