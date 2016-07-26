package hu.ait.android.minesweeper.model;

import java.util.Random;

import hu.ait.android.minesweeper.MainActivity;

/**
 * Created by mateocar on 9/28/15.
 */
public class MinesweeperModel {

    private static MinesweeperModel instance = null;

    private MinesweeperModel() {
    }

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }
        return instance;
    }

    public static final short MINE = 1;
    public static final short EMPTY = 0;
    public static final short FLAGGED_MINE = 2;
    public static final short MINE_LOST = 3;

    private short[][] modelMines = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
    };

    private int[][] modelNums = {
            {-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1}
    };

    public void resetModel() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                modelMines[i][j] = EMPTY;
                modelNums[i][j] = -1;
            }
        }
    }

    private int mine1_tX;
    private int mine1_tY;
    private int mine2_tX;
    private int mine2_tY;
    private int mine3_tX;
    private int mine3_tY;

    public void generateMines () {
        mine1_tX = generateCoordinateX();
        mine1_tY = generateCoordinateY();
        mine2_tX = generateCoordinateX();
        mine2_tY = generateCoordinateY();
        mine3_tX = generateCoordinateX();
        mine3_tY = generateCoordinateY();
    }

    public void setMines() {

        while (mine1_tX == mine2_tX) {
            mine2_tX = generateCoordinateX();
        }
        while (mine1_tY == mine3_tY) {
            mine3_tY = generateCoordinateY();
        }
        while (mine2_tX == mine3_tX) {
            mine3_tX = generateCoordinateX();
        }

        setFieldContentMines(mine1_tX,mine1_tY,MINE);
        setFieldContentMines(mine2_tX,mine2_tY,MINE);
        setFieldContentMines(mine3_tX,mine3_tY,MINE);
    }

    public short seeIfWon() {

        boolean mine1 = getFieldContentMines(mine1_tX,mine1_tY) == FLAGGED_MINE;
        boolean mine2 = getFieldContentMines(mine2_tX,mine2_tY) == FLAGGED_MINE;
        boolean mine3 = getFieldContentMines(mine3_tX,mine3_tY) == FLAGGED_MINE;

        if (mine1 && mine2 && mine3) {
            return 1;
        }
        return 0;
    }

    public short seeIfLost(int x, int y) {
        // toggleTag = 1 (flag mode), toggleTag = 2 (try on field mode)
        if (MainActivity.toggleTag == 1) {

            if (getFieldContentMines(x, y) != FLAGGED_MINE)
                return 1;
        }
        if (MainActivity.toggleTag == 2) {

            if (getFieldContentMines(x,y) == MINE)
                return 1;
        }
        return 0;
    }

    public void flagMine(int tX, int tY) {
        if (MainActivity.toggleTag == 1 &&
                MinesweeperModel.getInstance().getFieldContentMines(tX,tY)
                        == MINE) {
            MinesweeperModel.getInstance()
                    .setFieldContentMines(tX, tY, FLAGGED_MINE);
        }
    }

    public void numberOfMines(int tX, int tY) {

        int mineCounter = 0;
        for (int row = tX - 1; row <= tX + 1; row++) {
            for (int col = tY - 1; col <= tY +1; col++) {

                if( !(tX == row &&  tY == col) && row >= 0 && col >= 0 && row < 5 && col < 5
                        && getFieldContentMines(row, col) != EMPTY)
                {
                    mineCounter = mineCounter + 1;
                }
            }
        }
        setFieldContentNums(tX,tY,mineCounter);
    }

    public void showNumbersIfZero (int tX, int tY) {
        for (int row = tX - 1; row <= tX + 1; row++) {
            for (int col = tY - 1; col <= tY + 1; col++) {
                if( !(tX == row &&  tY == col) && row >= 0 && col >= 0 && row < 5 && col < 5
                        && getFieldContentNums(row,col) == -1)
                {
                    numberOfMines(row, col);
                    if (MinesweeperModel.getInstance().getFieldContentNums(row,col) == 0)
                    {
                        MinesweeperModel.getInstance().showNumbersIfZero(row, col);
                    }
                }
            }
        }
    }

    public short getFieldContentMines(int x, int y) {
        return modelMines[x][y];
    }

    public short setFieldContentMines(int x, int y, short content) {
        return modelMines[x][y] = content;
    }

    public int getFieldContentNums(int x, int y) {
        return modelNums[x][y];
    }

    public int setFieldContentNums(int x, int y, int content) {
        return modelNums[x][y] = content;
    }

    private int generateCoordinateX() {
        Random rand = new Random(System.currentTimeMillis());
        int generatedNum = rand.nextInt(5);
        return generatedNum;
    }

    private int generateCoordinateY() {
        Random rand = new Random(System.currentTimeMillis());
        int generatedNum = rand.nextInt(5);
        return generatedNum;
    }
}