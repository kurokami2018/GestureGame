package application;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;

public class MouthDetection {
	public MouthDetection() {
	}


	//------------------------------
	//口検出を実行する
	// 検出結果（画像上の座標位置）を返す
	//@param input_img 入力画像
	//------------------------------
	public MatOfRect execMouthDetection(Mat input_img) {

		CascadeClassifier eyeDetector = new CascadeClassifier("haarcascade_mcs_mouth.xml");
		MatOfRect mouthDetections = new MatOfRect();
		eyeDetector.detectMultiScale(input_img, mouthDetections);

		return mouthDetections;
	}
}
