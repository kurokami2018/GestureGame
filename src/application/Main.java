package application;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
	static int camController = 1;//1でオン、0でおふ

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("BattleMode.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//launch(args);
		Detection();
	}


	public static void Detection() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);//OpenCVを使うために必ず入れる一行
		VideoCaptureModule vcm = new VideoCaptureModule("s");//映像取得モジュールのインスタンス化
		//FaceDetection fd = new FaceDetection();//顔検出モジュールのインスタンス化
		MyImageProcessing mip = new MyImageProcessing();//画像処理モジュールのインスタンス化
		MatOfRect RP_REye,RP_LEye,LP_REye,LP_LEye;
		R_EyeDetection reye = new R_EyeDetection();
		L_EyeDetection leye = new L_EyeDetection();
		Mat image;
		Rect imageR_rect,imageL_rect;

		while(vcm.isCameraOpened())
		{
			image = vcm.getFrameFromCamera(); //カメラ映像から画像を一枚取り出す

			imageR_rect=new Rect(image.cols()/2-1,0,image.cols()/2,image.rows());//画面から見て右側の人の領域を定義
			imageL_rect=new Rect(0,0,image.cols()/2,image.rows());//画面から見て左側の人の領域を定義
			//カメラの実験をすると以下のような結果に、
			//RGBということchannels()=3,2次元配列であるということで動画なら3になるはずdims()=2,行cols()=1280,列rows()=720



			//************************   画面からみて右側のプレイヤーの検知  ***************************
			//

			//MatOfRect morR = fd.execFaceDetection(image.submat(imageR_rect)); //顔検出を実行
			RP_REye=reye.execEyeDetection(image.submat(imageR_rect));//右目検知を実行
			RP_LEye=leye.execEyeDetection(image.submat(imageR_rect));//左目検知を実行
			if(!RP_REye.empty());//もし右目が検知されたら...の処理を書くところ
			if(!RP_LEye.empty());//もし左目が検知されたら...の処理を書くところ

			//mip.drawDetectionResultsByShiftingHalf(image, morR); //顔位置に矩形を描画
			mip.drawDetectionResultsByShiftingHalf(image, RP_REye);//右目位置に矩形を描写
			mip.drawDetectionResultsByShiftingHalf(image, RP_LEye);//左目位置に矩形を描写




			//************************   画面からみて左側のプレイヤーの検知  ***************************
			//

			//MatOfRect morL = fd.execFaceDetection(image.submat(imageL_rect)); //顔検出を実行
			LP_REye=reye.execEyeDetection(image.submat(imageL_rect));//右目検知を実行
			LP_LEye=leye.execEyeDetection(image.submat(imageL_rect));//左目検知を実行
			if(!LP_REye.empty());//もし右目が検知されたら...の処理を書くところ
			if(!LP_LEye.empty());//もし左目が検知されたら...の処理を書くところ

			//mip.drawDetectionResults(image, morL); //顔位置に矩形を描画
			mip.drawDetectionResults(image, LP_REye);//右目位置に矩形を描写
			mip.drawDetectionResults(image, LP_LEye);//左目位置に矩形を描写




			//*************************************************************************************



			vcm.showImage(image);//色々処理された後のimage(カメラから取得された画像)を描写する

			if(camController != 1)break;
			if(image.empty())break;
			int key = vcm.getInputKey();
			if(key == 81)break;

		}
		vcm.stopVideoCapture();

	}

}
