package socketUdp;


import java.io.*;
import java.net.*;

import Score.*;

public class UdpServer {
  private int port=8880;
  private DatagramSocket serverSocket;

  public UdpServer() throws IOException {
    serverSocket = new DatagramSocket(port);//开启8008号监听端口。
    System.out.println("服务器启动");
  }


  public void service() {//单客户版本,即每次只能同时和一个客户建立通信连接。
    while (true) {
    	try {
    		byte[] buff = new byte[1024];
        	DatagramPacket packet = new DatagramPacket(buff, buff.length);
			serverSocket.receive(packet);
			String msg = new String(packet.getData(),0, packet.getLength(), "GBK");
			msg = "20111003632 尹川东 ：" + msg;
			packet.setData(msg.getBytes("GBK") );
			serverSocket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  }

  public static void main(String args[])throws IOException {
    UdpServer server = new UdpServer();
    server.service();
    
  }
}