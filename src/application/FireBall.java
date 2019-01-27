package application;

/*
 * 移動処理メソッド
 * (当たり時画像差し替えメソッド)
 * 画像と結びつけ
 */

public class FireBall {

	int x,y;

	public FireBall(int player,int y) {//プレイヤー番号とプレイヤーのy座標を受け取る
		if(player==1) this.x=90;
		else if(player==2) this.x=490;
		else throw new IllegalArgumentException("non-existent player");
		this.y=y+25;
	}
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