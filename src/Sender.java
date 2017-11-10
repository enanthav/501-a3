/* This class allows the user to create arbitrary objects */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Sender extends Thread {
	private ServerSocket serverSocket;
	public Sender(int port) {
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(100000); // will wait for client before it expires
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				System.out.println("Server running on: " + serverSocket.getLocalPort());
				Socket server = serverSocket.accept();
				System.out.println("Server connected to: " + server.getRemoteSocketAddress());
				// read message that client has sent to server
				DataInputStream in = new DataInputStream(server.getInputStream());
				System.out.println(in.readUTF()); // may have to use different read method with our assignment
				
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("Thank you for connecitng");
				server.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	// create simple object with only primitives for instance variables
	
	// object that contains references to other objects
	
	// object that contains an array of primitives
	
	// object that contains an array of object references
	
	public void displayMenu() {
		// create simple object with only primitives for instance variables
		
		// object that contains references to other objects
		
		// object that contains an array of primitives
		
		// object that contains an array of object references
	}
	
	
	public static void main(String[] args) {
		int port = 8080;
		try {
			Thread t = new Sender(port);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
