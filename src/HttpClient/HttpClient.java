package HttpClient;

/**
 *
 * @author Administrator
 */
import java.net.*;
import java.io.*;

public class HttpClient {
  private String host="smtp.qq.com";//查询成绩 8080
  private int port=25;//9009

  public Socket socket;
  private PrintWriter pw;//用于字节和字符之间转换用.
  private BufferedReader br;

  public HttpClient()throws IOException{
     socket=new Socket(host,port);//主动向对方发起连接,TCP中三次握手的连接阶段.
    
     pw=getWriter(socket);
     br=getReader(socket);
  }
  
  public HttpClient(String host, int port) throws IOException{
	  socket=new Socket(host,port);//主动向对方发起连接,TCP中三次握手的连接阶段.
	  pw=getWriter(socket);
	  br=getReader(socket);
  }

  private PrintWriter getWriter(Socket socket)throws IOException{
    OutputStream socketOut = socket.getOutputStream();
   // return new PrintWriter(socketOut,true);
    return new PrintWriter(new OutputStreamWriter(socketOut,"GB2312"),true);
   }

  private BufferedReader getReader(Socket socket)throws IOException{
    InputStream socketIn = socket.getInputStream();
    //return new BufferedReader(new InputStreamReader(socketIn));
    return new BufferedReader(new InputStreamReader(socketIn,"GB2312"));
  }

  public void send(String msg)throws IOException{
        pw.println(msg);//写入网卡输出流，由系统调用底层函数，经网卡发送。
   }
  public void send(byte[] b)throws IOException{
        socket.getOutputStream().write(b);
        //写入网卡输出流，由系统调用底层函数，经网卡发送。
   }

  public String receive()throws IOException{
    String msg=br.readLine();//只能接收一行信息.
  
    return msg;
  }

  public void close() throws IOException{
  socket.close();//四次握手断开.
  }
  
}
