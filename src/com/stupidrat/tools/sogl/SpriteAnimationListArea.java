package com.stupidrat.tools.sogl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SpriteAnimationListArea extends JPanel implements MouseListener {
	private int rowWidth = 15;
	
	public ArrayList<Sprite> entries;
	private int selected;

	private SpriteHandler handler;	
	private JTextArea textArea;

	public SpriteAnimationListArea(SpriteHandler parent, JTextArea textArea) {
		this.handler = parent;
		this.handler.setAnimationListArea(this);
		this.textArea = textArea;
		
		entries = new ArrayList<Sprite>();
	}	

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
    	g.setColor(Color.WHITE);
    	g.fillRect(0, 0, this.getWidth(), this.getHeight());
    	
    	int y=rowWidth*2;
    	for(Sprite entry : entries) {
        	g.setColor(Color.BLUE);
        	if(entry == this.getSelectedSprite())
        		g.setColor(Color.RED);
        	
    		g.drawString(entry.getName(), 0, y);
        	y+=rowWidth;
    	}
    }


	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void mousePressed(MouseEvent e) {
		System.out.println(e.getY());
		int y = (e.getY()-5) / rowWidth - 2;
		
		this.selected = -1;
		if(y >= 0) {
			this.selected = y;
		}
		
		Sprite selected = getSelectedSprite();
		textArea.setText("none");
		if(selected != null) {
			textArea.setText(selected.getName());
		}
		
		handler.redrawAll();
	}


	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void addNewEntry(Sprite newSprite) {
		entries.add(newSprite);
		
		sort();
		
		handler.redrawAll();
	}

	private void sort() {
		for(int t=0;t<entries.size();t++)
		for(int i=t;i<entries.size();i++) {
			String strA = entries.get(t).getName();
			String strB = entries.get(i).getName();
			if(strA.compareTo(strB) > 0) {
				Sprite entry = entries.get(t);
				entries.set(t, entries.get(i));
				entries.set(i, entry);
			}
		}
	}

	public Sprite getSelectedSprite() {
		if(selected != -1 && selected < entries.size())
			return entries.get(selected);
		return null;
	}
}
