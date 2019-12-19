package com.stupidrat.tools.sogl;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LoopPanel implements KeyListener {
	private JTextArea textArea;
	public SpriteAnimationListArea listArea;
	private SpriteHandler handler;

	public LoopPanel(SpriteHandler parent) {
		this.handler = parent;
		this.textArea = new JTextArea("none");
		textArea.addKeyListener(this);
		
		this.listArea = new SpriteAnimationListArea(parent, textArea);
		SpriteHandler.instance.sheet.sprites = this.listArea;
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
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			Sprite newSprite = new Sprite(textArea.getText());
			
			for(Frame frame : this.listArea.getSelectedSprite().frames)
				newSprite.frames.add(new Frame(frame));
			
			listArea.addNewEntry(newSprite);
			this.handler.resetFocus();
		}
		if(e.getKeyCode() == KeyEvent.VK_DELETE) {
			this.listArea.removeSelected();
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
