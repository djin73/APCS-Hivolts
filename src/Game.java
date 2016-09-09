package src;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Game extends JPanel{
	Player p;
	ArrayList<Mho> Mhos = new ArrayList<Mho>();
	Tile [][] board = new Tile[12][12];
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
		setSize(620, 640);
		initBoard();
		initPlayer();
	}
	public void initBoard(){
		Random r = new Random();
		for(int x = 0; x < 12; x++){
			for(int y = 0; y < 12; y++){
				if(x==0||x==11||y==0||y==11){
					board[x][y] = new Fence(x, y);
				}
				else{
					board[x][y] = new Tile();
				}
			}
		}
		for(int i = 0; i < 20; i++){
			int x = r.nextInt(11);
			int y = r.nextInt(11);
			while(board[x][y] instanceof Fence){
				 x = r.nextInt(11);
				 y = r.nextInt(11);
			}
			board[x][y] = new Fence(x, y);
		}
	}
	public void initPlayer(){
		Random r = new Random();
		int x = r.nextInt(11);
		int y = r.nextInt(11);
		while(board[x][y] instanceof Fence){
			x = r.nextInt(11);
			y = r.nextInt(11);
		}
		p = new Player(x, y);
		board[x][y] = p;
	}
	public void paint(Graphics g){
		g.fillRect(0, 0, 600, 600);
		for(int y = 0; y < 12; y++){
			for(int x = 0; x < 12; x++){
				board[x][y].draw(g);
			}
		}
	}
	
}