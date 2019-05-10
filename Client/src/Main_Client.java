import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main_Client extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Client frame = new Main_Client();
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
	public Main_Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 812, 574);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\uC804 \uCCB4 \uC811 \uC18D \uC790");
		label.setBounds(12, 10, 140, 15);
		contentPane.add(label);
		
		JList list = new JList();
		list.setBounds(12, 28, 140, 173);
		contentPane.add(list);
		
		JButton btnMessage = new JButton("\uCABD\uC9C0\uBCF4\uB0B4\uAE30");
		btnMessage.setBounds(12, 211, 140, 23);
		contentPane.add(btnMessage);
		
		JLabel label_1 = new JLabel("\uCC44\uD305\uBC29 \uBAA9\uB85D");
		label_1.setBounds(12, 244, 140, 15);
		contentPane.add(label_1);
		
		JList list_1 = new JList();
		list_1.setBounds(12, 265, 140, 173);
		contentPane.add(list_1);
		
		JButton button = new JButton("\uCC44\uD305\uBC29\uCC38\uC5EC");
		button.setBounds(12, 447, 140, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\uBC29 \uB9CC\uB4E4\uAE30");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_1.setBounds(12, 473, 140, 23);
		contentPane.add(button_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(164, 6, 620, 438);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(164, 474, 502, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\uC804\uC1A1");
		btnNewButton.setBounds(678, 473, 106, 23);
		contentPane.add(btnNewButton);
	}
}
