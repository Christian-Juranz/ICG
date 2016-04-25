package icg.rendering;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import icg.warmup.base.Painter;
import ogl.vecmath.Color;
import ogl.vecmath.Vector;

public abstract class Line implements Painter{
	/**
	 * the vertices of this line
	 */
	protected Vector p1, p2;

	/**
	 * constructor, setting the vertices of this line
	 * @param p1 vertex 1
	 * @param p2 vertex 2
	 */
	public Line(Vector p1, Vector p2){
		this.p1 = p1;
		this.p2 = p2;
	}

	/**
	 * Returns a list of pixels, which are part of the line. 
	 * This is the higher performance version of pixelColorAt 
	 * @param width width of the image
	 * @param height height of the image
	 * @return a list of pixels, which are part of the line
	 */
	public ArrayList<Pixel> getPixels(int width, int height){
		return null;
	}

	/**
	 * fills the given image
	 * @param img the image to be filled
	 */
	public void fillImage(BufferedImage img){
		int width = img.getWidth(), height = img.getHeight();
		ArrayList<Pixel> pixels = null;
		//fast version
		if ((pixels = getPixels(width, height)) != null){
			for (Pixel pixel : pixels){
				if (pixel.x >= 0 && pixel.x < width && pixel.y >= 0 && pixel.y < height)
					img.setRGB(pixel.x, pixel.y, pixel.color.toAwtColor());
			}
		}
		// slow case
		else {
			Color toSet = null;
			for (int x = 0; x != width; x++)
				for (int y = 0; y != height; y++){
					if ((toSet = pixelColorAt(x, y, width, height)) != null)
						img.setRGB(x, y, toSet.toAwtColor());
				}
		}
	}
}
