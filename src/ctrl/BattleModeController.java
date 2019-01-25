package ctrl;

import application.OpenCV;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	OpenCV opencvThread=new OpenCV();
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../application/BattleMode.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
		opencvThread.start();
	}
	public static void main(String args[]) {
		launch(args);
	}


}
