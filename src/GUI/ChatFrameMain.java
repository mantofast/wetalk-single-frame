package GUI;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import usermanager.User;


public class ChatFrameMain extends JFrame {
	protected static Logger log = 
			LoggerFactory.getLogger(ChatFrameOnline.class);
	
	User ME;
	
	private JPanel contentPane;
	private JTree usertree;

	private DefaultMutableTreeNode top;
	private DefaultMutableTreeNode nodeUser;
	
	DefaultMutableTreeNode NodeChosen = null;	//被选中的节点
	
	/**
	 * Create the frame.
	 */
	public ChatFrameMain(User me){
		this.ME = me;		
		
		setTitle("wetalk");
		setIconImage(Toolkit.getDefaultToolkit().getImage("./icons/title.png"));
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 279, 639);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);		
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
		);
		
		usertreeInit();
		usertree = new JTree(top);
		scrollPane.setViewportView(usertree);
		usertree.setCellRenderer(new NewDefaultTreeCellRenderer());
		usertree.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() > 0){
					nodeClicked(e);
				}	
			}
		});
		usertree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
            	DefaultMutableTreeNode node=(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
                if(node.isLeaf()){                	
                	NodeChosen = node;
                }
            }
        });

		contentPane.setLayout(gl_contentPane);
	}
	
	//处理用户操作	
	protected void nodeClicked(MouseEvent e){
		if(e.getClickCount() > 0 && NodeChosen != null){
			String nodestring = NodeChosen.getUserObject().toString();
			if(nodestring == "用户列表" || nodestring == "用户"){
				usertree.clearSelection();
				return;
			}
						
			User user = (User)NodeChosen.getUserObject();
			switch(e.getClickCount()){
			case 0:
				break;
			case 1:
				break;
			case 2:
				startchatting(user);
				break;
			default:					
				break;
			}
				
			usertree.clearSelection();		
		}
			
		return;
	}
	
	///////////////////
	//会话
	//////////////////
	protected void startchatting(User peer){
		Thread chatter = new chatting(peer);
		chatter.run();
		
	}
	protected class chatting extends Thread{
		User peer;
		
		chatting(User p){
			this.peer = p;
		}
		
		public void run(){
			showFrameMain(peer);
		}
		
	}
	
	//显示会话窗口
	protected void showFrameMain(User peer) {
		try {
			ChatFrameChat framechat;
			framechat = new ChatFrameChat(ME, peer);
			framechat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			// 把窗口置于中心
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = framechat.getSize();
			if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
			}
			framechat.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height -
			frameSize.height) / 2);
			
			framechat.setVisible(true);
		//	framemain.working();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//////////////////
	//维护用户列表
	//////////////////
	public void working(){
		Thread updater = new Updater();
		updater.run();
		return;
	}
	//TODO
	protected class Updater extends Thread{
		public void run(){
			//测试用户
			nodeUser.add(new DefaultMutableTreeNode(new User("小强","强哥","as:Sa:SA:sA:ss:As","192.168.1.2")));
			//while(true){
			
			//}
		}
	}
	
	
	////////////////
	//一些内部方法
	////////////////
	//初始化关系树
	protected void usertreeInit(){
		top = new DefaultMutableTreeNode("用户列表"); 		
		nodeUser = new DefaultMutableTreeNode("用户");         

		top.add(new DefaultMutableTreeNode(ME));
        top.add(nodeUser);       
        return;
	}
	
	
	//修改图标
	protected class NewDefaultTreeCellRenderer extends DefaultTreeCellRenderer{  	 
	    private static final long   serialVersionUID    = 1L;  
	  
	    // 重写父类DefaultTreeCellRenderer的方法     
	    public Component getTreeCellRendererComponent(JTree tree, Object value,  
	            boolean sel, boolean expanded, boolean leaf, int row,  
	            boolean hasFocus)  
	    {  	  
	        //执行父类原型操作  
	        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,  
	                row, hasFocus);  
	  
	        if (sel)  
	        {  
	            setForeground(getTextSelectionColor());  
	        }  
	        else  
	        {  
	            setForeground(getTextNonSelectionColor());  
	        } 

        
	       	switch(value.toString()){
	       	case "用户列表":
		       	setText("用户列表");
	       		this.setIcon(new ImageIcon("./icons/allusers24.png")); 
	      		break;
	      	case "用户":
		       	setText("用户");
	       		this.setIcon(new ImageIcon("./icons/users_green24.png"));
	       		break;	        			        	
	       	default:
		        DefaultMutableTreeNode node=(DefaultMutableTreeNode)value;
		        User user = (User)node.getUserObject();
		       	setText(user.getRemark()==null? user.getName() : user.getRemark());
	       		this.setIcon(new ImageIcon("./icons/user24.png"));  	        	
	       	}
	     return this;
	      
	    }
	}

}
