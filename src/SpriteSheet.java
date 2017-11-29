import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SpriteSheet {
    public static void saveSheet(String filename){
        File f = new File(filename+".sheet");
        try {
            FileWriter out = new FileWriter(f);
            SpriteHandler h = SpriteHandler.instance;
            out.write(h.spritesImages.size()+"\n");
            for(int i=0;i<h.spritesImages.size();i++){
                String filepath = h.getImagesCombo().getItemAt(i);
                out.write("\""+filepath+"\"\n");
            }
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void openSheet(String filename) {
        System.out.println("load   :"+filename);
    }
}
