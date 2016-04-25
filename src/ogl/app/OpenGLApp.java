/*******************************************************************************
 * Copyright (c) 2013 Henrik Tramberend, Marc Latoschik.
 * All rights reserved.
 *******************************************************************************/
package ogl.app;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
/**
 * A simple framework for OpenGL applications.
 */
public final class OpenGLApp {
	// keep these for native library connection
	@SuppressWarnings("unused")
	private GLFWKeyCallback keyCallback;
	@SuppressWarnings("unused")
	private GLFWCursorPosCallback cursorCallback;
	@SuppressWarnings("unused")
	private GLFWMouseButtonCallback mouseButtonCallback;
	@SuppressWarnings("unused")
	private GLFWWindowSizeCallback windowSizeCallback;

	/**
	 * Create an OpenGL application with one window. The application behavior is
	 * controlled by the <code>App</code> object.
	 * 
	 * @param title
	 *          The string that is displayed in the title bar of the application
	 *          window.
	 * @param application
	 *          The application object.
	 */	 
	public OpenGLApp(String title, App application) {
		this.title = title;
		this.application = application;

		System.out.println("LWJGL version " + Version.getVersion() + " running on "
				+ System.getProperty("os.name") + " version "
				+ System.getProperty("os.version") + ".");
	}


	public long createWindow(int major, int minor){
		// Configure our window
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GL_TRUE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
		
		if (major > 0){
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, major); // select GL version > major.x
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minor); // select GL version > x.minor
		}		
		if (major >= 3)
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE); 
		if ( major > 3 || (major == 3 && minor >= 2))
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		return glfwCreateWindow(width, height, title, NULL, NULL);
	}

	/**
	 * Start the application. The window is opened and the main loop is entered.
	 * This call does not return until the window is closed or an exception was
	 * caught.
	 */
	public void start() {
		GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
		try{
			// Setup an error callback. The default implementation
			// will print the error message in System.err.
			glfwSetErrorCallback(errorCallback);

			// Initialize GLFW. Most GLFW functions will not work before doing this.
			if ( glfwInit() != GL11.GL_TRUE )
				throw new IllegalStateException("Unable to initialize GLFW");

			// Configure our window
			glfwSetErrorCallback(null);
			for (Tuple versionToTest : glVersions){
				if (window == NULL)
					window = createWindow(versionToTest.major, versionToTest.minor);				
			}			
			glfwSetErrorCallback(errorCallback);
			
			if ( window == NULL )
				throw new RuntimeException("Failed to create the GLFW window");

			// Center our window
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window,
					(vidmode.width() - width) / 2,
					(vidmode.height() - height) / 2
					);

			// Make the OpenGL context current
			glfwMakeContextCurrent(window);
			createCapabilities();

			// Enable v-sync
			glfwSwapInterval(1);

			// Make the window visible
			glfwShowWindow(window);

			// update window title to include the used OpenGL version
			String glVersion = glGetString(GL_VERSION);
			System.out.println("Using OpenGL " + glVersion);

			// remove long contents for window title
			if (glVersion.contains(" "))
				OpenGLApp.setGLVersion(glVersion.split(" ")[0]);

			glfwSetWindowTitle(window, title += " using OpenGL " + OpenGLApp.getGLVersion());

			// store reference to this for use in callbacks
			final OpenGLApp self = this;

			application.init();			
			input = new Input();

			glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
				public void invoke(long window, int key, int scancode, int action, int mods) {
					if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
						glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
					else 
						input.updateKey(key, scancode, action, mods);
				}
			});

			glfwSetCursorPosCallback(window, cursorCallback = new GLFWCursorPosCallback() {
				public void invoke(long window, double x, double y) {
					input.updateCursorPos(x, y);					
				}
			});

			glfwSetMouseButtonCallback(window, mouseButtonCallback = new GLFWMouseButtonCallback(){
				public void invoke(long window, int button, int action, int mods) {
					input.updateMouseBtn(button, action, mods);					
				}				
			});

			glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback() {
				public void invoke(long window, int width, int height) {
					if (window == self.window){
						self.width = width;
						self.height = height;
						input.setWindowSize(width, height);
					}					
				}
			});

			time.reset();
			// Run the rendering loop until the user has attempted to close
			// the window or has pressed the ESCAPE key.
			while ( glfwWindowShouldClose(window) == GL_FALSE ) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

				application.simulate(time.elapsed(), input);
				application.display(width, height);

				glfwSwapBuffers(window); // swap the color buffers
				// Poll for window events. The key callback above will only be
				// invoked during this call.
				glfwPollEvents();
			}
		} finally {
			// user defined cleanup function
			application.cleanUp();
			// Terminate GLFW and release the GLFWerrorfun			
			glfwTerminate();
			errorCallback.release();
		}
	} 


	private long window = NULL;
	private int width = 600;
	private int height = 600;

	private StopWatch time = new StopWatch();
	private Input input;
	private App application;
	private String title;
	
	// OpenGL version management
	private static Tuple[] glVersions = new Tuple[]{
		new Tuple(4,5), new Tuple(4,4), new Tuple(4,3), new Tuple(4,2), new Tuple(4,1), new Tuple(4,0),
		new Tuple(3,3), new Tuple(3,2), new Tuple(3,1), new Tuple(3,0),
		new Tuple(2,1), new Tuple(2,0),
		new Tuple(0,0)
	};
		
	private static String glVersion = "unknown";
	private static int glMajor = -1, glMinor = -1;
		
	private static void setGLVersion(String versionString){
		glVersion = versionString;
		glMajor = glVersion.contains(".") ? Integer.parseInt(glVersion.split("\\.")[0]) : -1;
		glMinor = glVersion.contains(".") ? Integer.parseInt(glVersion.split("\\.")[1]) : -1;
	}
	
	public static String getGLVersion(){
		return glVersion;
	}
	
	public static int getGLMajor(){
		return glMajor;
	}
	
	public static int getGLMinor(){
		return glMinor;
	}
}

class Tuple{
	final int minor, major;
	
	public Tuple(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}
}
