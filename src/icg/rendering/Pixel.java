package icg.rendering;

import ogl.vecmath.Color;

public class Pixel {
	final public int x, y;
	final public Color color;
	
	public Pixel(int x, int y, Color c) {
		this.color = c;
		this.x = x;
		this.y = y;
	}
}
