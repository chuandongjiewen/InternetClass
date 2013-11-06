package socketUdp;

/**
 *
 * @author Administrator
 */
import yincd.*;

import java.net.*;
import java.io.*;

import javax.swing.*;

public class EchoUDPClient {
	  private String remoteHost="222.201.101.15";// or localhost"222.201.101.15
	  private int remotePort=8880;//对方（服务器）端口号
	  private DatagramSocket socket;
	  private InetAddress remoteIP;
	  
	  public EchoUDPClient() throws SocketException, UnknownHostException{
		  socket = new DatagramSocket();
		  remoteIP = InetAddress.getByName(remoteHost);
	  }
	  
	  public void send(String msg) throws IOException {
		  byte[] buff = msg.getBytes("GBK");
		  DatagramPacket packet = new DatagramPacket(buff, buff.length, remoteIP, remotePort);
		  socket.send(packet);
	  }
	  
	  public String receive() throws IOException{
		  byte[] buff = new byte[1024];
		  DatagramPacket packet = new DatagramPacket(buff, buff.length);
		  socket.receive(packet);
		  return new String(packet.getData(),0,packet.getLength(), "GBK");
	  }
	  
	  public void close(){
		  socket.close();
	  }

	
}
