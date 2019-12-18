package com.stupidrat.tools.sogl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LoopPanel implements KeyListener {
	private JTextArea textArea;
	private SpriteAnimationListArea listArea;

	public LoopPanel() {
		this.textArea = new JTextArea("none");
		textArea.addKeyListener(this);
		
		this.listArea = new SpriteAnimationListArea();
	}
	
	public JPanel getJPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		panel.add(listArea, BorderLayout.CENTER);
		panel.add(textArea, BorderLayout.NORTH);
		
		panel.addMouseListener(listArea);
		return panel;
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
