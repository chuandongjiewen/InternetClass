package ThreadServer;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginJFrame extends JFrame{
	
	private JLabel label;
	private JTextField usernameField;
	private JPanel mainPanel;
	private JButton submitBtn;
	
	public LoginJFrame(){
		initComponents();
		bindEvent();
	}
	
	private void submitBtnEvent(ActionEvent event){
		String username = usernameField.getText();
		if(username != "" && !username.equals("")){
			this.setVisible(false);
			ChatRoomJFrame jFrame = new ChatRoomJFrame();
			jFrame.setUsername(username);
			jFrame.setTitle("chatroom");
			jFrame.setSize(511,414);
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setLocationRelativeTo(null);
			jFrame.setVisible(true);
		}
	}
	
	private void bindEvent(){
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				submitBtnEvent(event);
			}
		});
	}
	
	private void initComponents(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		label = new JLabel("用户名");
		
		usernameField = new JTextField();
		usernameField.setColumns(15);
		
		submitBtn = new JButton("确定");
		
		mainPanel.add(label);
		mainPanel.add(usernameField);
		mainPanel.add(submitBtn);
		
		this.add(mainPanel);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame index = new LoginJFrame();
		index.setTitle("chatroom");
		index.setSize(300,200);
		index.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		index.setLocationRelativeTo(null);
		index.setVisible(true);
	}
}
