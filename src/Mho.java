package src;

import java.awt.Graphics;

public class Mho extends Tile {
	public boolean isAlive = true;

	public Mho(int x, int y) {
		super(x, y, "images/mho.png");
	}

	public void die() {
		isAlive = false;
	}
	
	public void draw(Graphics g){
		if (isAlive)
			super.draw(g);
	}

	/**
	 * 
	 * @param pX
	 *            the x coordinate of the player
	 * @param pY
	 *            the y coordinate of the player
	 * @return the position of the Mho relative to the player, like quadrants
	 *         relative to the origin on a Cartesian plane
	 */
	public int relToPlayer(int pX, int pY) { // think quadrants on a graph
		if (this.x > pX) { // to the right
			if (this.y < pY) { // above
				return 1;
			} else { // below
				return 4;
			}
		} else { // to the left
			if (this.y < pY) { // above
				return 2;
			} else { // below
				return 3;
			}
		}
	}
}