import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
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
	Rectangle[] faces = null;
	
	final static float TIME_SECONDS = 2f;
	long initTime = -1;
	int prevCount;
	long time = -1;
	boolean didFinishTime = false;
	
	private int width, height;
	
	private static final int MAX_HISTORY = 30;
	private int[] history = new int[MAX_HISTORY];
	private int historyIndex = 0;
	
	private static final float EPSILON = 0.05f; 
	
	public Webcam() {
		System.out.println("Loading OpenCV...");
		
		System.loadLibrary("opencv_java246"); // Load the native library.

		camera = new VideoCapture(0);
		//wait for camera to startup
		try {
			Thread.sleep(2000);
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
		if (width == 0) {
			width = frame.width();
			height = frame.height();
		}
		return frame;
	}
	
	public int getNumFaces(Mat frame) {
		int count = 0;
		
		CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath());
	    MatOfRect faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(frame, faceDetections);
	    
	    count = faceDetections.toArray().length;
	    faces = new Rectangle[count];
	    
	    int i = 0;
	    for (Rect rect : faceDetections.toArray()) {
	    	if ((double)(rect.width * rect.height)/(WebcamView.FULL_WIDTH * WebcamView.FULL_HEIGHT) > EPSILON) {
	    		Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
	    		faces[i++] = new Rectangle(WebcamView.FULL_WIDTH - (rect.x + rect.width), rect.y, rect.width, rect.height);
	    	} else System.out.println((double)(rect.width * rect.height)/(WebcamView.FULL_WIDTH * WebcamView.FULL_HEIGHT) + " is negligible");
	    }
	    
	    if (historyIndex < MAX_HISTORY) {
	    	history[historyIndex] = i;
	    	historyIndex ++;
	    	
	    	return -1;
	    } else {
	    	int total = 0;
	    	for (int j = MAX_HISTORY - 2; j >= 0; j--) {
	    		total += history[j];
	    		history[j + 1] = history[j];
	    	}
	    	
	    	history[0] = i;
	    	total += i;
	    	
	    	total = total / MAX_HISTORY;
	    	
	    	if (total != prevCount) {
	    		prevCount = total;
	    	} else {
	    		didFinishTime = true;
	    		prevCount = total;
	    	}
	    	
	    	return total;
	    }
	    
	    
	    
	    //return count; // + faceDetections.toArray().length;
	}
	
	public int getFinalFaces() {
		return (didFinishTime) ? prevCount : -1;
	}
	
	public Rectangle[] getFaces() {
		return faces;
	}
	
	public BufferedImage matToBufferedImage(Mat mat) {
		MatOfByte matOfByte = new MatOfByte();
		
		Highgui.imencode(".jpg", mat, matOfByte); 

	    byte[] byteArray = matOfByte.toArray();
	    BufferedImage bufImage = null;

	    try {
	        InputStream in = new ByteArrayInputStream(byteArray);
	        bufImage = horizontalflip(ImageIO.read(in));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return bufImage;
	}
	
	public int getFrameWidth() {
		return width;
	}
	
	public int getFrameHeight() {
		return height;
	}
	
	public BufferedImage horizontalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return dimg;
    }
	
	/*public static void main (String args[]){
		Webcam webcam = new Webcam();
		Mat frame = webcam.getFrame();
		System.out.println("NUMBER OF FACES DETECTED: " + webcam.getNumFaces(frame));
	}*/
}