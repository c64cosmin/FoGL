package com.stupidrat.tools.sogl;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class SpritePacker {
	public ArrayList<Sprite> sprites;
	public int exportSize;
	private AnimationPreviewRenderer renderer;
	
	public SpritePacker(ArrayList<Sprite> inputSprites, int exportSize) {
		refreshSprites(inputSprites);
		this.exportSize = exportSize;
		
		renderer = new AnimationPreviewRenderer(SpriteHandler.instance.spriteCanvas, SpriteHandler.instance);
		renderer.refreshImage();
	}

	public void refreshSprites(ArrayList<Sprite> inputSprites) {
		sprites = new ArrayList<Sprite>();
		
		for(Sprite sprite : inputSprites) {
			sprites.add(new Sprite(sprite));
		}
	}

	public void pack(){
		ArrayList<Frame> allFrames = new ArrayList<Frame>();
		for(Sprite sprite : sprites) {
			for(Frame frame : sprite.frames) {
				allFrames.add(frame);
			}
		}
		
		int x = 0;
		for(Frame frame : allFrames) {
			frame.box.x = x;
			frame.box.y = 0;
			x+=frame.box.width;
		}
	}
	
	public void draw(Graphics g) {
		refreshSprites(SpriteHandler.instance.sprites.entries);
		pack();
		
		for(int i = 0; i < sprites.size(); i++) {
			Sprite originalSprite = SpriteHandler.instance.sprites.entries.get(i);
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
