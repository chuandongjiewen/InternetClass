package ThreadServer;

/**
 *
 * @author Administrator
 */
import java.net.*;
import java.io.*;
import javax.swing.*;

public class GroupClient {
//  private String host="192.168.215.201";//222.201.101.15
  private String host="localhost";
  private int port=8008;//8081

  public Socket socket;
  private PrintWriter pw;//用于字节和字符之间转换用.
  private BufferedReader br;

  public GroupClient()throws IOException{
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
  
  @SuppressWarnings("unused")
public String fileUpload(File file) throws IOException{
      Socket upSocket = new Socket(host, 8080);
      if(upSocket == null){
          return "服务器错误";
      }
      byte[] buff = new byte[1024*2];
      
      FileInputStream fileIn = new FileInputStream(file);
      InputStream socketIn = upSocket.getInputStream();
      OutputStream socketOut = upSocket.getOutputStream();
      InputStreamReader reader = new InputStreamReader(fileIn);
      
      PrintWriter pw2 = new PrintWriter(new OutputStreamWriter(socketOut,"GBK"),true);
      pw2.println(file.getName());
      
//      char[] buff2 = new char[1024*2];
//      int len = reader.read(buff2);
//      while(len != -1){
//    	  len = reader.read(buff2);
//    	  System.out.println(buff2);
//      }
      int len = fileIn.read(buff);
      while(len != -1){
          socketOut.write(buff, 0, len);
          System.out.println(new String(buff));
          len = fileIn.read(buff);
      }
      fileIn.close();
      JOptionPane.showMessageDialog(null, "文件上传完毕.");
      upSocket.close();
      return "文件上传完成";
  }
  
  @SuppressWarnings("unused")
public String fileGet(String fName) throws IOException{

      Socket socket2 = new Socket(host, port+1);
      if(socket2 != null){
          byte[] buff = new byte[1024*2];
          JFileChooser chooser = new JFileChooser(fName);
          chooser.showSaveDialog(null);
          File file = chooser.getSelectedFile();
          
          if(file != null){
              FileOutputStream fileOut = new FileOutputStream(file);
              InputStream socketIn = socket2.getInputStream();
              OutputStream socketOut = socket2.getOutputStream();
              
              PrintWriter pw2 = new PrintWriter(new OutputStreamWriter(socketOut,"GBK"), true);
              pw2.println(fName);
              int len = socketIn.read(buff);
              while(len != -1){
                  fileOut.write(buff,0 ,len);
                  len = socketIn.read(buff);
              }
              
              fileOut.close();
              JOptionPane.showMessageDialog(null, "文件接收完毕.");
              socket2.close();
              return "文件下载成功.";
          }else{
              socket2.close();
              return "文件名错误";
          }
          
      }else{
          return "服务器连接失败";
      }
  }
    public String fileGet2(String fName) throws IOException{
    Socket socket2=new Socket(host,port+1);//连接数据文件服务器8081.

     if(socket2!=null){
       byte[] buff=new byte[1024*2];
         
        //文件保存对话框.
        JFileChooser chooser=new JFileChooser(fName);
        chooser.showSaveDialog(null);
        File file=chooser.getSelectedFile();

        if(file!=null){
           FileOutputStream fileOut=new FileOutputStream(file); //新建本地空文件.

           InputStream socketIn= socket2.getInputStream();//准备网络输入流。
           OutputStream socketOut=socket2.getOutputStream();
        
           PrintWriter pw2=new PrintWriter(new OutputStreamWriter(socketOut,"GBK"),true);
           pw2.println(fName);//告诉服务器要下载的文件名.

           //接下来,接收文件.
           int len=socketIn.read(buff);//读一块到缓冲区.
             while(len!=-1){
                fileOut.write(buff,0,len);//写一块到文件.
                len=socketIn.read(buff);
             }
            fileOut.close();
            JOptionPane.showMessageDialog(null, "文件接收完毕.");
            socket2.close();
            return "文件下载成功.";
           }else{
            socket2.close();
            return "文件名错误.";
           }
       }else
        return "服务器连接失败.";
    }

  public void close() throws IOException{
    socket.close();//四次握手断开.
  }
  
}
