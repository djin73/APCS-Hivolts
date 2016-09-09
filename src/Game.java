package src;
import java.util.ArrayList;
import java.util.Random;
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
	Tile [][] board = new Tile[12][12];
	public static void main(String [] args){
		Game g = new Game();
		JFrame j = new JFrame();
		j.setSize(600, 600);
		j.add(g);
		j.setDefaultCloseOperation(j.EXIT_ON_CLOSE);
		j.setVisible(true);
	}
	public Game(){
		init();
		repaint();
	}
	public void init(){
		setSize(620, 640);
		initBoard();
	}
	public void initBoard(){
		Random r = new Random();
		for(int y = 0; y < 12; y++){
			for(int x = 0; x < 12; x++){
				if(x==0||x==11||y==0||y==11){
					board[y][x] = new Fence();
				}
				else{
					board[y][x] = new Tile();
				}
			}
		}
		for(int i = 0; i < 20; i++){
			int x = r.nextInt(11);
			int y = r.nextInt(11);
			while(board[y][x] instanceof Fence){
				 x = r.nextInt(11);
				 y = r.nextInt(11);
			}
			board[y][x] = new Fence();
		}
	}
	public void paint(Graphics g){
		g.fillRect(0, 0, 600, 600);
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("images/rsz_test.jpg"));
		} catch (IOException e) {
		}
		for(int y = 0; y < 12; y++){
			for(int x = 0; x < 12; x++){
				if(board[y][x] instanceof Fence){
					g.drawImage(img, x*50, y*50, null);
				}
			}
		}
	}
	
}