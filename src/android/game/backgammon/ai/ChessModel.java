package android.game.backgammon.ai;

class ChessModel {
	//���̵Ŀ�ȡ��߶ȡ����̵�ģʽ����20��15��
	private int width,height,modeChess;
	//���̷���ĺ�����������
	private int x=0,y=0;
	//���̷���ĺ���������������Ӧ��������ɫ��
	//����arrMapShowֻ��3��ֵ��1��2��3��-5��
	//����1��������̷������µ�����Ϊ���ӣ�
	//2��������̷������µ�����Ϊ���ӣ�
	//3����Ϊ�����̷�����û�����ӣ�
	//-5��������̷����ܹ�������
	private int[][] arrMapShow;
	//�������ֵı�ʶ�����̷������Ƿ������ӵı�ʶ��
	private boolean isOdd,isExist;

	public ChessModel() {} 

	//�ù��췽�����ݲ�ͬ������ģʽ��modeChess����������Ӧ��С������
	public ChessModel(int modeChess){
	   this.isOdd=true;
	   if(modeChess == 1){
	    PanelInit(20, 15, modeChess);
	   }
	   if(modeChess == 2){
	    PanelInit(30, 20, modeChess);
	   }
	   if(modeChess == 3){
	    PanelInit(40, 30, modeChess);
	   }
	}

	//��������ģʽ�������̴�С
	private void PanelInit(int width, int height, int modeChess){
	   this.width = width;
	   this.height = height;
	   this.modeChess = modeChess;
	   arrMapShow = new int[width+1][height+1];
	   for(int i = 0; i <= width; i++){
	    for(int j = 0; j <= height; j++){
	     arrMapShow[i][j] = -5;
	    }
	   }
	}

	//��ȡ�Ƿ񽻻����ֵı�ʶ��
	public boolean getisOdd(){
	   return this.isOdd;
	}

	//���ý������ֵı�ʶ��
	public void setisOdd(boolean isodd){
	   if(isodd)
	    this.isOdd=true;
	   else
	    this.isOdd=false;
	}

	//��ȡĳ���̷����Ƿ������ӵı�ʶֵ
	public boolean getisExist(){
	   return this.isExist;
	}

	//��ȡ���̿��
	public int getWidth(){
	   return this.width;
	}

	//��ȡ���̸߶�
	public int getHeight(){
	   return this.height;
	}

	//��ȡ����ģʽ
	public int getModeChess(){
	   return this.modeChess;
	}

	//��ȡ���̷��������ӵ���Ϣ
	public int[][] getarrMapShow(){
	   return arrMapShow;
	}

	//�ж����ӵĺ������������Ƿ�Խ��
	private boolean badxy(int x, int y){
	   if(x >= width+20 || x < 0)
	    return true;
	   return y >= height+20 || y < 0;
	}

	//����������ĳһ�����ϰ˸��������ӵ����ֵ��
	//��˸�����ֱ��ǣ����ҡ��ϡ��¡����ϡ����¡����ϡ�����
	public boolean chessExist(int i,int j){
	   if(this.arrMapShow[i][j]==1 || this.arrMapShow[i][j]==2)
	    return true;
	   return false;
	}

	//�жϸ�����λ���Ƿ��������
	public void readyplay(int x,int y){
	   if(badxy(x,y))
	    return;
	   if (chessExist(x,y))
	    return;
	   this.arrMapShow[x][y]=3;
	}

	//�ڸ�����λ��������
	public void play(int x,int y){
	   if(badxy(x,y))
	    return;
	   if(chessExist(x,y)){
	    this.isExist=true;
	    return;
	   }else
	    this.isExist=false;
	   if(getisOdd()){
	    setisOdd(false);
	   this.arrMapShow[x][y]=1;
	   }else{
	    setisOdd(true);
	    this.arrMapShow[x][y]=2;
	   }
	}

	//���������
	/*
	*˵��������ٷ��ж�ÿһ���������ĸ�����ĵ������������
	*���ó����������ֵ�����꣬����
	**/
	public void computerDo(int width,int height){
	   int max_black,max_white,max_temp,max=0;
	   setisOdd(true);
	   System.out.println("��������� ...");
	   for(int i = 0; i <= width; i++){
	    for(int j = 0; j <= height; j++){
	     if(!chessExist(i,j)){//�㷨�ж��Ƿ�����
	      max_white=checkMax(i,j,2);//�жϰ��ӵ����ֵ
	      max_black=checkMax(i,j,1);//�жϺ��ӵ����ֵ
	      max_temp=Math.max(max_white,max_black);
	      if(max_temp>max){
	       max=max_temp;
	       this.x=i;
	       this.y=j;
	      }
	     }
	    }
	   }
	   setX(this.x);
	   setY(this.y);
	   this.arrMapShow[this.x][this.y]=2;
	}

	//��¼�������Ӻ�ĺ�������
	public void setX(int x){
	   this.x=x;
	}

	//��¼�������Ӻ����������
	public void setY(int y){
	   this.y=y;
	}

	//��ȡ�������ӵĺ�������
	public int getX(){
	   return this.x;
	}

	//��ȡ�������ӵ���������
	public int getY(){
	   return this.y;
	}

	//����������ĳһ�����ϰ˸��������ӵ����ֵ��
	//��˸�����ֱ��ǣ����ҡ��ϡ��¡����ϡ����¡����ϡ�����
	public int checkMax(int x, int y,int black_or_white){
	   int num=0,max_num,max_temp=0;
	   int x_temp=x,y_temp=y;
	   int x_temp1=x_temp,y_temp1=y_temp;
	   //judge right
	   for(int i=1;i<5;i++){
	    x_temp1+=1;
	    if(x_temp1>this.width)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   //judge left
	   x_temp1=x_temp;
	   for(int i=1;i<5;i++){
	    x_temp1-=1;
	    if(x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   if(num<5)
	    max_temp=num;

	   //judge up
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=0;
	   for(int i=1;i<5;i++){
	    y_temp1-=1;
	    if(y_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   //judge down
	   y_temp1=y_temp;
	   for(int i=1;i<5;i++){
	    y_temp1+=1;
	    if(y_temp1>this.height)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   if(num>max_temp&&num<5)
	    max_temp=num;

	   //judge left_up
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=0;
	   for(int i=1;i<5;i++){
	    x_temp1-=1;
	    y_temp1-=1;
	    if(y_temp1<0 || x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   //judge right_down
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   for(int i=1;i<5;i++){
	    x_temp1+=1;
	    y_temp1+=1;
	    if(y_temp1>this.height || x_temp1>this.width)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   if(num>max_temp&&num<5)
	    max_temp=num;

	   //judge right_up
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=0;
	   for(int i=1;i<5;i++){
	    x_temp1+=1;
	    y_temp1-=1;
	    if(y_temp1<0 || x_temp1>this.width)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   //judge left_down
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   for(int i=1;i<5;i++){
	    x_temp1-=1;
	    y_temp1+=1;
	    if(y_temp1>this.height || x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   if(num>max_temp&&num<5)
	    max_temp=num;
	   max_num=max_temp;
	   return max_num;
	}

	//�ж�ʤ��
	public boolean judgeSuccess(int x,int y,boolean isodd){
	   int num=1;
	   int arrvalue;
	   int x_temp=x,y_temp=y;
	   if(isodd)
	    arrvalue=2;
	   else
	    arrvalue=1;
	   int x_temp1=x_temp,y_temp1=y_temp;
	   //�ж��ұ�
	   for(int i=1;i<6;i++){
	    x_temp1+=1;
	    if(x_temp1>this.width)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   //�ж����
	   x_temp1=x_temp;
	   for(int i=1;i<6;i++){
	    x_temp1-=1;
	    if(x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   if(num==5)
	    return true;

	   //�ж��Ϸ�
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=1;
	   for(int i=1;i<6;i++){
	    y_temp1-=1;
	    if(y_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   //�ж��·�
	   y_temp1=y_temp;
	   for(int i=1;i<6;i++){
	    y_temp1+=1;
	    if(y_temp1>this.height)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   if(num==5)
	    return true;

	   //�ж�����
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=1;
	   for(int i=1;i<6;i++){
	    x_temp1-=1;
	    y_temp1-=1;
	    if(y_temp1<0 || x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   //�ж�����
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   for(int i=1;i<6;i++){
	   x_temp1+=1;
	   y_temp1+=1;
	   if(y_temp1>this.height || x_temp1>this.width)
	    break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   if(num==5)
	    return true;

	   //�ж�����
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=1;
	   for(int i=1;i<6;i++){
	    x_temp1+=1;
	    y_temp1-=1;
	    if(y_temp1<0 || x_temp1>this.width)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   //�ж�����
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   for(int i=1;i<6;i++){
	    x_temp1-=1;
	    y_temp1+=1;
	    if(y_temp1>this.height || x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   if(num==5)
	    return true;
	   return false;
	}

	}