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
    private ExecutorService executorService;  //�̳߳�
    private final int POOL_SIZE=12;  //����CPUʱ�̳߳��й����̵߳���Ŀ
    private static ArrayList<Socket>  groupMembers=new  ArrayList<Socket> (100);

    public GroupServer() throws IOException {
        serverSocket = new ServerSocket(port);
        //�����̳߳�
        //Runtime��availableProcessors()�������ص�ǰϵͳ��CPU����Ŀ
        //ϵͳ��CPUԽ�࣬�̳߳��й����̵߳���ĿҲԽ��
        executorService= Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * POOL_SIZE);

        System.out.println("����������");
    }
    
    public static void sendToAllMembers(String msg)throws IOException{

        PrintWriter     pw;
        Socket        tempSocket;
        OutputStream  socketOut;

        for(int i=0; i<groupMembers.size();i++){
            tempSocket=groupMembers.get(i); //ȡ��һ�����߳�Ա
            socketOut =tempSocket.getOutputStream();
            pw=new PrintWriter(new OutputStreamWriter(socketOut,"GB2312"),true);              
            pw.println(msg);
            System.out.println("sendtoall-->"+msg);
        }
    }//Ⱥ�鷢�ͽ�����

    public static void removeMember(Socket socket){
        groupMembers.remove(socket);//ɾ��һ����Ա
    }


    public void service() {
        while (true) {
        	Socket socket=null;
			try {
				socket = serverSocket.accept(); //�����ͻ�����, ��������״̬.
			    //����һ���ͻ�����,���̳߳����ó�һ���߳�ר�Ŵ���ÿͻ�.
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


