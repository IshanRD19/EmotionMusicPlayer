import java.io.Serializable;

import org.opencv.core.Core;


public class EmotionDetector {
	public static String mode = "Testing";
	private String emotionOfFace;	//Will be set after calculation of eigen vectors and comparisons.
	
	public static String imagePath;	//User input
	public static String emotion;		//User input
	GuiComponent gui = new GuiComponent();	//Remove in next phase.
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		new EmotionDetector().start();
	}
	
	public void setEmotion(String emotion) {
		emotionOfFace = emotion;
	}
	
	public void start() {
		getInput();
		imagePath = FeatureExtractor.testImagesLoc + imagePath;
		FeatureExtractor featureExtractor = new FeatureExtractor();
		System.out.println(imagePath);
		featureExtractor.start(imagePath);
		Covariance covariance = new Covariance();
		
		if(mode.equalsIgnoreCase("training")) {	//Training mode.	
			covariance.trainSystem(emotion);
		}
		else {	//Emotion Detection mode.
			emotionOfFace = covariance.detectEmotion();
//			covariance.getMeanSubtractedImage(imagePath, "");
			System.out.println("The emotion is: " + emotionOfFace);
		}
		
		if(!mode.equals("Training")) {
			gui.showResult(emotionOfFace);
		}
	}
	
	public void getInput() {
		//Set user inputs.
	//	imagePath = FeatureExtractor.testImagesLoc + "WP_20150124_13_35_53_Pro.jpg";
	//	emotion = "Sad";
	
		try {
			synchronized(gui) {
				gui.showGui();
				gui.wait();
			}
		} catch(Exception e) {e.printStackTrace();}
	}
}
