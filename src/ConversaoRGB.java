import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class ConversaoRGB{
	long startTime = System.currentTimeMillis();
	int[] greyList;
	int pixelCounter = 0;
	int graylevel;

	
	public void starter(){
		try {
			System.out.println("Loading Image");
			File f = new File("Castle.jpg");
			BufferedImage img = ImageIO.read(f);
			this.run(img);
		}
		catch (IOException e) {
		}
		}
	
	
	public void run(BufferedImage img){
		try {
			this.makeGray(img);
			File outputfile = new File("NewCastle2.jpg");
			ImageIO.write(img, "jpg", outputfile);
			System.out.println("New Image Created");
		}
		catch (IOException e) {
		}
		}
	
	private BufferedImage makeGray(BufferedImage img)
	{
		greyList = new int[img.getWidth()*img.getHeight()];
		
		int i = img.getWidth();
		int o = img.getHeight();
		

		System.out.println("Turning Image");
		
		img.getRGB(0, 0, i, o, greyList, 0, i);
		long startTime = System.currentTimeMillis();
	    for (int y = 0; y < greyList.length; ++y){
	    		greyList[y] = this.grayGenerator(greyList[y]);
	    }
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		double seconds = totalTime / 1000.0;
		System.out.println("Total time: "+seconds+" seconds");
	    img.setRGB(0, 0, i, o, greyList, 0, i);
		
	   return img;
	}

	public int grayGenerator(int rgb){
		int r = (rgb >> 16)&0xff;
		int g = (rgb >> 8)&0xff;
		int b = (rgb)&0xff;
		
		
		int red = (int) ((r * 3.2406)+(g*-1.5372)+ (b *-0.4986));
		int green = (int) ((r *-0.9689)+(g* 1.8758)+ (b *0.0415));
		int blue = (int) ((r * 0.0557)+(g*-0.2040)+ (b *1.0570));
		
		
		int graylevel = (int) ((red*0.2126) + (green*0.7152) + (blue*0.0722)); 
		
		return (graylevel << 16) + (graylevel << 8) + graylevel; 
	}
}
