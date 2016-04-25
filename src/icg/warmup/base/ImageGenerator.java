/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package icg.warmup.base;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Generate an image to specification and save it to disk in PNG format.
 */
public class ImageGenerator {

	/**
	 * Instantiate a generator for the specified painter object with default size
	 * and filename.
	 * 
	 * @param painter
	 *          The painter object.
	 */
	public ImageGenerator(Painter painter) {
		generate(painter, 480, 320, "image.png");
	}

	/**
	 * Instantiate a generator for the specified painter object with supplied size
	 * and filename.
	 * 
	 * @param painter
	 *          The painter object.
	 * @param width
	 *          Image width in pixels.
	 * @param height
	 *          Image height in pixels.
	 * @param filename
	 *          File to store the image in.
	 */
	public ImageGenerator(Painter painter, int width, int height, String filename) {
		generate(painter, width, height, filename);
	}

	private void generate(Painter painter, int width, int height, String filename) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		// Set a color to all pixels.
		for (int x = 0; x != width; x++)
			for (int y = 0; y != height; y++)
				// TODO Flip Y. Next year.
				// image.setRGB(x, y, painter.pixelColorAt(x, height - y - 1, width,
				// height).toAwtColor());
				image.setRGB(x, y, painter.pixelColorAt(x, y, width, height)
						.toAwtColor());

		// Write the image to disk.
		try {
			File file = new File(filename);
			ImageIO.write(image, "png", file);
			// Try to open an appropriate image viewer.
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
