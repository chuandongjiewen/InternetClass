package PortScanner;
import java.io.*;
import java.net.*;

public class PortScanner {

   public static void main(String args[]){
    String host="localhost";
//    if(args.length>0)host=args[0];
//    new PortScanner().scan(host,100,150);
   new PortScanner().quickScan(host,10,100);
   
  }
  
    public void scan(String host,int start,int end){
    Socket socket=null;
    for(int port=start;port<end;port++){
      try {
          socket = new Socket(host, port);//TCP的三次握手过程
          System.out.println("Open port "+port+socket);
      } catch (IOException e) {
          System.out.println("Can't connect to port "+port);
      } finally {
        try {
            if(socket!=null)socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
      }
    }
  }
    public void quickScan(String host,int start,int end){
        
        Socket socket=null;
        for(int port=start;port<end;port++){
          try {
              socket = new Socket();//TCP的三次握手过程
              socket.connect(new InetSocketAddress(host, port), 150);
              System.out.println("Open port "+port+socket);
          } catch (IOException e) {
              System.out.println("Can't connect to port "+port);
          } finally {
            try {
                if(socket!=null)socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
          }
        }
    }
    
    
    
}