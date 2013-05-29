package android.game.backgammon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class IndexActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	public static boolean playerFirst = true;//是否玩家先手
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findButtonsAndAddListeners();
    }
    
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_newgame:
			startNewGame();//开始新游戏
			break;
		case R.id.btn_about:
			showAboutDialog();//显示“关于”信息
			break;
		case R.id.btn_exit:
			quitGame();//退出游戏
			break;
		}
		
	}
	private void findButtonsAndAddListeners(){
        View btnNewGame = this.findViewById(R.id.btn_newgame);
        View btnAbout = this.findViewById(R.id.btn_about);
        View btnExit = this.findViewById(R.id.btn_exit);
        btnNewGame.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnExit.setOnClickListener(this);
	}
    private void startNewGame(){
    	Intent intent = new Intent(this,BackgammonActivity.class);
    	startActivity(intent);    	
    }
    
    private void showAboutDialog(){
    	Intent intent = new Intent(this,AboutActivity.class);
    	startActivity(intent);
    }
    private void quitGame(){
    	finish();
    }
}