package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import usermanager.User;

public class ChatFrameChat extends JFrame {
	User ME;
	User PEER;

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;

	/**
	 * Create the frame.
	 */
	public ChatFrameChat(User me, User peer) {
		ME = me;
		PEER = peer;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("./icons/title.png"));	
		setTitle(peer.getRemark()==null? peer.getName() : peer.getRemark());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 509, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		JLabel backgroundLabel = new JLabel("");
		backgroundLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		backgroundLabel.setForeground(new Color(102, 153, 153));
		backgroundLabel.setBounds(0, 0, 483, 431);
		layeredPane.add(backgroundLabel);
		
		JButton sendButton = new JButton("发送");
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendButtenEvent();
			}
		});
		sendButton.setFont(new Font("宋体", Font.PLAIN, 16));
		sendButton.setBounds(390, 383, 83, 38);
		layeredPane.add(sendButton);
		
		textField = new JTextField();
		textField.setFont(new Font("宋体", Font.PLAIN, 18));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					sendButtenEvent();
				} else if(e.getKeyCode()==KeyEvent.VK_ENTER){
					
				}
			}
		});
		textField.setForeground(new Color(153, 0, 0));
		textField.setBackground(new Color(153, 153, 153));
		textField.setBounds(10, 352, 370, 69);
		layeredPane.add(textField);
		textField.setColumns(10);
		textField.setHorizontalAlignment(JTextField.LEFT);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 370, 332);
		layeredPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setBackground(new Color(204, 204, 204));
		textArea.setFont(new Font("宋体", Font.PLAIN, 20));
		textArea.setForeground(new Color(0, 0, 102));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
	}
	
	protected void sendButtenEvent(){
		String payload = textField.getText(); //获得用户名
		if(payload.compareTo("")!=0 ) {
			textArea.append(ME.getName()+":"+payload + '\n');
			textField.setText(null);
			
			//TODO 发送给对端
		}
		
	}
	
	
}
