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
		FaceDetection fd = new FaceDetection();//顔検出モジュールのインスタンス化
		MyImageProcessing mip = new MyImageProcessing();//画像処理モジュールのインスタンス化

		Mat image;
		Rect imageR_rect,imageL_rect;

		while(vcm.isCameraOpened())
		{
			image = vcm.getFrameFromCamera(); //カメラ映像から画像を一枚取り出す

	//		System.out.println(image.cols()/2-1 + ","+0+","+ image.cols()/2 +","+image.rows());

			imageR_rect=new Rect(image.cols()/2-1,0,image.cols()/2,image.rows());
			imageL_rect=new Rect(0,0,image.cols()/2,image.rows());
			//カメラの実験をすると以下のような結果に、
			//RGBということchannels()=3,2次元配列であるということで動画なら3になるはずdims()=2,行cols()=1280,列rows()=720

			MatOfRect morR = fd.execFaceDetection(image.submat(imageR_rect)); //顔検出を実行
			mip.drawDetectionResultsByShiftingHalf(image, morR); //顔位置に矩形を描画

			MatOfRect morL = fd.execFaceDetection(image.submat(imageL_rect)); //顔検出を実行
			mip.drawDetectionResults(image, morL); //顔位置に矩形を描画

			vcm.showImage(image);

			if(camController != 1)break;
			if(image.empty())break;
			int key = vcm.getInputKey();
			if(key == 81)break;

		}
		vcm.stopVideoCapture();

	}

}
