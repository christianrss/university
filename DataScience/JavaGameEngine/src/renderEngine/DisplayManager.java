package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.ContextAttribs;

public class DisplayManager
{
	private static final int DISPLAY_WIDTH 	= 800;
	private static final int DISPLAY_HEIGHT = 600;
	private static final int FPS_CAP        = 120;
	
	public static void createDisplay()
	{
		ContextAttribs attribs = new ContextAttribs(3,2);
		attribs.withForwardCompatible(true);
		attribs.withProfileCore(true);
		
		try {
			Display.setDisplayMode( new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Christian Games");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
	}
	
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
}
