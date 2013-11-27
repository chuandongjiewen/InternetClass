package CapturePackage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;

public class SendPacket {
	JpcapSender sender;
	public SendPacket(){
		NetworkInterface[] device = JpcapCaptor.getDeviceList();
		try {
			sender = JpcapSender.openDevice(device[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void start(){
		
		try {
			TCPPacket tcp = new TCPPacket(8000, 80, 56, 78, false, false, false, false, 
					true, false, true, true, 50, 10);
			tcp.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, 
					IPPacket.IPPROTO_TCP, 
					InetAddress.getByName("192.168.233.15"), InetAddress.getByName("222.201.101.15"));
			tcp.data = ("20111003632 Òü´¨¶«").getBytes("GBK");
			
			EthernetPacket ether = new EthernetPacket();
			ether.frametype = EthernetPacket.ETHERTYPE_IP;
			
			ether.src_mac = new byte[]{(byte)56, (byte)234, (byte)167, (byte)238, (byte)109, (byte)127};
			ether.dst_mac = new byte[]{(byte)00, (byte)17, (byte)93, (byte)156, (byte)148, (byte)00};
			
			tcp.datalink = ether;
			
			for (int i = 0; i < 2; i++) {
				sender.sendPacket(tcp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		SendPacket packet = new SendPacket();
		packet.start();
				
	}
	
	
}
