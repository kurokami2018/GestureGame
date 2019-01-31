package application;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import ctrl.BattleModeController;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public class OpenCV extends BattleModeController implements Runnable{
    static int camController = 1;//1でオン、0でおふ

    @FXML private ImageView player1;//初期化せずともfxmlのfx:idと同じ名前をつけておけば勝手に中身が入るって
    @FXML private ImageView player2;

    public static Fighter RFighter,LFighter;
    final double defaultXOfR = 133;
    final double defaultXOfL = 0;

    int[] cR ={0,0,255};//描写する矩形の色、右目
    int[] cL = {50,50,50};//描写する矩形の色、左目
    int[] cM = {225,0,0};//描写する矩形の色、口

    @FXML
    public void run(){
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);//OpenCVを使うために必ず入れる一行

    	VideoCaptureModule vcm = new VideoCaptureModule("s");//映像取得モジュールのインスタンス化
    	//FaceDetection fd = new FaceDetection();//顔検出モジュールのインスタンス化
    	MyImageProcessing mip = new MyImageProcessing();//画像処理モジュールのインスタンス化

    	MatOfRect RP_REye,RP_LEye,LP_REye,LP_LEye,RP_Mouth,LP_Mouth;
    	R_EyeDetection reye = new R_EyeDetection();//右目検出モジュールのインスタンス化
    	L_EyeDetection leye = new L_EyeDetection();//左目検出モジュールのインスタンス化
    	MouthDetection mouth = new MouthDetection();//口検出モジュールのインスタンス化

    	RFighter = new Fighter(defaultXOfR);
    	LFighter = new Fighter(defaultXOfL);

    	Mat image;
    	Rect[] imageR_rect=new Rect[4];
    	Rect[] imageL_rect=new Rect[4];

    	while(vcm.isCameraOpened()){
    		image = vcm.getFrameFromCamera(); //カメラ映像から画像を一枚取り出す
	    	//---------------------------
    		//カメラ画像の縮小（処理高速化のため）
    		double ratio = 0.5; //入力画像を縮小する割合
    		Imgproc.resize(image, image, new Size(0, 0), ratio, ratio);
    		//---------------------------
    		//鮮度平滑化
    		Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
    		Imgproc.equalizeHist(image, image);
    		//----------------------------
    		imageR_rect[3]=new Rect(image.cols()/2-1,0,image.cols()/2,image.rows());//画面から見て右側の人の領域を定義
    		imageL_rect[3]=new Rect(0,0,image.cols()/2,image.rows());//画面から見て左側の人の領域を定義
    		imageR_rect[0]=new Rect(image.cols()/2-1,0,image.cols()/2,image.rows());//画面から見て右側の人の領域を定義
    		imageL_rect[0]=new Rect(0,0,image.cols()/2,image.rows()/2);//画面から見て左側の人の領域を定義
    		/* 領域を1/4に分けてます */
    		imageR_rect[1]=new Rect(image.cols()/2,0,image.cols()/4,image.rows()/2);//画面から見て右側の人の上領域を定義
    		imageR_rect[2]=new Rect(image.cols()*3/4,0,image.cols()/4,image.rows()/2);//画面から見て右側の人の上領域を定義
    		imageL_rect[1]=new Rect(0,0,image.cols()/4,image.rows()/2);//画面から見て左側の人の上領域を定義
    		imageL_rect[2]=new Rect(image.cols()/4,0,image.cols()/4,image.rows()/2);//画面から見て左側の人の上領域を定義

    		//************************   画面からみて右側のプレイヤーの検知  ***************************
    		//
    		//MatOfRect morR = fd.execFaceDetection(image.submat(imageR_rect)); //顔検出を実行
    		RP_REye=reye.execEyeDetection(image.submat(imageR_rect[2]));//右目検知を実行
    		RP_LEye=leye.execEyeDetection(image.submat(imageR_rect[1]));//左目検知を実行
    		RP_Mouth = mouth.execMouthDetection(image.submat(imageR_rect[3]));
    		if(RP_REye.empty()) {//もし右目が隠されたのが検知されたら...の処理を書くところ
    			RFighter.move(-5);
    		}
    		if(RP_LEye.empty()) {//もし左目が隠されたのが検知されたら...の処理を書くところ
    			RFighter.move(5);
    		}

    		if(RP_Mouth.empty() && gun_R == false) {
    			fR = new FireBall(180,RFighter.getY());
    			fireBall_R.setTranslateX(fR.getX());
    			fireBall_R.setTranslateY(fR.getY());
    			fireBall_R.setOpacity(1);
    			gun_R=true;
    			//RFighter.shoot();//もし口が隠されたのが検知されたら...の処理を書くところ
    		}
    		RP_REye=reye.execEyeDetection(image.submat(imageR_rect[0]));//右目検知(描画用データ)
    		RP_LEye=leye.execEyeDetection(image.submat(imageR_rect[0]));//左目検知(描画用データ)
    		//mip.drawDetectionResultsByShiftingHalf(image, morR); //顔位置に黒い矩形を描画
    		mip.drawDetectionResultsByShiftingHalf(image, RP_REye,cR);//右目位置に黒色の矩形を描写
    		mip.drawDetectionResultsByShiftingHalf(image, RP_LEye,cL);//左目位置に灰色の矩形を描写
    		mip.drawDetectionResultsByShiftingHalf(image, RP_Mouth,cM);//口位置に白の矩形を描写

    		//************************   画面からみて左側のプレイヤーの検知  ***************************
    		//
    		//MatOfRect morL = fd.execFaceDetection(image.submat(imageL_rect)); //顔検出を実行
    		LP_REye=reye.execEyeDetection(image.submat(imageL_rect[1]));//右目検知を実行
    		LP_LEye=leye.execEyeDetection(image.submat(imageL_rect[2]));//左目検知を実行
    		LP_Mouth = mouth.execMouthDetection(image.submat(imageL_rect[3]));
    		if(LP_REye.empty()) {//もし右目が隠されたのが検知されたら...の処理を書くところ
    			LFighter.move(-5);
    		}
    		if(LP_LEye.empty()) {//もし左目が隠されたのが検知されたら...の処理を書くところ
    			LFighter.move(5);
    		}
    		if(LP_Mouth.empty() && gun_L == false) {
        			fL = new FireBall(-180,LFighter.getY());
        			fireBall_L.setTranslateX(fL.getX());
        			fireBall_L.setTranslateY(fL.getY());
        			fireBall_L.setOpacity(1);
        			gun_L=true;
        			//LFighter.shoot();//もし口が隠されたのが検知されたら...の処理を書くところ
        		}

    		LP_REye=reye.execEyeDetection(image.submat(imageL_rect[0]));//右目検知(描画用データ)
    		LP_LEye=leye.execEyeDetection(image.submat(imageL_rect[0]));//左目検知(描画用データ)
    		//mip.drawDetectionResults(image, morL); //顔位置に矩形を描画
    		mip.drawDetectionResults(image, LP_REye,cR);//右目位置に黒色の矩形を描写
    		mip.drawDetectionResults(image, LP_LEye,cL);//左目位置に灰色の矩形を描写
    		mip.drawDetectionResults(image, LP_Mouth,cM);//口位置に白の矩形を描写

    		//**********************************************************************************
    		MoveOfFighter();

    		vcm.showImage(image);//色々処理された後のimage(カメラから取得された画像)を描写する
    		if(camController != 1)break;//camControllerが1に設定されたらカメラを止める
    		if(image.empty())break;//もしカメラがうまく起動しなかった時にカメラを止める
    		int key = vcm.getInputKey();//カメラ起動中にキーボードに入力されたキーを取得
    		if(key == 81) { break; }//もしqのキーだったら

    		if(gun_R) {
    			fR.setX(-30);
    			fireBall_R.setLayoutX(fR.getX());
    			if(fR.getX()<-240) {
    				fireBall_R.setOpacity(0);
    	   			fireBall_R.setTranslateX(fR.getX());
        			fireBall_R.setTranslateY(fR.getY());
        			gun_R=false;
    			}
    			Bounds boundsPlayer2 = player2.getBoundsInParent();
    			Bounds boundsFireBallL_1 = fireBall_L.getBoundsInParent();
    			if(boundsPlayer2.intersects(boundsFireBallL_1)) {
    				if(LFighter.life>=0)RFighter.life-=1;
    				System.out.println("右側のプレイヤーが被弾しました。残機は...");
    				System.out.println(RFighter.getLife());
    			}
    		}
    		if(gun_L) {
    			fL.setX(30);
    			fireBall_L.setLayoutX(fL.getX());
    			if(fL.getX()>240) {
    				fireBall_L.setOpacity(0);
    	   			fireBall_L.setTranslateX(fL.getX());
        			fireBall_L.setTranslateY(fL.getY());
        			gun_L=false;
    			}
    			Bounds boundsPlayer1 = player1.getBoundsInParent();
    			Bounds boundsFireBallR_1 = fireBall_R.getBoundsInParent();
    			if(boundsPlayer1.intersects(boundsFireBallR_1)) {
    				if(LFighter.life>=0)LFighter.life-=1;
    				System.out.println("左側のプレイヤーが被弾しました。残機は...");
    				System.out.println(LFighter.getLife());
    			}
    		}
    		setLife();
    	}
    	vcm.stopVideoCapture();
    }
    public Fighter getRFighter() {return RFighter;}
    public Fighter getLFighter() {return LFighter;}
	@FXML
	public void MoveOfFighter() {
		player2.setLayoutY(RFighter.getY());
		player1.setLayoutY(LFighter.getY());
	}
	@FXML
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
}
