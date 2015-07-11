package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import usermanager.User;


public class ChatFrameOnline extends JFrame {
	protected static Logger log =
			LoggerFactory.getLogger(ChatFrameOnline.class);
	
	private JPanel contentPane;
	private JTextField textField;
	private JLabel usernameLabel;

	/**
	 * Create the frame.
	 */
	public ChatFrameOnline() {
	
		setBackground(new Color(230, 230, 250));
		setIconImage(Toolkit.getDefaultToolkit().getImage("./icons/title.png"));
		setTitle("WeTalk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 331, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		
		usernameLabel = new JLabel("用户名：");
		usernameLabel.setForeground(Color.BLUE);
		usernameLabel.setFont(new Font("楷体", Font.BOLD, 20));
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					button_action(null);
				}
			}
		});
		textField.setForeground(Color.MAGENTA);
		textField.setFont(new Font("黑体", Font.PLAIN, 20));
		textField.setColumns(10);
		
		JButton loadButton = new JButton("登录");
		loadButton.setForeground(Color.BLUE);
		loadButton.setBackground(Color.GRAY);
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_action(e);
			}
		});
		loadButton.setFont(new Font("楷体", Font.PLAIN, 28));
		
		JLabel backgroundLabel = new JLabel("");
		backgroundLabel.setIcon(new ImageIcon("E:\\Eclipse\\wetalk-single\\icons\\online.jpg"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
						.addComponent(loadButton))
					.addGap(20))
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(backgroundLabel, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(477, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addComponent(backgroundLabel, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addComponent(usernameLabel, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(loadButton)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
	}

	///////////////////////////////////////
	// 上线按钮事件处理代码button_action
	///////////////////////////////////////
	void button_action(ActionEvent e) {
		String myname=textField.getText(); //获得用户名
		if(myname.compareTo("")!=0 ) {
			//创建自己的对象
			String myMAC = getHostIp();
			String myIP = getHostMac();
			User me = new User(myname,null,myMAC,myIP);
			
			//用户上线，显示主窗口
			this.setVisible(false);		
			
			log.info("show main frame.");
			showFrameMain(me);
			
		} else {
			usernameLabel.setText("填写用户名!");
			usernameLabel.setForeground(Color.RED);
		}

	}
	
	//显示上线窗口
	public static void showFrameOnline() {
		try {
			ChatFrameOnline frameonline;
			frameonline = new ChatFrameOnline();
			frameonline.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// 把窗口置于中心
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = frameonline.getSize();
			if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
			}
			frameonline.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height -
			frameSize.height) / 2);
			
			frameonline.setVisible(true);
			log.info("online frame show up.");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}	

	
	//显示主窗口
	protected void showFrameMain(User me) {
		try {
			ChatFrameMain framemain;
			framemain = new ChatFrameMain(me);
			framemain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// 把窗口置于中心
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = framemain.getSize();
			if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
			}
			framemain.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height -
			frameSize.height) / 2);
			
			framemain.setVisible(true);
			framemain.working();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//*************************
	//  内部函数
	//*************************

	//获得本机的IP地址
	protected String getHostIp(){
		String myip = null;
		try {
			InetAddress local = InetAddress.getLocalHost();
			myip = local.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myip;
	}

	//获得本机的MAC地址
	protected String getHostMac(){
		String mymac = null;
		NetworkInterface netInterface = null;
		try {
			netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
			byte[] macAddr = netInterface.getHardwareAddress();
			mymac = new String(macAddr);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mymac;	
	}
}
