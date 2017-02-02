
package socketsexample;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainSockets
{
	public static void main(String args[])
	{
		if(args[0].equals("s"))
			ServerSideEx();
		else
			ClientSideEx(Integer.parseInt(args[0]));
	}
	
	static void ServerSideEx() 
	{
		InputStream incomingStream	= null;
		OutputStream outgoingStream = null;
		
		DataInputStream inData		= null;	// "DataInputStream" permite la lectura de texto y tipos primitivos (int, float,...)
		DataOutputStream outData	= null;
		
		try
		{
			ServerSocket server	= new ServerSocket(10023);
			Socket connection	= server.accept();	// Allow one connection
			//server.close();	// No more connections allowed
			
			// Incoming data data
			incomingStream	= connection.getInputStream();
			inData		= new DataInputStream(incomingStream);
			
			// Outgoing data
			outgoingStream	= connection.getOutputStream();
			outData			= new DataOutputStream(outgoingStream);
			
			// Recieve data from client
			System.out.println(inData.readUTF());
			System.out.println(inData.readUTF());
			int age = inData.readInt();
			System.out.println("Client: " + age);
			System.out.println(inData.readUTF());
			System.out.println("Server: Sending answer....");

			// Send data to server
			if(age < 30)
				outData.writeUTF("Server: Qué jovencito, adelante!!!");
			if(age >= 30)
				outData.writeUTF("Server: Ufff, muy viejo... fuera!!!");
		
			outData.close();
			inData.close();
			connection.close();
		
		}
		catch(IOException e)
		{
			System.out.println("Error en el servidor");
			e.printStackTrace(System.out);
		}
	}
	
	static void ClientSideEx(int age)
	{
		InputStream incomingStream	= null;
		OutputStream outgoingStream	= null;
		
		DataInputStream inData		= null;	// "DataInputStream" permite la lectura de texto y tipos primitivos (int, float,...)
		DataOutputStream outData	= null;
		
		try
		{
			Socket connection = new Socket("localhost", 10023);
			// Outgoing data
			// Incoming data data
			incomingStream	= connection.getInputStream();
			inData		= new DataInputStream(incomingStream);
			
			// Outgoing data
			outgoingStream	= connection.getOutputStream();
			outData			= new DataOutputStream(outgoingStream);
			
			// Send data to server
			outData.writeUTF("Client: " + "Hola servidor");
			outData.writeUTF("Client: " + "Mi edad es: ");
			outData.writeInt(age);
			outData.writeUTF("Client: " + "¿Puedo entrar?");
			
			// Recieve data from server 
			System.out.println(inData.readUTF());
			
			outData.close();
			inData.close();
			connection.close();
			
		}
		catch (IOException e) {
			System.out.println("Error en el servidor");
			e.printStackTrace(System.out);
		}
	}
	
	
	static void PrintServerSocketData()
	{
		try
		{
			ServerSocket server = new ServerSocket(10023);
			System.out.println("Server listening on " + server.getLocalPort());
			Socket connection = server.accept();	// Allow one connection
			server.close();	// No more connections allowed
			print(connection.getInputStream());
			
		}catch(IOException e){System.out.println("Error en la conexión");}
	}
	
	
	static void print(InputStream is) throws IOException {
		if (is != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
}