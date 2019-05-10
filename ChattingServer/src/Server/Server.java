package Server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	public static void main(String[] args) {
		new Server();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == start_btn) {
			System.out.println("Start Button Click!");
		}else if(e.getSource() == stop_btn) {
			System.out.println("Stop Button Click!");
		}
	}

}
