import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class Webcam {	
	VideoCapture camera;
	
	final static int TIME_COUNT = 10;
	int prevCount;
	int time = TIME_COUNT;
	
	int cutoff = -1;
	
	public Webcam() {
		System.out.println("Loading OpenCV...");
		
		System.loadLibrary("opencv_java246"); // Load the native library.

		camera = new VideoCapture(0);
		//wait for camera to startup
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		if (!camera.isOpened()){
			System.out.println("Camera Error!");
		} else{
			System.out.println("Camera seems OK.");
		}
	}
	
	public void captureFrame(String url) {
		Mat frame = new Mat();
		camera.read(frame);
		System.out.println("Frame Obtained. Captured Frame Width " + frame.width());
		Highgui.imwrite(url, frame);
	}
	
	public Mat getFrame() {
		Mat frame = new Mat();
		camera.grab();
		//System.out.println("Frame Grabbed. Captured Frame Width " + frame.width());
		camera.retrieve(frame);
		return frame;
	}
	
	public int getNumFaces(Mat frame) {
		int count = 0;
		
		CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath());
	    MatOfRect faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(frame, faceDetections);
	    
	    for (Rect rect : faceDetections.toArray()) {
	        Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
	    }
	    
	    count = faceDetections.toArray().length;
	    /*faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_profileface.xml").getPath());
	    faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(frame, faceDetections);*/
	    
	    if (time > 0 && prevCount != count) {
	    	prevCount = count;
	    	time = TIME_COUNT;
	    } else if (count > 0 && time > -1){
	    	time --;
	    }
	    
	    return count;// + faceDetections.toArray().length;
	}
	
	public int getFinalFaces() {
		return (time < 0) ? prevCount : -1;
	}
	
	public BufferedImage matToBufferedImage(Mat mat) {
		MatOfByte matOfByte = new MatOfByte();
		
		Highgui.imencode(".jpg", mat, matOfByte); 

	    byte[] byteArray = matOfByte.toArray();
	    BufferedImage bufImage = null;

	    try {
	        InputStream in = new ByteArrayInputStream(byteArray);
	        bufImage = ImageIO.read(in);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return bufImage;
	}
	
	/*public static void main (String args[]){
		Webcam webcam = new Webcam();
		Mat frame = webcam.getFrame();
		System.out.println("NUMBER OF FACES DETECTED: " + webcam.getNumFaces(frame));
	}*/
}