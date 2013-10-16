package ThreadServer;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatRoomJFrame extends JFrame {

	JPanel mainPanel, bottomPanel;
	JScrollPane scrollPane;
	JTextArea textArea;
	JTextField textField;
	JLabel label1;
	JLabel label2;
	JButton sendBtn, uploadBtn, downloadBtn, exitBtn;
	
	private String sendMsg = "";
	private String receiveMsg = "";
	private GroupClient groupClient;
	private String username = "";
	
	public ChatRoomJFrame (){
		initJframe();
		initComponents();
		bindEvents();
		startAcceptService();
	}
	
	private void startAcceptService(){
		try {
			groupClient = new GroupClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(){
			@Override
			public void run(){
				try {
					while(true){
						receiveMsg += groupClient.receive();
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
	
	public void setUsername(String username){
		this.username = username;
	}

	
	private void sendBtnEvent(ActionEvent event) {
		sendMsg = textField.getText();
		textField.setText("");
		if (sendMsg != "") {
			try {
				sendMsg = username + ": " + sendMsg;
				groupClient.send(sendMsg);
//				textArea.append(sendMsg + "\n");
				sendMsg = "";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void uploadBtnEvent(ActionEvent event){
		try {
			JFileChooser chooser = new JFileChooser();
	      	chooser.showOpenDialog(null);
	      	File file = chooser.getSelectedFile();
	      	if(file == null) {
	          return ;
	      	}
		    groupClient.fileUpload(file);
		    
		    FileOutputStream stream = new FileOutputStream("test.txt");
		    stream.write(123);
		    stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void downloadBtnEvent(ActionEvent event){
		try {
			groupClient.fileGet(sendMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void exitBtnEvent(ActionEvent event){
		try {
			groupClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
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
		setLayout(new BorderLayout());
		
		mainPanel = new JPanel();
		bottomPanel = new JPanel();
		scrollPane = new JScrollPane();
		textArea = new JTextArea();
		textField = new JTextField();
		label1 = new JLabel();
		label2 = new JLabel();
		sendBtn = new JButton();
		uploadBtn = new JButton();
		downloadBtn = new JButton();
		exitBtn = new JButton();
		
		textArea.setColumns(25);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("微软雅黑", 1, 18));
		textArea.setRows(8);
		
		scrollPane.setViewportView(textArea);
		label1.setText("信息显示区");
		mainPanel.add(label1);
		mainPanel.add(scrollPane);
		label2.setText("信息输入区");
		label2.setHorizontalAlignment(JLabel.LEFT);
		mainPanel.add(label2);
		textField.setColumns(25);
		textField.setFont(new Font("微软雅黑", 1, 18));
		mainPanel.add(textField);
		add(mainPanel);//把mainpanel加入jframe
		
		sendBtn.setText("发送");
		bottomPanel.add(sendBtn);
		uploadBtn.setText("上传");
		bottomPanel.add(uploadBtn);
		downloadBtn.setText("下载");
		bottomPanel.add(downloadBtn);
		exitBtn.setText("退出");
		bottomPanel.add(exitBtn);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(bottomPanel, BorderLayout.SOUTH);
//		
		
	}
	
	/**
	 * @param args
	 */
	private void initJframe() {
//		this.setTitle("chatroom");
//		this.setSize(500,400);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setLocationRelativeTo(null);
//		this.setVisible(true);
	}
	

}


