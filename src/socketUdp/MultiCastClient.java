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
		socket = new MulticastSocket(port);
		address = InetAddress.getByName(groupIP);
		socket.joinGroup(address);
	}
	
	public void send(String msg) throws IOException{
		byte[] buff = msg.getBytes("GBK");
		DatagramPacket packet = new DatagramPacket(buff, buff.length, address, port);
		socket.send(packet);
	}
	
	public String receive() throws IOException{
		  byte[] buff = new byte[1024];
		  DatagramPacket packet = new DatagramPacket(buff, buff.length);
		  socket.receive(packet);
		  String msg = new String(packet.getData(),0,packet.getLength(), "GBK");
		  msg = "["+packet.getAddress()+"]"+msg;
		  return msg;
	 }
	
	public void close() throws IOException{
		socket.leaveGroup(address);
		socket.close();
	}
}
