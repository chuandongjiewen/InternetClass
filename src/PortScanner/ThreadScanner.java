/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PortScanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ThreadScanner extends Thread{

    String host;
    int startPort;
    int endPort;
    
    public static void  main(String[] args){
        String host="localhost";
        ThreadScanner thread1 = new ThreadScanner(host, 100,200);
        thread1.start();
        
        ThreadScanner thread2 = new ThreadScanner(host, 200,300);
        thread2.start();
    }
    
    public ThreadScanner(String host, int startPort, int endPort){
        this.host  = host;
        this.startPort = startPort;
        this.endPort = endPort;
        
    }
    
    
    @Override
    public void run() {
        Socket socket=null;
        for(int port=startPort;port<endPort;port++){
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
