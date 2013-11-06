package socketUdp;

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


public class UdpClientJFrame extends JFrame {

	JPanel mainPanel, bottomPanel;
	JScrollPane scrollPane;
	JTextArea textArea;
	JTextField textField;
	JLabel label1;
	JLabel label2;
	JButton sendBtn, uploadBtn, downloadBtn, exitBtn;
	
	private String sendMsg = "";
	private String receiveMsg = "";
	private MultiCastClient echoClient;
	
	public UdpClientJFrame (){
		initComponents();
		bindEvents();
		startAcceptService();
	}
	
	private void startAcceptService(){
		try {
			echoClient = new MultiCastClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(){
			@Override
			public void run(){
				try {
					while((receiveMsg = echoClient.receive()) != null){
						textArea.append(receiveMsg + "\n");
					}
					int len = textArea.getText().length();
					textArea.setCaretPosition(len);
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}.start();
	}
	

	
	private void sendBtnEvent(ActionEvent event) {
		sendMsg = textField.getText();
		textField.setText("");
		if (sendMsg != "") {
			try {
				echoClient.send(sendMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void uploadBtnEvent(ActionEvent event){
	}
	
	private void downloadBtnEvent(ActionEvent event){
	}
	
	private void exitBtnEvent(ActionEvent event){
		try {
			echoClient.close();
		} catch (Exception e) {
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
		getContentPane().setLayout(new BorderLayout());
		
		mainPanel = new JPanel();
		bottomPanel = new JPanel();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 25, 456, 198);
		textArea = new JTextArea();
		textField = new JTextField();
		textField.setBounds(19, 248, 456, 30);
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
		textField.setColumns(25);
		textField.setFont(new Font("微软雅黑", 1, 18));
		mainPanel.add(textField);
		getContentPane().add(mainPanel);//把mainpanel加入jframe
		
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
		JFrame jFrame = new UdpClientJFrame();
		jFrame.setTitle("chatroom");
		jFrame.setSize(511,414);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
	}
	

}


