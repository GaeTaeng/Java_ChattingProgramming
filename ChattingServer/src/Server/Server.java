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
//이벤트 리스너 작성법
// 1. 직접 상속 v
// 2. 익명 클래스
// 3. 내부 클래스, 외부 클래스
	private JPanel contentPane;
	private JScrollPane scrollPane = new JScrollPane();
	
	private JTextField port_tf = new JTextField();
	private JLabel lblPortNum = new JLabel("포트 번호");

	private JTextArea textArea = new JTextArea();

	private JButton start_btn = new JButton("서버실행");
	private JButton stop_btn = new JButton("서버 중지");
	
	//Network 자원
	private ServerSocket server_socket;
	private Socket socket;
	private int port;
	private Vector user_vc = new Vector();
	
	Server() {
		init(); // 화면생성
		start(); // 리스너
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
			server_socket = new ServerSocket(port); // 12345번 포트접속 
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(server_socket != null) {
			Connection();
		}
		socket = new Socket();
	}
	private void Connection() {
		
		
		//1가지 쓰레드는 1가지의 일만 처리가 가능하다
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() { // 스레드에서 할 일을 기재
				
				
				while(true) {
				try {
					textArea.append("사용자 접속 대기 중\n");
					socket = server_socket.accept(); // 사용자 접속 무한 대기
					textArea.append("사용자 접속!!!\n");
					
					
					
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
			Server_start(); // 소켓 생성 및 
		}else if(e.getSource() == stop_btn) {
			System.out.println("Stop Button Click!");
		}
	} // 액션 이벤트 End
	
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
		
		private void UserNetwork() { // 네트워크 자원 설정
			try {
				is = user_socket.getInputStream();
				dis = new DataInputStream(is);
				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);

				Nickname = dis.readUTF();// 사용자의 닉네임을 받는다.

				textArea.append(Nickname + " : 사용자 접속 !");
				
				
				// 기존 사용자들에게 사용자 접속을 알림.
				System.out.println("현재 접속된 사용자 수 : " + user_vc.size());
				
				
				BroadCast("NewUser/"+ Nickname);//자신에게 기존 사용자를 알림.
				
				for(int i = 0; i < user_vc.size(); i++) {
					UserInfo u = (UserInfo)user_vc.elementAt(i);
					
					send_Message("OldUser/" + u.Nickname);
				}
				
				user_vc.add(this); // 사용자에게 알린 후 Vector에 자신을 추가.
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run() { // Thread에서 처리할 내용.
			
			while(true) {
				try {
					String msg = dis.readUTF();// 메세지 수신
					textArea.append(Nickname+" : 사용자로부터 들어온 메세지 : " + msg + " \n");
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		private void BroadCast(String str) {
			//서버에서 보내는 모든 메세지를 모든 접속중인 사용자에게 메세지를 보낼 수 있게 함.
			for(int i = 0; i < user_vc.size(); i++) {
				// 현재 접속된 사용자에게 메세지를 보냄
				UserInfo u = (UserInfo)user_vc.elementAt(i);
				u.send_Message(str);
				
				
			}//이와 같은게 브로드케스트와 같은 방식
		}
		
		private void send_Message(String str) {
			//문자열을 받아서 현재 연결된 OS를 이용하여 유저에게 데이터를 넘겨줌.
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
