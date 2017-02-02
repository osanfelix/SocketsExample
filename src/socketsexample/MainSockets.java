
package socketsexample;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainSockets
{
	public static void main(String args[])
	{
		if(args[0].equals("eco"))
		{
			if(args[1].equals("s"))
				ServerSideEco();
			else
				ClientSideEco();
		}
		
		else
		{
			if(args[1].equals("s"))
				ServerSideEx();
			else
				ClientSideEx(Integer.parseInt(args[1]));
		}
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
			server.close();	// No more connections allowed
			
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
	
	
	static void ServerSideEco() 
	{
		InputStream incomingStream	= null;

		try
		{
			ServerSocket server	= new ServerSocket(10023);
			Socket connection	= server.accept();	// Allow one connection
			System.out.println("[ Client connected ]");
			server.close();	// No more connections allowed
			
			// Incoming data data
			incomingStream	= connection.getInputStream();
			
			print(incomingStream);
			incomingStream.close();
			connection.close();
		}
		catch(IOException e)
		{
			System.out.println("Error en el servidor");
			e.printStackTrace(System.out);
		}
	}
	
	static void ClientSideEco()
	{
		OutputStream outgoingStream	= null;
		PrintWriter outData = null;
		
		try
		{
			Socket connection = new Socket("localhost", 10023);
			// Outgoing data
			outgoingStream = connection.getOutputStream();
			
			
			Scanner input = new Scanner(System.in);
			
			outData = new PrintWriter(outgoingStream, true);
			
			while(true)
			{
				String keyboardInput = input.nextLine();
				if(keyboardInput.equals("exit"))
					break;
				outData.println(keyboardInput);
			}
			
			outData.close();
			connection.close();
		}
		catch (IOException e) {
			System.out.println("Error en el servidor");
			e.printStackTrace(System.out);
		}
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