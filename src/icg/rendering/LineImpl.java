package icg.rendering;

import java.util.ArrayList;

import ogl.vecmath.*;

public class LineImpl extends Line {
	/**
	 * constructor, sets vertices of this line
	 * @param p1 the first vertex
	 * @param p2 the second vertex
	 */
	public LineImpl(Vector p1, Vector p2) {
		super(p1, p2);
	}


	/**
	 * @param fx the x coordinate of the pixel
	 * @param fy the y coordinate of the pixel
	 * @param width the width of the image
	 * @param height the height of the image
	 * @return a color if the pixel is on the line, null otherwise
	 */
	public Color pixelColorAt(int fx, int fy, int width, int height) {
		// TODO: implement me
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see icg.rendering.Line#getPixels(int, int)
	 */
	@Override
	public ArrayList<Pixel> getPixels(int width, int height){
		return null;
	}
}
