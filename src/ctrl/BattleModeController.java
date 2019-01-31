package ctrl;

import java.nio.file.Paths;

import application.Fighter;
import application.FireBall;
import application.OpenCV;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/*
 * このクラスはBattleMode.fxmlのコントローラで、何かイベントが起きた時にする処理を記述してあります。
 * ・右目が隠された時に右側に移動する処理
 * ・左目が隠された時に左側に移動する処理
 * ・口を隠した時に攻撃をする処理
 * ・攻撃が当たった時に残機を減らす処理
 * ・攻撃が当たって残基の数が0になった時に勝ちと負けを表示する処理
 * ・勝ち負けが決まって表示された時にもう一度プレイする選択肢を提示する処理
 */
public class BattleModeController extends Application{
	public static Fighter RFighter,LFighter;
	protected Scene scence;
	final double defaultXOfR = 133;
	final double defaultXOfL = 0;
	final double defaultY = 50;//133;
    public static boolean gun_R,gun_L;
    public FireBall fR,fL;
    @FXML                        
    protected ImageView fireBall_R;
    @FXML
	protected ImageView fireBall_L;
	@FXML private ImageView hp1;
	@FXML private ImageView hp2;
	@FXML private ImageView hp3;
	@FXML private ImageView hp4;
	@FXML private ImageView hp5;
	@FXML private ImageView hp6;

	@Override
	public void start(Stage primaryStage) throws Exception {
		OpenCV opencvThread=new OpenCV();
		RFighter = new Fighter(defaultXOfR);
		LFighter = new Fighter(defaultXOfL);
		// TODO 自動生成されたメソッド・スタブ
		try {
		//***************************************************
		primaryStage.setTitle("BattleMode");
		//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BattleMode.fxml"));
		//TitledPane root = fxmlLoader.load();
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../application/BattleMode.fxml"));
		loader.setController(opencvThread); //fxmlファイルのcontrollerとしてOpenCV.javaを読み込む
		TitledPane root = loader.load();
		scence = new Scene(root);
        primaryStage.setScene(scence);
		primaryStage.show();
		//***************************************************
		hp1=(ImageView)loader.getNamespace().get("hp1");
		hp2=(ImageView)loader.getNamespace().get("hp2");
		hp3=(ImageView)loader.getNamespace().get("hp3");
		hp4=(ImageView)loader.getNamespace().get("hp4");
		hp5=(ImageView)loader.getNamespace().get("hp5");
		hp6=(ImageView)loader.getNamespace().get("hp6");

		// ここのhandle(){の部分}に書いた処理は繰り返し実行され続ける。だいたい1秒に1回の頻度らしい。

	/*	new AnimationTimer() {
			@Override
			public void handle(long now) {
				// TODO 自動生成されたメソッド・スタブ
				if(RFighter.getLife()==0 || LFighter.getLife()==0)stop();
				//もしRFighterが攻撃していたら、火球を描写する
				for(FireBall fb:RFighter.list) {
					fb.imgV.setLayoutX(fb.getX(System.currentTimeMillis()));
				}

			}
		}.start();*/
		//*****************************************************
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(opencvThread);
		thread.start();//openCVのスレッドを開始する。
	}
	public void setLife() {
		int lLife=LFighter.getLife();
		if(lLife!=3) {
			if(lLife==2)hp1.setVisible(false);
			else if(lLife==1)hp2.setVisible(false);
			else {
				hp3.setVisible(false);
				//LFighterの負けが確定する
			}
		}
		int rLife=RFighter.getLife();
		if(rLife!=3) {
			if(rLife==2)hp4.setVisible(false);
			else if(rLife==1)hp5.setVisible(false);
			else {
				hp6.setVisible(false);
				//RFighterの負けが確定する
			}
		}
	}
	public static void main(String[] args) {//ここはもういじらない。
		launch(args);
	}

}














