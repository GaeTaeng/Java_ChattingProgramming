package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame implements ActionListener{
	// 로그인 - Sub Frame 으로 구성.
	// 채팅창 - Main Frame

	JButton login_btn = new JButton("접 속");
	
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
	
	
	private JButton notesend_btn = new JButton("쪽 지 보 내 기");
	private JButton joinroom_btn = new JButton("채팅방 참여");
	private JButton createroom_btn = new JButton("방 만들기");
	private JButton send_btn = new JButton("전 송");
	
	
	private JList User_list = new JList(); // 접속자 리스트
	private JList Room_list = new JList(); // 전체 방 목록 리스트
	

	private JTextArea Chat_area = new JTextArea(); // 채팅창 변수
	
	//Network Resource
	private Socket socket;
	private String ip;
	private int port;
	
	private String id;
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	//그 외 변수들
	Vector user_list = new Vector();
	Vector room_list = new Vector();
	StringTokenizer st;
	Client() {
		Login_init();// 로그인창 구성 메소드
		Main_init();
		start();
		
	}
	
	public void start() {
		login_btn.addActionListener(this);
		notesend_btn.addActionListener(this);
		joinroom_btn.addActionListener(this);
		createroom_btn.addActionListener(this);
		send_btn.addActionListener(this);
	}
	private void Main_init() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 812, 574);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel label = new JLabel("전 체 접 속 자");
		label.setBounds(12, 10, 140, 15);
		contentPane.add(label);
		
		User_list.setBounds(12, 28, 140, 173);
		contentPane.add(User_list);
		
		notesend_btn.setBounds(12, 211, 140, 23);
		contentPane.add(notesend_btn);
		
		JLabel label_1 = new JLabel("채 팅 방 목 록");
		label_1.setBounds(12, 244, 140, 15);
		contentPane.add(label_1);
		
		Room_list.setBounds(12, 265, 140, 173);
		contentPane.add(Room_list);
		
		joinroom_btn.setBounds(12, 447, 140, 23);
		contentPane.add(joinroom_btn);
		
		createroom_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		createroom_btn.setBounds(12, 473, 140, 23);
		contentPane.add(createroom_btn);
		
		Chat_area.setBounds(164, 6, 620, 438);
		contentPane.add(Chat_area);
		
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
	
	private void Network() {
		
		try {
			socket = new Socket(ip, port);
			if(socket != null) { // 정상적인 소켓 연결 완료
				Connection();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void Connection() { // 실제적인 메소드 연결
		try {
		is = socket.getInputStream();
		dis = new DataInputStream(is);
		
		os = socket.getOutputStream();
		dos = new DataOutputStream(os);
		}catch(IOException e) {
			e.printStackTrace();
		} // Stream 설정 끝
		
		//처음 접속시 ID전송
		send_message(id);
		
		
		// User_list에 사용자 추가.
		user_list.add(id);
		User_list.setListData(user_list);
		
		
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String msg = dis.readUTF(); // 메세지 수신
					System.out.println("서버로부터 수신된 메세지 : " + msg);
					
					inmessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				} // 메세지 수신
			}
			
		});
		th.start();
	}
	
	private void inmessage(String str) { // 서버로부터 들어오는 모든 메세지
		st = new StringTokenizer(str, "/");
		
		String protocol = st.nextToken();
		String Message = st.nextToken();
		
		System.out.println("PROTOCOL : " + protocol);
		System.out.println("Message : " + Message);
		
		if(protocol.equals("NewUser")) { // 새로운 접속자가 입장시
			user_list.add(Message);
			User_list.setListData(user_list);
		}else if(protocol.equals("OldUser")) {
			user_list.addElement(Message);;
			User_list.setListData(user_list);
		}
	}
	
	private void send_message(String str) { // 서버에게 메세지를 보냄.
		try {
			dos.writeUTF(str);
		} catch (IOException e) { //에러처리
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Client();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == login_btn) {
			System.out.println("Login Button Click!");
			ip = ServerIP_tf.getText().trim();
			port = Integer.parseInt(ServerPort_tf.getText().trim());
			
			id = ID_tf.getText().trim(); // id를 받아온다.
			Network();
		}else if(e.getSource() == notesend_btn) {
			System.out.println("NoteSend Button Click!");
		}else if(e.getSource() == joinroom_btn) {
			System.out.println("joinroom_btn Click!");
		}else if(e.getSource() == createroom_btn) {
			System.out.println("createroom_btn Click!");
		}else if(e.getSource() == send_btn) {
			send_message(textField.getText());
			System.out.println("send_btn Click!");
		}
	}
}
