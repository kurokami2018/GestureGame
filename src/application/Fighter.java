package application;
/*
 * ・メンバ
 *    残機
 * ・メソッド
 *    初期化メソッド
 *    座標移動メソッド
 *    当たり判定メソッド
 *    勝敗決定メソッド
 */
public class Fighter {
	int life=0; //残機
	int x=0,y=0;
	public Fighter(int a, int x, int y) { //初期化	
		this.life=a;
		this.x=x; //x座標
		this.y=y; //y座標
	}
	public int getLife() { return life; }
	public void move(int i) { //座標移動
		y += y; //y座標
	}
	
	public int judge() { //当たり判定
		int judge=0; //0:なし 1:当たり
		
		return judge;
	}
	public int decision() { //勝敗決定
		int decision=0; //0:lose 1:win
		if(getLife() != 0) decision=1;                                                                      
		return decision;
	}
}