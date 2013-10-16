/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Administrator
 */
public class GroupServer {
    private int port=8008;
    private ServerSocket serverSocket;
    private ExecutorService executorService;  //线程池
    private final int POOL_SIZE=12;  //单个CPU时线程池中工作线程的数目
    private static ArrayList<Socket>  groupMembers=new  ArrayList<Socket> (100);

    public GroupServer() throws IOException {
        serverSocket = new ServerSocket(port);
        //创建线程池
        //Runtime的availableProcessors()方法返回当前系统的CPU的数目
        //系统的CPU越多，线程池中工作线程的数目也越多
        executorService= Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * POOL_SIZE);

        System.out.println("服务器启动");
    }
    
    public static void sendToAllMembers(String msg)throws IOException{

        PrintWriter     pw;
        Socket        tempSocket;
        OutputStream  socketOut;

        for(int i=0; i<groupMembers.size();i++){
            tempSocket=groupMembers.get(i); //取出一个在线成员
            socketOut =tempSocket.getOutputStream();
            pw=new PrintWriter(new OutputStreamWriter(socketOut,"GB2312"),true);              
            pw.println(msg);
            System.out.println("sendtoall-->"+msg);
        }
    }//群组发送结束。

    public static void removeMember(Socket socket){
        groupMembers.remove(socket);//删除一个成员
    }


    public void service() {
        while (true) {
        	Socket socket=null;
			try {
				socket = serverSocket.accept(); //监听客户请求, 处于阻塞状态.
			    //接受一个客户请求,从线程池中拿出一个线程专门处理该客户.
				groupMembers.add(socket);
			    executorService.execute(new GroupHandler(socket));
			}catch (IOException e) { e.printStackTrace(); }
        }
    }

    public static void main(String args[])throws IOException {
        new GroupServer().service();
    }
}

class GroupHandler implements Runnable{
    private Socket socket;

    public GroupHandler(Socket socket){
        this.socket=socket;
    }
    private PrintWriter getWriter(Socket socket)throws IOException{
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(new OutputStreamWriter(socketOut,"GB2312"),true);
    }

    private BufferedReader getReader(Socket socket)throws IOException{
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn,"GB2312"));
    }

    public String echo(String msg) {
        return "echo:" + msg;
    }
    public void run(){
        try {
            System.out.println("New connection accepted " +
            socket.getInetAddress() + ":" +socket.getPort());

            BufferedReader br =getReader(socket);
            PrintWriter pw = getWriter(socket);

            String msg = null;
            while ((msg = br.readLine()) != null) {

            	GroupServer.sendToAllMembers(msg);
//            	pw.println(echo(msg));
                System.out.println("groupserver-->"+msg);
                if (msg.contains("bye".subSequence(0, 2))){
                    System.out.println( socket.getInetAddress() + ":" +"Exit");
                    break;
                }
            }
        }catch (IOException e) {
           e.printStackTrace();
        }finally {
           try{
             if(socket!=null){
            	 GroupServer.removeMember(socket);
            	 socket.close();
             }
           }catch (IOException e) {e.printStackTrace();}
        }
    }

}


