package nn;

import processing.core.PApplet;

public class AMem  extends PApplet{

	public static void main(String[] args) {
		PApplet.main("nn.AMem");

	}
	
	static final int windowSizeX = 640;
	static final int windowSizeY = 640;
	
	
	public void settings() {
		size(windowSizeX, windowSizeY);
		
	}
	
	public void setup() {
		
	}
	
	public void draw() {
		square(320, 320, 400);
		
	}

}
