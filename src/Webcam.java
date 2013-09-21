import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class Webcam {
	
	VideoCapture camera;
	
	public Webcam() {
		System.out.println("Loading OpenCV...");
		
		System.loadLibrary("opencv_java246"); // Load the native library.

		camera = new VideoCapture(0);
		
		//wait for camera to startup
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		if(!camera.isOpened()){
			System.out.println("Camera Error!");
		}
		else{
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
		CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath());

	    MatOfRect faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(frame, faceDetections);
	    
	    return faceDetections.toArray().length;
	}
	
	public static void main (String args[]){
		Webcam webcam = new Webcam();
		Mat frame = webcam.getFrame();
		System.out.println("NUMBER OF FACES DETECTED: " + webcam.getNumFaces(frame));
	}
	
	/*public Webcam() {
		System.out.println("Hello, OpenCV");
		// Load the native library.
		System.loadLibrary("opencv_java246");

		VideoCapture camera = new VideoCapture(0);
		
		//let camera startup
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		//camera.open(0); //Useless - also crashes the thing
		if(!camera.isOpened()){
			System.out.println("Camera Error");
		}
		else{
			System.out.println("Camera OK?");
		}

		Mat frame = new Mat();

		//camera.grab();
		//System.out.println("Frame Grabbed");
		//camera.retrieve(frame);
		//System.out.println("Frame Decoded");

		camera.read(frame);
		System.out.println("Frame Obtained");

		// No difference
	    //camera.release();

		System.out.println("Captured Frame Width " + frame.width());
		
		//CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath());
	    //Mat image = Highgui.imdecode(frame, 0);
		
		Highgui.imwrite("camera.jpg", frame);
		System.out.println("OK");
	}*/
}