package src;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
public class Game extends JPanel{
	Player p;
	ArrayList<Mho> Mhos = new ArrayList<Mho>();
	Tile [][] board = new Tile[12][12];
	String gameState = "StartMenu";
	public static void main(String [] args){
		Game g = new Game();
		JFrame j = new JFrame();
		j.setSize(620, 640);
		j.add(g);
		j.setDefaultCloseOperation(j.EXIT_ON_CLOSE);
		j.setVisible(true);
		j.setLocationRelativeTo(null);
	}
	public Game(){
		this.setLayout(null);
		ImageIcon i = new ImageIcon("images/button.png");
		JButton b2 = new JButton("Start", i);
		JButton b1 = new JButton("Quit", i);
		b2.setBounds(240, 300, 160, 75);
		b1.setBounds(240, 400, 160, 75);
		b2.setVerticalTextPosition(AbstractButton.CENTER);
		b2.setHorizontalTextPosition(AbstractButton.CENTER); 
		b1.setVerticalTextPosition(AbstractButton.CENTER);
	    b1.setHorizontalTextPosition(AbstractButton.CENTER); 
		b1.setActionCommand("quit");
		b2.setActionCommand("start");
		b1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("quit")){
					System.exit(0);
				}
			}
		});
		b2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("start")){
					gameState = "game";
					init();
					repaint();
					b2.setVisible(false);
					b1.setVisible(false);
				}
				
			}
			
		});
		this.add(b2);
		this.add(b1);
		KeyListener listener = new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int xVel = 0, yVel = 0;
				switch(e.getKeyChar()){
				case 'w': yVel=-1; break;
				case 'a': xVel=-1; break;
				case 's': break;
				case 'd': xVel=1; break;
				case 'q': yVel=-1; xVel=-1; break;
				case 'e': yVel=-1; xVel=1; break;
				case 'z': yVel=1; xVel=-1; break;
				case 'c': yVel=1; xVel=1; break;
				case 'x': yVel=1; break;
				case 'j': jump();
				}
				if(!(board[p.x+xVel][p.y+yVel] instanceof Fence)){
					p.x += xVel;
					p.y += yVel;
				}
				else{
					gameState = "gameOver_Lost";
					b1.setVisible(true);
					b2.setText("Play Again");
					b2.setVisible(true);
				}
				repaint();
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
			}
		};
		addKeyListener(listener);
		setFocusable(true);
		repaint();
	}
	public void init(){
		setSize(620, 640);
		initBoard();
		initPlayer();
	}
	public void jump(){
		Random r = new Random();
		int x = r.nextInt(11);
		int y = r.nextInt(11);
		while(board[x][y] instanceof Fence){
			x = r.nextInt(11);
			y = r.nextInt(11);
		}
		p.x = x;
		p.y = y;
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
	public void paintComponent(Graphics g){
		if(gameState.equals("StartMenu")){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 620, 640);
			Image i;
			try{
				// image from http://homepage.cs.uiowa.edu/~jones/plato/
				i = ImageIO.read(new File("images/hivolts1.png"));
				g.drawImage(i, 0, 0, 620, 600, null);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		else if(gameState.equals("game")){
			g.fillRect(0, 0, 600, 600);
			for(int y = 0; y < 12; y++){
				for(int x = 0; x < 12; x++){
					board[x][y].draw(g);
				}
			}
		}
		else if(gameState.equals("gameOver_Lost")){
			g.fillRect(0, 0, 620, 640);
			g.setFont(new Font("SansSerif", 12, 48));
			g.setColor(Color.WHITE);
			g.drawString("YOU LOST!", 200, 200);
		}
	}
	
}