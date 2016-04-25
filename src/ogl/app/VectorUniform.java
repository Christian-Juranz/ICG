/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.app;

import static org.lwjgl.opengl.GL20.glUniform3f;
import ogl.vecmath.Vector;

/**
 * Encapsulate the transfer of vector values from the OpenGL application to an
 * GLSL shader uniform variable.
 */
public class VectorUniform extends Uniform {

  /**
   * Create a new named uniform binding for the specified shader program.
   * 
   * @param program
   *          The shader program that this uniform is bound to.
   * @param name
   *          The name of the uniform varibale as seen in the shader program.
   */
  public VectorUniform(int program, String name) {
    super(program, name);
  }

  /**
   * Transfer a new value to the shader uniform variable.
   * 
   * @param v
   *          The new value for the uniform variable.
   */
  public void set(Vector v) {
    glUniform3f(location, v.x(), v.y(), v.z());
  }
}
