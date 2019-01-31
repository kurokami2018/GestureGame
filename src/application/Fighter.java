package application;

import java.util.ArrayList;
import java.util.List;

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
	final static public double defaultY = 30;//133;

	int life=0; //残機
	double x=0,y=defaultY;
	//public ImageView img;
	public List<FireBall> list = new ArrayList<FireBall>();
	public double survivalTime=0.0;
	long generatedTime=0;

	public Fighter(double x){//コンストラクタ
		life=3;
		this.x=x;
		this.y=defaultY;
		this.generatedTime=System.currentTimeMillis();
	}
	public void setSurvivalTime() {
		long now=System.currentTimeMillis();
		this.survivalTime=now-generatedTime;
	}
	public int getLife() { return life; }
	public void setY(double y) {this.y=y;}
	public double getY() { return y; }
	public double getX() {return x;}
	public int judge() { //当たり判定
		int judge=0; //0:なし 1:当たり

		return judge;
	}
	public void move(double Y) {
		this.y=y+Y;
	}
	public int decision() { //勝敗決定
		int decision=0; //0:lose 1:win
		if(getLife() != 0) decision=1;
		return decision;
	}
	public void shoot() {
		list.add(new FireBall(getX(),getY()));
		/*
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		   int t=1;//消滅するまでの時間
		   service.schedule(() -> {
		    //弾を削除
		    service.shutdown();
		   }, t, TimeUnit.SECONDS);


		   while (true) {
		    int speed=10;//1秒ごとに動かすx座標
		    Thread.sleep(1000);//ms
		    if(player==1) x+=speed;
		    else if(player==2) x-=speed;
		    else throw new IllegalArgumentException("non-existent player");
		   }

		  }catch(InterruptedException e) {
		   e.printStackTrace();
		  }*/
	}
}