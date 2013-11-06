package HttpClient;

/**
 *
 * @author Administrator
 */
import java.net.*;
import java.io.*;

public class HttpClient {
  private String host="smtp.qq.com";//��ѯ�ɼ� 8080
  private int port=25;//9009

  public Socket socket;
  private PrintWriter pw;//�����ֽں��ַ�֮��ת����.
  private BufferedReader br;

  public HttpClient()throws IOException{
     socket=new Socket(host,port);//������Է���������,TCP���������ֵ����ӽ׶�.
    
     pw=getWriter(socket);
     br=getReader(socket);
  }
  
  public HttpClient(String host, int port) throws IOException{
	  socket=new Socket(host,port);//������Է���������,TCP���������ֵ����ӽ׶�.
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
        pw.println(msg);//д���������������ϵͳ���õײ㺯�������������͡�
   }
  public void send(byte[] b)throws IOException{
        socket.getOutputStream().write(b);
        //д���������������ϵͳ���õײ㺯�������������͡�
   }

  public String receive()throws IOException{
    String msg=br.readLine();//ֻ�ܽ���һ����Ϣ.
  
    return msg;
  }

  public void close() throws IOException{
  socket.close();//�Ĵ����ֶϿ�.
  }
  
}
