/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.app;

/**
 * Use a <code>StopWatch</code> to measure the passing of time and along the way
 * determine the current frame rate.
 */
public final class StopWatch {
  private long last = System.nanoTime();
  private float time = 0;
  private long frames = 0;

  /**
   * The number of frames currently rendered per second.
   */
  public float fps = 0;

  /**
   * Reset internal time keeping state.
   */
  public void reset() {
    last = System.nanoTime();
    time = 0;
    frames = 0;
  }

  /**
   * Determine the time that passed since the last call to this function. On the
   * first call, the time since instance construction is returned.
   * 
   * @return The elapsed time in seconds.
   */
  public float elapsed() {
    long now = System.nanoTime();
    float elapsed = (now - last) * 1e-9f;
    time += elapsed;
    frames += 1;
    last = now;

    if (time >= 1f) {
      fps = frames / time;
      time = 0;
      frames = 0;
    }

    return elapsed;
  }
}
