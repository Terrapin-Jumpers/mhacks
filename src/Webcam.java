import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class Webcam {
	
	public Webcam() {
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

		/* No difference
	    camera.release();
		 */

		System.out.println("Captured Frame Width " + frame.width());
		
		//CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath());
	    //Mat image = Highgui.imdecode(frame, 0);
		
		Highgui.imwrite("camera.jpg", frame);
		System.out.println("OK");
	}
	
	
	public static void main (String args[]){
		new Webcam();
	}
}