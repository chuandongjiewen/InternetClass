package MailSend;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Component;

public class test extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
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
	public test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 506, 389);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(294, 328, 93, 23);
		contentPane.add(btnNewButton);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		textArea.setBounds(10, 47, 451, 159);
		contentPane.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 451, 159);
		scrollPane.add(textArea);
		contentPane.add(scrollPane);
	}
}
