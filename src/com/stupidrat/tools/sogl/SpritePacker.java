package com.stupidrat.tools.sogl;

import java.awt.Graphics;
import java.util.ArrayList;

public class SpritePacker {
	public ArrayList<Sprite> sprites;
	
	private AnimationPreviewRenderer renderer;
	
	public SpritePacker() {

    	renderer = new AnimationPreviewRenderer(SpriteHandler.instance.spriteCanvas, SpriteHandler.instance);
		renderer.refreshImage();
	}

	public void refreshSprites(ArrayList<Sprite> inputSprites) {
		sprites = new ArrayList<Sprite>();
		
		for(Sprite sprite : inputSprites) {
			sprites.add(new Sprite(sprite));
		}
	}

	public void pack(int exportSize){
		ArrayList<Frame> allFrames = new ArrayList<Frame>();
		for(Sprite sprite : sprites) {
			for(Frame frame : sprite.frames) {
				allFrames.add(frame);
			}
		}
		
		int x = 0;
		int y=0;
		for(Frame frame : allFrames) {
			int rightSide = x + frame.box.width;
			if(rightSide >= exportSize) {
				x=0;
				y+=frame.box.height;
			}
			frame.box.x = x;
			frame.box.y = y;
			x+=frame.box.width;
		}
	}
	
	public void draw(Graphics g, SpriteSheet sheet, int exportSize) {
		refreshSprites(sheet.sprites.entries);
		renderer.refreshImage();
		pack(exportSize);
		
		for(int i = 0; i < sprites.size(); i++) {
			Sprite originalSprite = sheet.sprites.entries.get(i);
			Sprite newSprite = sprites.get(i);
			this.draw(g, originalSprite, newSprite);
		}
	}
	
	private void draw(Graphics g, Sprite originalSprite, Sprite newSprite) {
		for(int i = 0; i < originalSprite.frames.size(); i++) {
			Frame originalFrame = originalSprite.frames.get(i);
			Frame newFrame = newSprite.frames.get(i);
			
			renderer.paint(g, originalFrame, newFrame);
		}
	}
}
