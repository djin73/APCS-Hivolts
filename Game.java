import java.util.ArrayList;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
public class Game extends JPanel{
	Player p;
	ArrayList<Mho> Mhos = new ArrayList<Mho>();
	Board b = new Board();
	public static void main(String [] args){
		Game g = new Game();
		JFrame j = new JFrame();

		j.setSize(620, 640);
		j.add(g);
		j.setDefaultCloseOperation(j.EXIT_ON_CLOSE);
		j.setVisible(true);
	}
	public Game(){
		init();
		repaint();
	}
	public void init(){
		setSize(600, 600);
		
	}
	public void paint(Graphics g){
		g.fillRect(0, 0, 600, 600);
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("src/rsz_test.jpg"));
		} catch (IOException e) {
		}
		for(int y = 0; y < 12; y++){
			for(int x = 0; x < 12; x++){
				if(b.board[y][x] instanceof Fence){
					g.drawImage(img, x*50, y*50, null);
				}
			}
		}
	}
	
	public void update(){
		
	}
	public void updateMhos(){
		
	}
}