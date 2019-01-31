package application;

import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * 移動処理メソッド
 * (当たり時画像差し替えメソッド)
 * 画像と結びつけ
 */

public class FireBall {

	double x=0,y=0;
//	long generatedTime=0;
//	public ImageView imgV = new ImageView(new Image(Paths.get("././image/fireball.jpg").toUri().toString(), true));//url適当
	public Image img = new Image(Paths.get("././image/fireball.jpg").toUri().toString(), true);//url適当
	public FireBall(double x,double y) {//プレイヤーのy座標とプレイヤーのy座標を受け取る
		/*if(player==1) this.x=90;
		else if(player==2) this.x=490;
		else throw new IllegalArgumentException("non-existent player");*/
		this.x=x;
		this.y=y;//+25;
//		generatedTime=System.currentTimeMillis();
//		imgV.setLayoutY(y);
	}
//	public double getX(long now) {
//		return (int) ((now-generatedTime)*30);
//	}
	public double getX() {return x;}
	public void setX(double i) { x+=i;}
	public double getY() {return y;}

	/*Fighterクラスに移行
	public void shoot(int player) {

		try {
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
		}
	}*/
	/*
	void bomb() {//画像差し替え

	}*/
}