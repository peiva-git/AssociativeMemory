package nn;

import processing.core.PApplet;
import processing.core.PImage;

public class AMem extends PApplet{

	public static void main(String[] args) {
		PApplet.main("nn.AMem");

	}
	
	static final int imageResX = 60;
	static final int imageResY = 60;
	static final int imagesNumber = 1;
	private static PImage[] images = new PImage[imagesNumber];
	
	static final int scaleFactor = 10;
	static final int windowSizeX = scaleFactor * imageResX;
	static final int windowSizeY = scaleFactor * imageResY;
	
	PImage testImage;
	
	private static Neuron[] network;
	static final int neuronNumber = imageResX * imageResY * 24; //RGB
	static final int dimInputSpace = neuronNumber;
	
	static final int k = neuronNumber / 2;
	
	/*
	 * 
	 * n = 60 * 60 * 24 bit = 86400
	 * senza valore alpha
	 */
	
	public void settings() {
		size(windowSizeX, windowSizeY);
		
	}
	
	
	public void setup() {
		
		//example
		testImage = loadImage("/home/peiva/Downloads/nnTestImage_small.jpeg");
		testImage.loadPixels();
		//end example
		
		int mask = 0x00ffffff;
		for (int i = 0; i < testImage.pixels.length; i += 1) {
			testImage.pixels[i] = testImage.pixels[i] & mask;
		}

		images[0] = testImage;
		
		Network net;
		try {
			net = new Network(1, neuronNumber);
			network = net.get1Dnetwork();
			for (int i = 0; i < network.length; i++) {
				
				network[i] = new Neuron(dimInputSpace, 1, "discrete weights");
			}
			
			saveImages();
			
		} catch (Exception e) {
			println(e.getMessage());
		}
		
	}
	
	public void draw() {
		
		scale(scaleFactor);
		image(testImage, 0, 0);
		testImage.loadPixels();
		
		
		
	}
	
	private void saveImages() throws Exception {
		
		float newWeight = 0;
		for (int j = 0; j < network.length; j++) {
			for (int i = 0; i < dimInputSpace; i++) {
				for (int e = 0; e < imagesNumber; e++) {
					
					images[e].loadPixels();
					newWeight += (images[e].pixels[i] * images[e].pixels[j]) / neuronNumber;
					// errato, maschera per modifica singolo bit necessaria
				}
				if (i == j) {
					network[j].setWeight(0, i); // pesi minori per spazio (teoricamente n * n bit per matrice dei pesi)
				}
				network[j].setWeight(newWeight, i);
			}
		}
		println("[saveImages]: Image saved");
		
		enhanceWeights(); //più iterazioni??
	}
	
	private void enhanceWeights() throws Exception {
		
		float normalization = 0;
		float stabilityCond = 0;
		float weightVariation = 0;
		
		for (int j = 0; j < network.length; j++) {
			for (int i = 0; i < dimInputSpace; i++) {
				
				normalization += sq(network[j].getWeights()[i]);
			}
		}
		
		normalization = sqrt(normalization);
		
		for (int ex = 0; ex < imagesNumber; ex++) {
			for (int j = 0; j < network.length; j++) {
				for (int i = 0; i < dimInputSpace; i++) {
					for (int k = 0; k < dimInputSpace; k++) {
						
						stabilityCond += (images[ex].pixels[i] * network[j].getWeights()[k] * images[ex].pixels[k]);
					}
					
					stabilityCond = (2 * k * (imagesNumber / neuronNumber)) - (stabilityCond / normalization);
					if ((stabilityCond > 0) && i != j) {
						
						weightVariation = (images[ex].pixels[i] * images[ex].pixels[j]) / neuronNumber;
					}
					
					network[j].setWeight(network[j].getWeights()[i] + weightVariation, i);
				}
			}
		}
		println("[enhanceWeights]: enhance complete");
	}
	

}
