import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hmenn on 4/5/16.
 *
 * Oyun 8*8lik santranc tahtasi uzerinden oynanir.
 * Beyaz pul Sol ust koseden sag al koseye hareket ettirilir.
 * En az bir tane siyah pula carmadan oyun bitemez.
 * Sadece asagi,sag,yukari,sola hareket saglanabilir.
 *
 * Referans : https://www.cs.bu.edu/teaching/alg/maze/
 *
 */
public class Maze {
	private boolean dumpedStump = false; // siyah pullara carptimi
	public List<Coordinat> movements = new ArrayList<>(); // cozum yolu
	private static final Character blank = '.';
	private static final Character stamp = 'X';
	private static final Character start = 'S';
	private static final Character end = 'E';
	private static final int ROW_SIZE = 8;
	private static final int COLUMN_SIZE = 8;
	private Random rander = new Random(); // random sayi uretici
	private char[][] board = new char[ROW_SIZE][COLUMN_SIZE]; // oyun tahtamiz

//	private char[][] board = {
//			{'.', '.', '.', '.', '.', '.', 'x'},
//			{'.', '.', '.', 'x', '.', '.', '.'},
//			{'x', '.', '.', 'x', '.', 'x', '.'},
//			{'.', '.', 'x', '.', 'x', '.', '.'},
//			{'.', 'x', '.', 'x', '.', 'x', '.'},
//			{'.', 'x', '.', '.', '.', '.', 'G'}};

	// Parametresiz constructor
	public Maze() {
		int tempRow;
		int tempColumn;

		List<Coordinat> blackStamps = new ArrayList<>();

		int numOfStamp = rander.nextInt(5) + 3;

		// tahtayi bos olustur
		for (int i = 0; i < ROW_SIZE; ++i) {
			for (int j = 0; j < COLUMN_SIZE; ++j)
				board[i][j] = blank;
		}

		// random siyah pul olusturup yerlestir
		for (int i = 0; i < numOfStamp; ++i) {
			tempRow = rander.nextInt(ROW_SIZE);
			tempColumn = rander.nextInt(COLUMN_SIZE);
			if ((tempRow == 0 && tempColumn == 0) || (tempRow == ROW_SIZE - 1 && tempColumn == COLUMN_SIZE - 1))
				--i;
			else {
				board[tempRow][tempColumn] = stamp;
				blackStamps.add(new Coordinat(tempRow, tempColumn));
			}
		}

		tempRow = rander.nextInt(6)+1;
		board[tempRow][tempRow]=stamp;

		board[ROW_SIZE-1][COLUMN_SIZE-1] = end;
		System.out.println("Black Stamps : " + blackStamps);

	}

	// Bu method guncel tahtayi standart outputa basar
	public void showBoard() {

		Character rowCh = 'a';
		for (int i = 0; i < COLUMN_SIZE; ++i) {
			System.out.print(" " + (rowCh++));
		}
		System.out.println();
		for (int i = 0; i < ROW_SIZE; ++i) {
			System.out.print(i + 1);
			for (int j = 0; j < COLUMN_SIZE; ++j) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	// Bu metod recursive yardimci olarak kullanilacak.
	// 4 yonde hareket saglanabilmektedir.
	// oncelik sirasi asagi-sag-yukari-sol dur.
	public boolean findPath(int row, int column) {

		// tablo sinir kontrolu
		if (row < 0 || row >= ROW_SIZE || column < 0 || column >= COLUMN_SIZE)
			return false;
		// bitis noktasi kontrolu
		if (row == ROW_SIZE - 1 && column == COLUMN_SIZE - 1 && dumpedStump == true)
			return true;
		if (board[row][column] == stamp)
			dumpedStump = true;
		if (board[row][column] != blank)
			return false;
		board[row][column] = '*';

		if (findPath(row + 1, column+1) == true) {
			movements.add(new Coordinat(row, column));
			return true;
		}

		// asagi bak
		if (findPath(row + 1, column) == true) {
			movements.add(new Coordinat(row, column));
			return true;
		}
		// saga bak
		if (findPath(row, column + 1) == true) {
			movements.add(new Coordinat(row, column));
			return true;
		}
		// yukari bak
		if (findPath(row - 1, column) == true) {
			movements.add(new Coordinat(row, column));
			return true;
		}
		// sola bak
		if (findPath(row, column - 1) == true) {
			movements.add(new Coordinat(row, column));
			return true;
		}
		board[row][column] = 'h';

		return false;
	}

	// recursive wrapper metod
	public boolean solve() {

		boolean status = findPath(0, 0);
		board[0][0] = 'S';

		return status;
	}
}