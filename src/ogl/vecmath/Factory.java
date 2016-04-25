/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.vecmath;

public interface Factory {
  
  public Vector vector(float nx, float ny, float nz);
 
  public abstract Vector xAxis();
    
  public abstract Vector yAxis();
    
  public abstract Vector zAxis();
    
  public abstract int vectorSize(); 
    

  public abstract Matrix identityMatrix();
 
  /**
   * Construct a new matrix given 16 elements
   * 
   * @param m00, m01, ..., m32, m33
   *          The 16 elements
   * @return The matrix.
   */  
  public abstract Matrix matrix(float m00, float m01, float m02, float m03, float m10,
      float m11, float m12, float m13, float m20, float m21, float m22,
      float m23, float m30, float m31, float m32, float m33); 
  /**
   * Construct a new matrix given an array of elements
   * 
   * @param elements
   *          The array of elements
   * @return The matrix.
   */ 
  public abstract Matrix matrix(float[] elements);
  /**
   * Construct a new matrix given three base vectors
   * 
   * @param b0
   *          First base vector
   * @param b1
   *          Second base vector
   * @param b2
   *          Third base vector
   * @return The matrix.
   */
  public abstract Matrix matrix(Vector b0, Vector b1, Vector b2);
 
  /**
   * Construct a new matrix that represents a translation.
   * 
   * @param t
   *          The translation vector.
   * @return The translation matrix.
   */
  public abstract Matrix translationMatrix(Vector t);
  /**
   * Construct a new matrix that represents a translation.
   * 
   * @param x, y, z
   *          The translation values.
   * @return The translation matrix.
   */ 
  public abstract Matrix translationMatrix(float x, float y, float z);
  /**
   * Construct a new matrix that represents a rotation.
   * 
   * @param axis
   *          The rotation axis.
   * @param angle
   *          The angle of rotaion in degree.
   * @return The rotation matrix.
   */
  public abstract Matrix rotationMatrix(Vector axis, float angle);
  /**
   * Construct a new matrix that represents a rotation.
   * 
   * @param x
   *          The rotation axis' x component.
   * @param y
   *          The rotation axis' y component.
   * @param z
   *          The rotation axis' z component.
   * @param angle
   *          The angle of rotaion in degree.
   * @return The rotation matrix.
   */
  public abstract Matrix rotationMatrix(float ax, float ay, float az, float angle); 
  /**
   * Construct a new matrix that represents a scale transformation.
   * 
   * @param s
   *          The three scale factors.
   * @return The scale matrix.
   */
  public abstract Matrix scaleMatrix(Vector s); 
  /**
   * Construct a new matrix that represents a scale transformation.
   * 
   * @param x
   *          The value in x direction.
   * @param y
   *          The value in y direction.
   * @param z
   *          The value in z direction.
   * @return The scale matrix.
   */
  public abstract Matrix scaleMatrix(float x, float y, float z); 
  /**
   * Construct a new matrix that represents a 'lookat' transformation. The
   * result is consistent with the OpenGL function <code>gluLookAt()</code>. The
   * result ist the <b>inverse</b> of the camera transformation K. It tranforms
   * form world space into camera space.
   * 
   * @param eye
   *          The eye point.
   * @param center
   *          The lookat point.
   * @param up
   *          The up vector.
   * @return The rotation matrix.
   */
  public abstract Matrix lookatMatrix(Vector eye, Vector center, Vector up);
  /**
   * Construct a new matrix that represents a projection normalization transformation. The
   * result is consistent with the OpenGL function <code>glFrustum(<params>)</code>. The
   * result is a matrix which maps into the canonical view volume for arbitrary frustra.
   * 
   * @param left
   *          Camera-space left value of lower lower near point.  
   * @param right
   *          Camera-space right value of upper right far point.  
   * @param bottom
   *          Camera-space bottom value of lower lower near point. 
   * @param top
   *          Camera-space top value of upper right far point. 
   * @param near
   *          Camera-space near value of lower lower near point. 
   * @param far
   *          Camera-space far value of upper right far point. 
   * @return The rotation matrix.
   */
  public abstract Matrix frustumMatrix(float left, float right, float bottom,
      float top, float zNear, float zFar);

  /**
   * Construct a new matrix that represents a projection normalization transformation. The
   * result is consistent with the OpenGL function <code>glPerspective(<params>)</code>. The
   * result is a matrix which maps into the canonical view volume for symmetric frustra.
   * 
   * @param fovy
   *          Field of view in y-direction  
   * @param aspect
   *          Aspect ratio between width and height.  
   * @param near
   *          Camera-space distance to near plane. 
   * @param far
   *          Camera-space distance to far plane. 
   * @return The rotation matrix.
   */
  public abstract Matrix perspectiveMatrix(float fovy, float aspect, float zNear,
      float zFar); 
  
  /**
   * Get a new color object.
   * 
   * @param r
   *          red component 
   * @param g
   *          green component
   * @param b
   *          blue component
   * 
   * @return The object.
   */
  public abstract Color color(float r, float g, float b);
  /**
   * Get the size (number of floats) of objects of type color.
   * 
   * @return The number of floats of color objects.
   */
  public abstract int colorSize();
  
  
}


