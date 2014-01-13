package HttpClient;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.FormSubmitEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class UrlConnectionFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton openBtn;
	private JEditorPane editorPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UrlConnectionFrame frame = new UrlConnectionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private BufferedReader bReader = null;
	private void bindEvent(){
		openBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String address = textField.getText();
					URL url = new URL(address);
					InputStream stream = url.openStream();
					bReader = new BufferedReader(new InputStreamReader(stream,"utf-8"));
					editorPane.setPage(url);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				

			}
		});
		
		editorPane.addHyperlinkListener(new HyperlinkListener() {
			
			@Override
			public void hyperlinkUpdate(HyperlinkEvent evt) {
				try{
				      if (evt.getClass() == FormSubmitEvent.class) {  //�����ύ���¼�
				        FormSubmitEvent fevt = (FormSubmitEvent)evt;
				        URL url=fevt.getURL(); //���URL
				        String method=fevt.getMethod().toString(); //�������ʽ
				        String data=fevt.getData(); //��ñ�����

				        if(method.equals("GET")){  //���ΪGET����ʽ
				           editorPane.setPage(url.toString()+"?"+data);
				           textField.setText(url.toString()+"?"+data); //���ı�����Ϊ�û�ѡ��ĳ�������
				        }else if(method.equals("POST")){  //���ΪPOST����ʽ

				          URLConnection uc=url.openConnection();
				           //����HTTP��Ӧ����
				           uc.setDoOutput(true);
				           OutputStreamWriter out=new OutputStreamWriter(uc.getOutputStream());
				           out.write(data);
				           out.close();

				           //����HTTP��Ӧ����
				           InputStream in=uc.getInputStream();
				           ByteArrayOutputStream buffer=new ByteArrayOutputStream();
				           byte[] buff=new byte[1024];
				           int len=-1;

				           while((len=in.read(buff))!=-1){
				             buffer.write(buff,0,len);
				           }
				           in.close();

				           editorPane.setText(new String(buffer.toByteArray()));
				           textField.setText(url.toString()); //���ı�����Ϊ�û�ѡ��ĳ�������
				        }
				        System.out.println(fevt.getData()+"|"+fevt.getMethod()+"|"+fevt.getURL());
				      }else if(evt.getEventType()==HyperlinkEvent.EventType.ACTIVATED){
				        //�����û�ѡ��ĳ�������
				        editorPane.setPage(evt.getURL());
				        textField.setText(evt.getURL().toString()); //���ı�����Ϊ�û�ѡ��ĳ�������
				      } 

				    }catch(Exception e){
				       editorPane.setText(evt.getURL().toString());
				    }
				
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UrlConnectionFrame() {
		initComponents();
		bindEvent();
		
	}
	
	private void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 853, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 10, 708, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		openBtn = new JButton("\u6253\u5F00");
		openBtn.setBounds(728, 9, 99, 23);
		contentPane.add(openBtn);
		
		editorPane = new JEditorPane();
		editorPane.setBounds(10, 52, 806, 479);
		contentPane.add(editorPane);
	}
	
	
	
}
