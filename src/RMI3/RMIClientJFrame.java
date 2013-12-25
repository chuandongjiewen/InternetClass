package RMI3;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import org.omg.CORBA.PRIVATE_MEMBER;

public class RMIClientJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private JButton sendBtn;
	private JButton exitBtn;
	
	RMIClientView client;
	RMIServerService service;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RMIClientJFrame frame = new RMIClientJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RMIClientJFrame() {
		initComponents();
		initData();
		bindSendBtnEvent();
		bindExitBtnEvent();
	}
	
	private void initData(){
		try {
			 client = new RMIClientViewImpl(this);
			 
//			 String url = "rmi://192.168.233.15:1099/";//rmi://222.201.101.15:1099/
			 String url = "rmi://222.201.101.15:1099/";//rmi://222.201.101.15:1099/
			 service = (RMIServerService)Naming.lookup(url +"RMIService3");
			 service.addClientToOnLine(client, "20111003632 Òü´¨¶«");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void bindSendBtnEvent(){
		sendBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					service.sendMessageToServer("20111003632 Òü´¨¶«:"+textField.getText());
					textField.setText("");
					textArea.setCaretPosition(textArea.getText().length());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void bindExitBtnEvent(){
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					service.delClientFromOnLine(client);
				} catch (Exception e2) {
					
				}
				System.exit(0);
			}
		});
	}
	
	public void appendMessageToArea(String msg){
		textArea.append(msg);
		textArea.setCaretPosition(textArea.getText().length());
	}
	
	private void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		textArea = new JTextArea();
		
		textField = new JTextField();
		textField.setColumns(10);
		
		sendBtn = new JButton("\u53D1\u9001");
		
		exitBtn = new JButton("\u9000\u51FA");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(sendBtn)
							.addGap(18)
							.addComponent(exitBtn)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(sendBtn)
						.addComponent(exitBtn)))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
