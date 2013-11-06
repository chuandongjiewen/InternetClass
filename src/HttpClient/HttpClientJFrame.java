package HttpClient;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class HttpClientJFrame extends JFrame {

	JPanel mainPanel, bottomPanel;
	JScrollPane scrollPane;
	JTextArea textArea;
	JTextField ipTextField;
	JTextField portTextField;
	JLabel label1;
	JLabel label2;
	JButton sendBtn, uploadBtn, downloadBtn, exitBtn;
	
	private String sendMsg = "";
	private String receiveMsg = "";
	private HttpClient httpClient;
	
	
	public HttpClientJFrame (){
		initComponents();
		bindEvents();
//		startAcceptService();
	}
	
	private void startAcceptService(){
		new Thread(){
			@Override
			public void run(){
				try {
					while(true){
						receiveMsg += httpClient.receive();
						if(receiveMsg != ""){
							textArea.append(receiveMsg + "\n");
//							System.out.println("client-->"+receiveMsg);
							receiveMsg = "";
						}
						if (receiveMsg.contains("bye".subSequence(0, 2))){
		                    break;
		                }
					}
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}.start();
	}
	

	
	private void sendBtnEvent(ActionEvent event) {
		String host = ipTextField.getText();
		System.out.println(portTextField.getText());
		int port = Integer.parseInt(portTextField.getText());
		
		try {
			httpClient = new HttpClient(host, port);
			httpClient.send("GET /HTTP/1.1\r\n");
			httpClient.send("host: "+host+"\r\n");
			httpClient.send("Accept: */*\r\n");
			httpClient.send("Accept-Language: zh-cn\r\n");
			httpClient.send("Accept-Encoding: gzip, deflate\r\n");
			httpClient.send("User-Agent: Mozilla/4.0(compatible; MSIE 6.0; Windows XP)\r\n");
			httpClient.send("Connection: Keep-Alive\r\n");
			
			new Thread(){
				public void run(){
					try {
						String msg = null;
						while((msg = httpClient.receive()) != null){
							textArea.append(msg);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void uploadBtnEvent(ActionEvent event){

	}
	
	private URL url = null;
	private  BufferedReader br = null;
	private void downloadBtnEvent(ActionEvent event){
		try {
			String host = ipTextField.getText();
			url = new URL(host);
			 InputStream in = url.openStream();
		     br = new BufferedReader(new InputStreamReader(in,"utf-8"));
		     new Thread(){
		    	 public void run(){
		    		 
				     try {
				    	String temp = "";
						while((temp = br.readLine()) != null){
							 textArea.append(temp);
						 }
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	 }
		     }.start();
		     
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void exitBtnEvent(ActionEvent event){
		try {
			httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			System.exit(0);
		}
		
	}
	
	public void bindEvents(){
		sendBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendBtnEvent(e);
			}
		});
		
		uploadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				uploadBtnEvent(e);
			}
		});
		
		downloadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				downloadBtnEvent(e);
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitBtnEvent(e);
			}
		});
	}
	
	private void initComponents(){
		getContentPane().setLayout(new BorderLayout());
		
		mainPanel = new JPanel();
		bottomPanel = new JPanel();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 25, 456, 198);
		textArea = new JTextArea();
		ipTextField = new JTextField();
		ipTextField.setBounds(19, 248, 456, 30);
		label1 = new JLabel();
		label1.setBounds(19, 0, 60, 15);
		label2 = new JLabel();
		label2.setBounds(19, 233, 60, 15);
		sendBtn = new JButton();
		uploadBtn = new JButton();
		downloadBtn = new JButton();
		exitBtn = new JButton();
		
		textArea.setColumns(25);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("微软雅黑", 1, 18));
		textArea.setRows(8);
		
		scrollPane.setViewportView(textArea);
		mainPanel.setLayout(null);
		label1.setText("信息显示区");
		mainPanel.add(label1);
		mainPanel.add(scrollPane);
		label2.setText("信息输入区");
		label2.setHorizontalAlignment(JLabel.LEFT);
		mainPanel.add(label2);
		ipTextField.setColumns(25);
		ipTextField.setFont(new Font("微软雅黑", 1, 18));
		mainPanel.add(ipTextField);
		getContentPane().add(mainPanel);//把mainpanel加入jframe
		
		portTextField = new JTextField();
		portTextField.setBounds(19, 298, 145, 30);
		mainPanel.add(portTextField);
		portTextField.setColumns(10);
		
		sendBtn.setText("发送");
		bottomPanel.add(sendBtn);
		uploadBtn.setText("上传");
		bottomPanel.add(uploadBtn);
		downloadBtn.setText("下载");
		bottomPanel.add(downloadBtn);
		exitBtn.setText("退出");
		bottomPanel.add(exitBtn);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
//		
		
	}
	
	
	public static void main(String[] args){
		JFrame jFrame = new HttpClientJFrame();
		jFrame.setTitle("chatroom");
		jFrame.setSize(511,414);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
	}
}


