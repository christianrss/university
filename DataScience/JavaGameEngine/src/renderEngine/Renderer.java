package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;

import models.TexturedModel;

public class Renderer {
	
	public void prepare()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(1, 0, 0, 1);
	}
	
	public void render(TexturedModel texturedModel)
	{
		RawModel model = texturedModel.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertextCount());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertextCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
}
