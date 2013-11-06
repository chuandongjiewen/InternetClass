package socketUdp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MultiCastClient {

	String groupIP = "224.0.1.8";
	int port = 8900;
	MulticastSocket socket = null;
	InetAddress address = null;
	
	public MultiCastClient() throws IOException{
		socket = new MulticastSocket();
		address = InetAddress.getByName(groupIP);
		socket.joinGroup(address);
	}
	
	public void send(String msg) throws IOException{
		DatagramPacket packet = new DatagramPacket(new byte[1024], 1024, address, port);
		packet.setData(msg.getBytes("GBK"));
		socket.send(packet);
	}
	
	public String receive() throws IOException{
		DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
		socket.receive(packet);
		String msg = new String(packet.getData(),0, packet.getLength(),"GBK");
		return msg;
	}
	
	public void close() throws IOException{
		socket.leaveGroup(address);
		socket.close();
	}
}
