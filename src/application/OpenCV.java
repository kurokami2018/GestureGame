package application;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;



public class  OpenCV extends Thread{
	static int camController = 1;//1でオン、0でおふ


	public void run(){

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);//OpenCVを使うために必ず入れる一行
		VideoCaptureModule vcm = new VideoCaptureModule("s");//映像取得モジュールのインスタンス化
		//FaceDetection fd = new FaceDetection();//顔検出モジュールのインスタンス化
		MyImageProcessing mip = new MyImageProcessing();//画像処理モジュールのインスタンス化
		MatOfRect RP_REye,RP_LEye,LP_REye,LP_LEye,RP_Mouth,LP_Mouth;
		R_EyeDetection reye = new R_EyeDetection();//右目検出モジュールのインスタンス化
		L_EyeDetection leye = new L_EyeDetection();//左目検出モジュールのインスタンス化
		MouthDetection mouth = new MouthDetection();//口検出モジュールのインスタンス化

		Mat image;
		Rect imageR_rect,imageL_rect;

		while(vcm.isCameraOpened()){

			image = vcm.getFrameFromCamera(); //カメラ映像から画像を一枚取り出す

			//---------------------------
			//カメラ画像の縮小（処理高速化のため）
			double ratio = 0.5; //入力画像を縮小する割合
			//Imgproc.resize(image, image, new Size(0, 0), ratio, ratio);

			//---------------------------

			imageR_rect=new Rect(image.cols()/2-1,0,image.cols()/2,image.rows());//画面から見て右側の人の領域を定義
			imageL_rect=new Rect(0,0,image.cols()/2,image.rows());//画面から見て左側の人の領域を定義

			//************************   画面からみて右側のプレイヤーの検知  ***************************
			//

			//MatOfRect morR = fd.execFaceDetection(image.submat(imageR_rect)); //顔検出を実行
			RP_REye=reye.execEyeDetection(image.submat(imageR_rect));//右目検知を実行
			RP_LEye=leye.execEyeDetection(image.submat(imageR_rect));//左目検知を実行
			RP_Mouth = mouth.execMouthDetection(image.submat(imageR_rect));
			if(!RP_REye.empty());//もし右目が検知されたら...の処理を書くところ
			if(!RP_LEye.empty());//もし左目が検知されたら...の処理を書くところ
			if(!RP_Mouth.empty());//もし口が検知されたら...の処理を書くところ

			//mip.drawDetectionResultsByShiftingHalf(image, morR); //顔位置に矩形を描画
			mip.drawDetectionResultsByShiftingHalf(image, RP_REye);//右目位置に矩形を描写
			mip.drawDetectionResultsByShiftingHalf(image, RP_LEye);//左目位置に矩形を描写
			mip.drawDetectionResultsByShiftingHalf(image, RP_Mouth);//口位置に矩形を描写




			//************************   画面からみて左側のプレイヤーの検知  ***************************
			//

			//MatOfRect morL = fd.execFaceDetection(image.submat(imageL_rect)); //顔検出を実行
			LP_REye=reye.execEyeDetection(image.submat(imageL_rect));//右目検知を実行
			LP_LEye=leye.execEyeDetection(image.submat(imageL_rect));//左目検知を実行
			LP_Mouth = mouth.execMouthDetection(image.submat(imageL_rect));
			if(!LP_REye.empty());//もし右目が検知されたら...の処理を書くところ
			if(!LP_LEye.empty());//もし左目が検知されたら...の処理を書くところ
			if(!LP_Mouth.empty());//もし口が検知されたら...の処理を書くところ

			//mip.drawDetectionResults(image, morL); //顔位置に矩形を描画
			mip.drawDetectionResults(image, LP_REye);//右目位置に矩形を描写
			mip.drawDetectionResults(image, LP_LEye);//左目位置に矩形を描写
			mip.drawDetectionResults(image, LP_Mouth);//口位置に矩形を描写




			//*************************************************************************************

			vcm.showImage(image);//色々処理された後のimage(カメラから取得された画像)を描写する


			if(camController != 1)break;//camControllerが1に設定されたらカメラを止める
			if(image.empty())break;//もしカメラがうまく起動しなかった時にカメラを止める
			int key = vcm.getInputKey();//カメラ起動中にキーボードに入力されたキーを取得
			if(key == 81)break;//もしqのキーだったらカメラを止める

		}
		vcm.stopVideoCapture();


	}

}
