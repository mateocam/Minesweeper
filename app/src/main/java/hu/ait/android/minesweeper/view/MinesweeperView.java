package hu.ait.android.minesweeper.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import hu.ait.android.minesweeper.MainActivity;
import hu.ait.android.minesweeper.R;
import hu.ait.android.minesweeper.model.MinesweeperModel;

/**
 * Created by mateocar on 9/28/15.
 */
public class MinesweeperView extends View {

    private final Paint paintLittleBorder;
    private Paint paintSquare;
    private Paint paintLine;
    private Paint paintLittleSquare;

    private Bitmap oneBitmap;
    private Bitmap twoBitmap;
    private Bitmap threeBitmap;
    private Bitmap flagBitmap;
    private Bitmap mineBitmap;

    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintSquare = new Paint();
        paintSquare.setColor(Color.GRAY);
        paintSquare.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintLittleSquare = new Paint();
        paintLittleSquare.setColor(Color.LTGRAY);
        paintLittleSquare.setStyle(Paint.Style.FILL);

        paintLittleBorder = new Paint();
        paintLittleBorder.setColor(Color.BLACK);
        paintLittleBorder.setStyle(Paint.Style.STROKE);
        paintLittleBorder.setStrokeWidth(5);

        oneBitmap = BitmapFactory.decodeResource(
                getResources(), R.drawable.one);
        twoBitmap = BitmapFactory.decodeResource(
                getResources(), R.drawable.two);
        threeBitmap = BitmapFactory.decodeResource(
                getResources(), R.drawable.three);
        flagBitmap = BitmapFactory.decodeResource(
                getResources(), R.drawable.flag);
        mineBitmap = BitmapFactory.decodeResource(
                getResources(), R.drawable.bomb);

        MinesweeperModel.getInstance().setMines();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        oneBitmap = oneBitmap.createScaledBitmap(
                oneBitmap, getWidth() / 5, getHeight() / 5, false);

        twoBitmap = twoBitmap.createScaledBitmap(
                twoBitmap, getWidth() / 5, getHeight() / 5, false);

        threeBitmap = threeBitmap.createScaledBitmap(
                threeBitmap, getWidth() / 5, getHeight() / 5, false);

        flagBitmap = flagBitmap.createScaledBitmap(
                flagBitmap, getWidth() / 5, getHeight() / 5, false);

        mineBitmap = mineBitmap.createScaledBitmap(
                mineBitmap, getWidth() / 5, getHeight() / 5, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintSquare);
        drawGameArea(canvas);
        drawNumbers(canvas);
        drawFlaggedMines(canvas);
        drawMinesWhenLost(canvas);
    }

    private void drawGameArea(Canvas canvas) {

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);

        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintLine);

        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 5, 0, 2 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(3 * getWidth() / 5, 0, 3 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(4 * getWidth() / 5, 0, 4 * getWidth() / 5, getHeight(),
                paintLine);
    }

    public void drawNumbers(Canvas canvas) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (MinesweeperModel.getInstance().getFieldContentNums(x, y)
                        != -1) {

                    canvas.drawRect(x * (getWidth() / 5), y * (getHeight() / 5),
                            (x + 1) * (getWidth() / 5), (y + 1) * (getHeight() / 5),
                            paintLittleSquare);

                    int numOfMines = MinesweeperModel.getInstance().getFieldContentNums(x, y);

                    if (numOfMines == 1) {
                        canvas.drawBitmap(oneBitmap, x * (getWidth() / 5),
                                y * (getHeight() / 5), null);
                    }
                    if (numOfMines == 2) {
                        canvas.drawBitmap(twoBitmap, x * (getWidth() / 5),
                                y * (getHeight() / 5), null);
                    }
                    if (numOfMines == 3) {
                        canvas.drawBitmap(threeBitmap, x * (getWidth() / 5),
                                y * (getHeight() / 5), null);
                    }

                    canvas.drawRect(x * (getWidth() / 5), y * (getHeight() / 5),
                            (x + 1) * (getWidth() / 5), (y + 1) * (getHeight() / 5),
                            paintLittleBorder);
                }
            }
        }
    }

    public void drawFlaggedMines(Canvas canvas) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (MinesweeperModel.getInstance().getFieldContentMines(x, y)
                        == MinesweeperModel.FLAGGED_MINE) {

                    canvas.drawBitmap(flagBitmap, x * (getWidth() / 5),
                            y * (getHeight() / 5), null);

                    canvas.drawRect(x * (getWidth() / 5), y * (getHeight() / 5),
                            (x + 1) * (getWidth() / 5), (y + 1) * (getHeight() / 5),
                            paintLittleBorder);
                }
            }
        }
    }

    public void drawMinesWhenLost(Canvas canvas) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (MinesweeperModel.getInstance().getFieldContentMines(x, y)
                        == MinesweeperModel.MINE_LOST) {

                    canvas.drawBitmap(mineBitmap, x * (getWidth() / 5),
                            y * (getHeight() / 5), null);

                    canvas.drawRect(x * (getWidth() / 5), y * (getHeight() / 5),
                            (x + 1) * (getWidth() / 5), (y + 1) * (getHeight() / 5),
                            paintLittleBorder);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int tX = ((int) event.getX()) / (getWidth() / 5);
            int tY = ((int) event.getY()) / (getHeight() / 5);

            handleMove(tX, tY);

            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void handleMove(int tX, int tY) {
        if (tX < 5 && tY < 5) {

            MinesweeperModel.getInstance().flagMine(tX, tY);
            int lost = MinesweeperModel.getInstance().seeIfLost(tX, tY);
            int won = MinesweeperModel.getInstance().seeIfWon();

            if (lost == 1) {
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 5; y++) {
                        if (MinesweeperModel.getInstance().getFieldContentMines(x, y)
                                == MinesweeperModel.MINE) {
                            MinesweeperModel.getInstance().
                                    setFieldContentMines(x,y,MinesweeperModel.MINE_LOST);
                        }
                    }
                }
                ((MainActivity) getContext()).showSnackBarMessage(
                        getContext().getString(R.string.lost_msg));
                invalidate();
            }

            if (won == 1) {
                ((MainActivity) getContext()).showSnackBarMessage(
                        getContext().getString(R.string.won_msg));
            }

            if (won != 1 && lost != 1) {

                MinesweeperModel.getInstance().numberOfMines(tX, tY);

                if (MinesweeperModel.getInstance().getFieldContentNums(tX, tY) == 0) {
                    MinesweeperModel.getInstance().showNumbersIfZero(tX, tY);
                }
            }

            invalidate();
        }
    }

    public void restartGame() {
        MinesweeperModel.getInstance().resetModel();
        MinesweeperModel.getInstance().generateMines();
        MinesweeperModel.getInstance().setMines();
        invalidate();
    }
}
