package android.game.backgammon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.game.backgammon.ai.EasyAi;
import android.os.Bundle;

public class BackgammonActivity extends Activity {
	public static final int LINE = 15;//�������׼����Ϊ15*15��
	private int[][] gridBoard = new int[LINE][LINE];//����
	private boolean isUsersTurn = true;
	private EasyAi ai ;
	private int[][] combo = new int[2][572];  //��¼2λ������п��ܵ���������-1��Ϊ��Զ�޷�5���飬0������ԣ�1�������
    private boolean[][][][] table = new boolean[2][15][15][572];  
	private Coordinate lastPlayersCoordinate;
	private GameView gameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initGame();
		setContentView(gameView);
	}
	
	private void initGame() {
		GameView gameView = new GameView(this);
		this.gameView = gameView;
		this.ai = new EasyAi();
		int icount = 0;
		//�������е������ӿ��������Ȩֵ  
        //��  
        for(int i=0;i<15;i++)  
            for(int j=0;j<11;j++){  
                for(int k=0;k<5;k++){  
                    this.table[1][j+k][i][icount] = true;  
                    this.table[0][j+k][i][icount] = true;  
                }  
                icount++;  
            }  
        //��  
        for(int i=0;i<15;i++)  
            for(int j=0;j<11;j++){  
                for(int k=0;k<5;k++){  
                    this.table[1][i][j+k][icount] = true;  
                    this.table[0][i][j+k][icount] = true;  
                }  
                icount++;  
            }  
        //��б  
        for(int i=0;i<11;i++)  
            for(int j=0;j<11;j++){  
                for(int k=0;k<5;k++){  
                    this.table[1][j+k][i+k][icount] = true;  
                    this.table[0][j+k][i+k][icount] = true;  
                }  
                icount++;  
            }  
        //��б  
        for(int i=0;i<11;i++)  
            for(int j=14;j>=4;j--){  
                for(int k=0;k<5;k++){  
                    this.table[1][j-k][i+k][icount] = true;  
                    this.table[0][j-k][i+k][icount] = true;  
                }  
                icount++;  
            }  
        for(int i=0;i<=1;i++)  //��ʼ�����Ӱ����ϵ�ÿ��Ȩֵ�ϵ�������  
            for(int j=0;j<572;j++)  
                this.combo[i][j] = 0;  
        
        if(!IndexActivity.playerFirst){
        	isUsersTurn = false;
			Coordinate c = ai.comTurn(null);
			setPieceIfValid(c.x,c.y,1);
        }
        IndexActivity.playerFirst = !IndexActivity.playerFirst;
	}
	
	public int[][] getGridBoard(){
		return gridBoard;
	}
	public void setPieceIfValid(int selectX, int selectY,int id) {
		if(gridBoard[selectX][selectY] != 0 || haveWinner()){
			return;
		}
		if(isUsersTurn && id==2){
			setPiece(selectX,selectY,id);
			nextTurn();
		}else if(!isUsersTurn && id==1){
			setPiece(selectX,selectY,id);
			nextTurn();
		}
	}
	private boolean haveWinner(){//����Ƿ�����������
		for(int i=0;i<2;i++){
			for(int j=0;j<572;j++){
				if(combo[i][j] == 5){
					showWinner(i);
					return true;
				}
			}
		}
		return false;
	}
	private void showWinner(int id){//��ʾ������Ϣ
		Builder builder = new AlertDialog.Builder(this);
		String title = "";
		String msg = "";
		switch(id){
		case 0://����Ӯ�ˣ��������
			title = this.getResources().getString(R.string.title_lose);
			msg = this.getResources().getString(R.string.text_lose);
			break;
		case 1://���Ӯ��
			title = this.getResources().getString(R.string.title_victor);
			msg = this.getResources().getString(R.string.text_victor);
			break;
		}
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton("�������˵�", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				BackgammonActivity.this.finish();//�˳��˴���
				
			}
		});
		builder.setNegativeButton("�鿴���", null);
		builder.show();
	}
	
	private void nextTurn(){//��û���������������һ�غϣ�������ʾʤ����
		if(!haveWinner()){
			isUsersTurn = !isUsersTurn;
			if(!isUsersTurn){//�����һ�غϸõ�������
				Coordinate c = ai.comTurn(lastPlayersCoordinate);
				setPieceIfValid(c.x,c.y,1);
			}
		}
		gameView.invalidate();
	}
	/*setPiece���� ǰ���������Ѿ������ø�������Ӳ�����ɳ�ͻ�������Ѿ�����Ƿ��ֵ���ɫ������
	 * ������������ָ�����ӷ���ָ��������
	 * */
	private void setPiece(int x,int y,int id){
		gridBoard[x][y] = id;
		switch(id){
		case 1://������
	         for(int i=0;i<572;i++){  
	             if(this.table[0][x][y][i] && this.combo[0][i] != -1)  
	                 this.combo[0][i]++;     //�����ӵ����������ӿ��ܵļ��ص�ǰ������  
	             if(this.table[1][x][y][i]){  
	                 this.table[1][x][y][i] = false;  
	                 this.combo[1][i]= -1;  
	             } 
	         }
	         gameView.select(x,y);
			 break;
		case 2://�����
			lastPlayersCoordinate = new Coordinate(x,y);
            for(int i=0;i<572;i++){  
                if(this.table[1][x][y][i] && this.combo[1][i] != -1)  
                    this.combo[1][i]++;     //�����ӵ����������ӿ��ܵļ��ص�ǰ������  
                if(this.table[0][x][y][i]){  
                    this.table[0][x][y][i] = false;  
                    this.combo[0][i]=-1;  
                }  
            } 
			break;
		}
	}

}
