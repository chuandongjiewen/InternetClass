package FtpFileClient;

/**
 *
 * @author Administrator
 */
import java.net.*;
import java.io.*;
import javax.swing.*;

import Score.*;

public class FileClient {
  private String host="192.168.221.74";//222.201.101.15
//  private String host="localhost";
  private int port=8080;//8081

  public Socket socket;
  private PrintWriter pw;//�����ֽں��ַ�֮��ת����.
  private BufferedReader br;

  public FileClient()throws IOException{
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
  
  public String fileUpload() throws IOException{
      Socket upSocket = new Socket(host, port+1);
      if(upSocket == null){
          return "����������";
      }
      byte[] buff = new byte[1024*2];
      JFileChooser chooser = new JFileChooser();
      chooser.showOpenDialog(null);
      File file = chooser.getSelectedFile();
      if(file == null) {
          return "�ļ��򿪴���";
      }
      FileInputStream fileIn = new FileInputStream(file);
      InputStream socketIn = upSocket.getInputStream();
      OutputStream socketOut = upSocket.getOutputStream();
      
//      PrintWriter pw2 = new PrintWriter(new OutputStreamWriter(socketOut,"GBK"),true);
      int len = fileIn.read(buff);
      while(len != -1){
          socketOut.write(len);
          len = fileIn.read(buff);
      }
      fileIn.close();
      JOptionPane.showMessageDialog(null, "�ļ��ϴ����.");
      upSocket.close();
      return "�ļ��ϴ����";
  }
  
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
              JOptionPane.showMessageDialog(null, "�ļ��������.");
              socket2.close();
              return "�ļ����سɹ�.";
          }else{
              socket2.close();
              return "�ļ�������";
          }
          
      }else{
          return "����������ʧ��";
      }
  }
    public String fileGet2(String fName) throws IOException{
    Socket socket2=new Socket(host,port+1);//���������ļ�������8081.

     if(socket2!=null){
       byte[] buff=new byte[1024*2];
         
        //�ļ�����Ի���.
        JFileChooser chooser=new JFileChooser(fName);
        chooser.showSaveDialog(null);
        File file=chooser.getSelectedFile();

        if(file!=null){
           FileOutputStream fileOut=new FileOutputStream(file); //�½����ؿ��ļ�.

           InputStream socketIn= socket2.getInputStream();//׼��������������
           OutputStream socketOut=socket2.getOutputStream();
        
           PrintWriter pw2=new PrintWriter(new OutputStreamWriter(socketOut,"GBK"),true);
           pw2.println(fName);//���߷�����Ҫ���ص��ļ���.

           //������,�����ļ�.
           int len=socketIn.read(buff);//��һ�鵽������.
             while(len!=-1){
                fileOut.write(buff,0,len);//дһ�鵽�ļ�.
                len=socketIn.read(buff);
             }
            fileOut.close();
            JOptionPane.showMessageDialog(null, "�ļ��������.");
            socket2.close();
            return "�ļ����سɹ�.";
           }else{
            socket2.close();
            return "�ļ�������.";
           }
       }else
        return "����������ʧ��.";
    }

  public void close() throws IOException{
    socket.close();//�Ĵ����ֶϿ�.
  }
  
}
