import java.awt.Rectangle;
import java.util.ArrayList;

public class Sprite {
    public ArrayList<Frame> frames;
    private String name;

    @Override
    public String toString(){
        return name;
    }

    public Sprite(String name){
        this.name = name;
    }

    public void addFrame(Rectangle box){
        frames.add(new Frame(box));
    }

    public int getNumberOfFrames() {
        return frames.size();
    }

    public void delFrame(int selectedFrame) {
        frames.remove(selectedFrame);
    }
}
