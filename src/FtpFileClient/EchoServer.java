package FtpFileClient;

import yincd.*;
import java.io.*;
import java.net.*;

public class EchoServer {
  private int port=8008;
  private ServerSocket serverSocket;

  public EchoServer() throws IOException {
    serverSocket = new ServerSocket(port);//����8008�ż����˿ڡ�
    System.out.println("����������");
  }

  private PrintWriter putWriter(Socket socket)throws IOException{
    OutputStream socketOut = socket.getOutputStream();//������������������ĵ�ַ��
    return new PrintWriter(new OutputStreamWriter(socketOut,"GB2312"),true);

  }
  private BufferedReader getReader(Socket socket)throws IOException{
    InputStream socketIn = socket.getInputStream();//�����������뻺�����ĵ�ַ
    return new BufferedReader(new InputStreamReader(socketIn,"GB2312"));
  }
  public String echo(String msg){
    // return "echo"+msg;
    return "echo"+msg;
  }

  public void service() {//���ͻ��汾,��ÿ��ֻ��ͬʱ��һ���ͻ�����ͨ�����ӡ�
    while (true) {
      Socket socket=null;
      try {
        socket = serverSocket.accept();  //�ȴ��ͻ����ӣ������Ӿ�����һ���׽��֡��������.
         System.out.println("New connection accepted "
                        +socket.getInetAddress() + ":" +socket.getPort());
        BufferedReader br =getReader(socket);//���ַ�������������������
        PrintWriter pw = putWriter(socket);//���ַ���д�������������

        String msg = null;
        while ((msg = br.readLine())!= null) //�������������ж���һ���ַ������������.
         {
             System.out.println(msg);
             pw.println(echo("1:"+msg));//����������������һ���ַ�����
             pw.println(echo("2:"+msg));
             if (msg.equals("bye")) //����ͻ����͵���ϢΪ��bye�����ͽ���ͨ��
              break;
        }
      }catch (IOException e) {
         e.printStackTrace();
      }finally {
         try{
           if(socket!=null)socket.close();  //�Ͽ�����
         }catch (IOException e) {e.printStackTrace();}
      }
    }
  }

  public static void main(String args[])throws IOException {
    EchoServer server = new EchoServer();
    server.service();
    
  }
}