package com.stupidrat.tools.sogl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpriteAnimationListArea extends JPanel implements MouseListener {
	private int rowWidth = 15;
	
	public ArrayList<Sprite> entries;

	public SpriteAnimationListArea() {
		this.setVisible(true);
		
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
    	g.setColor(Color.BLUE);
    	for(Sprite entry : entries) {
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
		// TODO Auto-generated method stub
		
	}


	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void addNewEntry(String text) {
		Sprite entry = new Sprite(text);
		entries.add(entry);
		
		sort();
		
		this.repaint();
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
}
