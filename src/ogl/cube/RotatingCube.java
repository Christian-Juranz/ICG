/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.cube;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.glfw.GLFW.*;
import ogl.vecmath.Color;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import ogl.app.*;
//Select the factory we want to use.
import static ogl.vecmathimp.FactoryDefault.vecmath;

// A simple but complete OpenGL application.
public class RotatingCube implements App {
	static public void main(String[] args) {
		new OpenGLApp("Rotating Cube", new RotatingCube()).start();
	}

	@Override
	public void init() {
		// Set background color to black.
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Enable depth testing.
		glEnable(GL_DEPTH_TEST);

		// create Vertex Array Object (VAO) from the cube's vertices
		try{
			cube = new VertexArrayObject(cubeVertices);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		// Create and compile the vertex shader.
		int vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, getVsSource());
		glCompileShader(vs);
		Util.checkCompilation(vs);

		// Create and compile the fragment shader.
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, getFsSource());
		glCompileShader(fs);
		Util.checkCompilation(fs);

		// Create the shader program and link vertex and fragment shader
		// together.
		program = glCreateProgram();
		glAttachShader(program, vs);
		glAttachShader(program, fs);

		// Bind the vertex attribute data locations for this shader program. The
		// shader expects to get vertex and color data from the mesh. This needs to
		// be done *before* linking the program.
		glBindAttribLocation(program, VertexArrayObject.vertexAttribIdx, "vertex");
		glBindAttribLocation(program, VertexArrayObject.colorAttribIdx, "color");

		// Link the shader program.
		glLinkProgram(program);
		Util.checkLinkage(program);

		// Bind the matrix uniforms to locations on this shader program. This needs
		// to be done *after* linking the program.
		modelMatrixUniform = new MatrixUniform(program, "modelMatrix");
		viewMatrixUniform = new MatrixUniform(program, "viewMatrix");
		projectionMatrixUniform = new MatrixUniform(program, "projectionMatrix");
	}

	/*
	 * (non-Javadoc)
	 * @see cg2.cube.App#simulate(float, cg2.cube.Input)
	 */
	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(GLFW_KEY_R))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 90 * elapsed;
	}

	/*
	 * (non-Javadoc)
	 * @see cg2.cube.App#display(int, int, javax.media.opengl.GL2ES2)
	 */
	@Override
	public void display(int width, int height) {
		// Adjust the the viewport to the actual window size. This makes the
		// rendered image fill the entire window.
		glViewport(0, 0, width, height);

		// Clear all buffers.
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Assemble the transformation matrix that will be applied to all
		// vertices in the vertex shader.
		float aspect = (float) width / (float) height;

		// The perspective projection. Camera space to NDC.
		Matrix projectionMatrix = vecmath
				.perspectiveMatrix(60f, aspect, 0.1f, 100f);

		// The inverse camera transformation. World space to camera space.
		Matrix viewMatrix = vecmath.lookatMatrix(vecmath.vector(0f, 0f, 3f),
				vecmath.vector(0f, 0f, 0f), vecmath.vector(0f, 1f, 0f));

		// The modeling transformation. Object space to world space.
		Matrix modelMatrix = vecmath.rotationMatrix(vecmath.vector(1, 1, 1), angle);

		// Activate the shader program and set the transformation matrices to the
		// uniform variables.
		glUseProgram(program);
		modelMatrixUniform.set(modelMatrix);
		viewMatrixUniform.set(viewMatrix);
		projectionMatrixUniform.set(projectionMatrix);

		// bind the cube's VAO
		cube.bind();

		// draw the cube
		cube.draw();

		// unbind the cube's VAO
		cube.unbind();
	}

	/*
	 * (non-Javadoc)
	 * @see ogl.app.App#cleanUp()
	 */
	public void cleanUp(){
		if (cube != null)
			cube.cleanUp();
	}

	/************************
	 * Variable definitions *
	 ************************/

	// the Vertex Array Object which will represent the cube
	private VertexArrayObject cube = null;

	// The shader program.
	private int program;

	// The location of the "mvpMatrix" uniform variable.
	private MatrixUniform modelMatrixUniform;
	private MatrixUniform viewMatrixUniform;
	private MatrixUniform projectionMatrixUniform;

	// Width, depth and height of the cube divided by 2.
	float w2 = 0.5f;
	float h2 = 0.5f;
	float d2 = 0.5f;

	// select correct shader
	private String[] getVsSource() {
		if (OpenGLApp.getGLMajor() >= 3)
			return concatArrays(getGLSLVersionString(), vsHeader30, vsSource); 
		else 
			return concatArrays(vsHeader21, vsSource);
	}
	
	private String[] getGLSLVersionString(){
		return new String[]{
			OpenGLApp.getGLMajor() == 3 && OpenGLApp.getGLMinor() == 0 ? "#version 130\n" : "#version 140\n" 
		};
	}
	
	// The vertex program source code.
	// first lines of the vertex shader for OpenGL Versions < 3.0 (first line is empty for simplicity)
	private String[] vsHeader21 = {
			"attribute vec3 vertex;",
			"attribute vec3 color;",
			"varying vec3 fcolor;",
	};
	
	// first lines of the vertex shader for OpenGL Versions >= 3.0
	private String[] vsHeader30 = {
			"in vec3 vertex;",
			"in vec3 color;",
			"out vec3 fcolor;",
	};
	
	// rest of the vertex shader (gl version independent)
	private String[] vsSource = {
			"uniform mat4 modelMatrix;",
			"uniform mat4 viewMatrix;",
			"uniform mat4 projectionMatrix;",

			"void main() {",
			"  fcolor = color;",
			"  gl_Position = projectionMatrix * viewMatrix * modelMatrix *  vec4(vertex, 1.0);",
			"}" 
	};

	// The fragment program source code.
	// selects the correct shader version
	private String[] getFsSource() {
		return OpenGLApp.getGLMajor() >= 3 ? fsSourceGL30 : fsSourceGL21;
	}
	
	// fragment shader for OpenGL 3.0 and above
	private String[] fsSourceGL30 = { 
			"#version 140\n",
			
			"in vec3 fcolor;",
			"out vec4 fragColor;",
			
			"void main() {", 
			"  fragColor = vec4(fcolor, 1.0);", 
			"}"
	};

	// fragment shader for OpenGL 2.1 (and below)
	private String[] fsSourceGL21 = {
			"varying vec3 fcolor;",

			"void main() {", 
			"  gl_FragColor = vec4(fcolor, 1.0);", 
			"}" 
	};


	// Make construction of vertices easy on the eyes.
	private Vertex v(Vector p, Color c) {
		return new Vertex(p, c);
	}

	// Make construction of vectors easy on the eyes.
	private Vector vec(float x, float y, float z) {
		return vecmath.vector(x, y, z);
	}

	// Make construction of colors easy on the eyes.
	private Color col(float r, float g, float b) {
		return vecmath.color(r, g, b);
	}

	//
	//     6 ------- 7 
	//   / |       / | 
	//  3 ------- 2  | 
	//  |  |      |  | 
	//  |  5 -----|- 4 
	//  | /       | / 
	//  0 ------- 1
	//

	// Initialize the rotation angle of the cube.
	private float angle = 0;

	// The positions of the cube vertices.
	private Vector[] p = { 
			vec(-w2, -h2, d2), 
			vec(w2, -h2, d2),
			vec(w2, h2, d2), 
			vec(-w2, h2, d2), 
			vec(w2, -h2, -d2), 
			vec(-w2, -h2, -d2),
			vec(-w2, h2, -d2), 
			vec(w2, h2, -d2) 
	};

	// The colors of the cube vertices.
	private Color[] c = { 
			col(0, 0, 0), 
			col(1, 0, 0), 
			col(1, 1, 0), 
			col(0, 1, 0),
			col(1, 0, 1), 
			col(0, 0, 1), 
			col(0, 1, 1), 
			col(1, 1, 1) 
	};

	// Vertices combine position and color information. Every four vertices define
	// one side of the cube.
	private Vertex[] cubeVertices = {
			// front 1
			v(p[0], c[0]), v(p[1], c[1]), v(p[2], c[2]),
			// front 2
			v(p[2], c[2]), v(p[3], c[3]), v(p[0], c[0]), 
			// back 1
			v(p[4], c[4]), v(p[5], c[5]), v(p[6], c[6]), 
			// back 2
			v(p[6], c[6]), v(p[7], c[7]), v(p[4], c[4]),
			// right 1
			v(p[1], c[1]), v(p[4], c[4]), v(p[7], c[7]),
			// right 2
			v(p[7], c[7]), v(p[2], c[2]), v(p[1], c[1]),
			// top 1
			v(p[3], c[3]), v(p[2], c[2]), v(p[7], c[7]),
			//top 2
			v(p[7], c[7]), v(p[6], c[6]), v(p[3], c[3]),
			// left 1
			v(p[5], c[5]), v(p[0], c[0]), v(p[3], c[3]), 
			// left 2
			v(p[3], c[3]), v(p[6], c[6]), v(p[5], c[5]),
			// bottom 1
			v(p[5], c[5]), v(p[4], c[4]), v(p[1], c[1]),
			// bottom 2
			v(p[1], c[1]), v(p[0], c[0]),  v(p[5], c[5])
	};
	
	// concatenate two arrays
	private String[] concatArrays(String[]... arrs){
		int len = 0, pos = 0;
		// find combined length
		for (String[] arr : arrs) len += arr.length;
		// create empty return array
		String[] retVal = new String[len];
		
		// copy all arrays into return value
		for (String[] arr : arrs){
			System.arraycopy(arr, 0, retVal, pos, arr.length);
			pos += arr.length;
		}
		return retVal;
	}
}



