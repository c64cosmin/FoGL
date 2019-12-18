package com.stupidrat.tools.sogl;

import java.awt.Graphics;
import java.awt.Image;

public class AnimationPreviewRenderer {
    private SpriteHandler handler;
    private SpriteCanvas canvas;

    public int x;
    public int y;

    public AnimationPreviewRenderer(SpriteCanvas canvas, SpriteHandler handler) {
        this.handler = handler;
        this.canvas = canvas;
        x = y = 0;
    }

    public void paint(Graphics g) {
        int s = handler.zoom;
        int w = 0;
        int h = 0;
        int px = 0;
        int py = 0;
        int cx = 0;
        int cy = 0;
		Sprite sprite = handler.sprites.getSelectedSprite();;
        if (sprite != null) {
            Frame frame = null;
            int i = handler.selectedFrame - 1;
            if (i < sprite.frames.size()) {
                frame = sprite.frames.get(i);
                w = frame.box.width;
                h = frame.box.height;
                px = frame.box.x;
                py = frame.box.y;
                cx = frame.center.x;
                cy = frame.center.y;
            }
            if (frame != null) {
                Image img = handler.spritesImages.get(0);
                if (img != null) {
                    g.drawImage(img, x - cx * s, y - cy * s, x + (w - cx) * s, y + (h - cy) * s, px, py, px + w, py + h,
                            null);
                }
            }
        }
    }

    public void setPosition(int mouseX, int mouseY) {
        this.x = mouseX;
        this.y = mouseY;
    }
}
