/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.app;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/**
 * A thin wrapper for OpenGL texture images.
 * 
 */
public final class Texture {

  private ByteBuffer data;
  private int w;
  private int h;
  private int obj;

  /**
   * Create a new OpenGL texture. The image is loaded from file and transfered
   * to the GPU.
   * 
   * @param gl
   *          The OpenGL context object.
   * @param t
   *          The file containing the image.
   */
  public Texture(File t) {
    // Read the image from file (PNG, JPEG, BMP, GIF)
    BufferedImage bi = null;
    try {
      bi = ImageIO.read(t);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    // Get image size
    w = bi.getWidth();
    h = bi.getHeight();

    // Buffer for texture data
    data = BufferUtils.createByteBuffer(w * h * 4);

    // Convert pixel format
    for (int y = 0; y != h; y++) {
      for (int x = 0; x != w; x++) {
        int pp = bi.getRGB(x, y);
        byte a = (byte) ((pp & 0xff000000) >> 24);
        byte r = (byte) ((pp & 0x00ff0000) >> 16);
        byte g = (byte) ((pp & 0x0000ff00) >> 8);
        byte b = (byte) (pp & 0x000000ff);
        data.put((y * w + x) * 4 + 0, r);
        data.put((y * w + x) * 4 + 1, g);
        data.put((y * w + x) * 4 + 2, b);
        data.put((y * w + x) * 4 + 3, a);
      }
    }

    // Generate a new texture object id
    IntBuffer objs = BufferUtils.createIntBuffer(1);
    glGenTextures(objs);
    obj = objs.get(0);

    // Bind the texture object
    bind();

    // Set parameters
    glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
    glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

    glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
      GL11.GL_NEAREST);
    // GL2ES2.GL_LINEAR);
    glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
      GL11.GL_NEAREST);
    // GL2ES2.GL_LINEAR_MIPMAP_LINEAR);

    // Load the texture image
    glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA,
      GL11.GL_UNSIGNED_BYTE, data);

    // gl.glGenerateMipmap(GL11.GL_TEXTURE_2D);
  }

  /**
   * Bind the OpenGL texture object to the currently active texture unit.
   */
  public void bind() {
    glEnable(GL11.GL_TEXTURE_2D);
    glBindTexture(GL11.GL_TEXTURE_2D, obj);
  }

}