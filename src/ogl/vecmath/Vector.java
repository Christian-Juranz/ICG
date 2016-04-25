/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.vecmath;

import java.nio.FloatBuffer;

/**
 * Simple 3 component vector class. Vectors are non mutable and can be passed
 * around by value.
 * 
 * @author henrik, marc
 */
public interface Vector {

  /**
   * Accessors.
   * 
   * @return The components value.
   */
  public abstract float x();

  /**
   * Accessors.
   * 
   * @return The components value.
   */
  public abstract float y();

  /**
   * Accessors.
   * 
   * @return The components value.
   */
  public abstract float z();

  /**
   * Component-wise addition of two vectors.
   * 
   * @param v
   *          The second vector.
   * @return The sum.
   */
  public abstract Vector add(Vector v);

  /**
   * Subtract a vector from this vector.
   * 
   * @param v
   *          The second vector.
   * @return The difference.
   */
  public abstract Vector sub(Vector v);

  /**
   * Multiply this vector by a scalar.
   * 
   * @param s
   *          The scalar.
   * @return The scaled vector.
   */
  public abstract Vector mult(float s);

  /**
   * Componentwise multiplication of two vectors. This is not the dot product!
   * 
   * @param v
   *          The second vector.
   * @return The product.
   */
  public abstract Vector mult(Vector v);

  /**
   * Compute the length of this vector.
   * 
   * @return The lenght of this vector.
   */
  public abstract float length();

  /**
   * Normalize this vector.
   * 
   * @return The normalized vector.
   */
  public abstract Vector normalize();

  /**
   * Calculate the dot product of two vectors.
   * 
   * @param v
   *          The second vector.
   * @return The dot product.
   */
  public abstract float dot(Vector v);

  /**
   * Calculate the cross product of two vectors.
   * 
   * @param v
   *          The second vector.
   * @return The cross product.
   */
  public abstract Vector cross(Vector v);

  public abstract float[] asArray();

  public abstract FloatBuffer asBuffer();

  public abstract void fillBuffer(FloatBuffer buf);

  /*
   * @see java.lang.Object#toString()
   */
  public abstract String toString();

  public abstract boolean equals(final Object o);

  public abstract int compareTo(Vector o);

  public abstract int size();

}