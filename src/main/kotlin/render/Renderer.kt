package render

import models.RawModel
import models.TexturedModel
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*

/**
 * Render utility class for preparing a frame and rendering a model
 */
class Renderer {

    /**
     * Prepares a new frame by clearing it to a solid colour
     */
    fun prepare(){
        glClearColor(0.0f,0.0f,0.0f,0.0f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    /**
     * Renders a model to the display
     * @param model to draw
     */
    fun render(texModel: TexturedModel){
        val model = texModel.model
        glBindVertexArray(model.vaoID)
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, texModel.tex.texID)
        glDrawElements(GL_TRIANGLES, model.vertexCount, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glBindVertexArray(0)
    }

}