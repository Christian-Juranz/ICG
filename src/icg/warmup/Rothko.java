/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package icg.warmup;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import icg.warmup.base.ImageGenerator;
import icg.warmup.base.Painter;
import ogl.vecmath.Color;

/**
 * Paint images like Mark Rothko.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Mark_Rothko">Mark Rothko</a>, <a
 *      href="http://www.google.de/images?q=rothko">Images</a>
 */
public class Rothko implements Painter {

  public static void main(String[] args) {
    new ImageGenerator(new Rothko(1.0f, 0.3f, 0.0f), arg(args, 0, 600), arg(
      args, 1, 600), "rothko.png");
  }

  float red, green, blue;

  /**
   * Paint like Mark Rothko using the provided base color.
   * 
   * @param r
   *          The red component of the base color.
   * @param g
   *          The green component of the base color.
   * @param b
   *          The blue component of the base color.
   */
  public Rothko(float r, float g, float b) {
    red = r;
    green = g;
    blue = b;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cg2.warmup.Painter#pixelColorAt(int, int, int, int)
   */
  @Override
  public Color pixelColorAt(int x, int y, int width, int height) {
    float border = width * 0.05f;
    // Decide the pixel color based on position in the image.
    if (x < border || width - x < border || y < border || height - y < border) {
      // Border pixels.
      return vecmath.color(red * 0.3f, green * 0.3f, blue * 0.3f);
    } else if (y < height / 2) {
      // Pixels in the upper half of the image.
      return vecmath.color(red, green, blue);
    } else {
      // Pixels in the lower half of the image.
      return vecmath.color(red * 0.6f, green * 0.6f, blue * 0.6f);
    }
  }

  /**
   * Try to parse an integer argument from the command line. If that fails,
   * return a default value.
   * 
   * @param args
   *          The command line arguments.
   * @param i
   *          Index of the argument.
   * @param def
   *          Default value.
   * @return The arguments int value.
   */
  private static int arg(String[] args, int i, int def) {
    try {
      return Integer.parseInt(args[i]);
    } catch (Throwable t) {
      return def;
    }
  }
}
