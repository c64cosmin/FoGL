import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JoGL {
	public static void main(String[] args){
		JFrame win = new JFrame();
		AllFonts.instance.setWindow(win);
		
		//left side
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(10,1));
		leftPanel.add(new JLabel("Font"));
		leftPanel.add(AllFonts.instance.getFonts());
		leftPanel.add(new JLabel("Output string mapping"));
		leftPanel.add(AllFonts.instance.getFontsString());
		leftPanel.add(new JLabel("Font size"));
		leftPanel.add(AllFonts.instance.getFontsSize());
		leftPanel.add(new JLabel("Output size"));
		leftPanel.add(AllFonts.instance.getTexSizeX());
		leftPanel.add(AllFonts.instance.getTexSizeY());
		leftPanel.add(AllFonts.instance.getGenButton());
		
		//right side
		JPanel rightPanel = new JPanel();
		//rightPanel.setLayout(new GridLayout(1,1));
		//rightPanel.add();
		
		//window panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(AllFonts.instance.getCanvas(), BorderLayout.CENTER);
		
		win.setSize(500, 500);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.add(mainPanel);
		win.setVisible(true);
	}
}
