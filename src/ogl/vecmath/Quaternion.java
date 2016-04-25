/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.vecmath;

/**
 * A 4-element quaternion represented by float precision floating point x,y,z,w
 * coordinates. The quaternion is always normalized.
 */
public interface Quaternion {

  /**
   * Accessor method.
   * 
   * @return The component.
   */
  public abstract float x();
  /**
   * Accessor method.
   * 
   * @return The component.
   */
  public abstract float y();
  /**
   * Accessor method.
   * 
   * @return The component.
   */
  public abstract float z();
  /**
   * Accessor method.
   * 
   * @return The component.
   */
  public abstract float w();
  /**
   * Negate the quaternion.
   * 
   * @return The negated quaternion.
   */
  public abstract Quaternion negate();

  /**
   * Conjugate the quaternion.
   * 
   * @return The conjugated quaternion.
   */
  public abstract Quaternion conjugate();

  /**
   * Calculate the product of two quaternions.
   * 
   * @param q
   *          The other quaternion.
   * @return The resulting quaternion.
   */
  public abstract Quaternion mul(Quaternion q);

  /**
   * Invert the quaternion.
   * 
   * @return The inverted quaternion.
   */
  public abstract Quaternion inverse();

  /**
   * Normalizes the quaternion.
   * 
   * @return The normalized quaternion.
   */
  public abstract Quaternion normalize();

  /**
   * Perform a great circle (spherical linear) interpolation between this
   * quaternion and the quaternion parameter.
   * 
   * @param q
   *          The other quaternion.
   * @param alpha
   *          The interpolation parameter from the interval [0, 1].
   * @return The interpolated quaternion.
   */
  public abstract Quaternion interpolate(Quaternion q, float alpha);

}