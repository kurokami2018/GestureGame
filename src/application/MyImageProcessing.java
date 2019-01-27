package application;



import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;



//******************************
//画像処理クラス
//
//処理：
//
//- 検出結果を描画する
//
//******************************


public class MyImageProcessing {


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
    public MyImageProcessing()
    {}


    //------------------------------
    //検出結果の座標位置に四角形を描画するメソッド
    //@param img 描画する画像
    //@param mor 顔領域を示すデータ（左上の(x,y)座標と幅と高さが格納されている）
    //------------------------------
    public void drawDetectionResults(Mat img, MatOfRect mor,int[] c)//cで色を指定
    {
	for(Rect rect : mor.toArray()) {

	    Imgproc.rectangle(img,  new Point(rect.x, rect.y),
			      new Point (rect.x+rect.width, rect.y+rect.height),
			      new Scalar (c[0],c[1],c[2]), 5);
	}
    }
    public void drawDetectionResultsByShiftingHalf(Mat img, MatOfRect mor,int[] c) {//cで色を指定
	for(Rect rect : mor.toArray()) {

	    Imgproc.rectangle(img,  new Point(rect.x+(img.cols()/2) , rect.y),
			      new Point (rect.x+(img.cols()/2) +rect.width, rect.y+rect.height),
			      new Scalar (c[0],c[1],c[2]), 5);//color
	}
    }
}
