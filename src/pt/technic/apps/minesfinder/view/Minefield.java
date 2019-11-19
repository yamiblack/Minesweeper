package pt.technic.apps.minesfinder.view;

import java.util.Random;

/**
 *
 * @author Gabriel Massadas
 */
public class Minefield {

	public static final int EMPTY = 0;
	// from 1 to 8 is the number of mines around
	public static final int COVERED = 9;
	public static final int QUESTION = 10;
	public static final int MARKED = 11;
	public static final int BUSTED = 12;
	public static final int PORTION = 13;

	private boolean[][] mines;
	private int[][] states;
	private boolean[][] portion;
	private int width;
	private int height;
	private int numMines;
	private Random random;
	private int numMarkChances;
	private int leftmine;

	private boolean firstPlay;
	private boolean playerDefeated;
	private boolean battleWin;
	private boolean gameFinished;
	private boolean battleFinished;
	private boolean battleDefeated;

	private int numPortion;
	private int life;

	public Minefield(int width, int height, int numMines) {
		if (numMines <= 0) {
			throw new IllegalArgumentException("Mines nuumber must be bigger than 0");
		}

		this.width = width;
		this.height = height;
		this.numMines = numMines;
		this.numMarkChances = numMines;
		mines = new boolean[width][height];
		states = new int[width][height];
		portion = new boolean[width][height];

		random = new Random();

		firstPlay = true;
		playerDefeated = false;
		battleWin = false;
		gameFinished = false;
		battleDefeated = false;

		numPortion = 3;
		life = 15;
		leftmine = numMines;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				states[x][y] = COVERED;
			}
		}
	}

	public void revealGrid(int x, int y) {
		if (states[x][y] == COVERED && !gameFinished) {
			if (firstPlay) {
				firstPlay = false;
				placeMines(x, y);
			}

			if (mines[x][y]) {
				states[x][y] = BUSTED;
				for (x = 0; x < width; x++) {
					for (y = 0; y < height; y++) {
						if (mines[x][y]) {
							states[x][y] = BUSTED;
						}
					}
				}
				playerDefeated = true;
				gameFinished = true;
				return;
			}

			int minesAround = countMinesAround(x, y);
			states[x][y] = minesAround;

			if (minesAround == 0) {
				revealGridNeighbors(x, y);
			}

			if (checkVictory()) {
				gameFinished = true;
				playerDefeated = false;
				return;
			}
		}
	}

	public void BattlerevealGrid(int x, int y) {
		if (states[x][y] == COVERED && !battleFinished) {
			if (firstPlay) {
				firstPlay = false;
				placeMines(x, y);
				placePortion(x, y);
			}

			int minesAround = countMinesAround(x, y);
			states[x][y] = minesAround;

			if (minesAround == 0) {
				BattleGridNeighbors(x, y);
			}

			if (mines[x][y]) {
				leftmine--;
				life++;
				states[x][y] = BUSTED;

				if (leftmine == 0) {
					battleWin = true;
					battleFinished = true;
					return;
				}
			} else if (portion[x][y]) {
				life++;
				states[x][y] = PORTION;
			}

			else if (!mines[x][y]) {
				life--;
				if (life == 0) {
					battleDefeated = true;
					battleFinished = true;
				}
			}

		}
	}

	private void revealGridNeighbors(int x, int y) {
		for (int col = Math.max(0, x - 1); col < Math.min(width, x + 2); col++) {
			for (int line = Math.max(0, y - 1); line < Math.min(height, y + 2); line++)
				revealGrid(col, line);
		}
	}

	private void BattleGridNeighbors(int x, int y) {
		for (int col = Math.max(0, x - 1); col < Math.min(width, x + 2); col++) {
			for (int line = Math.max(0, y - 1); line < Math.min(height, y + 2); line++)
				revealGrid1(col, line);
		}
	}

	private void revealGrid1(int x, int y) {
		if (states[x][y] == COVERED && !battleFinished) {
			if (firstPlay) {
				firstPlay = false;
				placeMines(x, y);
				placePortion(x, y);
			}
			int minesAround = countMinesAround(x, y);
			states[x][y] = minesAround;

			if (minesAround == 0) {
				revealGrid1(x, y);
			} else {
				BattlerevealGrid(x, y);
			}
		}
	}

	public void setMineMarked(int x, int y) {
		if (numMarkChances > 0) {
			if (states[x][y] == COVERED || states[x][y] == QUESTION) {
				states[x][y] = MARKED;
				numMarkChances -= 1;// 마크 표시되면 찬스-1
			}
		}
	}

	public void setMineQuestion(int x, int y) {
		if (states[x][y] == COVERED) {
			states[x][y] = QUESTION;
		}

		if (states[x][y] == MARKED) {
			states[x][y] = QUESTION;
			numMarkChances += 1; // 마크 취소 되면 찬스+1
		}
	}

	public void setMineCovered(int x, int y) {
		if (states[x][y] == MARKED || states[x][y] == QUESTION) {
			states[x][y] = COVERED;
		}
	}

	private boolean checkVictory() {
		boolean victory = true;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (!mines[x][y]) {
					victory = victory && states[x][y] >= 0 && states[x][y] < 9;
				}
			}
		}
		return victory;
	}

	private int countMinesAround(int x, int y) {
		int result = 0;
		for (int col = Math.max(0, x - 1); col < Math.min(width, x + 2); col++) {
			for (int line = Math.max(0, y - 1); line < Math.min(height, y + 2); line++) {
				if (mines[col][line]) {
					result++;
				}
			}
		}
		return result - (mines[x][y] ? 1 : 0);
	}

	public boolean isPlayerDefeated() {
		return playerDefeated;
	}

	public boolean isGameFinished() {
		return gameFinished;
	}

	public boolean isBattleFinished() {
		return battleFinished;
	}

	public boolean isBattleWin() {
		return battleWin;
	}

	public boolean isBattleDefeated() {
		return battleDefeated;
	}

	private void placeMines(int plX, int plY) {
		// the plX and plY is the player's first play
		for (int i = 0; i < numMines; i++) {
			int x = 0;
			int y = 0;
			do {
				x = random.nextInt(width);
				y = random.nextInt(height);
			} while (mines[x][y] || (x == plX && y == plY));
			mines[x][y] = true;
		}
	}

	private void placePortion(int plX, int plY) {
		for (int i = 0; i < numPortion; i++) {
			int x = 0;
			int y = 0;
			do {
				x = random.nextInt(width);
				y = random.nextInt(height);

			} while (mines[x][y] || portion[x][y]);
			portion[x][y] = true;
		}
	}

	public int getGridState(int x, int y) {
		return states[x][y];
	}

	public boolean hasMine(int x, int y) {
		return mines[x][y];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getNumMines() {
		return numMines;
	}

	public int getleft() {
		return leftmine;
	}

	public int getnumlife() {
		return life;
	}

	public int getNumMarkChances() {
		return numMarkChances;
	}

}
