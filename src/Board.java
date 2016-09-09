package src;
import java.util.Random;
public class Board{
	public Tile [][] board = new Tile [12][12];
	public Board(){
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
	public static void main(String [] args){
		Board b = new Board();
		b.print();
	}
	public void print(){
		for(int y = 0; y < 12; y++){
			for(int x = 0; x < 12; x++){
				System.out.print(board[y][x]);
			}
			System.out.println();
		}
	}
	
}