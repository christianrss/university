package engineTester;

import org.lwjgl.opengl.Display;

import models.RawModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;

public class MainGameLoop {
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		Loader 		 loader		= new Loader();
		Renderer 	 renderer 	= new Renderer();
		StaticShader shader		= new StaticShader();
		
		/* OpenGL expects vertices to be defined counter clockwise by default
		float[] vertices = {
			// Left bottom triangle
			-0.5f,  0.5f, 0f,
			-0.5f, -0.5f, 0f,
			 0.5f, -0.5f, 0f,
			 // Right top triangle
			 0.5f, -0.5f, 0f,
			 0.5f,  0.5f, 0f,
			-0.5f,  0.5f, 0f
		};*/
		
		float[] vertices = {
			-0.5f,  0.5f, 0, //V0
			-0.5f, -0.5f, 0, //V1
			 0.5f, -0.5f, 0, //V2
			 0.5f,  0.5f, 0 //V3
		};
		
		int[] indices = {
			0,1,3, // Top left triangle (V0,V1,V3)
			3,1,2  // Bottom right triangle (V3,V1,V2)
		};
		
		RawModel model = loader.loadToVAO(vertices,indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("miranha"));
		
		TexturedModel texturedModel = new TexturedModel(model,texture);
		
		
		while(!Display.isCloseRequested())
		{
			renderer.prepare();
			shader.start();
			renderer.render(texturedModel);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
