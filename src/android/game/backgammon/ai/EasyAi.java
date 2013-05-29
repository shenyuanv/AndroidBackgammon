package android.game.backgammon.ai;

import android.game.backgammon.Coordinate;

public class EasyAi {
    /* 15*15����572����������Ŀ����ԣ�������i��j��ʾ����λ�ã�k��ʾ����һ������������Ե�ID�ţ�1-527��
    ����ÿ������Ԫ�ص�i��j�ͼ�¼�˵�ǰ���ֿ�������������λ��*/
    private boolean[][][] ptable = new boolean[15][15][572];  //��ҵĿ�����
    private boolean[][][] ctable = new boolean[15][15][572];  //���ԵĿ�����
    private int[][] win = new int[2][572];  //��¼2λ������п��ܵ���������-1��Ϊ��Զ�޷�5����
    private int[][] cgrades = new int[15][15];  //��¼ÿ��ķ�ֵ
    private int[][] pgrades = new int[15][15];  
    private int[][] board = new int[15][15];  	//��¼����
   
    //��ʱ����
    private int cgrade,pgrade;  
    private int icount,m,n;
    private int mat,nat,mde,nde;
    
    public EasyAi(){
    	initGame();
    }
    private void initGame()
    {   
        //��ʼ������  
        for(int i=0;i<15;i++)  
            for(int j=0;j<15;j++)  
            {  
                this.pgrades[i][j] = 0;  
                this.cgrades[i][j] = 0;  
                this.board[i][j] = 0;  
            }  
        //�������е������ӿ��������Ȩֵ  
        //��  
        for(int i=0;i<15;i++)  
            for(int j=0;j<11;j++){  
                for(int k=0;k<5;k++){  
                    this.ptable[j+k][i][icount] = true;  
                    this.ctable[j+k][i][icount] = true;  
                }  
                icount++;  
            }  
        //��  
        for(int i=0;i<15;i++)  
            for(int j=0;j<11;j++){  
                for(int k=0;k<5;k++){  
                    this.ptable[i][j+k][icount] = true;  
                    this.ctable[i][j+k][icount] = true;  
                }  
                icount++;  
            }  
        //��б  
        for(int i=0;i<11;i++)  
            for(int j=0;j<11;j++){  
                for(int k=0;k<5;k++){  
                    this.ptable[j+k][i+k][icount] = true;  
                    this.ctable[j+k][i+k][icount] = true;  
                }  
                icount++;  
            }  
        //��б  
        for(int i=0;i<11;i++)  
            for(int j=14;j>=4;j--){  
                for(int k=0;k<5;k++){  
                    this.ptable[j-k][i+k][icount] = true;  
                    this.ctable[j-k][i+k][icount] = true;  
                }  
                icount++;  
            }  
        for(int i=0;i<=1;i++)  //��ʼ�����Ӱ����ϵ�ÿ��Ȩֵ�ϵ�������  
            for(int j=0;j<572;j++)  
                this.win[i][j] = 0;  
        this.icount = 0;  
        this.m = 0;
        this.n = 0;
    }  
    
    public Coordinate comTurn(Coordinate c){     //�ҳ����ԣ����ӣ�������ӵ�  
    	if(c != null){
    		setPlayersPiece(c);
    	}
        for(int i=0;i<15;i++)     //���������ϵ���������  
             for(int j=0;j<15;j++){     
                 this.pgrades[i][j]=0;  //������ĺ��ӽ�����������  
                 if(this.board[i][j] == 0)  //�ڻ�û�����ӵĵط�����  
                     for(int k=0;k<572;k++)    //���������̿����ӵ��ϵĺ�������Ȩֵ��������������������ӵ������Ӧ������  
                         if(this.ptable[i][j][k]){  
                             switch(this.win[1][k]){     
                                 case 1: //һ����  
                                     this.pgrades[i][j]+=5;  
                                     break;  
                                 case 2: //������  
                                     this.pgrades[i][j]+=50;  
                                     break;  
                                 case 3: //������  
                                     this.pgrades[i][j]+=180;
                                     break;  
                                 case 4: //������  
                                     this.pgrades[i][j]+=400;  
                                     break;  
                             }  
                         }  
                 this.cgrades[i][j]=0;//������İ��ӵĽ�����������  
                 if(this.board[i][j] == 0)  //�ڻ�û�����ӵĵط�����  
                     for(int k=0;k<572;k++)     //���������̿����ӵ��ϵİ�������Ȩֵ��������������������ӵ������Ӧ������  
                         if(this.ctable[i][j][k]){  
                             switch(this.win[0][k]){    
                                 case 1:  //һ����  
                                     this.cgrades[i][j]+=5;  
                                     break;  
                                 case 2:  //������  
                                     this.cgrades[i][j]+=52;  
                                     break;  
                                 case 3: //������  
                                     this.cgrades[i][j]+=130;  
                                     break;  
                                 case 4:  //������  
                                     this.cgrades[i][j]+=10000;  
                                     break;  
                             }  
                         }  
             }  
	        if(c == null){      //��ʼʱ������������  
	            m = 7;
	            n = 7;
	        }else{
	             for(int i=0;i<15;i++)  
	                 for(int j=0;j<15;j++)  
	                     if(this.board[i][j] == 0){  //�ҳ������Ͽ����ӵ�ĺ��Ӱ��ӵĸ������Ȩֵ���ҳ����Ե�������ӵ�  
	                         if(this.cgrades[i][j]>=this.cgrade){  
	                             this.cgrade = this.cgrades[i][j];     
	                             this.mat = i;  
	                             this.nat = j;  
	                         }  
	                         if(this.pgrades[i][j]>=this.pgrade){  
	                             this.pgrade = this.pgrades[i][j];     
	                             this.mde = i;  
	                             this.nde = j;  
	                         }  
	                     }  
	             if(this.cgrade>=this.pgrade){   //������ӵ�������ӵ��Ȩֵ�Ⱥ��ӵ�������ӵ�Ȩֵ������Ե�������ӵ�Ϊ���ӵ�������ӵ㣬�����෴  
	                 m = mat;  
	                 n = nat;  
	             }else{  
	                 m = mde;  
	                 n = nde;  
	             }  
	     }
         this.cgrade = 0;          
         this.pgrade = 0;  
         this.board[m][n] = 1;  //��������λ��     
         for(int i=0;i<572;i++){  
             if(this.ctable[m][n][i] && this.win[0][i] != -1)  
                 this.win[0][i]++;     //�����ӵ����������ӿ��ܵļ��ص�ǰ������  
             if(this.ptable[m][n][i]){  
                 this.ptable[m][n][i] = false;  
                 this.win[1][i]= -1;  
             } 
         }
         Coordinate co = new Coordinate(m,n);
         return co;
     }

	private void setPlayersPiece(Coordinate c) {
		int m = c.x;
		int n = c.y;
		if(this.board[m][n] == 0){     
            this.board[m][n] = 2;   
            for(int i=0;i<572;i++){  
                if(this.ptable[m][n][i] && this.win[1][i] != -1)  
                    this.win[1][i]++;     //�����ӵ����������ӿ��ܵļ��ص�ǰ������  
                if(this.ctable[m][n][i]){  
                    this.ctable[m][n][i] = false;  
                    this.win[0][i]=-1;  
                }  
            }  
        }
		
	} 
}
