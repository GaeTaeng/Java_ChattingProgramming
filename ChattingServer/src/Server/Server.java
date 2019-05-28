package Server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame implements ActionListener{
//�̺�Ʈ ������ �ۼ���
// 1. ���� ��� v
// 2. �͸� Ŭ����
// 3. ���� Ŭ����, �ܺ� Ŭ����
	private JPanel contentPane;
	private JScrollPane scrollPane = new JScrollPane();
	
	private JTextField port_tf = new JTextField();
	private JLabel lblPortNum = new JLabel("��Ʈ ��ȣ");

	private JTextArea textArea = new JTextArea();

	private JButton start_btn = new JButton("��������");
	private JButton stop_btn = new JButton("���� ����");
	
	//Network �ڿ�
	private ServerSocket server_socket;
	private Socket socket;
	private int port;
	private Vector user_vc = new Vector();
	
	Server() {
		init(); // ȭ�����
		start(); // ������
	}
	private void start() {
		start_btn.addActionListener(this);
		stop_btn.addActionListener(this);
	}
	
	private void init() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 412, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane.setBounds(12, 10, 374, 260);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(textArea);
		
		lblPortNum.setBounds(12, 310, 57, 15);
		contentPane.add(lblPortNum);
		
		port_tf.setBounds(82, 307, 304, 21);
		contentPane.add(port_tf);
		port_tf.setColumns(10);
		
		start_btn.setBounds(12, 368, 180, 23);
		contentPane.add(start_btn);
		
		stop_btn.setBounds(204, 368, 182, 23);
		contentPane.add(stop_btn);
		
		this.setVisible(true);
	}
	
	private void Server_start() {
		try {
			server_socket = new ServerSocket(port); // 12345�� ��Ʈ���� 
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(server_socket != null) {
			Connection();
		}
		socket = new Socket();
	}
	private void Connection() {
		
		
		//1���� ������� 1������ �ϸ� ó���� �����ϴ�
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() { // �����忡�� �� ���� ����
				
				
				while(true) {
				try {
					textArea.append("����� ���� ��� ��\n");
					socket = server_socket.accept(); // ����� ���� ���� ���
					textArea.append("����� ����!!!\n");
					
					
					
					UserInfo user = new UserInfo(socket);
					user.start();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				
			
			}
		});
		th.start();
	}
	public static void main(String[] args) {
		new Server();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == start_btn) {
			System.out.println("Start Button Click!");
			port = Integer.parseInt(port_tf.getText().trim());
			Server_start(); // ���� ���� �� 
		}else if(e.getSource() == stop_btn) {
			System.out.println("Stop Button Click!");
		}
	} // �׼� �̺�Ʈ End
	
	class UserInfo extends Thread {
		private OutputStream os;
		private InputStream is;
		private DataOutputStream dos;
		private DataInputStream dis;
		
		private Socket user_socket;
		private String Nickname = "";

		UserInfo() {
		}
		UserInfo(Socket soc) {
			this.user_socket = soc;
			UserNetwork();
		}
		
		private void UserNetwork() { // ��Ʈ��ũ �ڿ� ����
			try {
				is = user_socket.getInputStream();
				dis = new DataInputStream(is);
				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);

				Nickname = dis.readUTF();// ������� �г����� �޴´�.

				textArea.append(Nickname + " : ����� ���� !");
				
				
				// ���� ����ڵ鿡�� ����� ������ �˸�.
				System.out.println("���� ���ӵ� ����� �� : " + user_vc.size());
				
				
				BroadCast("NewUser/"+ Nickname);//�ڽſ��� ���� ����ڸ� �˸�.
				
				for(int i = 0; i < user_vc.size(); i++) {
					UserInfo u = (UserInfo)user_vc.elementAt(i);
					
					send_Message("OldUser/" + u.Nickname);
				}
				
				user_vc.add(this); // ����ڿ��� �˸� �� Vector�� �ڽ��� �߰�.
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run() { // Thread���� ó���� ����.
			
			while(true) {
				try {
					String msg = dis.readUTF();// �޼��� ����
					textArea.append(Nickname+" : ����ڷκ��� ���� �޼��� : " + msg + " \n");
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		private void BroadCast(String str) {
			//�������� ������ ��� �޼����� ��� �������� ����ڿ��� �޼����� ���� �� �ְ� ��.
			for(int i = 0; i < user_vc.size(); i++) {
				// ���� ���ӵ� ����ڿ��� �޼����� ����
				UserInfo u = (UserInfo)user_vc.elementAt(i);
				u.send_Message(str);
				
				
			}//�̿� ������ ��ε��ɽ�Ʈ�� ���� ���
		}
		
		private void send_Message(String str) {
			//���ڿ��� �޾Ƽ� ���� ����� OS�� �̿��Ͽ� �������� �����͸� �Ѱ���.
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
