package icg.rendering;

import java.awt.image.BufferedImage;

import ogl.vecmath.*;


public class RotatingTriangle  extends RotatingTriangleBase{
	/**
	 * the vertices of this triangle
	 */
	protected Vector p1, p2, p3;

	/**
	 * constructor setting the vertices of this triangle
	 * @param p1 vertex 1
	 * @param p2 vertex 2
	 * @param p3 vertex 3
	 */
	public RotatingTriangle(Vector p1, Vector p2, Vector p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	/**
	 * provide a rotation axis
	 * @return a rotation axis
	 */
	public Vector getRotationAxis() {
		return MyMathFactory.vector(0, 0, -1);
	}
	
	/**
	 * calculates the center of this triangle
	 * @return the center of this triangle
	 */
	public Vector getCenter() {
		return p1.add(p2).add(p3).mult(1.0f/3.0f);
	}

	/**
	 * apply the given transform to the triangle
	 * @param m the transform to be applied
	 */
	public void transform(Matrix m) {
		p1 = m.transformPoint(p1);
		p2 = m.transformPoint(p2);
		p3 = m.transformPoint(p3);
	}

	/**
	 * fills the given image
	 * @param img the image to be filled	  
	 */
	public void fillImage(BufferedImage img) {
		Line[] lines = new Line[]{new LineImpl(p1, p2), new LineImpl(p2, p3), new LineImpl(p3, p1)};
		for (Line l : lines)
			l.fillImage(img);
	}

	/**
	 * specifies the background color of the image
	 * @return the background color of the image
	 */
	public Color getBackgroundColor() {
		return MyMathFactory.color(0, 0, 0);		
	}
}
