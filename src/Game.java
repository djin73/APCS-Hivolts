package src;

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

public class Game extends JPanel {
	Player p;
	Mho[] Mhos = new Mho[12];
	Tile[][] board = new Tile[12][12];
	String gameState = "StartMenu";
	
	JButton startButton = new JButton("Start");
	JButton quitButton = new JButton("Quit");
	JButton instructionsButton = new JButton("Instructions");
	JButton backButton = new JButton("Back");

	public static void main(String[] args) {
		Game g = new Game();
		JFrame j = new JFrame();
		j.setSize(620, 640);
		j.add(g);
		j.setDefaultCloseOperation(j.EXIT_ON_CLOSE);
		j.setVisible(true);
		j.setLocationRelativeTo(null);
		j.setResizable(false);
	}
	/**
	 * Game constructor. Sets up the buttons and ActionListeners, and also sets up the KeyListener used to get the player input.
	 */
	public Game() {
		this.setLayout(null);
		startButton.setBounds(240, 300, 160, 75);
		quitButton.setBounds(240, 400, 160, 75);
		quitButton.setBackground(Color.GRAY);
		startButton.setBackground(Color.GRAY);
		startButton.setVerticalTextPosition(AbstractButton.CENTER);
		startButton.setHorizontalTextPosition(AbstractButton.CENTER);
		quitButton.setVerticalTextPosition(AbstractButton.CENTER);
		quitButton.setHorizontalTextPosition(AbstractButton.CENTER);
		quitButton.setActionCommand("quit");
		startButton.setActionCommand("start");
		instructionsButton.setBounds(240, 500, 160, 75);
		instructionsButton.setBackground(Color.GRAY);
		instructionsButton.setVerticalTextPosition(AbstractButton.CENTER);
		instructionsButton.setHorizontalTextPosition(AbstractButton.CENTER);
		instructionsButton.setActionCommand("instructions");
		backButton.setBounds(240, 500, 160, 75);
		backButton.setBackground(Color.GRAY);
		backButton.setVerticalTextPosition(AbstractButton.CENTER);
		backButton.setHorizontalTextPosition(AbstractButton.CENTER);
		backButton.setActionCommand("back");
		backButton.setVisible(false);
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("quit")) {
					System.exit(0);
				}
			}
		});
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("start")) {
					gameState = "Game";
					init();
					repaint();
					startButton.setVisible(false);
					quitButton.setVisible(false);
					instructionsButton.setVisible(false);
					backButton.setVisible(false);
				}

			}

		});
		instructionsButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("instructions")){
					gameState = "instructions";
					backButton.setVisible(true);
					instructionsButton.setVisible(false);
					quitButton.setVisible(false);
					startButton.setVisible(false);
					repaint();
				}
				
		}});
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("back")){
					gameState = "StartMenu";
					backButton.setVisible(false);
					instructionsButton.setVisible(true);
					quitButton.setVisible(true);
					startButton.setVisible(true);
					repaint();
				}
				
		}});
		this.add(startButton);
		this.add(quitButton);
		this.add(instructionsButton);
		this.add(backButton);
		KeyListener listener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int xVel = 0, yVel = 0;
				boolean jumped = false;
				switch (e.getKeyChar()) {
				case 'w':
					yVel = -1;
					break;
				case 'a':
					xVel = -1;
					break;
				case 's':
					break;
				case 'd':
					xVel = 1;
					break;
				case 'q':
					yVel = -1;
					xVel = -1;
					break;
				case 'e':
					yVel = -1;
					xVel = 1;
					break;
				case 'z':
					yVel = 1;
					xVel = -1;
					break;
				case 'c':
					yVel = 1;
					xVel = 1;
					break;
				case 'x':
					yVel = 1;
					break;
				case 'j':
					jump();
					jumped = true;
				}
				if (!(board[p.x + xVel][p.y + yVel] instanceof Fence)
						&& (!(board[p.x + xVel][p.y + yVel] instanceof Mho))) {
					p.x += xVel;
					p.y += yVel;
					board[p.x - xVel][p.y - yVel] = new Tile();
					board[p.x][p.y] = p;
					if (!jumped)
						updateMhos();
					if(allMhosDead()){
						gameOver(true);
					}
				} else {
					gameOver(false);
				}
				repaint();

			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		};
		addKeyListener(listener);
		setFocusable(true);
		repaint();
	}
	/**
	 * Initializes the board(Fences and Mhos) and also the player 
	 */
	public void init() {
		setSize(620, 640);
		initBoard();
		initPlayer();
	}
	/**
	 * Moves the player to a random location on the board. Can't be a fence, but can be an Mho.
	 */
	public void jump() {
		Random r = new Random();
		int x = r.nextInt(11);
		int y = r.nextInt(11);
		while (board[x][y] instanceof Fence) {
			x = r.nextInt(11);
			y = r.nextInt(11);
		}
		p.x = x;
		p.y = y;
	}
	/**
	 * Initializes the fences on the board and the mhos. The fences are around the edge of the board, 
	 * with some randomly generated in the middle, and the Mhos are randomly generated on the board.
	 */
	public void initBoard() {
		Random r = new Random();
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 12; y++) {
				if (x == 0 || x == 11 || y == 0 || y == 11) {
					board[x][y] = new Fence(x, y);
				} else {
					board[x][y] = new Tile();
				}
			}
		}
		for (int i = 0; i < 20; i++) {
			int x = r.nextInt(11);
			int y = r.nextInt(11);
			while (board[x][y] instanceof Fence) {
				x = r.nextInt(11);
				y = r.nextInt(11);
			}
			board[x][y] = new Fence(x, y);
		}
		for (int i = 0; i < 12; i++) {
			int x = r.nextInt(11);
			int y = r.nextInt(11);
			while (board[x][y] instanceof Fence) {
				x = r.nextInt(11);
				y = r.nextInt(11);
			}
			Mhos[i] = new Mho(x, y);
			board[x][y] = Mhos[i];
		}

	}
	/**
	 * Determines whether or not all the Mhos are dead. Used to see if the player has won the game or not.
	 * @return true if all the Mhos are dead, false otherwise
	 */
	public boolean allMhosDead(){
		for(int i = 0; i < 12; i++){
			if(Mhos[i].isAlive){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Initializes the Player to a random location on the board. The location cannot be an Mho or a Fence.
	 */
	public void initPlayer() {
		Random r = new Random();
		int x = r.nextInt(11);
		int y = r.nextInt(11);
		while (board[x][y] instanceof Fence || board[x][y] instanceof Mho) {
			x = r.nextInt(11);
			y = r.nextInt(11);
		}
		p = new Player(x, y);
		board[x][y] = p;
	}

	/**
	 * Updates all of the Mhos. Uses a basic AI to determine where they should move, based off of where 
	 * the player, fences, and other Mhos are.
	 */
	public void updateMhos() {

		for (int i = 0; i < 12; i++) {
			if (Mhos[i].isAlive) {// Mho AI
				
				if (Mhos[i].x == p.x) // directly vertical
					if (Mhos[i].y > p.y) // move up
						moveMho(i, 0, -1);
					else // move down
						moveMho(i, 0, 1);

				else if (Mhos[i].y == p.y) { // directly horizontal
					if (Mhos[i].x > p.x) {
						moveMho(i, -1, 0);
					} else {
						moveMho(i, 1, 0);
					}
				} else {
					int relPos = Mhos[i].relToPlayer(p.x, p.y);
					int horDist = Math.abs(Mhos[i].x - p.x);
					int verDist = Math.abs(Mhos[i].y - p.y);

					if (relPos == 1) { // top right
						if (!(board[Mhos[i].x - 1][Mhos[i].y + 1] instanceof Fence
								|| board[Mhos[i].x - 1][Mhos[i].y + 1] instanceof Mho)) {
							// directly diagonal
							moveMho(i, -1, 1);
						} else if (horDist >= verDist && !(board[Mhos[i].x - 1][Mhos[i].y] instanceof Fence
								|| board[Mhos[i].x - 1][Mhos[i].y] instanceof Mho)) // horizontal
							moveMho(i, -1, 0);

						else if (horDist <= verDist && !(board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence)
								|| board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence) // vertical
							moveMho(i, 0, 1);
						
						// tries to move on a fence now
						else if (board[Mhos[i].x - 1][Mhos[i].y + 1] instanceof Fence)
							moveMho(i, -1, 1);
						else if (horDist >= verDist && board[Mhos[i].x - 1][Mhos[i].y] instanceof Fence)
							moveMho(i, -1, 0);
						else if (horDist >= verDist && board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence)
							moveMho(i, 0, 1);

					} else if (relPos == 2) { // top left
						if (!(board[Mhos[i].x + 1][Mhos[i].y + 1] instanceof Fence
								|| board[Mhos[i].x + 1][Mhos[i].y + 1] instanceof Mho)) {
							// directly diagonal
							moveMho(i, 1, 1);
						} else if (horDist >= verDist && !(board[Mhos[i].x + 1][Mhos[i].y] instanceof Fence
								|| board[Mhos[i].x + 1][Mhos[i].y] instanceof Mho)) // horizontal
							moveMho(i, 1, 0);

						else if (horDist <= verDist && !(board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence)
								|| board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence) // vertical
							moveMho(i, 0, 1);
						
						// tries to move on a fence now
						else if (board[Mhos[i].x + 1][Mhos[i].y + 1] instanceof Fence)
							moveMho(i, 1, 1);
						else if (horDist >= verDist && board[Mhos[i].x + 1][Mhos[i].y] instanceof Fence)
							moveMho(i, 1, 0);
						else if (horDist >= verDist && board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence)
							moveMho(i, 0, 1);
						
					} else if (relPos == 3) { // bottom left
						if (!(board[Mhos[i].x + 1][Mhos[i].y - 1] instanceof Fence
								|| board[Mhos[i].x + 1][Mhos[i].y - 1] instanceof Mho)) {
							// directly diagonal
							moveMho(i, 1, -1);
						} else if (horDist >= verDist && !(board[Mhos[i].x + 1][Mhos[i].y] instanceof Fence
								|| board[Mhos[i].x + 1][Mhos[i].y] instanceof Mho)) // horizontal
							moveMho(i, 1, 0);

						else if (horDist <= verDist && !(board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence)
								|| board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence) // vertical
							moveMho(i, 0, -1);
						
						// tries to move on a fence now
						else if (board[Mhos[i].x + 1][Mhos[i].y - 1] instanceof Fence)
							moveMho(i, 1, -1);
						else if (horDist >= verDist && board[Mhos[i].x + 1][Mhos[i].y] instanceof Fence)
							moveMho(i, 1, 0);
						else if (horDist >= verDist && board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence)
							moveMho(i, 0, -1);
						
					} else if (relPos == 4) { // bottom right
						if (!(board[Mhos[i].x - 1][Mhos[i].y - 1] instanceof Fence
								|| board[Mhos[i].x - 1][Mhos[i].y - 1] instanceof Mho)) {
							// directly diagonal
							moveMho(i, -1, -1);
						} else if (horDist >= verDist && !(board[Mhos[i].x - 1][Mhos[i].y] instanceof Fence
								|| board[Mhos[i].x - 1][Mhos[i].y] instanceof Mho)) // horizontal
							moveMho(i, -1, 0);

						else if (horDist <= verDist && !(board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence)
								|| board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence) // vertical
							moveMho(i, 0, -1);
						
						// tries to move on a fence now
						else if (board[Mhos[i].x - 1][Mhos[i].y - 1] instanceof Fence)
							moveMho(i, -1, -1);
						else if (horDist >= verDist && board[Mhos[i].x - 1][Mhos[i].y] instanceof Fence)
							moveMho(i, -1, 0);
						else if (horDist >= verDist && board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence)
							moveMho(i, 0, -1);
						
					}

				}
			}

		}

	}

	/**
	 * @param id
	 *            the index of the Mho (Mhos are stored in an array)
	 * @param dx
	 *            the amount moved in the x direction
	 * @param dy
	 *            the amount moved in the y direction
	 */
	public void moveMho(int id, int dx, int dy) {
		board[Mhos[id].x][Mhos[id].y] = new Tile();
		Mhos[id].x += dx;
		Mhos[id].y += dy;
		if (board[Mhos[id].x][Mhos[id].y] instanceof Fence)
			Mhos[id].die();
		else
			board[Mhos[id].x][Mhos[id].y] = Mhos[id];

		if (contact(Mhos[id])) {
			gameOver(false);
		}
		
		if (board[Mhos[id].x][Mhos[id].y] instanceof Fence)
			Mhos[id].die();

	}
	/**
	 * Determines if the player is on the same Tile as the Mho m
	 * @param m the Mho that the player might be in contact with
	 * @return whether or not the player is on the same Tile as the Mho
	 */
	public boolean contact(Mho m) {
		if (m.isAlive && m.y == p.y && m.x == p.x)
			return true;
		return false;
	}
	/**
	 * Sets the game state to the end game menu, either Won or Lost.
	 * @param won Whether the player won the game or not.
	 */
	public void gameOver(boolean won){
		if(won){
			gameState = "Won";
		}else{
			gameState = "Lost";
		}
		quitButton.setVisible(true);
		startButton.setText("Play Again");
		startButton.setVisible(true);
	}
	/**
	 * Method used to display the game in the JFrame. First determines what the state of the game is based off of gameState,
	 * then draws to the JFrame accordingly
	 */
	public void paintComponent(Graphics g) {
		if (gameState.equals("StartMenu")) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 620, 640);
			Image i;
			try {
				i = ImageIO.read(new File("images/hivolts1.png")); // image from http://homepage.cs.uiowa.edu/~jones/plato/
				g.drawImage(i, 0, 0, 620, 600, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(gameState.equals("instructions")){
			g.fillRect(0, 0, 650, 650);
			g.setFont(new Font("SansSerif", 12, 48));
			g.setColor(Color.WHITE);
			g.drawString("HOW TO PLAY", 150, 50);
			g.setFont(new Font("SansSerif", 12, 36));
			// display all the movement keys
			g.drawString("MOVEMENT", 25, 100);
			g.setFont(new Font("SansSerif", 12, 22));
			g.drawString("Q: UP AND LEFT", 25, 125);
			g.drawString("W: UP", 25, 150);
			g.drawString("E: UP AND RIGHT", 25, 175);
			g.drawString("A: LEFT", 25, 200);
			g.drawString("D: RIGHT", 25, 225);
			g.drawString("Z: DOWN AND LEFT", 25, 250);
			g.drawString("X: DOWN", 25, 275);
			g.drawString("C: DOWN AND RIGHT", 25, 300);
			g.drawString("S: SIT (STAY ON THE SAME SQUARE)", 25, 325);
			g.drawString("J: JUMP (MOVE ONTO A RANDOM SQUARE)", 25, 350);
			g.drawString("NOTE: A JUMP CAN'T LAND YOU ON A FENCE", 100, 400);
			g.drawString("BUT CAN LAND YOU ON AN MHO!", 150, 425);
			//tell the player the basic goals/rules of the game
			g.drawString(" = YOU", 350, 100);
			g.drawString("AVOID THE MHOS:", 300, 150);
			g.drawString("DON'T STEP ON FENCES:", 300, 200);
			Image mho, fence, player;
			try {
				fence =  ImageIO.read(new File("images/fence.png"));
				mho = ImageIO.read(new File("images/mho.png"));
				player = ImageIO.read(new File("images/player.png"));
				g.drawImage(mho, 500, 125, 50, 50, null);
				g.drawImage(player, 300, 70, 50, 50, null);
				g.drawImage(fence, 555, 170, 50, 50, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (gameState.equals("Game")) {
			g.fillRect(0, 0, 650, 650);
			for (int y = 0; y < 12; y++) {
				for (int x = 0; x < 12; x++) {
					board[x][y].draw(g);
				}
			}
		} else if (gameState.equals("Lost")) {
			g.fillRect(0, 0, 620, 640);
			g.setFont(new Font("SansSerif", 12, 48));
			g.setColor(Color.WHITE);
			g.drawString("YOU LOST!", 200, 200);
		}  else if (gameState.equals("Won")) {
			g.fillRect(0, 0, 620, 640);
			g.setFont(new Font("SansSerif", 12, 48));
			g.setColor(Color.WHITE);
			g.drawString("YOU WON!", 200, 200);
		}
	}
}