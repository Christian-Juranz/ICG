/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.app;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;

/**
 * Encapsulate the transfer of (compound) float values from the OpenGL
 * application to an GLSL shader uniform variable.
 */
public abstract class Uniform {

  protected final int location;

  /**
   * Create a new named uniform binding for the specified shader program.
   * 
   * @param program
   *          The shader program that this uniform is bound to.
   * @param name
   *          The name of the uniform varibale as seen in the shader program.
   */
  public Uniform(int program, String name) {
    location = glGetUniformLocation(program, name);
  }
}
