import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class clientudp {
	DatagramSocket ds;
	clientudp()
	{
		try {
			ds=new DatagramSocket(8888);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	void sendtext(String s)
	{
		byte arr[]=new byte[1024];
		arr=s.getBytes();
		InetAddress inet;
		try {
			inet = InetAddress.getByName("localhost");
			DatagramPacket dp1=new DatagramPacket(arr,arr.length,inet,9997);	
			ds.send(dp1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	String revievetext()
	{
		byte[] in_data=new byte[1024];
		DatagramPacket dp2=new DatagramPacket(in_data,in_data.length);
		try {
			ds.receive(dp2);
			String s=new String(dp2.getData());
			return s;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
}
