package PingPong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Frame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Panel panel;
	
	Frame(){
		panel = new Panel();
		this.add(panel);
		this.setTitle("Ping Pong");
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

}