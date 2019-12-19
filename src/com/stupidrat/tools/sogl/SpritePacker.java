package com.stupidrat.tools.sogl;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SpritePacker {
	public ArrayList<Sprite> sprites;

	private AnimationPreviewRenderer renderer;

	public SpritePacker() {

		renderer = new AnimationPreviewRenderer(SpriteHandler.instance.spriteCanvas, SpriteHandler.instance);
		renderer.refreshImage();
	}

	public void refreshSprites(ArrayList<Sprite> inputSprites) {
		sprites = new ArrayList<Sprite>();

		for (Sprite sprite : inputSprites) {
			sprites.add(new Sprite(sprite));
		}
	}

	public void pack(int exportSize) {
		ArrayList<Frame> allFrames = new ArrayList<Frame>();
		ArrayList<Frame> addFrames = new ArrayList<Frame>();
		for (Sprite sprite : sprites) {
			for (Frame frame : sprite.frames) {
				allFrames.add(frame);
			}
		}

		Collections.sort(allFrames, new Comparator<Frame>() {
			public int compare(Frame f1, Frame f2) {
				int area1 = f1.box.width * f1.box.height * f1.box.height;
				int area2 = f2.box.width * f2.box.height * f2.box.height;
				return area2 - area1;
			}
		});

		for (Frame frame : allFrames) {
			int x = 0;
			int y = 0;

			boolean intersects = true;

			// find empty spot
			while (intersects) {
				intersects = false;
				frame.box.x = x;
				frame.box.y = y;

				Frame collision = null;

				// find next collision
				for (Frame testFrame : addFrames) {
					if (frame != testFrame) {
						if (frame.box.intersects(testFrame.box)) {
							collision = testFrame;
							break;
						}
					}
				}

				if (collision != null) {
					intersects = true;
					x += collision.box.width;
				}

				if (x + frame.box.width >= exportSize) {
					intersects = true;
					x = 0;
					y++;
				}
			}

			frame.box.x = x;
			frame.box.y = y;
			addFrames.add(frame);
		}
	}

	public void draw(Graphics g, SpriteSheet sheet, int exportSize) {
		refreshSprites(sheet.sprites.entries);
		renderer.refreshImage();
		pack(exportSize);

		for (int i = 0; i < sprites.size(); i++) {
			Sprite originalSprite = sheet.sprites.entries.get(i);
			Sprite newSprite = sprites.get(i);
			this.draw(g, originalSprite, newSprite);
		}
	}

	private void draw(Graphics g, Sprite originalSprite, Sprite newSprite) {
		for (int i = 0; i < originalSprite.frames.size(); i++) {
			Frame originalFrame = originalSprite.frames.get(i);
			Frame newFrame = newSprite.frames.get(i);

			renderer.paint(g, originalFrame, newFrame);
		}
	}
}
