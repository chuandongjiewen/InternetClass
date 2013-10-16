package ThreadServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class FileServer{
	private ServerSocket serverSocket;
	private int port = 8080;
	
	public FileServer(){
		try {
			serverSocket = new ServerSocket(port);
			System.out.print("fielserver");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException{
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(socketOut,  "GBK"), true);
	}
	
	private BufferedReader getReader(Socket socket) throws IOException{
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn, "GBK"));
	}
	
	
	public void service(){
		new Thread(){
			
			public void run(){
				while(true){
					Socket socket = null;
					try {
						socket = serverSocket.accept();
						
						System.out.println("New file connection accepted " +
					            socket.getInetAddress() + ":" +socket.getPort());
						
						BufferedReader reader = getReader(socket);
						PrintWriter writer = getWriter(socket);
						String fileName = reader.readLine();
//						File file = new File(fileName);
						FileOutputStream fileOut= new FileOutputStream(fileName);
						InputStream socketIn = socket.getInputStream();
//						byte[] buff = new byte[1024*2];
//						int len = socketIn.read(buff);
//						System.out.println("len:" + len +"-->" + new String(buff));
//						while(len != -1){
//							fileOut.write(buff, 0, len);
//							System.out.println(new String(buff));
//							len = socketIn.read(buff);
//						}
						String msg = "";
						while((msg = reader.readLine()) != null){
							fileOut.write(msg.getBytes());
							System.out.println(msg);
						}
						fileOut.close();
						socketIn.close();
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						if (socket != null) {
							try {
								socket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				
			}
			
		}.start();
	}
	
	
	public static void main(String[] args){
		new FileServer().service();
	}
	
	
	
	
	
}