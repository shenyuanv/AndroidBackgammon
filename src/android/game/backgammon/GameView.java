package android.game.backgammon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	private int gridSize = 0; // ��������ÿ��Ĵ�С
	private int selectX = 7; // ѡ������������̵�X����
	private int selectY = 7; // ѡ������������̵�Y����
	private final Rect selectRect = new Rect();// ѡ�����������Ļ�ķ�����Ϣ
	private BackgammonActivity backAct;

	public GameView(Context context) {
		super(context);
		this.setBackgroundResource(R.drawable.wood1);// ���ñ���ͼ
		backAct = (BackgammonActivity) context;
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d("GameView", "onDraw");
		drawGridboard(canvas);// ������
		drawPieces(canvas); // ������
		drawSelectRect(canvas);// ��ѡ������
		super.onDraw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {// ��ʼ������Ļ��С�ı�ʱ���øú���
		int min = Math.min(w, h);
		gridSize = min / BackgammonActivity.LINE;
		this.getRect(selectX, selectY, selectRect);
		Log.d("GameView", "onSizeChanged:gridSize=" + gridSize + ",gridSize="
				+ gridSize + ".");
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/*
	 * ͨ������������̵�X��Y����rect��ֵ ʹ֮��Ϊ��X��Y�������Ļ����ֵ
	 */
	private void getRect(int x, int y, Rect rect) {
		rect.set((int) (x * gridSize + getTopX()),
				(int) (y * gridSize + getTopY()), (int) (x * gridSize
						+ gridSize + getTopX()),
				(int) (y * gridSize + gridSize + getTopY()));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {// �����¼�
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			select(selectX, selectY - 1);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			select(selectX, selectY + 1);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			select(selectX - 1, selectY);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			select(selectX + 1, selectY);
			break;
		case KeyEvent.KEYCODE_DPAD_CENTER:
			backAct.setPieceIfValid(selectX,selectY,2);
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {// �������¼�
		this.select((int) ((event.getX() - getTopX()) / gridSize),
				(int) ((event.getY() - getTopY()) / gridSize));
		backAct.setPieceIfValid(selectX,selectY,2);
		return super.onTouchEvent(event);
	}

	public void select(int x, int y) {// ѡ������
		this.invalidate(selectRect);
		selectX = Math.min(Math.max(x, 0), BackgammonActivity.LINE - 1);
		selectY = Math.min(Math.max(y, 0), BackgammonActivity.LINE - 1);
		this.getRect(selectX, selectY, selectRect);
		this.invalidate(selectRect);
	}

	private void drawSelectRect(Canvas canvas) {
		Paint selected = new Paint();
		selected.setColor(this.getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selectRect, selected);
	}

	private void drawPieces(Canvas canvas) {
		Paint whitePiece = new Paint();
		whitePiece.setColor(Color.WHITE);
		Paint blackPiece = new Paint();
		blackPiece.setColor(Color.BLACK);
		int[][] gridBoard = backAct.getGridBoard();
		Rect pieceRect = new Rect();
		for (int i = 0; i < BackgammonActivity.LINE; i++) {
			for (int j = 0; j < BackgammonActivity.LINE; j++) {
				if (gridBoard[i][j] == 1) {// ����
					getRect(i, j, pieceRect);
					canvas.drawCircle(pieceRect.centerX(), pieceRect.centerY(), pieceRect.height()/2f, whitePiece);
					//canvas.drawRect(pieceRect, whitePiece);
				} else if (gridBoard[i][j] == 2) {// ����
					getRect(i, j, pieceRect);
					canvas.drawCircle(pieceRect.centerX(), pieceRect.centerY(), pieceRect.height()/2f, blackPiece);
					//canvas.drawRect(pieceRect, blackPiece);
				}
			}
		}
	}

	private void drawGridboard(Canvas canvas) {
		// ��������
		Paint hilite = new Paint();
		hilite.setColor(this.getResources().getColor(R.color.puzzle_hilite));
		Paint light = new Paint();
		light.setColor(this.getResources().getColor(R.color.puzzle_light));
		for (int i = 0; i <= BackgammonActivity.LINE; i++) {
			canvas.drawLine(getTopX(), i * gridSize + getTopY(), getTopX()
					+ gridSize * BackgammonActivity.LINE, getTopY() + i
					* gridSize, hilite);
			canvas.drawLine(getTopX() + i * gridSize, getTopY(), i * gridSize,
					getTopY() + gridSize * BackgammonActivity.LINE, hilite);
			canvas.drawLine(getTopX(), i * gridSize + getTopY() + 1, getTopX()
					+ gridSize * BackgammonActivity.LINE, getTopY() + i
					* gridSize + 1, light);
			canvas.drawLine(getTopX() + i * gridSize + 1, getTopY(), i
					* gridSize + 1, getTopY() + gridSize
					* BackgammonActivity.LINE, light);
		}
	}

	private float getTopX() {// �����������Ͻǵ�X����
		return 0;
	}

	private int getTopY() {// �����������Ͻǵ�Y����
		int blankSize = Math.abs(getWidth() - getHeight());
		return blankSize / 2;
	}
}
