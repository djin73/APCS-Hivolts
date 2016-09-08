package src;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile{
	public int x, y;
	public BufferedImage img;
	public Tile(int x, int y, String imageName){
		this.x = x;
		this.y = y;
		try {
		    img = ImageIO.read(new File(imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Tile(){
		
	}
	public void draw(Graphics g){
		g.drawImage(img, x*50, y*50, null);
	}
}