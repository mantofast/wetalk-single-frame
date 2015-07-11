package wetalk;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import usermanager.User;
import GUI.ChatFrameOnline;
public class MAIN{
	protected static Logger log = LoggerFactory.getLogger(MAIN.class);
		
	public static void main(String[] args) {		
		ChatFrameOnline.showFrameOnline();		
	}


}
