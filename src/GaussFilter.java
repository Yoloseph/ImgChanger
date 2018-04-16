import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class GaussFilter{
	BufferedImage output;
	BufferedImage grey;
	BufferedImage noise;
	private int i;
	private int o;
	private double mean = 0;
	private double variance = 30;
	int[][] pixelValues;
	
    float[][] matrix = {
    		{0.066584f,	0.124871f,	0.066584f},
    		{0.124871f,	0.234181f,	0.124871f},
    		{0.066584f,	0.124871f,	0.066584f} 
        };


    
	public void run(){
			System.out.println("Creating noise in image");
			
			this.imgNoise();
			
			System.out.println("Bluring the image");
			
			this.imgBlur();

			System.out.println("All done");
	}

	
	private BufferedImage imgNoise(){
		 try {
			 File b = new File("NewCastle.jpg");
			grey = ImageIO.read(b);
			this.i = grey.getWidth();
			this.o = grey.getHeight();
			output = new BufferedImage(grey.getWidth(), grey.getHeight(), BufferedImage.TYPE_INT_RGB);
			File outputfile = new File("NoisyCastle.jpg");
			this.gaussianNoise(grey, output);
			ImageIO.write(output, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return this.output;
	}
	
	private BufferedImage imgBlur(){
		 try {
			 File n = new File("NoisyCastle.jpg");
			noise = ImageIO.read(n);
			this.i = noise.getWidth();
			this.o = noise.getHeight();
			pixelValues = new int[noise.getWidth()][noise.getHeight()];
			
			for(int x =0; x < i;x++){
				for(int y =0; y < o;y++){
					pixelValues[x][y] = noise.getRGB(x, y);
				}
			}
			
			long startTime1 = System.currentTimeMillis();
			this.gaussianBlur(noise);
			long endTime1   = System.currentTimeMillis();
			long totalTime1 = endTime1 - startTime1;
			double seconds1 = totalTime1 / 1000.0;
			System.out.println("Time for blur creation: "+seconds1+" seconds");
			File outputfile = new File("BlurCastle.jpg");
			
			ImageIO.write(this.gaussianBlur(noise), "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return noise;
	}
	
	
    private BufferedImage gaussianNoise(BufferedImage image, BufferedImage output) {
        Raster source = image.getRaster();
        WritableRaster out = output.getRaster();
          
        int bands  = out.getNumBands();
        Random rand = new java.util.Random();
          
        for (int w=0; w<this.i; w++) {
            for (int h=0; h<this.o; h++) {
                double gaussian = rand.nextGaussian();
                  
                for (int b=0; b<bands; b++) {
                    double gaussianMath = this.variance * gaussian + this.mean;
                    int pixelValue = source.getSample(w, h, b);
                    gaussianMath = gaussianMath + pixelValue;
                    if (gaussianMath < 0)   gaussianMath = 0.0;
                    if (gaussianMath > 255) gaussianMath = 255.0;
                      
                    out.setSample(w, h, b, (int)(gaussianMath));
                }
            }
        }
        return output;
    }
	
	public BufferedImage gaussianBlur(BufferedImage img){
		for (int x =0; x < i-1; x++){
			for (int y =0; y < o-1; y++){
				double r = 0;
				double g = 0;
				double b = 0;
				for(int z =0;z < this.matrix.length;z++){
					for(int w = 0; w < this.matrix.length;w++){
						if(x-1 > 0 && y-1 >0 && x+1 < this.i && y+1 < this.o){
							Color p = new Color(pixelValues[x-1+z][y-1+w]);
							r += p.getRed() * matrix[z][w];
							g += p.getGreen() * matrix[z][w];
							b += p.getBlue() * matrix[z][w];
						}
					}
				}
				int newtotal = ((int)r << 16) + ((int)g << 8) +(int)b;
				img.setRGB(x,y, newtotal);
			}
		}
		return img;
	}
	
}