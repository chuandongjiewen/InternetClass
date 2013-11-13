package CapturePackage;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import jpcap.*;
import jpcap.packet.Packet;



public class CaptureFrame extends JFrame {

	private JPanel contentPane;
	JButton startBtn;
	JButton stopBtn;
	JButton clearBtn;
	JButton exitBtn;
	JTextArea textArea;
	private final JScrollPane scrollPane = new JScrollPane();
	
	JpcapCaptor jpcap;
	Thread jpcapThread;
	PacketReceiver handler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		CaptureFrame frame = new CaptureFrame();
		frame.setVisible(true);
	}
	
	private void bindEvent(){
		startBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					NetworkInterface[] device = JpcapCaptor.getDeviceList();
//					jpcap = JpcapCaptor.openDevice(device[2], 1514, true, 60);
//					jpcap.setFilter("tcp", true);
					jpcap = JcaptureDialog.getJpcap(CaptureFrame.this);
					System.out.println(device.length);
					jpcapThread = new Thread(new Runnable() {
						
						public void run(){
							while(jpcapThread != null){
								jpcap.processPacket(1, handler);
							}
						}
					});
					
					jpcapThread.setPriority(Thread.MIN_PRIORITY);
					jpcapThread.start();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		stopBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jpcapThread != null){
					jpcapThread = null;
				}
				
			}
		});
		
		clearBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CaptureFrame() {
		initComponents();
		handler = new PacketReceiver() {
			
			@Override
			public void receivePacket(Packet packet) {
				String msg=packet.toString();
				
	             if(msg.contains("202.116.192.92")){//只查看和某IP地址有关的.
	                 textArea.append(msg+"\n");
					 try {
					    String  submsg = new String(packet.data,0,packet.data.length,"utf-8");
					    textArea.append("数据部分: \n"+submsg+"\n\n");
					} catch (IOException ex) { }
	             }
			}
		};
		bindEvent();
	}
	
	private void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		startBtn = new JButton("\u5F00\u59CB");
		
		startBtn.setBounds(0, 290, 85, 23);
		contentPane.add(startBtn);
		
		stopBtn = new JButton("\u505C\u6B62");
		stopBtn.setBounds(95, 290, 93, 23);
		contentPane.add(stopBtn);
		
		clearBtn = new JButton("\u6E05\u7A7A");
		clearBtn.setBounds(196, 290, 93, 23);
		contentPane.add(clearBtn);
		
		exitBtn = new JButton("\u9000\u51FA");
		exitBtn.setBounds(421, 290, 93, 23);
		contentPane.add(exitBtn);
		
		textArea = new JTextArea();
		textArea.setBounds(0, 0, 534, 272);
		scrollPane.setBounds(0, 0, 544, 272);
		scrollPane.setViewportView(textArea);
		contentPane.add(scrollPane);
		
		
	}
}
