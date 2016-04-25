/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.app;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Asserted utility functions.
 */
public final class Util {

  /**
   * Check if a program linkage produced any errors. If it did, a
   * <code>RuntimeException</code> is thrown.
   * 
   * @param gl
   *          The GL object.
   * @param program
   *          The program that has been linked.
   */
  public static void checkLinkage(int program) {
    int linked = glGetProgrami(program, GL_LINK_STATUS);
    if (linked == GL_FALSE) {
      int logLength = glGetProgrami(program, GL_INFO_LOG_LENGTH);
      String log = glGetProgramInfoLog(program, logLength);

      glDeleteProgram(program);
      throw new RuntimeException("Error linking GLSL program:\n" + log);
    }
  }

  /**
   * Check if a shader compilation produced any errors. If it did, a
   * <code>RuntimeException</code> is thrown.
   * 
   * @param gl
   *          The OpenGL object.
   * @param shader
   *          The shader that has been compiled.
   */
  public static void checkCompilation(int shader) {
    int compiled = glGetShaderi(shader, GL_COMPILE_STATUS);
    if (compiled == GL_FALSE) {
      int logLength = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
      String log = glGetShaderInfoLog(shader, logLength);

      glDeleteShader(shader);
      throw new RuntimeException("Error compiling GLSL shader:\n" + log);
    }
  }

  /**
   * Read a file and return its contents as a string.
   * 
   * @param f
   *          The file to read.
   * @return The contents of the file as a string.
   */
  public static String[] fileToString(File f) {
    try {
      StringBuffer buff = new StringBuffer();
      BufferedReader in = new BufferedReader(new FileReader(f));
      String line = null;
      while ((line = in.readLine()) != null) {
        buff.append(line);
        buff.append("\n");
      }
      in.close();
      return new String[] { buff.toString() };
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
