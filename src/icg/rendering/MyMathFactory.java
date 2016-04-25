package icg.rendering;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import ogl.vecmath.*;

public class MyMathFactory {
	/**
	 * returns a matrix that translates a point by the given vector
	 * @param translation the translation vector
	 * @return a matrix that translates a point by the given vector
	 */
	public static Matrix translationMatrix(Vector translation){
		return vecmath.translationMatrix(translation);
	}
	
	/**
	 * returns a rotation matrix that represents the rotation defined by the given axis and angle
	 * @param axis the rotation axis
	 * @param angleInDegrees the rotation angle in degrees
	 * @return a rotation matrix that represents the rotation defined by the given axis and angle
	 */
	public static Matrix rotationMatrix(Vector axis, float angleInDegrees){
		return vecmath.rotationMatrix(axis, angleInDegrees);
	}
	
	/**
	 * creates a new vector
	 * @param x the vector's x component
	 * @param y the vector's y component
	 * @param z the vector's z component
	 * @return the created vector
	 */
	public static Vector vector(float x, float y, float z){
		return vecmath.vector(x, y, z);
	}
	
	/**
	 * creates a new color
	 * @param r the color's red component
	 * @param g the color's green component
	 * @param b the color's blue component
	 * @return the created vector
	 */
	public static Color color(float r, float g, float b){
		return vecmath.color(r, g, b);
	}
}
