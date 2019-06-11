package nn;

import processing.core.PApplet;
import processing.core.PImage;

public class AMem extends PApplet{

	public static void main(String[] args) {
		PApplet.main("nn.AMem");

	}
	
	static final int windowSizeX = 640;
	static final int windowSizeY = 640;
	
	
	PImage testImage;
	
	public void settings() {
		size(windowSizeX, windowSizeY);
		
	}
	
	float xoff = 0;
	
	public void setup() {
		testImage = loadImage("/home/peiva/Downloads/nnTestImage_small.jpeg");
		testImage.loadPixels();
		
	
	}
	
	public void draw() {
		image(testImage, 0, 0);
		background(255);
		/*
		xoff = xoff + .01f;
		float n = noise(xoff) * width;
		line(n, 0, n, height);
		*/
		println(noise(random(1), random(1))); //random value between 0 and 1 for every coordinate
		
	}

}
