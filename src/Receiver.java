import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver {
	public static void main(String[] args) {
		String address = "127.0.0.1";
		int portNumber = 8080;
		try {
			System.out.println("Connecting to server: " + address + " on port: " + portNumber);
			Socket client = new Socket(address, portNumber);
			System.out.println("Client connected to " + client.getRemoteSocketAddress());
	
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF("Hello from client");
			
			// create input stream and send message from client to server 
			DataInputStream in = new DataInputStream(client.getInputStream());
			
			System.out.println("Server messaged: " + in.readUTF());
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
