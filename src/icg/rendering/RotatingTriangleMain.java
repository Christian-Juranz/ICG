package icg.rendering;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import ogl.vecmath.*;

public class RotatingTriangleMain extends JFrame{
	private static final long serialVersionUID = 1270967605637096622L;
	/**
	 * the image into which the triangle shall be rendered
	 */
	private BufferedImage imgToRender;

	/**
	 * current width and height of the image / window
	 */
	private int width = 200, height = 200;

	/**
	 * the triangle instance to be rendered
	 */
	private RotatingTriangleBase toRender;

	/**
	 * a timer
	 */
	private static long lastStep = System.currentTimeMillis();

	public static void main(String[] args) {
		// create an instance of this class
		RotatingTriangleMain instance = new RotatingTriangleMain();

		// initialize timer
		resetTimer();
		// loop until window is closed
		while(true){
			// update window/image bounds
			instance.updateWindow();
			// update triangle vertices
			instance.simulate( getElapsedMillis() );
			// store time of last r
			// render the triangle
			instance.render();

			// wait a (milli)second...
			sleep(1);
		}
	}

	/**
	 * initialize program
	 */
	public RotatingTriangleMain(){
		// initialize window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setVisible(true);

		// create new rotating triangle instance
		toRender = new RotatingTriangle(
				MyMathFactory.vector(150, 150, 0), 
				MyMathFactory.vector(250, 250, 0),
				MyMathFactory.vector(150, 250, 0)
				);		
	}

	/**
	 * updates the image size
	 */
	public void updateWindow(){
		// check for new window size
		width = getWidth();
		height = getHeight();

		// if either there is no image, or its dimension does not match the current window size, create a new image
		if (imgToRender == null || imgToRender.getWidth() != width || imgToRender.getHeight() != height)
			imgToRender = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = imgToRender.createGraphics();
		g.setColor(new Color(toRender.getBackgroundColor().toAwtColor()));
		g.fillRect(0, 0, width, height);
	}


	/**
	 * updates the vertices of the rendered triangle
	 * @param elapsedMilliseconds the time in milliseconds that has passed since the last call to this method
	 */
	public void simulate(long elapsedMilliseconds){
		// get matrix from the triangle
		Matrix currMat = toRender.getTransform();
		
		// use default implementation (i.e. rotate by 90 degrees per second) if retrieved transform is null
		if (currMat == null) {
			Vector center = toRender.getCenter();			
			currMat = MyMathFactory.translationMatrix(MyMathFactory.vector(width/2, height/2, 0));
			currMat = currMat.mult(MyMathFactory.rotationMatrix( toRender.getRotationAxis().normalize(), 90f * elapsedMilliseconds / 1000f));
			currMat = currMat.mult(MyMathFactory.translationMatrix(center.mult(-1)));
		}

		// apply the transformation to the triangle
		toRender.transform(currMat);
	}


	/**
	 * render the triangle
	 */
	public void render(){
		// call the fill image function of the triangle
		toRender.fillImage(imgToRender);
		// repaint this window
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		// directly draw the image, if available
		if (imgToRender != null)
			g.drawImage( imgToRender, 0, 0, null);
	}
	
	/********************
	 * Helper Functions *
	 ********************/
	
	/**
	 * reset the timer
	 */
	private static void resetTimer(){
		lastStep = System.currentTimeMillis();
	}

	/**
	 * @return the time in milliseconds since the method was invoked the last time
	 */
	private static long getElapsedMillis(){
		long retVal = System.currentTimeMillis() - lastStep;
		lastStep = System.currentTimeMillis();
		return retVal;		
	}

	/**
	 * sleep for some time (simply avoids annoying exception handling)
	 * @param time the time in milliseconds to sleep
	 */
	private static void sleep(long time){
		try {  Thread.sleep(time); } catch (InterruptedException e) { e.printStackTrace(); }
	}
}


