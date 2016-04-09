/**
 * Created by hmenn on 4/5/16.
 *
 *
 */
public class Main {
	public static void main(String ... args){

		Maze brd = new Maze();
		brd.showBoard();
		if (brd.solve()) {
			System.out.println("Maze solved!");
			System.out.println("Movements : "+brd.movements);
			brd.showBoard();
		}
		else
			System.out.println ("No solution.");
	}
}
