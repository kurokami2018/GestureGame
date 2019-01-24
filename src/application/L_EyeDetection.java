package application;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;

public class L_EyeDetection {
	public L_EyeDetection() {
	}


	//------------------------------
	//右目検出を実行する
	// 検出結果（画像上の座標位置）を返す
	//@param input_img 入力画像
	//------------------------------
	public MatOfRect execEyeDetection(Mat input_img) {

		CascadeClassifier eyeDetector = new CascadeClassifier("haarcascade_lefteye_2splits.xml");
		MatOfRect eyeDetections = new MatOfRect();
		eyeDetector.detectMultiScale(input_img, eyeDetections);

		return eyeDetections;
	}

}
