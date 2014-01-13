package socketUdp;


import java.io.*;
import java.net.*;

import Score.*;

public class UdpServer {
  private int port=8880;
  private DatagramSocket serverSocket;

  public UdpServer() throws IOException {
    serverSocket = new DatagramSocket(port);//����8008�ż����˿ڡ�
    System.out.println("����������");
  }


  public void service() {//���ͻ��汾,��ÿ��ֻ��ͬʱ��һ���ͻ�����ͨ�����ӡ�
    while (true) {
    	try {
    		byte[] buff = new byte[1024];
        	DatagramPacket packet = new DatagramPacket(buff, buff.length);
			serverSocket.receive(packet);
			String msg = new String(packet.getData(),0, packet.getLength(), "GBK");
			msg = "20111003632 ������ ��" + msg;
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