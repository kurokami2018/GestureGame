package application;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetection {

	//******************************
	//フィールド
	//******************************
	//none

	//******************************
	//メソッド
	//******************************

	//------------------------------
	//コンストラクタ
	//------------------------------
	public FaceDetection() {
	}

	//------------------------------
	//顔検出を実行する
	// 検出結果（画像上の座標位置）を返す
	//@param input_img 入力画像
	//------------------------------
	public MatOfRect execFaceDetection(Mat input_img) {

		CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(input_img, faceDetections);

		return faceDetections;
	}
}
