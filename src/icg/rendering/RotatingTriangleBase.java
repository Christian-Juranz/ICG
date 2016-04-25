package icg.rendering;

import java.awt.image.BufferedImage;

import ogl.vecmath.*;

abstract class RotatingTriangleBase {
	protected Matrix localTrafo = null;
	
	/**
	 * Multiplies every vertex of this Triangle with the given Matrix
	 * @param m the matrix to be applied to the points of this triangle  
	 */
	public abstract void transform(Matrix m);
	
	/**
	 * returns the center of the triangle
	 * @return a vector representing the center of the triangle
	 */
	public abstract Vector getCenter();
	
	/**
	 * returns the rotation axis
	 * @return the rotation axis
	 */
	public abstract Vector getRotationAxis();
	
	/**
	 * Renders the triangle into a given image. The default implementation calls the pixelColorAt method for each pixel.
	 * Override this function for improved performance
	 * @param img the image to render to
	 */
	public abstract void fillImage(BufferedImage img);
	
	/**
	 * returns the local transformation (which may be null)
	 * @return a matrix representing the local transformation
	 */
	public Matrix getTransform(){
		return localTrafo;
	}
	
	/**
	 *  updates the local transformation
	 */
	public void setTransform(Matrix m){
		localTrafo = m;
	}

	/**
	 * specifies the background color of the image
	 * @return the background color of the image
	 */
	public abstract Color getBackgroundColor(); 
}
