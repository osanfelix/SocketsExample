
package socketsexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class URLExample
{
	public static void showExamples()
	{
		showUrlInfo("http://images5.fanpop.com/image/photos/31600000/Miyamoto-shigeru-miyamoto-31665334-300-374.jpg?resize=600px");
		getUrl("https://www.google.es");
		
		System.out.println("-----------");
		getURLConnection("https://www.google.es");
	}
	
	
	static void showUrlInfo(String urlString)
	{
		try
		{
			URL url = new URL(urlString);
			
			// Print URL info
			System.out.println("URL Completa: " + url.toString());
			System.out.println("URL String: " + url.toString());
			System.out.println("URL Protocol: " + url.getProtocol());
			System.out.println("URL Host: " + url.getHost());
			System.out.println("URL Port: " + url.getPort());
			System.out.println("URL File: " + url.getFile());
			System.out.println("URL User Info: " + url.getUserInfo());
			System.out.println("URL Path: " + url.getPath());
			System.out.println("URL Authority: " + url.getAuthority());
			System.out.println("URL Query: " + url.getQuery());
		}
		catch (MalformedURLException ex)
		{
			System.out.println("Error en la URL");
			ex.printStackTrace(System.out);
		}
	}
	
	
	static void getUrl(String urlString)
	{
		try
		{
			URL url = new URL(urlString);
			print(url.openStream());		// Print html page
		}
		catch (IOException ex)
		{
			System.out.println("Error en la URL");
			ex.printStackTrace(System.out);
		}
	}
	
	

	
	
	static void getURLConnection(String urlString)
	{
		try
		{
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			
			// Print headers fields:
			Iterator<Map.Entry<String,List<String>>> iter = connection.getHeaderFields().entrySet().iterator();
			while(iter.hasNext())
			{
				Map.Entry<String,List<String>> entry = iter.next();
				System.out.println(entry.getKey() + ": " + (entry.getValue().size() == 1 ? entry.getValue().get(0) : ""));
				if(entry.getValue().size() > 1)
				{
					for(String value : entry.getValue())
					{
						System.out.println(value);
					}
				}
			}
			System.out.println("Página HTML: ");
			print(connection.getInputStream());	// Print html page
		}
		catch (IOException ex)
		{
			System.out.println("Error en la URL");
			ex.printStackTrace(System.out);
		}
	}
	
	
	static void TestInetAddress()
	{
		try
		{
			InetAddress address = InetAddress.getByName("www.google.com");
			
			// Print InetAddress info
			System.out.println("Host Ip: " + address.getHostAddress());
			System.out.println("Host Name: " + address.getHostName());
			System.out.println("Host String: " + address.toString());
			System.out.println("Host Canonical name: " + address.getCanonicalHostName());
		}
		catch(IOException e)
		{
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
