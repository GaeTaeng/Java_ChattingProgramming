package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame implements ActionListener{
	// �α��� - Sub Frame ���� ����.
	// ä��â - Main Frame

	JButton login_btn = new JButton("�� ��");
	
	//
	
	//Login GUI Value;
	private JPanel LoginPane;
	private JTextField ServerIP_tf;
	private JTextField ServerPort_tf;
	private JTextField ID_tf;
	private JFrame Login_GUI = new JFrame();
	
	// Main GUI
	private JPanel contentPane;
	private JTextField textField;
	
	
	private JButton notesend_btn = new JButton("�� �� �� �� ��");
	private JButton joinroom_btn = new JButton("ä�ù� ����");
	private JButton createroom_btn = new JButton("�� �����");
	private JButton send_btn = new JButton("�� ��");
	
	
	private JList list = new JList(); // ������ ����Ʈ
	private JList list_1 = new JList(); // ��ü �� ��� ����Ʈ
	Client() {
		Login_init();// �α���â ���� �޼ҵ�
		Main_init();
		start();
		
	}
	
	public void start() {
		login_btn.addActionListener(this);
	}
	private void Main_init() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 812, 574);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel label = new JLabel("�� ü �� �� ��");
		label.setBounds(12, 10, 140, 15);
		contentPane.add(label);
		
		list.setBounds(12, 28, 140, 173);
		contentPane.add(list);
		
		notesend_btn.setBounds(12, 211, 140, 23);
		contentPane.add(notesend_btn);
		
		JLabel label_1 = new JLabel("ä �� �� �� ��");
		label_1.setBounds(12, 244, 140, 15);
		contentPane.add(label_1);
		
		list_1.setBounds(12, 265, 140, 173);
		contentPane.add(list_1);
		
		joinroom_btn.setBounds(12, 447, 140, 23);
		contentPane.add(joinroom_btn);
		
		createroom_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		createroom_btn.setBounds(12, 473, 140, 23);
		contentPane.add(createroom_btn);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(164, 6, 620, 438);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(164, 474, 502, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		send_btn.setBounds(678, 473, 106, 23);
		contentPane.add(send_btn);
		this.setVisible(true);
	}
	
	private void Login_init() {

		Login_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login_GUI.setBounds(100, 100, 281, 280);
		LoginPane = new JPanel();
		LoginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Login_GUI.setContentPane(LoginPane);
		LoginPane.setLayout(null);
		
		JLabel lblServerIp = new JLabel("Server IP");
		lblServerIp.setBounds(31, 35, 57, 15);
		LoginPane.add(lblServerIp);
		
		JLabel lblServerPort = new JLabel("Server Port");
		lblServerPort.setBounds(31, 79, 62, 15);
		LoginPane.add(lblServerPort);
		
		ServerIP_tf = new JTextField();
		ServerIP_tf.setBounds(116, 32, 116, 21);
		LoginPane.add(ServerIP_tf);
		ServerIP_tf.setColumns(10);
		
		ServerPort_tf = new JTextField();
		ServerPort_tf.setBounds(116, 76, 116, 21);
		LoginPane.add(ServerPort_tf);
		ServerPort_tf.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(31, 121, 57, 15);
		LoginPane.add(lblNewLabel);
		
		ID_tf = new JTextField();
		ID_tf.setBounds(116, 121, 116, 21);
		LoginPane.add(ID_tf);
		ID_tf.setColumns(10);
		
		login_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		login_btn.setBounds(12, 177, 241, 32);
		LoginPane.add(login_btn);
		
		Login_GUI.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Client();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == login_btn) {
			System.out.println("Login Button Click!");
		}
	}
}
