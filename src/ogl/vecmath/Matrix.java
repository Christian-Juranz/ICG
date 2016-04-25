/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.vecmath;

import java.nio.FloatBuffer;

/**
 * A simple 4x4 matrix class using float getValues(). Matrices are non-mutable and
 * can be passed around as getValues(). The matrix is stored in one float array in
 * column-major format.
 * 
 * @author henrik, marc
 */
public interface Matrix {

  /**
   * Get elements from the matrix.
   * 
   * @return A vector of floats of the values.
   */
  public abstract float[] getValues(); 
  /**
   * Set element of the matrix.
   * 
   * @param vals
   *            The vector of new vals
   * 
   * @return A vector of floats of the values.
   */
  public abstract void setValues(float[] vals); 
 
  
  /**
   * Get one value of one element from the matrix.
   * 
   * @param c
   *          The column from which to get the value.
   * @param r
   *          The row from which to get the value.
   * @return The value at position (c, r).
   */
  public abstract float get(int c, int r);

  /**
   * Calculate the product of two matrices.
   * 
   * @param m
   *          The second matrix.
   * @return The product.
   */

  public abstract Matrix mult(Matrix m);

  public abstract Matrix multSlow(Matrix m);

  /**
   * Transform a point by the current matrix. The homogenous coordinate is
   * assumed to be 1.0.
   * 
   * @param v
   *          The point.
   * @return The transformed point.
   */
  public abstract Vector transformPoint(Vector v);

  /**
   * Transform a direction by the current matrix. The homogenous coordinate is
   * assumed to be 0.0.
   * 
   * @param v
   *          The direction vector.
   * @return The transformed point.
   */
  public abstract Vector transformDirection(Vector v);

  /**
   * Transform a normal by the current matrix. The homogenous coordinate is
   * assumed to be 0.0. The matrix is assumed to be orthonormal with a uniform
   * scaling component at most (ie. a rigid-body transformation).
   * 
   * @param v
   *          The normal.
   * @return The transformed point.
   */
  public abstract Vector transformNormal(Vector v);

  public abstract Matrix transpose();

  /**
   * Calculate the inverse matrix. The matrix is assumed to be orthonormal.
   * 
   * @return The inverse matrix.
   */
  public abstract Matrix invertRigid();

  /**
   * Calculate the full inverse of this matrix. This takes some time.
   * 
   * @return The inverse matrix.
   */
  public abstract Matrix invertFull();

  /**
   * Get the array of 16 matrix values. This returns the internal
   * represenatation of the matrix in OpenGL compatible column-major format.
   * 
   * @return The array of matrix values.
   */
  public abstract float[] asArray();

  public abstract FloatBuffer asBuffer();

  public abstract void fillBuffer(FloatBuffer buf);

  /**
   * Construct a new Matrix containing only the rotation.
   * 
   * @return The newly constructed matrix.
   */
  public abstract Matrix getRotation();

  /**
   * Construct a new matrix containing only the translation.
   * 
   * @return The newly constructed matrix.
   */
  public abstract Matrix getTranslation();

  /**
   * Construct a new vector containing the translational elements.
   * 
   * @return The newly constructed vector.
   */
  public abstract Vector getPosition();

  /*
   * @see java.lang.Object#toString()
   */
  public abstract String toString();

  public abstract boolean equals(Object o);

  public abstract boolean equals(Matrix m, float epsilon);

}